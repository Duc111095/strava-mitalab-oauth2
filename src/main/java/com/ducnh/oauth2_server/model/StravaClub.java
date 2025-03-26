package com.ducnh.oauth2_server.model;

import com.ducnh.oauth2_server.model.constants.ResourceState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StravaClub {
	private Long id;
	private ResourceState resourceState;
	private String name;
	private String profileMedium;
	private String profile;
	private String coverPhoto;
	private String coverPhotoSmall;
	private String sportType;
	private String city;
	private String state;
	private String country;
	private Integer memberCount;
	private Boolean featured;
	private Boolean verified;
	private String url;
	private String membership;
	private boolean admin;
	private boolean owner;
	private Integer followingCount;
}
