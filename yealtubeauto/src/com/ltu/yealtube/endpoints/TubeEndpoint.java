package com.ltu.yealtube.endpoints;

import java.util.Date;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.client.http.HttpMethods;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.CollectionResponse;
import com.ltu.yealtube.entity.Tube;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.service.TubeService;

/**
 * The Class TubeEndpoint.
 * uyphu
 */
@Api(name = "tubeendpoint", namespace = @ApiNamespace(ownerDomain = "ltu.com", ownerName = "ltu.com", packagePath = "yealtube.entity"))
public class TubeEndpoint {
	
	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @param cursorString the cursor string
	 * @param limit the limit
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@ApiMethod(name = "listTube")
	public CollectionResponse<Tube> listTube(@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {
		TubeService service = TubeService.getInstance();
		return service.list(cursorString, limit);
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getTube")
	public Tube getTube(@Named("id") String id) {
		TubeService service = TubeService.getInstance();
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
	@ApiMethod(name = "insertTube")
	public Tube insertTube(Tube tube) throws CommonException {
		TubeService service = TubeService.getInstance();
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
	public Tube insertFromYoutube(@Named("id") String id) throws CommonException {
		TubeService service = TubeService.getInstance();
		return service.insert(id);
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
	@ApiMethod(name = "updateTube")
	public Tube updateTube(Tube tube) throws CommonException {
		TubeService service = TubeService.getInstance();
		return service.update(tube);
	}
	
	/**
	 * Update tube.
	 *
	 * @param id the id
	 * @param status the status
	 * @return the tube
	 * @throws CommonException the common exception
	 */
	@ApiMethod(name = "updateStatus", httpMethod=HttpMethods.POST, path="updateStatus")
	public Tube updateStatus(@Named("id") String id, @Named("status") Integer status) throws CommonException {
		TubeService service = TubeService.getInstance();
		return service.update(id, status);
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 * @throws CommonException the common exception
	 */
	@ApiMethod(name = "removeTube")
	public void removeTube(@Named("id") String id) throws CommonException {
		TubeService service = TubeService.getInstance();
		service.deleteWithChildren(id);
	}
	
	/**
	 * Clean data.
	 *
	 * @throws CommonException the common exception
	 */
	@ApiMethod(name = "cleanData", httpMethod=HttpMethods.POST, path="cleanData")
	public void cleanData() throws CommonException {
		TubeService service = TubeService.getInstance();
		service.cleanData();
	}
	
	/**
	 * Clean data by status.
	 *
	 * @param status the status
	 * @throws CommonException the common exception
	 */
	@ApiMethod(name = "cleanDataByStatus", httpMethod=HttpMethods.POST, path="cleanDataByStatus")
	public void cleanDataByStatus(@Named("status") Integer status) throws CommonException {
		TubeService service = TubeService.getInstance();
		service.cleanData(status);
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
	@ApiMethod(name = "searchTubes", httpMethod = HttpMethod.GET, path = "searchTubes")
	public CollectionResponse<Tube> searchTubes(@Nullable @Named("querySearch") String querySearch,
			@Nullable @Named("cursor") String cursorString, @Nullable @Named("count") Integer count) throws CommonException {
		TubeService service = TubeService.getInstance();
		return service.searchTubes(querySearch, cursorString, count);
	}
	
	
	/**
	 * Search tubes by status.
	 *
	 * @param field the field
	 * @param status the status
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 * @throws CommonException the common exception
	 */
	@ApiMethod(name = "searchTubesByStatus", httpMethod = HttpMethod.GET, path = "searchTubesByStatus")
	public CollectionResponse<Tube> searchTubesByStatus(@Named("field") String field, @Named("status") Integer status,
			@Nullable @Named("cursor") String cursorString, @Nullable @Named("count") Integer count) throws CommonException {
		TubeService service = TubeService.getInstance();
		return service.searchTubes(field, status, cursorString, count);
	}
	
	/**
	 * Search by date.
	 *
	 * @param createAt the create at
	 * @param status the status
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 * @throws CommonException the common exception
	 */
	@ApiMethod(name = "searchByDate", httpMethod = HttpMethod.GET, path = "searchByDate")
	public CollectionResponse<Tube> searchByDate(@Named("createAt") Date createAt, @Named("status") Integer status,
			@Nullable @Named("cursor") String cursorString, @Nullable @Named("count") Integer count) throws CommonException {
		TubeService service = TubeService.getInstance();
		return service.searchTubes(createAt, status, cursorString, count);
	}
}
