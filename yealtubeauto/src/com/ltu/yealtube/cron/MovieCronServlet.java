package com.ltu.yealtube.cron;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ltu.yealtube.constants.Constant;
import com.ltu.yealtube.entity.Tube;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.service.ReportService;
import com.ltu.yealtube.utils.YoutubeUtil;

@SuppressWarnings("serial")
public class MovieCronServlet extends HttpServlet {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(MovieCronServlet.class);
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		logger.info("Movie Cron Job has been executed");

		List<Tube> tubes = YoutubeUtil.getHotTube(Constant.MOVIE_TYPE);
		ReportService reportService = ReportService.getInstance();
		for (Tube tube : tubes) {
			try {
				logger.debug("Send tube: " + tube.getId() + " with rating: " + String.valueOf(tube.getRating()));
				boolean flag = YoutubeUtil.sendMovie(tube.getId(), String.valueOf(tube.getRating()));
				if (!flag) {
					reportService.addReport(Constant.UNSENT_STATUS);
				} else {
					reportService.addReport(Constant.SENT_STATUS);
				}
			} catch (CommonException e) {
				logger.error(e.getMessage(), e.getCause());
				reportService.addReport(Constant.UNSENT_STATUS);
			}
		}
		logger.info("End Cron Job.");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
