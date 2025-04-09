package com.ducnh.oauth2_server.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.JsonNode;

@Entity(name = "strava_lap")
public class StravaLap {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Id
    private Long id;
    
    @Column(name = "athlete_id", nullable = false)
    private Long athleteId;
    private Long activityId;
    private Double distance;
    private Integer elapsedTime;
    private Integer startIndex;
    private Integer endIndex;
    private Integer lapIndex;
    private Double maxSpeed;
    private Integer movingTime;
    private String name;
    private Integer paceZone;
    private String split;
    private LocalDateTime startDate;
    private LocalDateTime startDateLocal;
    private Boolean isViolated;
    @Transient
    private String formatPaced;
    public StravaLap() {
    }

    public StravaLap(Long id, Long athleteId, Long activityId, Double distance, Integer elapsedTime, Integer startIndex,
            Integer endIndex, Integer lapIndex, Double maxSpeed, Integer movingTime, String name, Integer paceZone,
            String split, LocalDateTime startDate, LocalDateTime startDateLocal, Boolean isViolated) {
        this.id = id;
        this.athleteId = athleteId;
        this.activityId = activityId;
        this.distance = distance;
        this.elapsedTime = elapsedTime;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.lapIndex = lapIndex;
        this.maxSpeed = maxSpeed;
        this.movingTime = movingTime;
        this.name = name;
        this.paceZone = paceZone;
        this.split = split;
        this.startDate = startDate;
        this.startDateLocal = startDateLocal;
        this.isViolated = isViolated;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getAthleteId() {
        return athleteId;
    }
    public void setAthleteId(Long athleteId) {
        this.athleteId = athleteId;
    }

    public Long getActivityId() {
        return activityId;
    }
    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }
    public Double getDistance() {
        return distance;
    }
    public void setDistance(Double distance) {
        this.distance = distance;
    }
    public Integer getElapsedTime() {
        return elapsedTime;
    }
    public void setElapsedTime(Integer elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
    public Integer getStartIndex() {
        return startIndex;
    }
    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }
    public Integer getEndIndex() {
        return endIndex;
    }

    public Boolean isViolated() {
        return isViolated;
    }
    public void setViolated(Boolean isViolated) {
        this.isViolated = isViolated;
    }

    public void setViolated(StravaEvent event) {
        if (event.getHighPace() < this.getPaced() || event.getLowPace() > this.getPaced()) {
            this.isViolated = true;
        } else {
            this.isViolated = false;
        }
    }

    public Double getPaced() {
        if (movingTime == null || movingTime == 0) {
            return 0.0;
        }
        return (movingTime * 1.0 / 60) / (distance / 1000); // pace in min/km
    }

    public String getFormatPaced() {
        if (movingTime == null || movingTime == 0) {
            return "0:00";
        }
        double pace = ((movingTime / 60) / (distance / 1000)); // pace in min/km
        int minutes = (int) pace; // minutes in km
        int seconds = (int) ((pace % 1) * 60); // seconds in km
        return String.format("%d:%02d /km", minutes, seconds);
    }

    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }
    public Integer getLapIndex() {
        return lapIndex;
    }
    public void setLapIndex(Integer lapIndex) {
        this.lapIndex = lapIndex;
    }
    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    public String getMovingTime() {
        return (this.movingTime == null) ? "0" : "" + this.movingTime / 60 + "m" + (this.movingTime % 60) + "s";    
    }
    public void setMovingTime(Integer movingTime) {
        this.movingTime = movingTime;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getPaceZone() {
        return paceZone;
    }

    public void setPaceZone(Integer paceZone) {
        this.paceZone = paceZone;
    }

    public String getSplit() {
        return split;
    }

    public void setSplit(String split) {
        this.split = split;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    
    public LocalDateTime getStartDateLocal() {
        return startDateLocal;
    }
    public void setStartDateLocal(LocalDateTime startDateLocal) {
        this.startDateLocal = startDateLocal;
    }

    public static StravaLap createStravaLapFromJsonNode(JsonNode jsonNode) {
        StravaLap stravaLap = new StravaLap();

        stravaLap.setId(jsonNode.get("id") == null ? null : jsonNode.get("id").asLong());
        stravaLap.setAthleteId(jsonNode.get("athlete") == null ? null : jsonNode.get("athlete").get("id").asLong());
        stravaLap.setActivityId(jsonNode.get("activity") == null ? null : jsonNode.get("activity").get("id").asLong());
        stravaLap.setDistance(jsonNode.get("distance") == null ? null : jsonNode.get("distance").asDouble());
        stravaLap.setElapsedTime(jsonNode.get("elapsed_time") == null ? null : jsonNode.get("elapsed_time").asInt());
        stravaLap.setStartIndex(jsonNode.get("start_index") == null ? null : jsonNode.get("start_index").asInt());
        stravaLap.setEndIndex(jsonNode.get("end_index") == null ? null : jsonNode.get("end_index").asInt());
        stravaLap.setLapIndex(jsonNode.get("lap_index") == null ? null : jsonNode.get("lap_index").asInt());
        stravaLap.setMaxSpeed(jsonNode.get("max_speed") == null ? null : jsonNode.get("max_speed").asDouble());
        stravaLap.setMovingTime(jsonNode.get("moving_time") == null ? null : jsonNode.get("moving_time").asInt());
        stravaLap.setName(jsonNode.get("name") == null ? null : jsonNode.get("name").asText());
        stravaLap.setPaceZone(jsonNode.get("pace_zone") == null ? null : jsonNode.get("pace_zone").asInt());
        stravaLap.setSplit(jsonNode.get("split") == null ? null : jsonNode.get("split").asText());
        stravaLap.setStartDate(LocalDateTime.parse(jsonNode.get("start_date") == null ? null : jsonNode.get("start_date").asText(), formatter));
        stravaLap.setStartDateLocal(LocalDateTime.parse(jsonNode.get("start_date_local") == null ? null : jsonNode.get("start_date_local").asText(), formatter));
        stravaLap.setViolated(false);
        return stravaLap;
    }

    @Override
    public String toString() {
        return "StravaLap [id=" + id + ", athleteId=" + athleteId + ", activityId=" + activityId + ", distance="
                + distance + ", elapsedTime=" + elapsedTime + ", startIndex=" + startIndex + ", endIndex=" + endIndex
                + ", lapIndex=" + lapIndex + ", maxSpeed=" + maxSpeed + ", movingTime=" + movingTime + ", name="
                + name + ", paceZone=" + paceZone + ", split=" + split + ", startDate=" + startDate
                + ", startDateLocal=" + startDateLocal + "]";
    }
}
