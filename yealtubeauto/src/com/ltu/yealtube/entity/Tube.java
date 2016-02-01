package com.ltu.yealtube.entity;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

public class Tube {

	@Id
	private String id;
	
	@Index
	private Long userId;
	
	@Index
	private String name;
	
	@Index
	private String description;
}
