package com.ltu.yealtube.dao;

import java.util.HashMap;
import java.util.Map;

import com.google.api.client.http.HttpStatusCodes;
import com.google.api.server.spi.response.CollectionResponse;
import com.googlecode.objectify.cmd.Query;
import com.ltu.yealtube.entity.Report;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.exeptions.ErrorCodeDetail;

/**
 * The Class ReportDao.
 * @author uyphu
 */
public class ReportDao extends RemoteAbstractDao<Report> {
	
	/** The instance. */
	private static ReportDao instance = null;

	/**
	 * Instantiates a new report dao.
	 */
	public ReportDao() {
		super(Report.class);
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
	 * Gets the single instance of ReportDao.
	 *
	 * @return single instance of ReportDao
	 */
	public static ReportDao getInstance() {
		if (instance == null) {
			instance = new ReportDao();
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
	public Query<Report> getQuery(String querySearch) throws CommonException {
		try {
			if (querySearch != null) {
				Query<Report> query;
				Map<String, Object> map = new HashMap<String, Object>();
				if (querySearch.indexOf("date:") != -1) {
					String[] queries = querySearch.split(":");
					map.put("date", queries[1]);
					query = getQuery(map);
				} else {
					query = getQueryByName("date", querySearch);
				}
				return query;
			} else {
				return ofy().load().type(Report.class);
			}
		} catch (Exception e) {
			throw new CommonException(HttpStatusCodes.STATUS_CODE_SERVER_ERROR, ErrorCodeDetail.ERROR_QUERY_NOT_VALID.getMsg());
		}
	}


	/**
	 * Search reports.
	 *
	 * @param querySearch the query search
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 * @throws CommonException the common exception
	 */
	public CollectionResponse<Report> searchReports(String querySearch, String cursorString, Integer count)
			throws CommonException {
		Query<Report> query = getQuery(querySearch).order("-date");
		return executeQuery(query, cursorString, count);
	}

	@Override
	public CollectionResponse<Report> list(String cursorString, Integer count) {
		Query<Report> query = getQuery().order("-date");
		return executeQuery(query, cursorString, count);
	}

}
