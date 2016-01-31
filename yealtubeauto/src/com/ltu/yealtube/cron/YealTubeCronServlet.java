package com.ltu.yealtube.cron;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ltu.yealtube.domain.Tube;
import com.ltu.yealtube.utils.YoutubeUtil;

@SuppressWarnings("serial")
public class YealTubeCronServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(YealTubeCronServlet.class);
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		try {
			logger.info("Cron Job has been executed");

			List<Tube> tubes =  YoutubeUtil.getHotTube();
			System.out.println(tubes.size());
//			for (Tube tube : tubes) {
//				System.out.println(tube.toString());
//			}
		} catch (Exception ex) {
			// Log any exceptions in your Cron Job
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
