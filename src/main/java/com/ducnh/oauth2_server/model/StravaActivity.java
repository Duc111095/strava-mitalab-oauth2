package com.ducnh.oauth2_server.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ducnh.oauth2_server.model.constants.ResourceState;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "strava_activity")
public class StravaActivity {
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

	@Id
	private Long id;
	@Column(nullable = true)
	private ResourceState resourceState;
	@Column(nullable = true)
	private String externalId;
	@Column(nullable = true)
	private Long uploadId;
	private Long athleteId;
	@Column(nullable = true)
	private String name;
	@Column(nullable = true)
	private double distance;
	private int movingTime;
	private int elapsedTime;
	
	@Column(nullable = true)
	private double totalElevationGain;
	@Column(nullable = true)
	private double elevHigh;
	@Column(nullable = true)
	private double elevLow;
	@Column(nullable = true)
	private String sportType;
	
	@Column(nullable = false)	
	private LocalDateTime startDate;
	@Column(nullable = false)
	private LocalDateTime startDateLocal;
	@Column(nullable = true)
	private String timezone;
	@Column(nullable = true)
	private int achievementCount;
	@Column(nullable = true)
	private int kudosCount;
	@Column(nullable = true)
	private int commentCount;
	@Column(nullable = true)
	private int athleteCount;
	@Column(nullable = true)
	private int photoCount;
	@Column(nullable = true)
	private int totalPhotoCount;
	
	@ManyToOne
	@JoinColumn(name="map_id")
	private PolylineMap map; 
	@Column(nullable = true)
	private boolean isTrainer;
	@Column(nullable = true)
	private boolean isCommute;
	@Column(nullable = true)
	private boolean isManual;
	@Column(nullable = true)
	private boolean isPrivate;
	@Column(nullable = true)
	private boolean flagged;
	@Column(nullable = true)
	private int workoutType;
	@Column(nullable = true)
	private String uploadIdStr;
	@Column(nullable = true)
	private double averageSpeed;
	@Column(nullable = true)
	private double maxSpeed;
	@Column(nullable = true)
	private boolean hasKudoed;
	@Column(nullable = true)
	private boolean hideFromHome;
	@Column(nullable = true)
	private String gearId;
	@Column(nullable = true)
	private double kilojoules;
	@Column(nullable = true)
	private double averageWatts;
	@Column(nullable = true)
	private boolean deviceWatts;
	@Column(nullable = true)
	private int maxWatts;
	@Column(nullable = true)
	private int weightedAverageWatts;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setResourceState(int resourceState) {
		this.resourceState = ResourceState.create(resourceState);
	}
	
	public ResourceState getResourceState() {
		return this.resourceState;
	}
	
	public String getExternalId() {
		return this.externalId;
	}
	
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	public Long getUploadId() {
		return this.uploadId;
	}
	
	public void setUploadId(Long uploadId) {
		this.uploadId = uploadId;
	}
	
	public Long getAthleteId() {
		return this.athleteId;
	}
	
	public void setAthleteId(Long athleteId) {
		this.athleteId = athleteId;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getDistance() {
		return this.distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public int getMovingTime() {
		return this.movingTime;
	}
	
	public void setMovingTime(int movingTime) {
		this.movingTime = movingTime;
	}
	
	public int getElapsedTime() {
		return this.elapsedTime;
	}
	
	public void setElapsedTime(int elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	
	public double getToTalElevationGain() {
		return this.totalElevationGain;
	}
	
	public void setTotalElevationGain(double totalElevationGain) {
		this.totalElevationGain = totalElevationGain;
	}
	
	public double getElevHigh() {
		return this.elevHigh;
	}
	
	public void setElevHigh(double elevHigh) {
		this.elevHigh = elevHigh;
	}

	public double getElevLow() {
		return this.elevLow;
	}
	
	public void setElevLow(double elevLow) {
		this.elevLow = elevLow;
	}
	
	public String getSportType() {
		return this.sportType;
	}
	
	public void setSportType(String sportType) {
		this.sportType = sportType;
	}
	
	public LocalDateTime getStartDate() {
		return this.startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = LocalDateTime.parse(startDate, formatter);
	}
	
	public LocalDateTime getStartDateLocal() {
		return this.startDateLocal;
	}
	
	public void setStartDateLocal(String startDateLocal) {
		this.startDateLocal = LocalDateTime.parse(startDateLocal, formatter);
	}
	
	public String getTimezone() {
		return this.timezone;
	}
	
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	
	public int getAchievementCount() {
		return this.achievementCount;
	}
	
	public void setAchievementCount(int achievementCount) {
		this.achievementCount = achievementCount;
	}
	
	public int getKudosCount() {
		return this.kudosCount;
	}
	
	public void setKudosCount(int kudosCount) {
		this.kudosCount = kudosCount;
	}
	
	public int getCommentCount() {
		return this.commentCount;
	}
	
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	
	public int getAthleteCount() {
		return this.athleteCount;
	}
	
	public void setAthleteCount(int athleteCount) {
		this.athleteCount = athleteCount;
	}
	
	public int getPhotoCount() {
		return this.photoCount;
	}
	
	public void setPhotoCount(int photoCount) {
		this.photoCount = photoCount;
	}
	
	public int getTotalPhotoCount() {
		return this.totalPhotoCount;
	}
	
	public void setTotalPhotoCount(int totalPhotoCount) {
		this.totalPhotoCount = totalPhotoCount;
	}
	
	public PolylineMap getMap() {
		return this.map;
	}
	
	public void setMap(PolylineMap map) {
		this.map = map;
	}
	
	public boolean isTrainer() {
		return this.isTrainer;
	}
	
	public void setTrainer(boolean isTrainer) {
		this.isTrainer = isTrainer;
	}
	
	public boolean getCommute() {
		return this.isCommute;
	}
	
	public void setCommute(boolean isCommute) {
		this.isCommute = isCommute;
	}
	
	public boolean getManual() {
		return this.isManual;
	}
	
	public void setManual(boolean isManual) {
		this.isManual = isManual;
	}
	
	public boolean isPrivate() {
		return this.isPrivate;
	}
	
	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
	public boolean isFlagged() {
		return this.flagged;
	}
	
	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}
	
	public int getWorkoutType() {
		return this.workoutType;
	}
	
	public void setWorkoutType(int workoutType) {
		this.workoutType = workoutType;
	}
	
	public String getUploadIdStr() {
		return this.uploadIdStr;
	}
	
	public void setUploadIdStr(String uploadIdStr) {
		this.uploadIdStr = uploadIdStr;
	}
	
	public double getAverageSpeed() {
		return this.averageSpeed;
	}
	
	public void setAverageSpeed(double averageSpeed) {
		this.averageSpeed = averageSpeed;
	}
	
	public double getMaxSpeed() {
		return this.maxSpeed;
	}
	
	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
	public boolean hasKudoed() {
		return hasKudoed;
	}
	
	public void setHasKudoed(boolean hasKudoed) {
		this.hasKudoed = hasKudoed;
	}
	
	public boolean hideFromHome() {
		return this.hideFromHome;
	}
	
	public void setHideFromHome(boolean hideFromHome) {
		this.hideFromHome = hideFromHome;
	}
	
	public String getGearId() {
		return this.gearId;
	}
	
	public void setGearId(String gearId) {
		this.gearId = gearId;
	}

	public double getKilojoules() {
		return this.kilojoules;
	}
	
	public void setKilojoules(double kilojoules) {
		this.kilojoules = kilojoules;
	}
	
	public double getAverageWatts() {
		return this.averageWatts;
	}
	
	public void setAverageWatts(double averageWatts) {
		this.averageWatts = averageWatts;
	}
	
	public boolean isDeviceWatts() {
		return this.deviceWatts;
	}
	
	public void setDeviceWatts(boolean deviceWatts) {
		this.deviceWatts = deviceWatts;
	}

	public int getMaxWatts() {
		return this.maxWatts;
	}
	
	public void setMaxWatts(int maxWatts) {
		this.maxWatts = maxWatts;
	}
	
	public int getWeightedAverageWatts() {
		return this.weightedAverageWatts;
	}
	
	public void setWeightedAverageWatts(int weightedAverageWatts) {
		this.weightedAverageWatts = weightedAverageWatts;
	}
	
	public static StravaActivity createActivityFromResponse(JsonNode root) {
		StravaActivity activity = new StravaActivity();
		activity.setResourceState(root.get("resource_state") == null ? null : root.get("resource_state").asInt());
		activity.setAthleteId(root.get("athlete").get("id") == null ? null : root.get("athlete").get("id").asLong());
		activity.setName(root.get("name") == null ? null : root.get("name").asText());
		activity.setId(root.get("id")== null ? null :root.get("id").asLong());
		activity.setElapsedTime(root.get("elapsed_time")== null ? null :root.get("elapsed_time").asInt());
		activity.setMovingTime(root.get("moving_time")== null ? null :root.get("moving_time").asInt());
		activity.setStartDateLocal(root.get("start_date_local")== null ? null :root.get("start_date_local").asText());
		activity.setStartDate(root.get("start_date")== null ? null :root.get("start_date").asText());
		activity.setSportType(root.get("sport_type")== null ? null :root.get("sport_type").asText());
		activity.setTimezone(root.get("timezone")== null ? null :root.get("timezone").asText());
		PolylineMap map = new PolylineMap();
		map.setId(root.get("map").get("id")== null ? null :root.get("map").get("id").asText());
		map.setSummaryPolyline(root.get("map").get("summary_polyline")== null ? null :root.get("map").get("summary_polyline").asText());
		activity.setMap(map);
		activity.setGearId(root.get("gear_id")== null ? null :root.get("gear_id").asText());
		activity.setUploadIdStr(root.get("upload_id_str")== null ? null :root.get("upload_id_str").asText());
		activity.setExternalId(root.get("external_id")== null ? null :root.get("external_id").asText());
		activity.setTrainer(root.get("trainer")== null ? null :root.get("trainer").asBoolean());
		activity.setCommute(root.get("commute")== null ? null :root.get("commute").asBoolean());
		activity.setManual(root.get("manual")== null ? null :root.get("manual").asBoolean());
		activity.setPrivate(root.get("private")== null ? null :root.get("private").asBoolean());
		activity.setFlagged(root.get("flagged")== null ? null :root.get("flagged").asBoolean());
		activity.setTotalElevationGain(root.get("total_elevation_gain")== null ? null :root.get("elapsed_time").asInt());
		activity.setAchievementCount(root.get("achievement_count")== null ? null :root.get("achievement_count").asInt());
		activity.setWorkoutType(root.get("workout_type")== null ? null :root.get("workout_type").asInt());
		activity.setKudosCount(root.get("kudos_count")== null ? null :root.get("kudos_count").asInt());
		activity.setCommentCount(root.get("comment_count")== null ? null :root.get("comment_count").asInt());
		activity.setAthleteCount(root.get("athlete_count")== null ? null :root.get("athlete_count").asInt());
		activity.setPhotoCount(root.get("photo_count")== null ? null :root.get("photo_count").asInt());
		activity.setMaxWatts(root.get("max_watts")== null ? null :root.get("max_watts").asInt());
		activity.setWeightedAverageWatts(root.get("weighted_average_watts")== null ? null :root.get("weighted_average_watts").asInt());
		activity.setDeviceWatts(root.get("device_watts")== null ? null :root.get("device_watts").asBoolean());
		activity.setUploadId(root.get("upload_id")== null ? null :root.get("upload_id").asLong());
		activity.setTotalPhotoCount(root.get("total_photo_count")== null ? null :root.get("total_photo_count").asInt());
		activity.setHasKudoed(root.get("has_kudoed")== null ? null :root.get("has_kudoed").asBoolean());
		activity.setDistance(root.get("distance") == null ? null :root.get("distance").asDouble());
		activity.setAverageSpeed(root.get("average_speed")== null ? null :root.get("average_speed").asDouble());
		activity.setMaxSpeed(root.get("max_speed")== null ? null :root.get("max_speed").asDouble());
		activity.setAverageWatts(root.get("average_watts")== null ? null :root.get("average_watts").asDouble());
		activity.setKilojoules(root.get("kilojoules")== null ? null :root.get("kilojoules").asDouble());
		activity.setElevHigh(root.get("elev_high")== null ? null : root.get("elev_high").asDouble());
		activity.setElevLow(root.get("elev_low")== null ? null : root.get("elev_low").asDouble());
		return activity;
	}
}
