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
	private static TubeDao instance;
    
    /**
     * Instantiates a new tube dao.
     */
    private TubeDao(){
    	super(Tube.class);
    }
     
    /**
     * Gets the single instance of TubeDao.
     *
     * @return single instance of TubeDao
     */
    public static TubeDao getInstance(){
        if(instance == null){
        	synchronized (TubeDao.class) {
        		instance = new TubeDao();
			}
        }
        return instance;
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
	 * Clean data.
	 *
	 * @param status the status
	 * @throws CommonException the common exception
	 */
	public void cleanData(int status) throws CommonException {
		CollectionResponse<Tube> list = searchTubes("status = ", status, null, null);
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
	 * Gets the query over status.
	 *
	 * @param field the field
	 * @param status the status
	 * @return the query over status
	 */
	public CollectionResponse<Tube> getQueryByStatus(String field, int status) {
		return search(field, status, null, null);
	}
	
	/**
	 * Gets the query by status.
	 *
	 * @param field the field
	 * @param status the status
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the query by status
	 */
	public CollectionResponse<Tube> getQueryByStatus(String field, int status, String cursorString, Integer count) {
		return search(field, status, cursorString, count);
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
		return search(getQuery(querySearch),cursorString, count);
	}
	
	/**
	 * Search tubes over status.
	 *
	 * @param field the field
	 * @param status the status
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 * @throws CommonException the common exception
	 */
	public CollectionResponse<Tube> searchTubes(String field, int status, String cursorString, Integer count)
			throws CommonException {
		return search(field, status, cursorString, count);
	}

	/**
	 * Gets the tube by name.
	 *
	 * @param title the title
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
	
	/* (non-Javadoc)
	 * @see com.ltu.yealtube.dao.AbstractDao#list(java.lang.String, java.lang.Integer)
	 */
	@Override
	public CollectionResponse<Tube> list(String cursorString, Integer count) {
		Query<Tube> query = getQuery().order("-createdAt");
		return executeQuery(query, cursorString, count);
	}
	
	/**
	 * Gets the query.
	 *
	 * @param querySearch the query search
	 * @return the query
	 * @throws CommonException the common exception
	 */
	public Map<String, Object> getQuery(String querySearch) throws CommonException {
		try {
			if (querySearch != null) {
				String[] fields = querySearch.split("@@");
				Map<String, Object> map = new HashMap<String, Object>();
				if (fields != null && fields.length > 1) {
					for (String field : fields) {
						if (field.indexOf("status:") != -1) {
							String[] queries = field.split(":");
							map.put("status", Long.parseLong(queries[1]));
						} else if (field.indexOf("id:") != -1) {
							String[] queries = field.split(":");
							map.put("id", queries[1]);
						}
					}
				} else {
					if (querySearch.indexOf("status:") != -1) {
						String[] queries = querySearch.split(":");
						map.put("status", Long.parseLong(queries[1]));
					} else if (querySearch.indexOf("id:") != -1) {
						String[] queries = querySearch.split(":");
						map.put("id", queries[1]);
					} else {
						map.put("id", querySearch);
					}
				}
				return map;
			} 
			return null;
		} catch (Exception e) {
			throw new CommonException(ErrorCode.SYSTEM_EXCEPTION.getId(), ErrorCodeDetail.ERROR_PARSE_QUERY
					+ Constant.EXEPTION_DETAIL + e.getMessage());
		}
	}
	
}
