package com.ducnh.oauth2_server.model.constants;

public enum ResourceState {
	META(1),
	SUMMARY(2),
	DETAIL(3);
	private Integer id;
	
	private ResourceState(Integer id) {
		this.id = id;
	}
	
	public static ResourceState create(Integer id) {
		ResourceState[] states = ResourceState.values();
		for (ResourceState state : states) {
			if (state.getId() == id) {
				return state;
			}
		}
		return null;
	}
	
	public Integer getId() {
		return this.id;
	}
}
