package com.ltu.yealtube.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.api.client.http.HttpStatusCodes;
import com.google.api.server.spi.response.CollectionResponse;
import com.ltu.yealtube.constants.Constant;
import com.ltu.yealtube.dao.StatisticsDao;
import com.ltu.yealtube.dao.TubeDao;
import com.ltu.yealtube.entity.Statistics;
import com.ltu.yealtube.entity.Tube;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.exeptions.ErrorCodeDetail;
import com.ltu.yealtube.utils.YoutubeUtil;

/**
 * The Class TubeService.
 * @author uyphu
 */
public class TubeService {
	
//	/** The Constant logger. */
//	private static final Logger logger = Logger.getLogger(TubeService.class);

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
			//FIXME Phu
//			if (!containsTube(tube)) {
//				throw new CommonException(HttpStatusCodes.STATUS_CODE_NOT_FOUND, ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
//			} 
			tube.setModifiedAt(Calendar.getInstance().getTime());
			return tubeDao.update(tube);
		} else {
			throw new CommonException(HttpStatusCodes.STATUS_CODE_BAD_GATEWAY, ErrorCodeDetail.ERROR_INPUT_NOT_VALID.getMsg());
		}
	}
	
	/**
	 * Update.
	 *
	 * @param id the id
	 * @param status the status
	 * @return the tube
	 * @throws CommonException the common exception
	 */
	public Tube update(String id, Integer status) throws CommonException {
		if (id != null && status != null) {
			//FIXME Phu
//			if (!containsTube(id)) {
//				throw new CommonException(HttpStatusCodes.STATUS_CODE_NOT_FOUND, ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
//			} 
			Tube tube = find(id);
			tube.setStatus(status);
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
			//FIXME Phu
//			if (!containsTube(tube)) {
//				throw new CommonException(HttpStatusCodes.STATUS_CODE_NOT_FOUND, ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
//			} 
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
	public void deleteWithChildren(String id) throws CommonException {
		Tube tube = find(id);
		if (tube != null) {
			StatisticsDao dao = StatisticsDao.getInstance();
			for (Statistics item : tube.getStatistics()) {
				dao.delete(item);
			}
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
	
	/**
	 * Contains tube.
	 *
	 * @param youtubeId the youtube id
	 * @return true, if successful
	 */
	private boolean containsTube(String youtubeId) {
		boolean contains = true;
		if (youtubeId != null) {
			Tube item = tubeDao.find(youtubeId);
			if (item == null) {
				contains = false;
			}
		} else {
			return contains = false;
		}
		
		return contains;
	}
	
	/**
	 * Insert.
	 *
	 * @param youtubeId the youtube id
	 * @return the tube
	 * @throws CommonException the common exception
	 */
	public Tube insert(String youtubeId) throws CommonException {
		if (youtubeId != null) {
			if (containsTube(youtubeId)) {
				throw new CommonException(HttpStatusCodes.STATUS_CODE_FOUND, ErrorCodeDetail.ERROR_EXIST_OBJECT.getMsg());
			} 
			Tube tube = YoutubeUtil.getTube(youtubeId);
			tube.setCreatedAt(Calendar.getInstance().getTime());
			tube.setModifiedAt(Calendar.getInstance().getTime());
			tube.setStatus(Constant.PENDING_STATUS);
			tube = tubeDao.persist(tube);
			if (tube != null) {
				Statistics statistics = YoutubeUtil.getStatistics(youtubeId);
				StatisticsService service = StatisticsService.getInstance();
				statistics = service.insert(statistics);
				List<Statistics> list = new ArrayList<Statistics>();
				list.add(statistics);
				tube.setStatistics(list);
				return tube;
			}
		} else {
			throw new CommonException(HttpStatusCodes.STATUS_CODE_BAD_GATEWAY, ErrorCodeDetail.ERROR_INPUT_NOT_VALID.getMsg());
		}
		return null;
	}
	
	/**
	 * Clean data.
	 *
	 * @throws CommonException the common exception
	 */
	public void cleanData() throws CommonException {
		tubeDao.cleanData();
	}
	
	/**
	 * Clean data.
	 *
	 * @param status the status
	 * @throws CommonException the common exception
	 */
	public void cleanData(int status) throws CommonException {
		tubeDao.cleanData(status);
	}
	
	/**
	 * Search tubes.
	 *
	 * @param status the status
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 * @throws CommonException the common exception
	 */
	public CollectionResponse<Tube> searchTubes(int status, String cursorString, Integer count)
			throws CommonException {
		String querySearch = "status:" + status;
		return tubeDao.searchTubes(querySearch, cursorString, count);
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
	public CollectionResponse<Tube> searchTubes(String field, int value, String cursorString, Integer count)
			throws CommonException {
		return tubeDao.searchTubes(field, value, cursorString, count);
	}
	
}
