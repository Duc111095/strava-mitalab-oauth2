package com.ducnh.oauth2_server.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "strava_event")
public class StravaEvent {
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

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
	private Double lowPace;
	
	@Column(nullable = true)
	private Double highPace;
	
	@Column(nullable = true)
	private String description;
	
	public StravaEvent() {}
	
	public StravaEvent(String eventName, int teamCount, LocalDateTime startDate, LocalDateTime endDate, String description, double lowPace, double highPace) {
		this.id = UUID.randomUUID().toString();
		this.eventName = eventName;
		this.teamCount = teamCount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.lowPace = lowPace;
		this.highPace = highPace;
		
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setLowPace(double lowPace) {
		this.lowPace = lowPace;
	}
	
	public Double getLowPace() {
		return this.lowPace;
	}
	
	public void setHighPace(double highPace) { 
		this.highPace = highPace;
	}
	
	public Double getHighPace() {
		return this.highPace;
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
	
	public String getStartDate() {
		return this.startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
	}
	
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	
	public String getEndDate() {
		return this.endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
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
