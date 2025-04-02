package com.ducnh.oauth2_server.form;

public class RegisterForm {
	private String eventId;
	private int teamId;
	
	public RegisterForm() {}
	
	public RegisterForm(String eventId, int teamId) {
		this.eventId = eventId;
		this.teamId = teamId;
	}
	
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	public String getEventId() {
		return this.eventId;		
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public int getTeamId() {
		return this.teamId;	
	}

	@Override
	public String toString() {
		return "RegisterForm [eventId=" + eventId + ", teamId=" + teamId + "]";
	}
} 
