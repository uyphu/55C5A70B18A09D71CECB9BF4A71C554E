package com.ltu.yealtube.cron;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ltu.yealtube.entity.Tube;
import com.ltu.yealtube.service.TubeService;
import com.ltu.yealtube.utils.YoutubeUtil;

/**
 * The Class YealTubeCronServlet.
 * @author uyphu
 */
@SuppressWarnings("serial")
public class YealtubeCronServlet extends HttpServlet {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(YealtubeCronServlet.class);
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		try {
			logger.info("Cron Job has been executed");
			
			List<Tube> tubes = YoutubeUtil.getHotTube();
			System.out.println(tubes.size());
			for (Tube tube : tubes) {
				TubeService service = TubeService.getInstance();
				//System.out.println(tube.getId());
				service.insert(tube.getId());
			}
			logger.info("End Cron Job.");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex.getCause());
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
