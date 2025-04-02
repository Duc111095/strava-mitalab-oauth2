package com.ducnh.oauth2_server.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.ducnh.oauth2_server.model.keys.RegisterIdentity;

@Entity(name="strava_register")
public class RegisterEvent {
	
	@EmbeddedId
	private RegisterIdentity registerId;
		
	@Column(nullable = false)
	private int teamId;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public RegisterEvent() {}
	
	public RegisterEvent(RegisterIdentity registerId, int teamId) {
		this.registerId = registerId;
		this.teamId = teamId;
	}
	
	public RegisterIdentity getRegisterId() {
		return this.registerId;
	}

	public void setRegisterId(RegisterIdentity registerId) {
		this.registerId = registerId;		
	}

	public String getEventId() {
		return this.registerId.getEventId();
	}
	
	public Long getAthleteId() {
		return this.registerId.getAthleteId();
	}
	
	public int getTeamId() {
		return this.teamId;
	}
	
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	
	public LocalDateTime getCreatedAt() {
		return this.createdAt;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public LocalDateTime getUpdatedAt() {
		return this.updatedAt;
	}
	
	@PrePersist
	public void onPrePersist() {
		this.setCreatedAt(LocalDateTime.now());
		this.setUpdatedAt(LocalDateTime.now());
	}
	
	@PreUpdate
	public void onPreUpdate() {
		this.setUpdatedAt(LocalDateTime.now());
	}
 }