package com.ltu.yealtube.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.client.http.HttpStatusCodes;
import com.google.api.server.spi.response.CollectionResponse;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.ltu.yealtube.entity.Statistics;
import com.ltu.yealtube.entity.Tube;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.exeptions.ErrorCodeDetail;

/**
 * The Class StatisticsDao.
 * @author uyphu
 */
public class StatisticsDao extends AbstractDao<Statistics> {
	
	/** The instance. */
	private static StatisticsDao instance = null;

	/**
	 * Instantiates a new statistics dao.
	 */
	public StatisticsDao() {
		super(Statistics.class);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanData() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Gets the single instance of StatisticsDao.
	 *
	 * @return single instance of StatisticsDao
	 */
	public static StatisticsDao getInstance() {
		if (instance == null) {
			instance = new StatisticsDao();
		}
		return instance;
	}
	
	/**
	 * Find one by video id.
	 *
	 * @param videoId the video id
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 * @throws CommonException the common exception
	 */
	public CollectionResponse<Statistics> findOneByVideoId(String videoId, String cursorString, Integer count)
			throws CommonException {
		Query<Statistics> query;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("videoId", videoId);
		query = getQuery(map);
		return executeQuery(query, cursorString, count);
	}
	
	/**
	 * Gets the query.
	 * 
	 * @param querySearch
	 *            the query search
	 * @return the query
	 * @throws CommonException
	 *             the proconco exception
	 */
	public Query<Statistics> getQuery(String querySearch) throws CommonException {
		try {
			if (querySearch != null) {
				Query<Statistics> query;
				Map<String, Object> map = new HashMap<String, Object>();
				if (querySearch.indexOf("status:") != -1) {
					String[] queries = querySearch.split(":");
					map.put("status", queries[1]);
					query = getQuery(map);
				} else {
					query = getQueryByName("name", querySearch);
				}
				return query;
			} else {
				return ofy().load().type(Statistics.class);
			}
		} catch (Exception e) {
			throw new CommonException(HttpStatusCodes.STATUS_CODE_SERVER_ERROR, ErrorCodeDetail.ERROR_QUERY_NOT_VALID.getMsg());
		}
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
		Query<Statistics> query = getQuery(querySearch);
		return executeQuery(query, cursorString, count);
	}

	@Override
	public CollectionResponse<Statistics> list(String cursorString, Integer count) {
		Query<Statistics> query = getQuery().order("createdAt");
		return executeQuery(query, cursorString, count);
	}
	
	public CollectionResponse<Statistics> listByParent(String parentId, String cursorString, Integer count) {
		Query<Statistics> query = getQuery();
		query = query.ancestor(Key.create(Tube.class, parentId));
		query = query.order("createdAt");
		return executeQuery(query, cursorString, count);
	}
	
	public List<Statistics> listByParent(String parentId, Integer count) {
		Query<Statistics> query = getQuery();
		query = query.ancestor(Key.create(Tube.class, parentId));
		return executeQuery(query, count);
	}

}
