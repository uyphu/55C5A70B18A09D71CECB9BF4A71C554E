package com.ltu.yealtube.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Tube {

	@Id
	private String id;
	
	@Index
	private Long userId;
	
	@Index
	private String name;
	
	@Index
	private String description;
	
	@Index
	private int viewCount;
	
	@IgnoreSave
	private int likeCount;
	
	@IgnoreSave
	private int dislikeCount;
	
	@IgnoreSave
	private int favoriteCount;
	
	@IgnoreSave
	private int commentCount;
	
	@IgnoreSave
	private float rating;
	
	@IgnoreSave
	private String author;
	
	@Index
	private String tags;
	
//	@Load
//    private List<Key<Category>> categoryKeys;
	
	/** The categories. */
	@IgnoreSave
	private List<String> categories;
	
	@Index
	private int status;
	
	@Index
	private Date dateAdded;
	
	private Date dateModified;
	

	public final String getId() {
		return this.id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final Long getUserId() {
		return this.userId;
	}

	public final void setUserId(Long userId) {
		this.userId = userId;
	}

//	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE) 
//	public final List<Key<Category>> getCategoryKeys() {
//		return this.categoryKeys;
//	}
//
//	public final void setCategoryKeys(List<Key<Category>> categoryKeys) {
//		this.categoryKeys = categoryKeys;
//	}

	public final List<String> getCategories() {
		return this.categories;
	}

	public final void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public final String getName() {
		return this.name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getDescription() {
		return this.description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final int getStatus() {
		return this.status;
	}

	public final void setStatus(int status) {
		this.status = status;
	}

	public final Date getDateAdded() {
		return this.dateAdded;
	}

	public final void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public final Date getDateModified() {
		return this.dateModified;
	}

	public final void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	
	public final int getViewCount() {
		return this.viewCount;
	}

	public final void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public final int getLikeCount() {
		return this.likeCount;
	}

	public final void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public final int getDislikeCount() {
		return this.dislikeCount;
	}

	public final void setDislikeCount(int dislikeCount) {
		this.dislikeCount = dislikeCount;
	}

	public final int getFavoriteCount() {
		return this.favoriteCount;
	}

	public final void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public final int getCommentCount() {
		return this.commentCount;
	}

	public final void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public final float getRating() {
		return this.rating;
	}

	public final void setRating(float rating) {
		this.rating = rating;
	}
	
	public final String getAuthor() {
		return this.author;
	}

	public final void setAuthor(String author) {
		this.author = author;
	}
	
	public final String getTags() {
		return this.tags;
	}

	public final void setTags(String tags) {
		this.tags = tags;
	}

	public Tube() {
		
	}
	
	public Tube(String id, Long userId, String name, String description, int status) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.description = description;
		this.status = status;
		this.dateAdded = Calendar.getInstance().getTime();
		this.dateModified = Calendar.getInstance().getTime();
	}

	public Tube(String id, Long userId, String name, String description, int status, Date dateAdded,
			Date dateModified) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.description = description;
		this.status = status;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
	}
	
	public Tube(Long userId, String name, String description, int status, Date dateAdded,
			Date dateModified) {
		this.userId = userId;
		this.name = name;
		this.description = description;
		this.status = status;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
	}

	@Override
	public String toString() {
		return "Tube [id=" + id + ", userId=" + userId + ", name=" + name
				+ ", description=" + description + ", viewCount=" + viewCount
				+ ", likeCount=" + likeCount + ", dislikeCount=" + dislikeCount
				+ ", favoriteCount=" + favoriteCount + ", commentCount="
				+ commentCount + ", rating=" + rating + ", author=" + author
				+ ", tags=" + tags + ", categories=" + categories + ", status="
				+ status + ", dateAdded=" + dateAdded + ", dateModified="
				+ dateModified + "]";
	}

//	@Override
//	public String toString() {
//		return "Tube [id=" + id + ", userId=" + userId + ", name=" + name
//				+ ", description=" + description + ", viewCount=" + viewCount
//				+ ", likeCount=" + likeCount + ", dislikeCount=" + dislikeCount
//				+ ", favoriteCount=" + favoriteCount + ", commentCount="
//				+ commentCount + ", rating=" + rating + ", author=" + author
//				+ ", tags=" + tags + ", categoryKeys=" + categoryKeys
//				+ ", categories=" + categories + ", status=" + status
//				+ ", dateAdded=" + dateAdded + ", dateModified=" + dateModified
//				+ "]";
//	}
	
}
