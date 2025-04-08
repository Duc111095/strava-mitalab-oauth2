package com.ducnh.oauth2_server.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.ducnh.oauth2_server.model.PolylineMap;

public class ActivitiesDTO {
    
    private Long athleteID;
    private String athleteName;
    private Long activityID;
    private String activityName;
    private Double distance;
    private Integer movingTime;
    private LocalDateTime startDateLocal;
    private PolylineMap map;

    public ActivitiesDTO() {
    }

    public ActivitiesDTO(Long athleteID, String athleteName, Long activityID, Double distance, Integer movingTime, LocalDateTime startDateLocal) {
        this.athleteID = athleteID;
        this.athleteName = athleteName;
        this.activityID = activityID;
        this.distance = distance;
        this.movingTime = movingTime;
        this.startDateLocal = startDateLocal;
    }

    public Long getAthleteID() {
        return athleteID;
    }
    public void setAthleteID(Long athleteID) {
        this.athleteID = athleteID;
    }
    public String getAthleteName() {
        return athleteName;
    }
    public void setAthleteName(String athleteName) {
        this.athleteName = athleteName;
    }
    public String getActivityName() {
        return activityName;
    }
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
    public Long getActivityID() {
        return activityID;
    }
    public void setActivityID(Long activityID) {
        this.activityID = activityID;
    }
    public String getDistance() {
        return String.format("%.2f km", distance / 1000);
    }

    public String getStartDateLocal() {
        return this.startDateLocal.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }
    public void setDistance(Double distance) {
        this.distance = distance;
    }
    
    public PolylineMap getMap() {
        return map;
    }
    public void setMap(PolylineMap map) {
        this.map = map;
    }

    public String getMovingTime() {
        if (movingTime < 3600) {
            return "" + (movingTime / 60) + "m" + (movingTime % 60) + "s";
        }
        int hours = (movingTime / 3600);
        int minutes = (movingTime % 3600) / 60;
		int seconds = this.movingTime - hours * 3600 - 60 * minutes;
        return "" + hours + "h" + minutes + "m" + seconds + "s";
    }

    public void setMovingTime(Integer movingTime) {
        this.movingTime = movingTime;
    }

    public void setStartDateLocal(LocalDateTime startDateLocal) {
        this.startDateLocal = startDateLocal;
    }
    public String formatDistance() {
        return String.format("%.2f km", distance / 1000);
    }

    public String formatStartDate() {
        return this.startDateLocal.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    public String formatMovingTime() {
        if (movingTime < 3600) {
            return "" + (movingTime / 60) + "m" + (movingTime % 60) + "s";
        }
        int hours = (movingTime / 3600);
        int minutes = (movingTime % 3600) / 60;
		int seconds = this.movingTime - hours * 3600 - 60 * minutes;
        return "" + hours + "h" + minutes + "m" + seconds + "s";
    }

    public String getPaced() {
        if (movingTime == 0 ) {
            return "0:00/km";
        }
        double pace = movingTime / (distance/1000);
        int minutes = (int) pace / 60;
        int second = (int) pace % 60;
        return String.format("%d:%02d/km", minutes, second);
    }

    @Override  
    public String toString() {
        return "ActivitiesDTO [athleteID=" + athleteID + ", athleteName=" + athleteName + ", activityID=" + activityID
                + ", distance=" + distance + ", movingTime=" + movingTime + ", startDateLocal=" + startDateLocal + "]";
    }
}
