package com.ltu.yealtube.dao;

import java.util.HashMap;
import java.util.Map;

import com.google.api.client.http.HttpStatusCodes;
import com.google.api.server.spi.response.CollectionResponse;
import com.googlecode.objectify.cmd.Query;
import com.ltu.yealtube.entity.Playlist;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.exeptions.ErrorCodeDetail;

/**
 * The Class PlaylistDao.
 * @author uyphu
 */
public class PlaylistDao extends AbstractDao<Playlist> {
	
	/** The instance. */
	private static PlaylistDao instance;
    
    /**
     * Instantiates a new statistics dao.
     */
    private PlaylistDao(){
    	super(Playlist.class);
    }
     
    /**
     * Gets the single instance of PlaylistDao.
     *
     * @return single instance of PlaylistDao
     */
    public static PlaylistDao getInstance(){
        if(instance == null){
        	synchronized (PlaylistDao.class) {
        		instance = new PlaylistDao();
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
		// TODO Auto-generated method stub
		
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
	public Query<Playlist> getQuery(String querySearch) throws CommonException {
		try {
			if (querySearch != null) {
				Query<Playlist> query;
				Map<String, Object> map = new HashMap<String, Object>();
				if (querySearch.indexOf("date:") != -1) {
					String[] queries = querySearch.split(":");
					map.put("date", queries[1]);
					query = getQuery(map);
				} else {
					query = getQueryByName("title", querySearch);
				}
				return query;
			} else {
				return ofy().load().type(Playlist.class);
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
	public CollectionResponse<Playlist> searchPlaylists(String querySearch, String cursorString, Integer count)
			throws CommonException {
		Query<Playlist> query = getQuery(querySearch);
		return executeQuery(query, cursorString, count);
	}

	@Override
	public CollectionResponse<Playlist> list(String cursorString, Integer count) {
		Query<Playlist> query = getQuery();
		return executeQuery(query, cursorString, count);
	}

}
