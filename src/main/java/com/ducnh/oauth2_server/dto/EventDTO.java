package com.ducnh.oauth2_server.dto;

public class EventDTO {
    
    private String eventId;
    private String eventName;
    private Long athleteId;
    private int teamId;

    public EventDTO() {}

    public EventDTO(String eventId, Long athleteId, int teamId, String eventName) {
        this.eventId = eventId;
        this.athleteId = athleteId;
        this.teamId = teamId;
        this.eventName = eventName;
    }
    public String getEventId() {
        return eventId;
    }
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    public Long getAthleteId() {
        return athleteId;
    }
    public void setAthleteId(Long athleteId) {
        this.athleteId = athleteId;
    }
    public int getTeamId() {
        return teamId;
    }
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getEventName() {
        return eventName;
    }
    
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    @Override
    public String toString() {
        return "EventDTO [eventId=" + eventId + ", athleteId=" + athleteId + ", teamId=" + teamId + "]";
    }
    
    public String toJson() {
        return "{ \"eventId\": \"" + eventId + "\", \"athleteId\": " + athleteId + ", \"teamId\": " + teamId + " }";
    }
}
