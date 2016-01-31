package com.ltu.yealtube.entity;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class TubeStatistics {

	@Id
	private Long id;
	
	@Index
	private String videoId;
	
	@Index
	private Date createdAt;
	
	@Index
	private int viewCount;
	
	@Index
	private int likeCount;
	
	@Index
	private int dislikeCount;
	
	@Index
	private int favoriteCount;
	
	@Index
	private int commentCount;
	
	@Index
	private float rating;
	
	@Index
	private int status;
	
	@Index
	private Date publishedAt;
	
}
