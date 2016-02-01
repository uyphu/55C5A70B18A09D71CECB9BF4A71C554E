package com.ltu.yealtube.endpoints;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.ltu.yealtube.entity.Tube;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.service.TubeService;

@Api(name = "tubeendpoint", namespace = @ApiNamespace(ownerDomain = "ltu.com", ownerName = "ltu.com", packagePath = "yealtube.entity"))
public class TubeEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
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
	 */
	@ApiMethod(name = "insertTube")
	public Tube insertTube(Tube tube) throws CommonException {
		TubeService service = TubeService.getInstance();
		return service.insert(tube);
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param tube the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateTube")
	public Tube updateTube(Tube tube) throws CommonException {
		TubeService service = TubeService.getInstance();
		return service.update(tube);
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeTube")
	public void removeTube(@Named("id") String id) throws CommonException {
		TubeService service = TubeService.getInstance();
		service.delete(id);
	}


}
