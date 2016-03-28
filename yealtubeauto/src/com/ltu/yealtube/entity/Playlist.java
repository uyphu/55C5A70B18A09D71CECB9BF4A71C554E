package com.ltu.yealtube.entity;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Playlist {

	@Id
	private String id;

	@Index
	private String title;

	private String description;

	@Index
	private int viewCount;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
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

	public Playlist(String id, String title, String description, int viewCount, Date createdAt, Date modifiedAt,
			Date publishedAt, int status) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.viewCount = viewCount;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.publishedAt = publishedAt;
		this.status = status;
	}

	public Playlist() {

	}

	@Override
	public String toString() {
		return "Playlist [id=" + id + ", title=" + title + ", description=" + description + ", viewCount=" + viewCount
				+ ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt + ", publishedAt=" + publishedAt + ", status="
				+ status + "]";
	}

}
