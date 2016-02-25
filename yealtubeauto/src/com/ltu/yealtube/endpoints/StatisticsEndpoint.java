package com.ltu.yealtube.endpoints;

import java.util.Calendar;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.client.http.HttpMethods;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.ltu.yealtube.entity.Statistics;
import com.ltu.yealtube.exeptions.CommonException;
import com.ltu.yealtube.service.StatisticsService;

/**
 * The Class StatisticsEndpoint.
 */
@Api(name = "statisticsendpoint", namespace = @ApiNamespace(ownerDomain = "ltu.com", ownerName = "ltu.com", packagePath = "yealstatistics.entity"))
public class StatisticsEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @param cursorString the cursor string
	 * @param limit the limit
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@ApiMethod(name = "listStatistics")
	public CollectionResponse<Statistics> listStatistics(@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {
		StatisticsService service = StatisticsService.getInstance();
		return service.list(cursorString, limit);
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getStatistics")
	public Statistics getStatistics(@Named("id") String id) {
		StatisticsService service = StatisticsService.getInstance();
		return service.find(id);
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param statistics the entity to be inserted.
	 * @return The inserted entity.
	 * @throws CommonException the common exception
	 */
	@ApiMethod(name = "insertStatistics")
	public Statistics insertStatistics(Statistics statistics) throws CommonException {
		StatisticsService service = StatisticsService.getInstance();
		return service.insert(statistics);
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param statistics the entity to be updated.
	 * @return The updated entity.
	 * @throws CommonException the common exception
	 */
	@ApiMethod(name = "updateStatistics")
	public Statistics updateStatistics(Statistics statistics) throws CommonException {
		StatisticsService service = StatisticsService.getInstance();
		return service.update(statistics);
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 * @throws CommonException the common exception
	 */
	@ApiMethod(name = "removeStatistics")
	public void removeStatistics(@Named("id") String id) throws CommonException {
		StatisticsService service = StatisticsService.getInstance();
		service.delete(id);
	}
	
	/**
	 * List by parent.
	 *
	 * @param parentId the parent id
	 * @param cursorString the cursor string
	 * @param limit the limit
	 * @return the collection response
	 */
	@ApiMethod(name = "listByParent", httpMethod=HttpMethods.GET, path="listByParent")
	public CollectionResponse<Statistics> listByParent(@Named("parentId") String parentId, @Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {
		StatisticsService service = StatisticsService.getInstance();
		return service.listByParent(parentId, cursorString, limit);
	}
	
	/**
	 * List by parent.
	 *
	 * @param parentId the parent id
	 * @return the statistics
	 */
	@ApiMethod(name = "getAverage", httpMethod=HttpMethods.GET, path="getAverage")
	public Statistics getAverage(@Named("parentId") String parentId) {
		StatisticsService service = StatisticsService.getInstance();
		Integer viewCount = service.getAverage(parentId);
		Statistics statistics = new Statistics();
		statistics.setId(1L);
		statistics.setCreatedAt(Calendar.getInstance().getTime());
		statistics.setViewCount(viewCount);
		return statistics;
	}
	
	/**
	 * Insert from youtube.
	 *
	 * @param videoId the video id
	 * @return the statistics
	 * @throws CommonException the common exception
	 */
	@ApiMethod(name = "insertFromYoutube", httpMethod=HttpMethods.POST, path="insertFromYoutube")
	public Statistics insertFromYoutube(@Named("videoId") String videoId) throws CommonException {
		StatisticsService service = StatisticsService.getInstance();
		return service.insert(videoId);
	}
	

}
