package com.ltu.yealtube.cron;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ltu.yealtube.constants.Constant;
import com.ltu.yealtube.entity.Playlist;
import com.ltu.yealtube.service.ReportService;
import com.ltu.yealtube.utils.YoutubeUtil;

@SuppressWarnings("serial")
public class ProcessCronServlet extends HttpServlet {
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(ProcessCronServlet.class);

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ReportService reportService = ReportService.getInstance();
		try {
			logger.info("Statistics Cron Job has been executed");
			
			List<Playlist> list = YoutubeUtil.getHotPlayList();
			for (Playlist playlist : list) {
				
			}

			logger.info("End Cron Job.");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex.getCause());
			reportService.addReport(Constant.EXCEPTION_STATUS);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
