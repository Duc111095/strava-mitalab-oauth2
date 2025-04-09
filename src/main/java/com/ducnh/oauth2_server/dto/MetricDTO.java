package com.ducnh.oauth2_server.dto;

import com.ducnh.oauth2_server.model.StravaEvent;

public class MetricDTO {
    private Long activityId;
    private Integer splitId;
    private Double distance;
    private Double averageHeartRate;
    private Double averageSpeed;
    private Integer elapsedTime;
    private Integer movingTime;
    private boolean isViolated = false;
    private String formatPaced = "";

    public MetricDTO() {
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long activityId() {
        return this.activityId;
    }

    public void setSplitId(Integer splitId) {
        this.splitId = splitId;
    }

    public Integer getSplitId() {
        return this.splitId;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getDistance() {
        if ( 0.95 <= (this.distance / 1000) && (this.distance / 1000) < 1.05) {
            return "1";
        }
        return String.format("%.2f", this.distance / 1000);
    }

    public Double getAverageHeartRate() {
        return this.averageHeartRate;
    }

    public void setAverageHeartRate(Double averageHeartRate) {
        this.averageHeartRate = averageHeartRate;
    }

    public Double getAverageSpeed() {
        return this.averageSpeed;
    }

    public void setAverageSpeed(Double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public Integer getElapsedTime() {
        return this.elapsedTime;
    }

    public void setElapsedTime(Integer elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Integer getMovingTime() {
        return this.movingTime;
    }

    public void setMovingTime(Integer movingTime) {
        this.movingTime = movingTime;
    }

    public Double getPaced() {
        if (this.getMovingTime() == null || this.getMovingTime() == 0) {
            return 0.0;
        }
        return (this.getMovingTime() * 1.0 / 60) / (this.distance / 1000);
    }

    public String getFormatPaced() {
        int minutes = (int) Math.floor(getPaced());
        int seconds = (int) (getPaced() * 60) % 60; // seconds in km
        return String.format("%dm%ds", minutes, seconds);
    }

    public void setFormatPaced(String formatPaced) {
        this.formatPaced = formatPaced;
    }

    public void setViolated(StravaEvent event) {
        if (event.getHighPace() < this.getPaced() || event.getLowPace() > this.getPaced()) {
            this.isViolated = true;
        } else {
            this.isViolated = false;
        }
    }

    public boolean isViolated() {
        return this.isViolated;
    }
}   
