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
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.service.ReportService;
import com.ltu.yealtube.utils.YoutubeUtil;

/**
 * The Class PlaylistServlet.
 * @author uyphu
 */
@SuppressWarnings("serial")
public class PlaylistServlet extends HttpServlet {

	/** The Constant logger. */
	private static final Logger LOGGER = Logger.getLogger(ProcessCronServlet.class);

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		final ReportService reportService = ReportService.getInstance();
		try {
			LOGGER.info("Playlist Cron Job has been executed");
			
			final List<Playlist> list = YoutubeUtil.getHotPlayList();
			for (Playlist playlist : list) {
				YoutubeUtil.sendPlaylist(playlist);
			}
			LOGGER.info("End Cron Job.");
		} catch (CommonException ex) {
			LOGGER.error(ex.getMessage(), ex.getCause());
			reportService.addReport(Constant.EXCEPTION_STATUS);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
