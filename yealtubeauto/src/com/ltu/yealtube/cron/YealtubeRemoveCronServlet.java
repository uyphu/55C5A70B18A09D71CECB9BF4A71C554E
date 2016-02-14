package com.ltu.yealtube.cron;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.api.server.spi.response.CollectionResponse;
import com.ltu.yealtube.constants.Constant;
import com.ltu.yealtube.entity.Tube;
import com.ltu.yealtube.service.TubeService;

/**
 * The Class YealTubeCronServlet.
 * 
 * @author uyphu
 */
@SuppressWarnings("serial")
public class YealtubeRemoveCronServlet extends HttpServlet {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(YealtubeRemoveCronServlet.class);

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		try {
			logger.info("Remove Cron Job has been executed");
			TubeService tubeService = TubeService.getInstance();
			String cursor = null;
			do {
				CollectionResponse<Tube> tubes = tubeService.searchTubes("status >= ", 3, cursor, Constant.MAX_RECORDS);
				if (tubes != null && tubes.getItems().size() > 0) {
					for (Tube tube : tubes.getItems()) {
						Date createdAt = tube.getCreatedAt();
						Date modifiedAt = tube.getModifiedAt();
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(createdAt);
						calendar.add(Calendar.DAY_OF_YEAR, Constant.MAX_DAYS + 2);
						if (modifiedAt.after(calendar.getTime())) {
							if (Constant.UNSENT_STATUS == tube.getStatus() || Constant.SENT_STATUS == tube.getStatus()
									|| Constant.CANCELLED_STATUS == tube.getStatus()) {
								tubeService.deleteWithChildren(tube.getId());
							}
						} else {
							tubeService.update(tube);
						}
					}
					cursor = tubes.getNextPageToken();
				} else {
					cursor = null;
				}

			} while (cursor != null);

			logger.info("End Cron Job.");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex.getCause());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
