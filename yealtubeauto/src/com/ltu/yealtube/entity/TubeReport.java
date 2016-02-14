package com.ltu.yealtube.entity;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class TubeReport {

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
	
	/** The published at. */
	@Index
	private Date publishedAt;
	
	/** The status. */
	@Index
	private int status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public TubeReport() {
		
	}
	
	public TubeReport(String id, String title, int viewCount, String description, Date createdAt, Date publishedAt, int status) {
		this.id = id;
		this.title = title;
		this.viewCount = viewCount;
		this.description = description;
		this.createdAt = createdAt;
		this.publishedAt = publishedAt;
		this.status = status;
	}
	
	public TubeReport(Tube tube) {
		this.id = tube.getId();
		this.title = tube.getTitle();
		this.viewCount = tube.getViewCount();
		this.description = tube.getDescription();
		this.createdAt = tube.getCreatedAt();
		this.publishedAt = tube.getPublishedAt();
		this.status = tube.getStatus();
	}

	@Override
	public String toString() {
		return "TubeReport [id=" + id + ", title=" + title + ", viewCount=" + viewCount + ", description=" + description
				+ ", createdAt=" + createdAt + ", publishedAt=" + publishedAt + ", status=" + status + "]";
	}
	
}
