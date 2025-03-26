package com.ducnh.oauth2_server.model;

import com.ducnh.oauth2_server.model.constants.ResourceState;

public class StravaShoe {
	private String id;
	private Boolean primary;
	private String name;
	private ResourceState resourceState;
	private Long distance;
	
	public StravaShoe() {}
	
	public StravaShoe(String id, Boolean primary, String name, Integer resourceId, Long distance) {
		this.id = id;
		this.primary = primary;
		this.name = name;
		this.resourceState = ResourceState.create(resourceId);
		this.distance = distance;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public Boolean isPrimary() {
		return this.primary;
	}
	
	public void setPrimary(boolean primary) {
		this.primary = primary;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ResourceState getResourceState() {
		return this.resourceState;
	}
	
	public void setResourceState(Integer resourceId) {
		this.resourceState = ResourceState.create(resourceId);
	}
	
	public Long getDistance() {
		return this.distance;
	}
	
	public void setDistance(Long distance) {
		this.distance = distance;
	}
}
