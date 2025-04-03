package com.ducnh.oauth2_server.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ducnh.oauth2_server.model.constants.ResourceState;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "strava_activity")
@NamedStoredProcedureQueries({
@NamedStoredProcedureQuery(name = "summary_activities", procedureName = "summary_activities", 
		parameters = {@StoredProcedureParameter(mode = ParameterMode.IN,name = "start_date",type=LocalDateTime.class), 
				@StoredProcedureParameter(mode = ParameterMode.IN,name = "end_date",type=LocalDateTime.class),
				@StoredProcedureParameter(mode = ParameterMode.IN,name = "type_summary",type=String.class)}
)})
public class StravaActivity {
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
	public static final Logger logger = LoggerFactory.getLogger(StravaActivity.class);

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
	private Double distance;
	private Integer movingTime;
	private Integer elapsedTime;
	
	@Column(nullable = true)
	private Double totalElevationGain;
	@Column(nullable = true)
	private Double elevHigh;
	@Column(nullable = true)
	private Double elevLow;
	@Column(nullable = true)
	private String sportType;
	
	@Column(nullable = false)	
	private LocalDateTime startDate;
	@Column(nullable = false)
	private LocalDateTime startDateLocal;
	@Column(nullable = true)
	private String timezone;
	@Column(nullable = true)
	private Integer achievementCount;
	@Column(nullable = true)
	private Integer kudosCount;
	@Column(nullable = true)
	private Integer commentCount;
	@Column(nullable = true)
	private Integer athleteCount;
	@Column(nullable = true)
	private Integer photoCount;
	@Column(nullable = true)
	private Integer totalPhotoCount;
	
	@ManyToOne
	@JoinColumn(name="map_id")
	private PolylineMap map; 
	@Column(nullable = true)
	private Boolean isTrainer;
	@Column(nullable = true)
	private Boolean isCommute;
	@Column(nullable = true)
	private Boolean isManual;
	@Column(nullable = true)
	private Boolean isPrivate;
	@Column(nullable = true)
	private Boolean flagged;
	@Column(nullable = true)
	private Integer workoutType;
	@Column(nullable = true)
	private String uploadIdStr;
	@Column(nullable = true)
	private Double averageSpeed;
	@Column(nullable = true)
	private Double maxSpeed;
	@Column(nullable = true)
	private Boolean hasKudoed;
	@Column(nullable = true)
	private Boolean hideFromHome;
	@Column(nullable = true)
	private String gearId;
	@Column(nullable = true)
	private Double kilojoules;
	@Column(nullable = true)
	private Double averageWatts;
	@Column(nullable = true)
	private boolean deviceWatts;
	@Column(nullable = true)
	private Integer maxWatts;
	@Column(nullable = true)
	private Integer weightedAverageWatts;
	
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
	
	public Double getDistance() {
		return this.distance;
	}
	
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	public Integer getMovingTime() {
		return this.movingTime;
	}
	
	public void setMovingTime(Integer movingTime) {
		this.movingTime = movingTime;
	}
	
	public Integer getElapsedTime() {
		return this.elapsedTime;
	}
	
	public void setElapsedTime(Integer elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	
	public Double getToTalElevationGain() {
		return this.totalElevationGain;
	}
	
	public void setTotalElevationGain(Double totalElevationGain) {
		this.totalElevationGain = totalElevationGain;
	}
	
	public Double getElevHigh() {
		return this.elevHigh;
	}
	
	public void setElevHigh(Double elevHigh) {
		this.elevHigh = elevHigh;
	}

	public Double getElevLow() {
		return this.elevLow;
	}
	
	public void setElevLow(Double elevLow) {
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
	
	public Integer getAchievementCount() {
		return this.achievementCount;
	}
	
	public void setAchievementCount(Integer achievementCount) {
		this.achievementCount = achievementCount;
	}
	
	public Integer getKudosCount() {
		return this.kudosCount;
	}
	
	public void setKudosCount(Integer kudosCount) {
		this.kudosCount = kudosCount;
	}
	
	public Integer getCommentCount() {
		return this.commentCount;
	}
	
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	
	public Integer getAthleteCount() {
		return this.athleteCount;
	}
	
	public void setAthleteCount(Integer athleteCount) {
		this.athleteCount = athleteCount;
	}
	
	public Integer getPhotoCount() {
		return this.photoCount;
	}
	
	public void setPhotoCount(Integer photoCount) {
		this.photoCount = photoCount;
	}
	
	public Integer getTotalPhotoCount() {
		return this.totalPhotoCount;
	}
	
	public void setTotalPhotoCount(Integer totalPhotoCount) {
		this.totalPhotoCount = totalPhotoCount;
	}
	
	public PolylineMap getMap() {
		return this.map;
	}
	
	public void setMap(PolylineMap map) {
		this.map = map;
	}
	
	public Boolean isTrainer() {
		return this.isTrainer;
	}
	
	public void setTrainer(Boolean isTrainer) {
		this.isTrainer = isTrainer;
	}
	
	public Boolean getCommute() {
		return this.isCommute;
	}
	
	public void setCommute(Boolean isCommute) {
		this.isCommute = isCommute;
	}
	
	public Boolean getManual() {
		return this.isManual;
	}
	
	public void setManual(Boolean isManual) {
		this.isManual = isManual;
	}
	
	public Boolean isPrivate() {
		return this.isPrivate;
	}
	
	public void setPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
	public Boolean isFlagged() {
		return this.flagged;
	}
	
	public void setFlagged(Boolean flagged) {
		this.flagged = flagged;
	}
	
	public Integer getWorkoutType() {
		return this.workoutType;
	}
	
	public void setWorkoutType(Integer workoutType) {
		this.workoutType = workoutType;
	}
	
	public String getUploadIdStr() {
		return this.uploadIdStr;
	}
	
	public void setUploadIdStr(String uploadIdStr) {
		this.uploadIdStr = uploadIdStr;
	}
	
	public Double getAverageSpeed() {
		return this.averageSpeed;
	}
	
	public void setAverageSpeed(Double averageSpeed) {
		this.averageSpeed = averageSpeed;
	}
	
	public Double getMaxSpeed() {
		return this.maxSpeed;
	}
	
	public void setMaxSpeed(Double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
	public Boolean hasKudoed() {
		return hasKudoed;
	}
	
	public void setHasKudoed(Boolean hasKudoed) {
		this.hasKudoed = hasKudoed;
	}
	
	public Boolean hideFromHome() {
		return this.hideFromHome;
	}
	
	public void setHideFromHome(Boolean hideFromHome) {
		this.hideFromHome = hideFromHome;
	}
	
	public String getGearId() {
		return this.gearId;
	}
	
	public void setGearId(String gearId) {
		this.gearId = gearId;
	}

	public Double getKilojoules() {
		return this.kilojoules;
	}
	
	public void setKilojoules(Double kilojoules) {
		this.kilojoules = kilojoules;
	}
	
	public Double getAverageWatts() {
		return this.averageWatts;
	}
	
	public void setAverageWatts(Double averageWatts) {
		this.averageWatts = averageWatts;
	}
	
	public Boolean isDeviceWatts() {
		return this.deviceWatts;
	}
	
	public void setDeviceWatts(Boolean deviceWatts) {
		this.deviceWatts = deviceWatts;
	}

	public Integer getMaxWatts() {
		return this.maxWatts;
	}
	
	public void setMaxWatts(Integer maxWatts) {
		this.maxWatts = maxWatts;
	}
	
	public Integer getWeightedAverageWatts() {
		return this.weightedAverageWatts;
	}
	
	public void setWeightedAverageWatts(Integer weightedAverageWatts) {
		this.weightedAverageWatts = weightedAverageWatts;
	}
	
	public String getFormatedDateFromStartDateLocal() {
		return this.startDateLocal.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
	}
	
	public String formatTimeElapsed() {
		return "" +(this.elapsedTime/60) + "m" + ("0" + (this.elapsedTime % 60)).substring(1) + "s";
	}

	public String formatDistance() {
		return String.format("%.2f", this.distance / 1000) + " km";
	}

	public String formatMovingTime() {
		if (this.movingTime < 3600) {
			return "" + (this.movingTime / 60) + "m" + ("0" +(this.movingTime % 60)).substring(1) + "s"; 
		}
		int hour = this.movingTime / 3600;
		int minutes = (this.movingTime % 3600) / 60;
		int second = this.movingTime - hour * 3600 - 60 * minutes;
		return  "" + hour + "h" + minutes + "m" + second + "s"; 
	}

	public String getPaced() {

		if (this.movingTime == 0) {
			return "0:00 /km";
		}
		int pace = (int)(this.movingTime / this.distance * 1000);
		int minute = pace / 60;
		int second = pace - minute * 60;
		return String.format("%d:%02d/km",minute, second);
	}
	

	@Override
	public String toString() {
		return this.getName() + this.getAthleteId() + " : " +  this.movingTime + "s" + this.getDistance() + "m" + this.getStartDateLocal() + this.getAverageSpeed() + this.getMaxSpeed() + this.getAverageWatts() + this.getMaxWatts() + this.getWeightedAverageWatts() + this.getKilojoules();
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
		//activity.setSportType(root.get("sport_type")== null ? null :root.get("sport_type").asText());
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
		activity.setTotalElevationGain(root.get("total_elevation_gain")== null ? null :root.get("elapsed_time").asDouble());
		activity.setAchievementCount(root.get("achievement_count")== null ? null :root.get("achievement_count").asInt());
		activity.setWorkoutType(root.get("workout_type")== null ? null :root.get("workout_type").asInt());
		activity.setKudosCount(root.get("kudos_count")== null ? null :root.get("kudos_count").asInt());
		activity.setCommentCount(root.get("comment_count")== null ? null :root.get("comment_count").asInt());
		activity.setAthleteCount(root.get("athlete_count")== null ? null :root.get("athlete_count").asInt());
		//activity.setPhotoCount(root.get("photo_count")== null ? null :root.get("photo_count").asInt());
		//activity.setMaxWatts(root.get("max_watts")== null ? null :root.get("max_watts").asInt());
		activity.setWeightedAverageWatts(root.get("weighted_average_watts")== null ? null :root.get("weighted_average_watts").asInt());
		//activity.setDeviceWatts(root.get("device_watts")== null ? null :root.get("device_watts").asBoolean());
		//activity.setUploadId(root.get("upload_id")== null ? null :root.get("upload_id").asLong());
		//activity.setTotalPhotoCount(root.get("total_photo_count")== null ? null :root.get("total_photo_count").asInt());
		activity.setHasKudoed(root.get("has_kudoed")== null ? null :root.get("has_kudoed").asBoolean());
		activity.setDistance(root.get("distance") == null ? null :root.get("distance").asDouble());
		//activity.setAverageSpeed(root.get("average_speed")== null ? null :root.get("average_speed").asDouble());
		//activity.setMaxSpeed(root.get("max_speed")== null ? null :root.get("max_speed").asDouble());
		//activity.setAverageWatts(root.get("average_watts")== null ? null :root.get("average_watts").asDouble());
		//activity.setKilojoules(root.get("kilojoules")== null ? null :root.get("kilojoules").asDouble());
		//activity.setElevHigh(root.get("elev_high")== null ? null : root.get("elev_high").asDouble());
		//activity.setElevLow(root.get("elev_low")== null ? null : root.get("elev_low").asDouble());
		return activity;
	}
}
