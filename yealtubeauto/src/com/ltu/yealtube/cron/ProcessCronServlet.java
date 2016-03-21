package com.ltu.yealtube.cron;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.api.server.spi.response.CollectionResponse;
import com.ltu.yealtube.constants.Constant;
import com.ltu.yealtube.entity.Statistics;
import com.ltu.yealtube.entity.Tube;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.service.ReportService;
import com.ltu.yealtube.service.StatisticsService;
import com.ltu.yealtube.service.TubeService;
import com.ltu.yealtube.utils.AppUtils;

@SuppressWarnings("serial")
public class ProcessCronServlet extends HttpServlet {
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(ProcessCronServlet.class);

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ReportService reportService = ReportService.getInstance();
		try {
			logger.info("Statistics Cron Job has been executed");

			TubeService tubeService = TubeService.getInstance();
			
			String cursor = null;

			do {
				CollectionResponse<Tube> tubes = tubeService.searchTubes("status = ", Constant.IN_WORK_STATUS, cursor, Constant.MAX_RECORDS);
				if (tubes != null && tubes.getItems().size() > 0) {
					for (Tube tube : tubes.getItems()) {
						logger.info("Processint tube: " + tube.getId());
						if (validateStatistics(tube)) {
							reportService.addReport(Constant.IN_WORK_STATUS);
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

	/**
	 * Validate statistics.
	 * 
	 * @param videoId
	 *            the video id
	 * @return true, if successful
	 */
	private boolean validateStatistics(Tube tube) {
		StatisticsService statisticsService = StatisticsService.getInstance();
		TubeService tubeService = TubeService.getInstance();
		ReportService reportService = ReportService.getInstance();
		CollectionResponse<Statistics> result = statisticsService.listByParent(tube.getId(), null, null);
		if (result != null) {
			List<Statistics> list = new ArrayList<Statistics>(result.getItems());
			if (list.size() < 2) {
				tube.setStatus(Constant.PENDING_STATUS);
				tube.setModifiedAt(Calendar.getInstance().getTime());
				try {
					tubeService.update(tube);
				} catch (CommonException e) {
					logger.error(e.getMessage(), e.getCause());
				}
				return false;
			}
			int totalView = 0;
			int viewCount = list.get(0).getViewCount();
			int likeCount = list.get(0).getLikeCount();
			int dislikeCount = list.get(0).getDislikeCount();
			int favoriteCount = list.get(0).getFavoriteCount();
			int commentCount = list.get(0).getCommentCount();
			int ratingValue = AppUtils.getParmValue("RATING_PARAM") != 0 ? AppUtils.getParmValue("RATING_PARAM") : Constant.RATING_PARAM;
			float percent = ratingValue / list.size();
			float rating = 0;

			for (int i = 1; i < list.size(); i++) {
				Statistics item = list.get(i);
				totalView += (item.getViewCount() - viewCount);
				totalView += (item.getLikeCount() - likeCount) * 4;
				totalView -= (item.getDislikeCount() - dislikeCount) * 7;
				totalView += (item.getFavoriteCount() - favoriteCount) * 4;
				totalView += (item.getCommentCount() - commentCount) * 3;

				viewCount = item.getViewCount();
				likeCount = item.getLikeCount();
				dislikeCount = item.getDislikeCount();
				favoriteCount = item.getFavoriteCount();
				commentCount = item.getCommentCount();

				float r = totalView
						/ (viewCount + likeCount * 4 - dislikeCount * 7 + favoriteCount * 4 + commentCount * 3);
				rating += r > percent ? percent : r;
			}

			int average = totalView / list.size();
			try {
				tube.setRating(Math.round(rating*100)/100.0f);
				tube.setAverageView(average);
				int maxAverageValue = AppUtils.getParmValue("MAX_AVERAGE") != 0 ? AppUtils.getParmValue("MAX_AVERAGE") : Constant.MAX_AVERAGE;
				if (average > maxAverageValue) {
					tube.setStatus(Constant.APPROVED_STATUS);
					tubeService.update(tube);
					return true;
				} else {
					tube.setStatus(Constant.CANCELLED_STATUS);
					tubeService.update(tube);
					reportService.addReport(Constant.UNSENT_STATUS);
					return false;
				}
			} catch (CommonException e) {
				logger.error(e.getMessage(), e.getCause());
			}
		}

		return false;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
