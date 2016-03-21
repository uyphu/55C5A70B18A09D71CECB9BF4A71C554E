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
import com.ltu.yealtube.entity.Statistics;
import com.ltu.yealtube.entity.Tube;
import com.ltu.yealtube.service.ReportService;
import com.ltu.yealtube.service.StatisticsService;
import com.ltu.yealtube.service.TubeService;
import com.ltu.yealtube.utils.AppUtils;

/**
 * The Class StatisticsCronServlet.
 * 
 * @author uyphu
 */
@SuppressWarnings("serial")
public class StatisticsCronServlet extends HttpServlet {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(StatisticsCronServlet.class);

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		ReportService reportService = ReportService.getInstance();

		try {
			logger.info("Statistics Cron Job has been executed");
			TubeService tubeService = TubeService.getInstance();
			StatisticsService statisticsService = StatisticsService.getInstance();
			String cursor = null;

			do {
				CollectionResponse<Tube> tubes = tubeService.searchTubes("status = ", Constant.PENDING_STATUS, cursor,
						Constant.MAX_RECORDS);

				if (tubes != null && tubes.getItems().size() > 0) {
					for (Tube tube : tubes.getItems()) {
						Date createdAt = tube.getCreatedAt();
						Date modifiedAt = tube.getModifiedAt();
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(createdAt);
						calendar.add(Calendar.DAY_OF_YEAR, Constant.MAX_DAYS);
						logger.debug("Tube: " + tubes.getItems().size() + " CreatedAt: " + AppUtils.toString(createdAt)
								+ " ModifiedAt: " + AppUtils.toString(modifiedAt));
						if (modifiedAt.after(calendar.getTime())) {
							tube.setStatus(Constant.IN_WORK_STATUS);
							tubeService.update(tube);
						} else {
							tube.setModifiedAt(Calendar.getInstance().getTime());
							Statistics statistics = statisticsService.insert(tube.getId());
							tube.setViewCount(statistics.getViewCount());
							tubeService.update(tube);
							reportService.addReport(Constant.PROCESS_STATUS);
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
			reportService.addReport(Constant.EXCEPTION_STATUS);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
