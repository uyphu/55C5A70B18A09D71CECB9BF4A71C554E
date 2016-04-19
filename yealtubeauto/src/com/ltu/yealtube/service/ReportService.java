package com.ltu.yealtube.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.api.client.http.HttpStatusCodes;
import com.google.api.server.spi.response.CollectionResponse;
import com.ltu.yealtube.constants.Constant;
import com.ltu.yealtube.dao.ReportDao;
import com.ltu.yealtube.entity.Report;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.exeptions.ErrorCodeDetail;
import com.ltu.yealtube.utils.AppUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportService.
 * @author uyphu
 */
public final class ReportService {
	
	/** The Constant logger. */
	private static final Logger LOGGER = Logger.getLogger(TubeService.class);

	/** The report dao. */
	private static ReportDao reportDao = ReportDao.getInstance();
	
	/** The instance. */
	private static ReportService instance;
    
    /**
     * Instantiates a new report service.
     */
    private ReportService(){}
     
    /**
     * Gets the single instance of ReportService.
     *
     * @return single instance of ReportService
     */
    public static ReportService getInstance(){
        if(instance == null){
        	synchronized (ReportService.class) {
        		instance = new ReportService();
    		}
            
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
	public CollectionResponse<Report> list(String cursorString, Integer count) {
		return reportDao.list(cursorString, count);
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
		return reportDao.searchReports(querySearch, cursorString, count);
	}
	
	/**
	 * Insert.
	 *
	 * @param report the report
	 * @return the report
	 * @throws CommonException the common exception
	 */
	public Report insert(Report report) throws CommonException {
		if (report != null && report.getId() == null) {
			if (containsReport(report)) {
				throw new CommonException(HttpStatusCodes.STATUS_CODE_FOUND, ErrorCodeDetail.ERROR_EXIST_OBJECT.getMsg());
			}
			report.setModifiedAt(AppUtils.getCurrentDate());
			return reportDao.persist(report);
		} else {
			throw new CommonException(HttpStatusCodes.STATUS_CODE_BAD_GATEWAY, ErrorCodeDetail.ERROR_INPUT_NOT_VALID.getMsg());
		}
	}
	
	/**
	 * Update.
	 *
	 * @param report the report
	 * @return the report
	 * @throws CommonException the common exception
	 */
	public Report update(Report report) throws CommonException {
		if (report != null && report.getId() != null) {
			if (!containsReport(report)) {
				throw new CommonException(HttpStatusCodes.STATUS_CODE_NOT_FOUND, ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
			} 
			report.setModifiedAt(AppUtils.getCurrentDate());
			return reportDao.update(report);
		} else {
			throw new CommonException(HttpStatusCodes.STATUS_CODE_BAD_GATEWAY, ErrorCodeDetail.ERROR_INPUT_NOT_VALID.getMsg());
		}
	}
	
	/**
	 * Adds the.
	 *
	 * @param report the report
	 * @return the report
	 * @throws CommonException the common exception
	 */
	public Report add(Report report) throws CommonException {
		if (report != null) {
			CollectionResponse<Report> list = searchReports("date:" + report.getDate(), null, null);
			if (list != null && list.getItems().size() > 0) {
				List<Report> reports = new ArrayList<Report>(list.getItems());
				Report oldReport = reports.get(0);
				oldReport.setCancelledCount(oldReport.getCancelledCount() + report.getCancelledCount());
				oldReport.setPendingCount(oldReport.getPendingCount() + report.getPendingCount());
				oldReport.setSentCount(oldReport.getSentCount()+ report.getSentCount());
				oldReport.setUnsentCount(oldReport.getUnsentCount() + report.getUnsentCount());
				oldReport.setProcessCount(oldReport.getProcessCount() + report.getProcessCount());
				oldReport.setInWorkCount(oldReport.getInWorkCount() + report.getInWorkCount());
				oldReport.setExceptionCount(oldReport.getExceptionCount() + report.getExceptionCount());
				return update(oldReport);
			}
			return insert(report);
		} else {
			throw new CommonException(HttpStatusCodes.STATUS_CODE_BAD_GATEWAY, ErrorCodeDetail.ERROR_INPUT_NOT_VALID.getMsg());
		}
	}
	
	
	/**
	 * Delete.
	 *
	 * @param report the report
	 * @throws CommonException the common exception
	 */
	public void delete(Report report) throws CommonException {
		if (report != null && report.getId() != null) {
			if (!containsReport(report)) {
				throw new CommonException(HttpStatusCodes.STATUS_CODE_NOT_FOUND, ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
			} 
			reportDao.delete(report);
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
		Report report = find(id);
		if (report != null) {
			reportDao.delete(report);
		} else {
			throw new CommonException(HttpStatusCodes.STATUS_CODE_NOT_FOUND, ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
		}
	}
	
	/**
	 * Find.
	 *
	 * @param id the id
	 * @return the report
	 */
	public Report find(String id) {
		return reportDao.find(id);
	}
	
	/**
	 * Contains report.
	 *
	 * @param report the report
	 * @return true, if successful
	 */
	private boolean containsReport(Report report) {
		boolean contains = true;
		if (report.getId() != null) {
			Report item = reportDao.find(report.getId());
			if (item == null) {
				contains = false;
			}
		} else {
			contains =  false;
		}
		return contains;
	}
	
	/**
	 * Adds the report.
	 *
	 * @param status the status
	 */
	public void addReport(int status) {
		try {
			ReportService reportService = ReportService.getInstance();
			Report report = new Report(AppUtils.toShortDateString(AppUtils.getCurrentDate()));
			switch (status) {
			case Constant.PENDING_STATUS:
				report.setPendingCount(1);
				reportService.add(report);
				break;
			case Constant.SENT_STATUS:
				report.setSentCount(1);
				reportService.add(report);
				break;
			case Constant.UNSENT_STATUS:
				report.setUnsentCount(1);
				reportService.add(report);
				break;
			case Constant.CANCELLED_STATUS:
				report.setCancelledCount(1);
				reportService.add(report);
				break;
			case Constant.IN_WORK_STATUS:
				report.setInWorkCount(1);
				reportService.add(report);
				break;
			case Constant.EXCEPTION_STATUS:
				report.setExceptionCount(1);
				reportService.add(report);
				break;
			case Constant.PROCESS_STATUS:
				report.setProcessCount(1);
				reportService.add(report);
				break;
			default:
				break;
			}
		} catch (CommonException e) {
			LOGGER.error(e.getMessage(), e.getCause());
		}
	}

}
