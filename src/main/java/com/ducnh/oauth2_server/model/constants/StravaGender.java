package com.ducnh.oauth2_server.model.constants;

public enum StravaGender {

	MALE("M"),
	FEMALE("F");

	private String id;
	
	private StravaGender(String id) {
		this.id = id;
	}
	
	public static StravaGender create(final String id) {
		StravaGender[] genders = StravaGender.values();
		for (StravaGender gender : genders) {
			if (gender.getId().equals(id)) {
				return gender;
			}
		}
		return null;
	}

	public String getId() {
		return this.id;
	}
}
