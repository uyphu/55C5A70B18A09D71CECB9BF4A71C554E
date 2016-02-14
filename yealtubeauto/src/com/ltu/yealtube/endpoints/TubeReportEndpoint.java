package com.ltu.yealtube.endpoints;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.client.http.HttpMethods;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.ltu.yealtube.entity.TubeReport;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.service.TubeReportService;

@Api(name = "tubereportendpoint", namespace = @ApiNamespace(ownerDomain = "ltu.com", ownerName = "ltu.com", packagePath="yealtube.entity"))
public class TubeReportEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @param cursorString the cursor string
	 * @param limit the limit
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@ApiMethod(name = "listTubeReport")
	public CollectionResponse<TubeReport> listTubeReport(@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {
		TubeReportService service = TubeReportService.getInstance();
		return service.list(cursorString, limit);
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getTubeReport")
	public TubeReport getTubeReport(@Named("id") String id) {
		TubeReportService service = TubeReportService.getInstance();
		return service.find(id);
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param tube the entity to be inserted.
	 * @return The inserted entity.
	 * @throws CommonException the common exception
	 */
	@ApiMethod(name = "insertTubeReport")
	public TubeReport insertTubeReport(TubeReport tube) throws CommonException {
		TubeReportService service = TubeReportService.getInstance();
		return service.insert(tube);
	}
	
	/**
	 * Insert from youtube.
	 *
	 * @param id the id
	 * @return the tube
	 * @throws CommonException the common exception
	 */
	@ApiMethod(name = "insertFromYoutube", httpMethod=HttpMethods.POST, path="insertFromYoutube")
	public TubeReport insertFromYoutube(@Named("id") String id, @Named("status") Integer status) throws CommonException {
		TubeReportService service = TubeReportService.getInstance();
		return service.insert(id, status);
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param tube the entity to be updated.
	 * @return The updated entity.
	 * @throws CommonException the common exception
	 */
	@ApiMethod(name = "updateTubeReport")
	public TubeReport updateTubeReport(TubeReport tube) throws CommonException {
		TubeReportService service = TubeReportService.getInstance();
		return service.update(tube);
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 * @throws CommonException the common exception
	 */
	@ApiMethod(name = "removeTubeReport")
	public void removeTubeReport(@Named("id") String id) throws CommonException {
		TubeReportService service = TubeReportService.getInstance();
		service.delete(id);
	}
	
	/**
	 * Clean data.
	 *
	 * @throws CommonException the common exception
	 */
	@ApiMethod(name = "cleanData", httpMethod=HttpMethods.POST, path="cleanData")
	public void cleanData() throws CommonException {
		TubeReportService service = TubeReportService.getInstance();
		service.cleanData();
	}

}
