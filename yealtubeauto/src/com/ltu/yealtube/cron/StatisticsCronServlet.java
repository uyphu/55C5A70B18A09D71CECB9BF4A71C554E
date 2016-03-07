package com.ltu.yealtube.cron;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.api.server.spi.response.CollectionResponse;
import com.ltu.yealtube.constants.Constant;
import com.ltu.yealtube.entity.Report;
import com.ltu.yealtube.entity.Statistics;
import com.ltu.yealtube.entity.Tube;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.service.ReportService;
import com.ltu.yealtube.service.StatisticsService;
import com.ltu.yealtube.service.TubeService;
import com.ltu.yealtube.utils.AppUtils;
import com.ltu.yealtube.utils.YoutubeUtil;

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

		try {
			logger.info("Statistics Cron Job has been executed");

			TubeService tubeService = TubeService.getInstance();
			StatisticsService statisticsService = StatisticsService.getInstance();
			String cursor = null;

			do {
				// CollectionResponse<Tube> tubes = tubeService.list(cursor,
				// Constant.MAX_RECORDS);
				CollectionResponse<Tube> tubes = tubeService.searchTubes("status <= ", 3, cursor, Constant.MAX_RECORDS);
				if (tubes != null && tubes.getItems().size() > 0) {
					for (Tube tube : tubes.getItems()) {
						if (Constant.PENDING_STATUS == tube.getStatus()) {
							Date createdAt = tube.getCreatedAt();
							Date modifiedAt = tube.getModifiedAt();
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(createdAt);
							calendar.add(Calendar.DAY_OF_YEAR, Constant.MAX_DAYS);
							if (modifiedAt.after(calendar.getTime())) {
								tube.setStatus(Constant.IN_WORK_STATUS);
								tubeService.update(tube);
							} else {
								tube.setModifiedAt(Calendar.getInstance().getTime());
								Statistics statistics = statisticsService.insert(tube.getId());
								tube.setViewCount(statistics.getViewCount());
								tubeService.update(tube);
							}
						} else if (Constant.IN_WORK_STATUS == tube.getStatus()) {
							validateStatistics(tube.getId());
							addReport(Constant.IN_WORK_STATUS);
						} else if (Constant.APPROVED_STATUS == tube.getStatus()) {
							// send tube to yealtube
							try {
								boolean flag = YoutubeUtil.sendTube(tube.getId(), String.valueOf(tube.getRating()));
								if (!flag) {

									tube.setStatus(Constant.UNSENT_STATUS);
									addReport(Constant.UNSENT_STATUS);
								} else {
									tube.setStatus(Constant.SENT_STATUS);
									addReport(Constant.SENT_STATUS);
								}
							} catch (CommonException e) {
								logger.error(e.getMessage(), e.getCause());
								tube.setStatus(Constant.UNSENT_STATUS);
								addReport(Constant.UNSENT_STATUS);
							}
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

	/**
	 * Adds the report.
	 * 
	 * @param status
	 *            the status
	 */
	private void addReport(int status) {
		try {
			ReportService reportService = ReportService.getInstance();
			Report report = new Report(AppUtils.toShortDateString(AppUtils.getCurrentDate()));
			switch (status) {
			case Constant.PENDING_STATUS:
				report.setPendingCount(1);
				reportService.add(report);
				break;
			case Constant.SENT_STATUS:
				report.setSentCount(1);
				reportService.add(report);
				break;
			case Constant.UNSENT_STATUS:
				report.setUnsentCount(1);
				reportService.add(report);
				break;
			case Constant.CANCELLED_STATUS:
				report.setCancelledCount(1);
				reportService.add(report);
				break;
			case Constant.IN_WORK_STATUS:
				report.setInWorkCount(1);
				reportService.add(report);
				break;
			default:
				break;
			}
		} catch (CommonException e) {
			logger.error(e.getMessage(), e.getCause());
		}

	}

	/**
	 * Validate statistics.
	 * 
	 * @param videoId
	 *            the video id
	 * @return true, if successful
	 */
	private boolean validateStatistics(String videoId) {
		StatisticsService statisticsService = StatisticsService.getInstance();
		TubeService tubeService = TubeService.getInstance();
		CollectionResponse<Statistics> result = statisticsService.listByParent(videoId, null, null);
		if (result != null) {
			List<Statistics> list = new ArrayList<Statistics>(result.getItems());
			Tube tube = tubeService.find(videoId);
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

			int average = totalView / (list.size() - 1);
			try {
				tube.setRating(Math.round(rating*100)/100.0f);
				int maxAverageValue = AppUtils.getParmValue("MAX_AVERAGE") != 0 ? AppUtils.getParmValue("MAX_AVERAGE") : Constant.MAX_AVERAGE;
				if (average > maxAverageValue) {
					tube.setStatus(Constant.APPROVED_STATUS);
					tubeService.update(tube);
					return true;
				} else {
					tube.setStatus(Constant.CANCELLED_STATUS);
					tubeService.update(tube);
					addReport(Constant.UNSENT_STATUS);
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
