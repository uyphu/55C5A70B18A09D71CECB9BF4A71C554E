package com.ltu.yealtube.cron;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ltu.yealtube.entity.Report;
import com.ltu.yealtube.entity.Tube;
import com.ltu.yealtube.service.ReportService;
import com.ltu.yealtube.service.TubeService;
import com.ltu.yealtube.utils.AppUtils;
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
			TubeService service = TubeService.getInstance();
			ReportService reportService = ReportService.getInstance();
			for (Tube tube : tubes) {
				service.insert(tube.getId());
				Report report = new Report(AppUtils.toShortDateString(AppUtils.getCurrentDate()));
				report.setPendingCount(1);
				reportService.add(report);
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
