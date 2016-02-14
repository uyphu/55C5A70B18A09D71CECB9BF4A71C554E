package com.ltu.yealtube.service;

import java.util.Calendar;

import com.google.api.client.http.HttpStatusCodes;
import com.google.api.server.spi.response.CollectionResponse;
import com.ltu.yealtube.dao.TubeReportDao;
import com.ltu.yealtube.entity.Statistics;
import com.ltu.yealtube.entity.Tube;
import com.ltu.yealtube.entity.TubeReport;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.exeptions.ErrorCodeDetail;
import com.ltu.yealtube.utils.YoutubeUtil;

/**
 * The Class TubeService.
 * @author uyphu
 */
public class TubeReportService {

	/** The instance. */
	private static TubeReportService instance = null;
	
	/** The tube dao. */
	private static TubeReportDao tubeDao = TubeReportDao.getInstance();
	
	/**
	 * Gets the single instance of TubeService.
	 *
	 * @return single instance of TubeService
	 */
	public static TubeReportService getInstance() {
		if (instance == null) {
			instance = new TubeReportService();
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
	public CollectionResponse<TubeReport> list(String cursorString, Integer count) {
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
	public CollectionResponse<TubeReport> searchTubes(String querySearch, String cursorString, Integer count)
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
	public TubeReport insert(TubeReport tube) throws CommonException {
		if (tube != null && tube.getId() != null) {
			if (containsTube(tube)) {
				throw new CommonException(HttpStatusCodes.STATUS_CODE_FOUND, ErrorCodeDetail.ERROR_EXIST_OBJECT.getMsg());
			}
			tube.setCreatedAt(Calendar.getInstance().getTime());
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
	public TubeReport update(TubeReport tube) throws CommonException {
		if (tube != null && tube.getId() != null) {
			if (!containsTube(tube)) {
				throw new CommonException(HttpStatusCodes.STATUS_CODE_NOT_FOUND, ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
			} 
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
	public void delete(TubeReport tube) throws CommonException {
		if (tube != null && tube.getId() != null) {
			if (!containsTube(tube)) {
				throw new CommonException(HttpStatusCodes.STATUS_CODE_NOT_FOUND, ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
			} 
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
		if (id != null) {
			if (!containsTube(id)) {
				throw new CommonException(HttpStatusCodes.STATUS_CODE_NOT_FOUND, ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
			} 
			TubeReport tube = find(id);
			tubeDao.delete(tube);
		} else {
			throw new CommonException(HttpStatusCodes.STATUS_CODE_BAD_GATEWAY, ErrorCodeDetail.ERROR_INPUT_NOT_VALID.getMsg());
		}
	}
	
	/**
	 * Find.
	 *
	 * @param id the id
	 * @return the tube
	 */
	public TubeReport find(String id) {
		return tubeDao.find(id);
	}
	
	/**
	 * Contains tube.
	 *
	 * @param tube the tube
	 * @return true, if successful
	 */
	private boolean containsTube(TubeReport tube) {
		boolean contains = true;
		TubeReport item = tubeDao.find(tube.getId());
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
			TubeReport item = tubeDao.find(youtubeId);
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
	public TubeReport insert(String youtubeId, Integer status) throws CommonException {
		if (youtubeId != null) {
			if (containsTube(youtubeId)) {
				throw new CommonException(HttpStatusCodes.STATUS_CODE_FOUND, ErrorCodeDetail.ERROR_EXIST_OBJECT.getMsg());
			} 
			Tube tube = YoutubeUtil.getTube(youtubeId);
			TubeReport tubeReport = new TubeReport(tube);
			Statistics statistics = YoutubeUtil.getStatistics(youtubeId);
			tubeReport.setViewCount(statistics.getViewCount());
			tubeReport.setCreatedAt(Calendar.getInstance().getTime());
			tubeReport.setStatus(status);
			tubeReport = tubeDao.persist(tubeReport);
			return tubeReport;
		} else {
			throw new CommonException(HttpStatusCodes.STATUS_CODE_BAD_GATEWAY, ErrorCodeDetail.ERROR_INPUT_NOT_VALID.getMsg());
		}
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
	 * Search tubes.
	 *
	 * @param status the status
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 * @throws CommonException the common exception
	 */
	public CollectionResponse<TubeReport> searchTubes(int status, String cursorString, Integer count)
			throws CommonException {
		String querySearch = "status:" + status;
		return tubeDao.searchTubes(querySearch, cursorString, count);
	}
	
}
