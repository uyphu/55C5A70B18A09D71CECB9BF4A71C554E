package com.ltu.yealtube.entity;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * The Class Statistics.
 * @author uyphu
 */
@Entity
public class Statistics {

	/** The id. */
	@Id
	private Long id;
	
	/** The video id. */
	@Index
	private String videoId;
	
	/** The created at. */
	@Index
	private Date createdAt;
	
	/** The view count. */
	private int viewCount;
	
	/** The like count. */
	private int likeCount;
	
	/** The dislike count. */
	private int dislikeCount;
	
	/** The favorite count. */
	private int favoriteCount;
	
	/** The comment count. */
	private int commentCount;
	
	/** The rating. */
	private float rating;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public final Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public final void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the video id.
	 *
	 * @return the video id
	 */
	public final String getVideoId() {
		return videoId;
	}

	/**
	 * Sets the video id.
	 *
	 * @param videoId the new video id
	 */
	public final void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	/**
	 * Gets the created at.
	 *
	 * @return the created at
	 */
	public final Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the created at.
	 *
	 * @param createdAt the new created at
	 */
	public final void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the view count.
	 *
	 * @return the view count
	 */
	public final int getViewCount() {
		return viewCount;
	}

	/**
	 * Sets the view count.
	 *
	 * @param viewCount the new view count
	 */
	public final void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	/**
	 * Gets the like count.
	 *
	 * @return the like count
	 */
	public final int getLikeCount() {
		return likeCount;
	}

	/**
	 * Sets the like count.
	 *
	 * @param likeCount the new like count
	 */
	public final void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	/**
	 * Gets the dislike count.
	 *
	 * @return the dislike count
	 */
	public final int getDislikeCount() {
		return dislikeCount;
	}

	/**
	 * Sets the dislike count.
	 *
	 * @param dislikeCount the new dislike count
	 */
	public final void setDislikeCount(int dislikeCount) {
		this.dislikeCount = dislikeCount;
	}

	/**
	 * Gets the favorite count.
	 *
	 * @return the favorite count
	 */
	public final int getFavoriteCount() {
		return favoriteCount;
	}

	/**
	 * Sets the favorite count.
	 *
	 * @param favoriteCount the new favorite count
	 */
	public final void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	/**
	 * Gets the comment count.
	 *
	 * @return the comment count
	 */
	public final int getCommentCount() {
		return commentCount;
	}

	/**
	 * Sets the comment count.
	 *
	 * @param commentCount the new comment count
	 */
	public final void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	/**
	 * Gets the rating.
	 *
	 * @return the rating
	 */
	public final float getRating() {
		return rating;
	}

	/**
	 * Sets the rating.
	 *
	 * @param rating the new rating
	 */
	public final void setRating(float rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Statistics [id=" + id + ", videoId=" + videoId + ", createdAt="
				+ createdAt + ", viewCount=" + viewCount + ", likeCount="
				+ likeCount + ", dislikeCount=" + dislikeCount
				+ ", favoriteCount=" + favoriteCount + ", commentCount="
				+ commentCount + ", rating=" + rating + "]";
	}

	/**
	 * Instantiates a new statistics.
	 *
	 * @param videoId the video id
	 * @param viewCount the view count
	 * @param likeCount the like count
	 * @param dislikeCount the dislike count
	 * @param favoriteCount the favorite count
	 * @param commentCount the comment count
	 * @param rating the rating
	 */
	public Statistics(String videoId, int viewCount,
			int likeCount, int dislikeCount, int favoriteCount,
			int commentCount, float rating) {
		this.videoId = videoId;
		this.viewCount = viewCount;
		this.likeCount = likeCount;
		this.dislikeCount = dislikeCount;
		this.favoriteCount = favoriteCount;
		this.commentCount = commentCount;
		this.rating = rating;
	}
	
}
