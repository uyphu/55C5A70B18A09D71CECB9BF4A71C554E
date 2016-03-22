package com.ltu.yealtube.cron;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ltu.yealtube.entity.Report;
import com.ltu.yealtube.service.ReportService;
import com.ltu.yealtube.service.TubeService;
import com.ltu.yealtube.utils.AppUtils;

@SuppressWarnings("serial")
public class TestServlet extends HttpServlet {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(TestServlet.class);

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//ReportService reportService = ReportService.getInstance();
		try {
			logger.info("Statistics Cron Job has been executed");
			logger.trace("Trace Message!");
			logger.debug("Debug Message!");
			logger.info("Info Message!");
			logger.warn("Warn Message!");
			logger.error("Error Message!");
			logger.fatal("Fatal Message!");
			logger.info("End Cron Job.");
			//TubeService service = TubeService.getInstance();
			//service.insert("fB_W7wzk3Xw");
			//Tube tube = service.find("fB_W7wzk3Xw");
			//service.delete("fB_W7wzk3Xw");	
			
			ReportService reportService = ReportService.getInstance();
			Report report = new Report(AppUtils.toShortDateString(AppUtils.getCurrentDate()));	
			report.setExceptionCount(1);
			reportService.add(report);
			//int a = 1/0;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex.getCause());
			//reportService.addReport(Constant.EXCEPTION_STATUS);
		}
	}
}
