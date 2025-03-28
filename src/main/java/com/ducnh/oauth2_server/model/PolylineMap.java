package com.ducnh.oauth2_server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PolylineMap {
	@Id
	private String id;
	
	@Column(nullable = true, length = 4000)
	private String polyline;
	
	@Column(nullable = true, length = 4000)
	private String summaryPolyline;

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setPolyline(String polyline) {
		this.polyline = polyline;
	}
	
	public String getPolyline() {
		return this.polyline;
	}
	
	public void setSummaryPolyline(String summaryPolyline) {
		this.summaryPolyline = summaryPolyline;
	}
	
	public String getSummaryPolyline() {
		return this.summaryPolyline;
	}
}
