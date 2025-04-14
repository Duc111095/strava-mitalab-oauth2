package com.ducnh.oauth2_server.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.springframework.http.ResponseEntity;

import com.ducnh.oauth2_server.model.constants.MeasurementPreference;
import com.ducnh.oauth2_server.model.constants.ResourceState;
import com.ducnh.oauth2_server.model.constants.StravaGender;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "strava_user")
public class AthleteUser {
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
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
    @Enumerated(EnumType.STRING)
	private StravaGender sex;
	
	@JsonProperty("premium")
	@Column(nullable=true)
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
	private Long weight;
	
	private StravaClub[] clubs;
	private StravaBike[] bikes;
	private StravaShoe[] shoes;
	
	@Override
	public String toString() {
		return "ID: " + String.valueOf(this.id) + " - " + this.firstName + " " + this.lastName;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public ResourceState getResourceState() {
		return this.resourceState;
	}
	
	public void setResourceState(Integer id) {
		this.resourceState = ResourceState.create(id);
	}
	
	public String getCity() {
		return this.city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return this.state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getCountry() {
		return this.country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public StravaGender getSex() {
		return this.sex;
	}
	
	public void setSex(String gender) {
		this.sex = StravaGender.create(gender);
	}
	
	public boolean getPremium() {
		return this.premium;
	}
	
	public void setPremium(boolean premium) {
		this.premium = premium;
	}
	
	public LocalDateTime getCreatedAt() {
		return this.createdAt;
	}
	
	public void setCreatedAt(String createdAt) {
		this.createdAt = LocalDateTime.parse(createdAt, formatter);
	}
	
	public LocalDateTime getUpdatedAt() {
		return this.updatedAt;
	}
	
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = LocalDateTime.parse(updatedAt, formatter);
	}
	
	public Integer getBadgeTypeId() {
		return this.badgeTypeId;
	}
	
	public void setBadgeTypeId(Integer badgeTypeId) {
		this.badgeTypeId = badgeTypeId;
	}
	
	public String getProfileMedium() {
		return this.profileMedium;
	}
	
	public void setProfileMedium(String profileMedium) {
		this.profileMedium = profileMedium;
	}

	public String getProfile() {
		return this.profile;
	}
	
	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	public String getFriend() {
		return this.friend;
	}
	
	public void setFriend(String friend) {
		this.friend = friend;
	}
	
	public String getFollower() {
		return this.follower;
	}
	
	public void setFollower(String follower) {
		this.follower = follower;
	}
	
	public Integer getFriendCount() {
		return this.friendCount;
	}
	
	public void setFriendCount(Integer friendCount) {
		this.friendCount = friendCount;
	}
	
	public Integer getMutualFriendCount() {
		return this.mutualFriendCount;
	}
	
	public void setMutualFriendCount(Integer mutualFriendCount) {
		this.mutualFriendCount = mutualFriendCount;
	}
	
	public Integer getAthleteType() {
		return this.athleteType;
	}
	
	public void setAthleteType(Integer athleteType) {
		this.athleteType = athleteType;
	}
	public String getDatePreference() {
		return this.datePreference;
	}

	public void setDatePreference(String datePreference) {
		this.datePreference = datePreference;
	}
	
	public Integer getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(Integer followerCount) {
		this.followerCount = followerCount;
	}
	
	public MeasurementPreference getMeasurementPreference() {
		return this.measurementPreference;
	}
	
	public void setMeasurementPreference(String measurementPreference) {
		this.measurementPreference = MeasurementPreference.create(measurementPreference);
	}
	
	public Integer getFtp() {
		return this.ftp;
	}
	
	public void setFtp(Integer ftp) {
		this.ftp = ftp;
	}
	
	public Long getWeight() {
		return this.weight;
	}
	
	public void setWeight(Long weight) {
		this.weight = weight;
	}
	
	public String getName() {
		return this.firstName + " " + this.lastName;
	}

	public static AthleteUser createFromJsonString(JsonNode root) throws Exception{
		AthleteUser user = new AthleteUser();
		return createFromJsonString(root, user);
	}

	public static AthleteUser createFromJsonString(JsonNode root, AthleteUser user) throws Exception{
		user.setId(root.get("id") == null ? null : root.get("id").asLong());
		user.setUsername(root.get("username")== null ? null : root.get("username").asText());
		user.setResourceState(root.get("resource_state")== null ? null : root.get("resource_state").asInt());
		user.setFirstName(root.get("firstname")== null ? null : root.get("firstname").asText());
		user.setLastName(root.get("lastname")== null ? null : root.get("lastname").asText());
		user.setCity(root.get("city")== null ? null : root.get("city").asText());
		user.setState(root.get("state")== null ? null : root.get("state").asText());
		user.setCountry(root.get("country")== null ? null : root.get("country").asText());
		user.setSex(root.get("sex")== null ? null : root.get("sex").asText());
		user.setPremium(root.get("premium")== null ? null : root.get("premium").asBoolean());
		user.setCreatedAt(root.get("created_at")== null ? null :root.get("created_at").asText());
		user.setUpdatedAt(root.get("updated_at")== null ? null :root.get("updated_at").asText());
		user.setBadgeTypeId(root.get("badge_type_id")== null ? null :root.get("badge_type_id").asInt());
		user.setWeight(root.get("weight")== null ? null :root.get("weight").asLong());
		user.setProfileMedium(root.get("profile_medium")== null ? null :root.get("profile_medium").asText());
		user.setProfile(root.get("profile")== null ? null :root.get("profile").asText());
		user.setFriend(root.get("friend")== null ? null :root.get("friend").asText());
		user.setFollower(root.get("follower")== null ? null :root.get("follower").asText());
		return user;
	}

    public static AthleteUser createFromJsonMapStringObject(ResponseEntity<Map<String, Object>> response) {
		Map<String, Object> bodyMap = response.getBody();
		AthleteUser user = new AthleteUser();
		user.setId(Long.parseLong(bodyMap.get("id").toString()));
		user.setUsername(bodyMap.get("username") != null ? bodyMap.get("username").toString() : null);
		user.setResourceState(bodyMap.get("resource_state") != null ? Integer.parseInt(bodyMap.get("resource_state").toString()) : null);
		user.setFirstName(bodyMap.get("firstname") != null ? bodyMap.get("firstname").toString() : null);
		user.setLastName(bodyMap.get("lastname") != null ? bodyMap.get("lastname").toString() : null);
		user.setCity(bodyMap.get("city") != null ? bodyMap.get("city").toString() : null);
		user.setState(bodyMap.get("state") != null ? bodyMap.get("state").toString() : null);
		user.setCountry(bodyMap.get("country") != null ? bodyMap.get("country").toString() : null);
		user.setSex(bodyMap.get("sex") != null ? bodyMap.get("sex").toString() : null);
		user.setPremium(bodyMap.get("premium") != null ? Boolean.parseBoolean(bodyMap.get("premium").toString()) : false);
		user.setCreatedAt(bodyMap.get("created_at") != null ? bodyMap.get("created_at").toString() : null);
		user.setUpdatedAt(bodyMap.get("updated_at") != null ? bodyMap.get("updated_at").toString() : null);
		user.setBadgeTypeId(bodyMap.get("badge_type_id") != null ? Integer.parseInt(bodyMap.get("badge_type_id").toString()) : null);
		//user.setWeight(bodyMap.get("weight") != null ? (Long) bodyMap.get("weight") : null);
		user.setProfileMedium(bodyMap.get("profile_medium") != null ? bodyMap.get("profile_medium").toString() : null);
		user.setProfile(bodyMap.get("profile") != null ? bodyMap.get("profile").toString() : null);
		user.setFriend(bodyMap.get("friend") != null ? bodyMap.get("friend").toString() : null);
		user.setFollower(bodyMap.get("follower") != null ? bodyMap.get("follower").toString() : null);
		return user;
	}
}
