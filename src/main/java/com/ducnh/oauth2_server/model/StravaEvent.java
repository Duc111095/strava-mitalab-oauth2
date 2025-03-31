package com.ducnh.oauth2_server.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "strava_event")
public class StravaEvent {
	@Id
	private String id;
	
	@Column(nullable = false)
	private String eventName;
	
	@Column(nullable = true)
	private int teamCount;
	
	@Column(nullable = true)
	private LocalDateTime startDate;
	
	@Column(nullable = true)
	private LocalDateTime endDate;
	
	@Column(nullable = true)
	private String description;
	
	public StravaEvent() {}
	
	public StravaEvent(String eventName, int teamCount, LocalDateTime startDate, LocalDateTime endDate, String description) {
		this.id = UUID.randomUUID().toString();
		this.eventName = eventName;
		this.teamCount = teamCount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	public String getEventName() {
		return this.eventName;
	}
	
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	
	public LocalDateTime getStartDate() {
		return this.startDate;
	}
	
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	
	public LocalDateTime getEndDate() {
		return this.endDate;
	}
	
	public void setTeamCount(int teamCount) {
		this.teamCount = teamCount;
	}
	
	public int getTeamCount() {
		return this.teamCount;
	}

	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
