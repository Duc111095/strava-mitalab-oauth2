package com.ducnh.oauth2_server.model.keys;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class RegisterIdentity implements Serializable{
	
	@NotNull
	private String eventId;
	
	@NotNull
	private Long athleteId;
	
	public RegisterIdentity(String eventId, Long athleteId) {
		this.athleteId = athleteId;
		this.eventId = eventId;
	}
	
	public RegisterIdentity() {
	}

	public String getEventId() {
		return this.eventId;
	}
	
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	public Long getAthleteId() {
		return this.athleteId;
	}
	
	public void setAthleteId(Long athleteId) {
		this.athleteId = athleteId;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null  || getClass() != o.getClass()) return false;
		RegisterIdentity that = (RegisterIdentity) o;
		return that.getAthleteId().equals(this.getAthleteId()) && that.getEventId().equals(this.getEventId());
	}
	
	@Override
	public int hashCode() {
		int result = this.athleteId.hashCode();
		result = 31 * result + this.eventId.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "RegisterIdentity [eventId=" + eventId + ", athleteId=" + athleteId + "]";
	}
}
