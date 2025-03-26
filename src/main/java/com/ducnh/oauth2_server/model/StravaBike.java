package com.ducnh.oauth2_server.model;

import com.ducnh.oauth2_server.model.constants.ResourceState;

public class StravaBike {
	private String id;
	private Boolean primary;
	private String name;
	private ResourceState resourceState;
	private Long distance;
	
	public StravaBike() {}
	
	public StravaBike(String id, Boolean primary, String name, int resourceState, Long distance) {
		this.id = id;
		this.primary = primary;
		this.name = name;
		this.resourceState = ResourceState.create(resourceState);
		this.distance = distance;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}
	
	public Boolean isPrimary() {
		return this.primary;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ResourceState getResourceState() {
		return this.resourceState;
	}
	
	public void setResourceState(Integer id) {
		this.resourceState = ResourceState.create(id);
	}
	
	public Long getDistance() {
		return this.distance;
	}
	
	public void setDistance(Long distance) {
		this.distance = distance;
	}
}
