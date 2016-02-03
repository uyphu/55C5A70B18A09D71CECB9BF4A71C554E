package com.ltu.yealtube.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;
import com.ltu.yealtube.dao.StatisticsDao;

/**
 * The Class Tube.
 * @author uyphu
 */
@Entity
public class Tube {

	/** The id. */
	@Id
	private String id;
	
	/** The name. */
	@Index
	private String title;
	
	@Index
	private int viewCount;
	
	/** The description. */
	//@Index
	private String description;
	
	/** The created at. */
	@Index
	private Date createdAt;
	
	/** The modified at. */
	@Index
	private Date modifiedAt;
	
	/** The published at. */
	@Index
	private Date publishedAt;
	
	/** The status. */
	@Index
	private int status;
	
	/** The statistic keys. */
	@Load
    private List<Key<Statistics>> statisticKeys;
	
	/** The statistics. */
	@IgnoreSave
	private List<Statistics> statistics;
	
	/**
	 * On load.
	 */
	@OnLoad
	private void onLoad() {
		StatisticsDao dao = StatisticsDao.getInstance();
		if (statisticKeys != null) {
			statistics = new ArrayList<Statistics>();
			for (Key<Statistics> key : statisticKeys) {
				Statistics item = dao.find(key.getId());
				if (item != null) {
					statistics.add(item);
				}
			}
		}
		
		statistics = dao.listByParent(id, null);
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the created at.
	 *
	 * @return the created at
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the created at.
	 *
	 * @param createdAt the new created at
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the published at.
	 *
	 * @return the published at
	 */
	public Date getPublishedAt() {
		return publishedAt;
	}

	/**
	 * Sets the published at.
	 *
	 * @param publishedAt the new published at
	 */
	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Gets the statistic keys.
	 *
	 * @return the statistic keys
	 */
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE) 
	public List<Key<Statistics>> getStatisticKeys() {
		return statisticKeys;
	}

	/**
	 * Sets the statistic keys.
	 *
	 * @param statisticKeys the new statistic keys
	 */
	public void setStatisticKeys(List<Key<Statistics>> statisticKeys) {
		this.statisticKeys = statisticKeys;
	}

	/**
	 * Gets the statistics.
	 *
	 * @return the statistics
	 */
	public List<Statistics> getStatistics() {
		return statistics;
	}

	/**
	 * Sets the statistics.
	 *
	 * @param statistics the new statistics
	 */
	public void setStatistics(List<Statistics> statistics) {
		this.statistics = statistics;
	}
	
	/**
	 * Gets the modified at.
	 *
	 * @return the modified at
	 */
	public final Date getModifiedAt() {
		return modifiedAt;
	}

	/**
	 * Sets the modified at.
	 *
	 * @param modifiedAt the new modified at
	 */
	public final void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
	
	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	/**
	 * Instantiates a new tube.
	 */
	public Tube() {
		
	}

	/**
	 * Instantiates a new tube.
	 *
	 * @param id the id
	 * @param title the title
	 * @param description the description
	 * @param publishedAt the published at
	 * @param status the status
	 * @param statisticKeys the statistic keys
	 */
	public Tube(String id, String title, String description, 
			Date publishedAt, int status, List<Key<Statistics>> statisticKeys) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.publishedAt = publishedAt;
		this.status = status;
		this.statisticKeys = statisticKeys;
	}
	

	/**
	 * Instantiates a new tube.
	 *
	 * @param id the id
	 * @param title the title
	 * @param description the description
	 * @param publishedAt the published at
	 * @param status the status
	 */
	public Tube(String id, String title, String description, 
			Date publishedAt, int status) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.publishedAt = publishedAt;
		this.status = status;
	}

	public Tube(String id, String title, int viewCount, String description, 
			Date publishedAt, int status) {
		this.id = id;
		this.title = title;
		this.viewCount = viewCount;
		this.description = description;
		this.publishedAt = publishedAt;
		this.status = status;
	}
	
}
