package com.ltu.yealtube.dao;

import java.util.HashMap;
import java.util.Map;

import com.google.api.server.spi.response.CollectionResponse;
import com.googlecode.objectify.cmd.Query;
import com.ltu.yealtube.constants.Constant;
import com.ltu.yealtube.entity.TubeReport;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.exeptions.ErrorCode;
import com.ltu.yealtube.exeptions.ErrorCodeDetail;

/**
 * The Class TubeTubeDao.
 * @author uyphu
 */
public class TubeReportDao extends AbstractDao<TubeReport> {
	
	/** The instance. */
	private static TubeReportDao instance = null;

	/**
	 * Instantiates a new tube Tube dao.
	 */
	public TubeReportDao() {
		super(TubeReport.class);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanData() {
		CollectionResponse<TubeReport> list = list(null, null);
		if (list != null) {
			for (TubeReport tube : list.getItems()) {
				delete(tube);
			}
		}
	}
	
	/**
	 * Gets the single instance of TubeTubeDao.
	 *
	 * @return single instance of TubeTubeDao
	 */
	public static TubeReportDao getInstance() {
		if (instance == null) {
			instance = new TubeReportDao();
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
	public Query<TubeReport> getQuery(String querySearch) throws CommonException {
		try {
			if (querySearch != null) {
				Query<TubeReport> query;
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
				return ofy().load().type(TubeReport.class);
			}
		} catch (Exception e) {
			throw new CommonException(ErrorCode.SYSTEM_EXCEPTION.getId(), ErrorCodeDetail.ERROR_PARSE_QUERY
					+ Constant.STRING_EXEPTION_DETAIL + e.getMessage());
		}
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
	public CollectionResponse<TubeReport> searchTubes(String querySearch, String cursorString, Integer count)
			throws CommonException {
		Query<TubeReport> query = getQuery(querySearch);
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
	public CollectionResponse<TubeReport> getTubeByName(String title, String cursorString, Integer count) throws CommonException {
		if (title != null) {
			Query<TubeReport> query = getQueryByName("title", title);
			return executeQuery(query, cursorString, count);
		}
		return null;
	}
	
	@Override
	public CollectionResponse<TubeReport> list(String cursorString, Integer count) {
		Query<TubeReport> query = getQuery().order("-createdAt");
		return executeQuery(query, cursorString, count);
	}
	
}
