package com.ltu.yealtube.endpoints;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.client.http.HttpMethods;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.ltu.yealtube.entity.Report;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.service.ReportService;
import com.ltu.yealtube.utils.AppUtils;

@Api(name = "reportendpoint", namespace = @ApiNamespace(ownerDomain = "ltu.com", ownerName = "ltu.com", packagePath = "yealtube.entity"))
public class ReportEndpoint {

	/**
	 * This method lists all the entities inserted in datastore. It uses HTTP
	 * GET method and paging support.
	 * 
	 * @param cursorString
	 *            the cursor string
	 * @param limit
	 *            the limit
	 * @return A CollectionResponse class containing the list of all entities
	 *         persisted and a cursor to the next page.
	 */
	@ApiMethod(name = "listReport")
	public CollectionResponse<Report> listReport(@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {
		ReportService service = ReportService.getInstance();
		return service.list(cursorString, limit);
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET
	 * method.
	 * 
	 * @param id
	 *            the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getReport")
	public Report getReport(@Named("id") String id) {
		ReportService service = ReportService.getInstance();
		return service.find(id);
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity
	 * already exists in the datastore, an exception is thrown. It uses HTTP
	 * POST method.
	 * 
	 * @param report
	 *            the entity to be inserted.
	 * @return The inserted entity.
	 * @throws CommonException
	 *             the common exception
	 */
	@ApiMethod(name = "insertReport")
	public Report insertReport(Report report) throws CommonException {
		ReportService service = ReportService.getInstance();
		return service.insert(report);
	}
	
	/**
	 * Adds the report.
	 *
	 * @param report the report
	 * @return the report
	 * @throws CommonException the common exception
	 */
//	@ApiMethod(name = "addReport", httpMethod=HttpMethods.POST, path="addReport")
//	public Report addReport() throws CommonException {
//		ReportService service = ReportService.getInstance();
//		Report report = new Report(AppUtils.toShortDateString(AppUtils.getCurrentDate()));
//		return service.add(report);
//	}

	/**
	 * This method is used for updating an existing entity. If the entity does
	 * not exist in the datastore, an exception is thrown. It uses HTTP PUT
	 * method.
	 * 
	 * @param report
	 *            the entity to be updated.
	 * @return The updated entity.
	 * @throws CommonException
	 *             the common exception
	 */
	@ApiMethod(name = "updateReport")
	public Report updateReport(Report report) throws CommonException {
		ReportService service = ReportService.getInstance();
		return service.update(report);
	}

	/**
	 * This method removes the entity with primary key id. It uses HTTP DELETE
	 * method.
	 * 
	 * @param id
	 *            the primary key of the entity to be deleted.
	 * @throws CommonException
	 *             the common exception
	 */
	@ApiMethod(name = "removeReport")
	public void removeReport(@Named("id") String id) throws CommonException {
		ReportService service = ReportService.getInstance();
		service.delete(id);
	}
	
	@ApiMethod(name = "searchReports", httpMethod = HttpMethod.GET, path = "searchReports")
	public CollectionResponse<Report> searchReports(@Nullable @Named("querySearch") String querySearch,
			@Nullable @Named("cursor") String cursorString, @Nullable @Named("count") Integer count) throws CommonException {
		ReportService service = ReportService.getInstance();
		return service.searchReports(querySearch, cursorString, count);
	}

}
