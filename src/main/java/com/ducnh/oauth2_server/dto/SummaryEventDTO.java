package com.ducnh.oauth2_server.dto;

public class SummaryEventDTO {
    
    private String teamName;
    private String eventId;
    private int teamId;
    private int teamCount;
    private Double totalDistance;
    private Double totalCurrentDistance;
    private int stt;

    public SummaryEventDTO() {}

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    
    public String getEventId() {
        return this.eventId;
    }

    public void setTeamCount(int teamCount) {
        this.teamCount = teamCount;
    }

    public int getTeamCount() {
        return this.teamCount;
    }   

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getTeamId() {
        return this.teamId;
    }

    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Double getTotalDistance() {
        return this.totalDistance;
    }

    public String formatTotalDistance() {
        return String.format("%.1f", this.totalDistance / 1000);
    }

    public String formatTotalCurrentDistance() {
        return String.format("%.2f", this.totalCurrentDistance / 1000);
    }

    public void setTotalCurrentDistance(Double currentDistance) {
        this.totalCurrentDistance = currentDistance;
    }

    public Double getTotalCurrentDistance() {
        return this.totalCurrentDistance;
    }

    public void setSTT(int stt) {
        this.stt = stt;
    }

    public int getSTT() {
        return this.stt;
    }

}


