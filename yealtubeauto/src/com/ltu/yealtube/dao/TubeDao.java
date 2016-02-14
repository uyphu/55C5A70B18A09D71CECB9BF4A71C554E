package com.ltu.yealtube.dao;

import java.util.HashMap;
import java.util.Map;

import com.google.api.server.spi.response.CollectionResponse;
import com.googlecode.objectify.cmd.Query;
import com.ltu.yealtube.constants.Constant;
import com.ltu.yealtube.entity.Statistics;
import com.ltu.yealtube.entity.Tube;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.exeptions.ErrorCode;
import com.ltu.yealtube.exeptions.ErrorCodeDetail;

/**
 * The Class TubeTubeDao.
 * @author uyphu
 */
public class TubeDao extends AbstractDao<Tube> {
	
	/** The instance. */
	private static TubeDao instance = null;

	/**
	 * Instantiates a new tube Tube dao.
	 */
	public TubeDao() {
		super(Tube.class);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanData() {
		CollectionResponse<Tube> list = list(null, null);
		if (list != null) {
			StatisticsDao dao = StatisticsDao.getInstance();
			for (Tube tube : list.getItems()) {
				for (Statistics statistics : tube.getStatistics()) {
					dao.delete(statistics);
				}
				delete(tube);
			}
		}
	}
	
	/**
	 * Gets the single instance of TubeTubeDao.
	 *
	 * @return single instance of TubeTubeDao
	 */
	public static TubeDao getInstance() {
		if (instance == null) {
			instance = new TubeDao();
		}
		return instance;
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
	public Query<Tube> getQuery(String querySearch) throws CommonException {
		try {
			if (querySearch != null) {
				Query<Tube> query;
				Map<String, Object> map = new HashMap<String, Object>();
				if (querySearch.indexOf("status:") != -1) {
					String[] queries = querySearch.split(":");
					map.put("status", Integer.parseInt(queries[1]));
					query = getQuery(map);
				} else {
					query = getQueryByName("title", querySearch);
				}
				return query;
			} else {
				return ofy().load().type(Tube.class);
			}
		} catch (Exception e) {
			throw new CommonException(ErrorCode.SYSTEM_EXCEPTION.getId(), ErrorCodeDetail.ERROR_PARSE_QUERY
					+ Constant.STRING_EXEPTION_DETAIL + e.getMessage());
		}
	}
	
	/**
	 * Gets the query over status.
	 *
	 * @param status the status
	 * @return the query over status
	 */
	public Query<Tube> getQueryByStatus(String field, int status) {
		return ofy().load().type(Tube.class).filter(field, status);
	}


	/**
	 * Search tubes.
	 *
	 * @param querySearch the query search
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 * @throws CommonException the common exception
	 */
	public CollectionResponse<Tube> searchTubes(String querySearch, String cursorString, Integer count)
			throws CommonException {
		Query<Tube> query = getQuery(querySearch);
		return executeQuery(query, cursorString, count);
	}
	
	/**
	 * Search tubes over status.
	 *
	 * @param status the status
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 * @throws CommonException the common exception
	 */
	public CollectionResponse<Tube> searchTubes(String field, int status, String cursorString, Integer count)
			throws CommonException {
		Query<Tube> query = getQueryByStatus(field, status);
		return executeQuery(query, cursorString, count);
	}

	/**
	 * Gets the tube by name.
	 *
	 * @param name            the name
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the tube by name
	 * @throws CommonException the common exception
	 */
	public CollectionResponse<Tube> getTubeByName(String title, String cursorString, Integer count) throws CommonException {
		if (title != null) {
			Query<Tube> query = getQueryByName("title", title);
			return executeQuery(query, cursorString, count);
		}
		return null;
	}
	
	@Override
	public CollectionResponse<Tube> list(String cursorString, Integer count) {
		Query<Tube> query = getQuery().order("-createdAt");
		return executeQuery(query, cursorString, count);
	}
	
}
