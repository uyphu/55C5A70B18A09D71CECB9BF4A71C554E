package com.ltu.yealtube.cron;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.api.server.spi.response.CollectionResponse;
import com.ltu.yealtube.constants.Constant;
import com.ltu.yealtube.entity.Tube;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.service.ReportService;
import com.ltu.yealtube.service.TubeService;
import com.ltu.yealtube.utils.YoutubeUtil;

@SuppressWarnings("serial")
public class ApproveCronServlet extends HttpServlet {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(ApproveCronServlet.class);

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ReportService reportService = ReportService.getInstance();
		try {
			logger.info("Statistics Cron Job has been executed");

			TubeService tubeService = TubeService.getInstance();

			String cursor = null;

			do {
				CollectionResponse<Tube> tubes = tubeService.searchTubes("status = ", Constant.APPROVED_STATUS, cursor,
						Constant.MAX_RECORDS);
				if (tubes != null && tubes.getItems().size() > 0) {
					for (Tube tube : tubes.getItems()) {
						// send tube to yealtube
						try {
							logger.debug("Send tube: " + tube.getId() + " with rating: " + String.valueOf(tube.getRating()));
							boolean flag = YoutubeUtil.sendTube(tube.getId(), String.valueOf(tube.getRating()));
							if (!flag) {
								tube.setStatus(Constant.UNSENT_STATUS);
								reportService.addReport(Constant.UNSENT_STATUS);
							} else {
								tube.setStatus(Constant.SENT_STATUS);
								reportService.addReport(Constant.SENT_STATUS);
							}
						} catch (CommonException e) {
							logger.error(e.getMessage(), e.getCause());
							tube.setStatus(Constant.UNSENT_STATUS);
							reportService.addReport(Constant.UNSENT_STATUS);
						}
						tubeService.update(tube);
					}
					cursor = tubes.getNextPageToken();
				} else {
					cursor = null;
				}

			} while (cursor != null);

			logger.info("End Cron Job.");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex.getCause());
			reportService.addReport(Constant.EXCEPTION_STATUS);
		}
	}
}
