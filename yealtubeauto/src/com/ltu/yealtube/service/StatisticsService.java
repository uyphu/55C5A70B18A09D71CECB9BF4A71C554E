package com.ltu.yealtube.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.api.client.http.HttpStatusCodes;
import com.google.api.server.spi.response.CollectionResponse;
import com.googlecode.objectify.Key;
import com.ltu.yealtube.dao.StatisticsDao;
import com.ltu.yealtube.entity.Statistics;
import com.ltu.yealtube.entity.Tube;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.exeptions.ErrorCodeDetail;
import com.ltu.yealtube.utils.YoutubeUtil;

/**
 * The Class StatisticsService.
 * @author uyphu
 */
public class StatisticsService {

	/** The instance. */
	private static StatisticsService instance = null;
	
	/** The statistics dao. */
	private static StatisticsDao statisticsDao = StatisticsDao.getInstance();
	
	/**
	 * Gets the single instance of StatisticsService.
	 *
	 * @return single instance of StatisticsService
	 */
	public static StatisticsService getInstance() {
		if (instance == null) {
			instance = new StatisticsService();
		}
		return instance;
	}
	
	/**
	 * List.
	 *
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 */
	public CollectionResponse<Statistics> list(String cursorString, Integer count) {
		return statisticsDao.list(cursorString, count);
	}
	
	/**
	 * Search statisticss.
	 *
	 * @param querySearch the query search
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 * @throws CommonException the common exception
	 */
	public CollectionResponse<Statistics> searchStatisticss(String querySearch, String cursorString, Integer count)
			throws CommonException {
		return statisticsDao.searchStatisticss(querySearch, cursorString, count);
	}
	
	/**
	 * Insert.
	 *
	 * @param statistics the statistics
	 * @return the statistics
	 */
	public Statistics insert(Statistics statistics) throws CommonException {
		if (statistics != null && statistics.getId() == null) {
			if (containsStatistics(statistics)) {
				throw new CommonException(HttpStatusCodes.STATUS_CODE_FOUND, ErrorCodeDetail.ERROR_EXIST_OBJECT.getMsg());
			}
			statistics.setCreatedAt(Calendar.getInstance().getTime());
			return statisticsDao.persist(statistics);
		} else {
			throw new CommonException(HttpStatusCodes.STATUS_CODE_BAD_GATEWAY, ErrorCodeDetail.ERROR_INPUT_NOT_VALID.getMsg());
		}
	}
	
	public Statistics insert(String videoId) throws CommonException {
		if (videoId != null) {
			Statistics statistics = YoutubeUtil.getStatistics(videoId);
			statistics.setVideo(Key.create(Tube.class, videoId));
			StatisticsService service = StatisticsService.getInstance();
			statistics = service.insert(statistics);
			return statistics;
		} else {
			throw new CommonException(HttpStatusCodes.STATUS_CODE_BAD_GATEWAY, ErrorCodeDetail.ERROR_INPUT_NOT_VALID.getMsg());
		}
	}
	
	/**
	 * Update.
	 *
	 * @param statistics the statistics
	 * @return the statistics
	 */
	public Statistics update(Statistics statistics) throws CommonException {
		if (statistics != null && statistics.getId() != null) {
			if (!containsStatistics(statistics)) {
				throw new CommonException(HttpStatusCodes.STATUS_CODE_NOT_FOUND, ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
			} 
			return statisticsDao.update(statistics);
		} else {
			throw new CommonException(HttpStatusCodes.STATUS_CODE_BAD_GATEWAY, ErrorCodeDetail.ERROR_INPUT_NOT_VALID.getMsg());
		}
	}
	
	/**
	 * Delete.
	 *
	 * @param statistics the statistics
	 */
	public void delete(Statistics statistics) throws CommonException {
		if (statistics != null && statistics.getId() != null) {
			if (!containsStatistics(statistics)) {
				throw new CommonException(HttpStatusCodes.STATUS_CODE_NOT_FOUND, ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
			} 
			statisticsDao.delete(statistics);
		} else {
			throw new CommonException(HttpStatusCodes.STATUS_CODE_BAD_GATEWAY, ErrorCodeDetail.ERROR_INPUT_NOT_VALID.getMsg());
		}
	}
	
	/**
	 * Delete.
	 *
	 * @param id the id
	 * @throws CommonException the common exception
	 */
	public void delete(String id) throws CommonException {
		Statistics statistics = find(id);
		if (statistics != null) {
			statisticsDao.delete(statistics);
		} else {
			throw new CommonException(HttpStatusCodes.STATUS_CODE_NOT_FOUND, ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
		}
	}
	
	/**
	 * Find.
	 *
	 * @param id the id
	 * @return the statistics
	 */
	public Statistics find(String id) {
		return statisticsDao.find(id);
	}
	
	/**
	 * Contains statistics.
	 *
	 * @param statistics the statistics
	 * @return true, if successful
	 */
	private boolean containsStatistics(Statistics statistics) {
		boolean contains = true;
		if (statistics.getId() != null) {
			Statistics item = statisticsDao.find(statistics.getId());
			if (item == null) {
				contains = false;
			}
		} else {
			contains =  false;
		}
		return contains;
	}
	
	/**
	 * List by parent.
	 *
	 * @param parentId the parent id
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 */
	public CollectionResponse<Statistics> listByParent(String parentId, String cursorString, Integer count) {
		return statisticsDao.listByParent(parentId, cursorString, count);
	}
	
	/**
	 * Gets the average.
	 *
	 * @param parentId the parent id
	 * @return the average
	 */
	public Integer getAverage(String parentId) {
		StatisticsService statisticsService = StatisticsService.getInstance();
		CollectionResponse<Statistics> result = statisticsService.listByParent(parentId, null, null);
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
			return average;
		}
		return 0;
	}
	
}
