package com.ducnh.oauth2_server.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.JsonNode;

public class StravaLap {
    private Long id;
    private Long athleteId;
    private Long activityId;
    private Double distance;
    private Integer elapsedTime;
    private Integer startIndex;
    private Integer endIndex;
    private String lapIndex;
    private Double maxSpeed;
    private Integer movingTime;
    private String name;
    private Integer paceZone;
    private String split;
    private LocalDateTime startDate;
    private LocalDateTime startDateLocal;

    public StravaLap() {
    }

    public StravaLap(Long id, Long athleteId, Long activityId, Double distance, Integer elapsedTime, Integer startIndex,
            Integer endIndex, String lapIndex, Double maxSpeed, Integer movingTime, String name, Integer paceZone,
            String split, LocalDateTime startDate, LocalDateTime startDateLocal) {
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
    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }
    public String getLapIndex() {
        return lapIndex;
    }
    public void setLapIndex(String lapIndex) {
        this.lapIndex = lapIndex;
    }
    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    public Integer getMovingTime() {
        return movingTime;
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
        stravaLap.setLapIndex(jsonNode.get("lap_index") == null ? null : jsonNode.get("lap_index").asText());
        stravaLap.setMaxSpeed(jsonNode.get("max_speed") == null ? null : jsonNode.get("max_speed").asDouble());
        stravaLap.setMovingTime(jsonNode.get("moving_time") == null ? null : jsonNode.get("moving_time").asInt());
        stravaLap.setName(jsonNode.get("name") == null ? null : jsonNode.get("name").asText());
        stravaLap.setPaceZone(jsonNode.get("pace_zone") == null ? null : jsonNode.get("pace_zone").asInt());
        stravaLap.setSplit(jsonNode.get("split") == null ? null : jsonNode.get("split").asText());
        stravaLap.setStartDate(LocalDateTime.parse(jsonNode.get("start_date") == null ? null : jsonNode.get("start_date").asText()));
        stravaLap.setStartDateLocal(LocalDateTime.parse(jsonNode.get("start_date_local") == null ? null : jsonNode.get("start_date_local").asText()));

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
