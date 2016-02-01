package com.ltu.yealtube.service;

import java.util.Calendar;

import com.google.api.client.http.HttpStatusCodes;
import com.google.api.server.spi.response.CollectionResponse;
import com.ltu.yealtube.dao.TubeDao;
import com.ltu.yealtube.entity.Tube;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.exeptions.ErrorCodeDetail;


// TODO: Auto-generated Javadoc
/**
 * The Class TubeService.
 * @author uyphu
 */
public class TubeService {

	/** The instance. */
	private static TubeService instance = null;
	
	/** The tube dao. */
	private static TubeDao tubeDao = TubeDao.getInstance();
	
	/**
	 * Gets the single instance of TubeService.
	 *
	 * @return single instance of TubeService
	 */
	public static TubeService getInstance() {
		if (instance == null) {
			instance = new TubeService();
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
	public CollectionResponse<Tube> list(String cursorString, Integer count) {
		return tubeDao.list(cursorString, count);
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
		return tubeDao.searchTubes(querySearch, cursorString, count);
	}
	
	/**
	 * Insert.
	 *
	 * @param tube the tube
	 * @return the tube
	 * @throws CommonException the common exception
	 */
	public Tube insert(Tube tube) throws CommonException {
		if (tube != null && tube.getId() != null) {
			if (containsTube(tube)) {
				throw new CommonException(HttpStatusCodes.STATUS_CODE_FOUND, ErrorCodeDetail.ERROR_EXIST_OBJECT.getMsg());
			}
			tube.setCreatedAt(Calendar.getInstance().getTime());
			tube.setModifiedAt(Calendar.getInstance().getTime());
			return tubeDao.persist(tube);
		} else {
			throw new CommonException(HttpStatusCodes.STATUS_CODE_BAD_GATEWAY, ErrorCodeDetail.ERROR_INPUT_NOT_VALID.getMsg());
		}
	}
	
	/**
	 * Update.
	 *
	 * @param tube the tube
	 * @return the tube
	 * @throws CommonException the common exception
	 */
	public Tube update(Tube tube) throws CommonException {
		if (tube != null && tube.getId() != null) {
			if (!containsTube(tube)) {
				throw new CommonException(HttpStatusCodes.STATUS_CODE_NOT_FOUND, ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
			} 
			tube.setModifiedAt(Calendar.getInstance().getTime());
			return tubeDao.update(tube);
		} else {
			throw new CommonException(HttpStatusCodes.STATUS_CODE_BAD_GATEWAY, ErrorCodeDetail.ERROR_INPUT_NOT_VALID.getMsg());
		}
	}
	
	/**
	 * Delete.
	 *
	 * @param tube the tube
	 * @throws CommonException the common exception
	 */
	public void delete(Tube tube) throws CommonException {
		if (tube != null && tube.getId() != null) {
			if (!containsTube(tube)) {
				throw new CommonException(HttpStatusCodes.STATUS_CODE_NOT_FOUND, ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
			} 
			tube.setModifiedAt(Calendar.getInstance().getTime());
			tubeDao.delete(tube);
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
		Tube tube = find(id);
		if (tube != null) {
			tubeDao.delete(tube);
		} else {
			throw new CommonException(HttpStatusCodes.STATUS_CODE_NOT_FOUND, ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
		}
	}
	
	/**
	 * Find.
	 *
	 * @param id the id
	 * @return the tube
	 */
	public Tube find(String id) {
		return tubeDao.find(id);
	}
	
	/**
	 * Contains tube.
	 *
	 * @param tube the tube
	 * @return true, if successful
	 */
	private boolean containsTube(Tube tube) {
		boolean contains = true;
		Tube item = tubeDao.find(tube.getId());
		if (item == null) {
			contains = false;
		}
		return contains;
	}
	
}
