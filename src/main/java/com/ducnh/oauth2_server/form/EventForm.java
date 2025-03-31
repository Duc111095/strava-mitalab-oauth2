package com.ducnh.oauth2_server.form;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class EventForm {
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

	private String eventName;
	private int teamCount;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String description;

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	public String getEventName() {
		return this.eventName;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = LocalDateTime.parse(startDate, formatter);
	}
	
	public LocalDateTime getStartDate() {
		return this.startDate;
	}

	
	public void setEndDate(String endDate) {
		this.endDate = LocalDateTime.parse(endDate, formatter);;
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
