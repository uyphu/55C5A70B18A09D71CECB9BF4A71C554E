package com.ltu.yealtube.cron;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.api.server.spi.response.CollectionResponse;
import com.ltu.yealtube.constants.Constant;
import com.ltu.yealtube.entity.Statistics;
import com.ltu.yealtube.entity.Tube;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.service.StatisticsService;
import com.ltu.yealtube.service.TubeService;
import com.ltu.yealtube.utils.AppUtils;

@SuppressWarnings("serial")
public class PlaylistServlet extends HttpServlet {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(PlaylistServlet.class);

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			logger.debug("Start");
			TubeService service = TubeService.getInstance();
			String id = req.getParameter("id");
			Tube tube = service.find(id);
			validateStatistics(tube);
			logger.debug("End");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex.getCause());
		}
	}
	
	private boolean validateStatistics(Tube tube) {
		StatisticsService statisticsService = StatisticsService.getInstance();
		TubeService tubeService = TubeService.getInstance();
		//ReportService reportService = ReportService.getInstance();
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
			logger.debug("ratingValue: " + ratingValue);
			float percent = ratingValue / list.size();
			percent = 0.05f;
			float rating = 0.0f;
			int totalLike = 0;

			for (int i = 1; i < list.size(); i++) {
				Statistics item = list.get(i);
				totalView += (item.getViewCount() - viewCount);
				totalView += (item.getLikeCount() - likeCount) * 4;
				totalView -= (item.getDislikeCount() - dislikeCount) * 7;
				totalView += (item.getFavoriteCount() - favoriteCount) * 4;
				totalView += (item.getCommentCount() - commentCount) * 3;
				
				totalLike += (item.getLikeCount() - likeCount) * 7;
				totalLike -= (item.getDislikeCount() - dislikeCount) * 7;
				totalLike += (item.getFavoriteCount() - favoriteCount) * 4;
				totalLike += (item.getLikeCount() - likeCount) * 4;
				totalLike += (item.getCommentCount() - commentCount) * 3;
				logger.debug("totalLike: " + i + " : " + totalLike);
				
				viewCount = item.getViewCount();
				likeCount = item.getLikeCount();
				dislikeCount = item.getDislikeCount();
				favoriteCount = item.getFavoriteCount();
				commentCount = item.getCommentCount();

			}
			
			float r = (float)totalLike*2 / totalView;
			logger.debug("totalLike: " + totalLike + "totalView: " + totalView);
			rating += r > percent ? percent : r;
			logger.debug("Everage: " + r);
			rating = Math.round(rating*10000)/100.0f;
			logger.debug("rating: " + rating);

			int average = totalView / list.size();
			try {
				tube.setRating(Math.round(rating*10000)/100.0f);
				tube.setAverageView(average);
				int maxAverageValue = AppUtils.getParmValue("MAX_AVERAGE") != 0 ? AppUtils.getParmValue("MAX_AVERAGE") : Constant.MAX_AVERAGE;
				if (average > maxAverageValue) {
					tube.setStatus(Constant.APPROVED_STATUS);
					//tubeService.update(tube);
					logger.debug("Everage: " + maxAverageValue);
					return true;
				} else {
					tube.setStatus(Constant.CANCELLED_STATUS);
					//tubeService.update(tube);
					//reportService.addReport(Constant.UNSENT_STATUS);
					return false;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e.getCause());
			}
		}
		logger.debug("End");
		return false;
	}
	
	public static void main(String[] args) {
		int totalLike = 552;
		int totalView = 66673;
		float r = (float)totalLike / totalView;
		float rating = 0.0f;
		float percent = 0.05f;
		rating += r > percent ? percent : r;
		System.out.println(rating);
	}
}
