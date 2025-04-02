package com.ducnh.oauth2_server.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegisteredAthleteDTO {

    private Long athleteId;
    private String athleteName;
    private String eventId;
    private String eventName;
    private String teamId;
    private LocalDateTime registeredAt;

    public RegisteredAthleteDTO() {}

    public RegisteredAthleteDTO(Long athleteId, String athleteName, String eventId, String eventName, String teamId,
            LocalDateTime registeredAt) {
        this.athleteId = athleteId;
        this.athleteName = athleteName;
        this.eventId = eventId;
        this.eventName = eventName;
        this.teamId = teamId;
        this.registeredAt = registeredAt;
    }

    public void setAthleteId(Long athleteId) {
        this.athleteId = athleteId;
    }

    public Long getAthleteId() {
        return athleteId;
    }

    public void setAthleteName(String athleteName) {
        this.athleteName = athleteName;
    }

    public String getAthleteName() {
        return athleteName;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamId() {
        return teamId;
    }
    
    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public String getFormatedDateFromStartDateLocal(){
        return this.registeredAt == null ? null : this.registeredAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return "RegisteredAthleteDTO [athleteId=" + athleteId + ", athleteName=" + athleteName + ", eventId=" + eventId
                + ", eventName=" + eventName + ", teamId=" + teamId + ", registeredAt=" + registeredAt + "]";
    }
}