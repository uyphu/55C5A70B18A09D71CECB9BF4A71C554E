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
import com.ltu.yealtube.entity.Statistics;
import com.ltu.yealtube.entity.Tube;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.service.StatisticsService;
import com.ltu.yealtube.service.TubeService;
import com.ltu.yealtube.utils.YoutubeUtil;


/**
 * The Class StatisticsCronServlet.
 * @author uyphu
 */
@SuppressWarnings("serial")
public class StatisticsCronServlet extends HttpServlet {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(StatisticsCronServlet.class);
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		try {
			logger.info("Statistics Cron Job has been executed");
			
			TubeService tubeService = TubeService.getInstance();
			StatisticsService statisticsService = StatisticsService.getInstance();
			String cursor = null;
			
			do {
				CollectionResponse<Tube> tubes = tubeService.list(cursor, Constant.MAX_RECORDS);
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
								tubeService.update(tube);
								statisticsService.insert(tube.getId());
							}
						} else if (Constant.IN_WORK_STATUS == tube.getStatus()) {
							validateStatistics(tube.getId());
						} else if (Constant.APPROVED_STATUS == tube.getStatus()) {
							//send tube to yealtube
							if (YoutubeUtil.sendTube(tube.getId())){
								tubeService.deleteWithChildren(tube.getId());
							}
						} else if (Constant.CANCELLED_STATUS == tube.getStatus()) {
							tubeService.deleteWithChildren(tube.getId());
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
	 * Validate statistics.
	 *
	 * @param videoId the video id
	 * @return true, if successful
	 */
	private boolean validateStatistics(String videoId) {
		StatisticsService statisticsService = StatisticsService.getInstance();
		TubeService tubeService = TubeService.getInstance();
		CollectionResponse<Statistics> result = statisticsService.listByParent(videoId, null, null);
		if (result != null) {
			List<Statistics> list = new ArrayList<Statistics>(result.getItems());
			int totalView = 0;
			int viewCount = list.get(0).getViewCount();
			int likeCount = list.get(0).getLikeCount();
			int dislikeCount = list.get(0).getDislikeCount();
			int favoriteCount = list.get(0).getFavoriteCount();
			int commentCount = list.get(0).getCommentCount();
			
			for (int i = 1; i < list.size(); i++) {
				Statistics item = list.get(i);
				totalView += (item.getViewCount() - viewCount);
				totalView += (item.getLikeCount() - likeCount) * 4;
				totalView -= (item.getDislikeCount() - dislikeCount) * 5;
				totalView += (item.getFavoriteCount() - favoriteCount) * 3;
				totalView += (item.getCommentCount() - commentCount) * 4;
				
				viewCount = item.getViewCount();
				likeCount = item.getLikeCount();
				dislikeCount = item.getDislikeCount();
				favoriteCount = item.getFavoriteCount();
				commentCount = item.getCommentCount();
			}
			
			int average = totalView/(list.size()-1);
			Tube tube = tubeService.find(videoId);
			try {
				if (average > Constant.MAX_AVERAGE) {
					tube.setStatus(Constant.APPROVED_STATUS);
					tubeService.update(tube);
					return true;
				} else {
					tube.setStatus(Constant.CANCELLED_STATUS);
					tubeService.update(tube);
					return false;
				}
			} catch (CommonException e) {
				logger.error(e.getMessage(), e.getCause());
			}
		}
		
		return false;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
