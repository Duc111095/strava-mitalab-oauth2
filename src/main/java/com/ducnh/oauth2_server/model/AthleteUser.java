package com.ducnh.oauth2_server.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.ducnh.oauth2_server.model.constants.MeasurementPreference;
import com.ducnh.oauth2_server.model.constants.ResourceState;
import com.ducnh.oauth2_server.model.constants.StravaGender;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "strava_user")
public class AthleteUser {
	
	@Id
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("username")
	private String username;
	
	@JsonProperty("resource_state")
    @Enumerated(EnumType.STRING)
	private ResourceState resourceState;
	@JsonProperty("firstname")
	private String firstName;
	
	@JsonProperty("lastname")
	private String lastName;
	
	@JsonProperty("city")
	private String city;
	
	@JsonProperty("state")
	private String state;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("sex")
	private StravaGender sex;
	
	@JsonProperty("premium")
	private boolean premium;
	
	@JsonProperty("created_at")
	private LocalDateTime createdAt;
	
	@JsonProperty("updated_at")
	private LocalDateTime updatedAt;
	
	@JsonProperty("badge_type_id")
	private Integer badgeTypeId;
	
	@JsonProperty("profile_medium")
	private String profileMedium;
	
	@JsonProperty("profile")
	private String profile;
	
	@JsonProperty("friend")
	private String friend;

	@JsonProperty("follower")
	private String follower;
	
	private Integer followerCount;
	private Integer friendCount;
	private Integer mutualFriendCount;
	private Integer athleteType;
	private String datePreference;
	
    @Enumerated(EnumType.STRING)
	private MeasurementPreference measurementPreference;
	private Integer ftp;
	
	@JsonProperty("weight")
	private Float weight;
	
	private StravaClub[] clubs;
	private StravaBike[] bikes;
	private StravaShoe[] shoes;
	
	@Override
	public String toString() {
		return "ID: " + String.valueOf(this.id) + " - " + this.firstName + " " + this.lastName;
	}
}
