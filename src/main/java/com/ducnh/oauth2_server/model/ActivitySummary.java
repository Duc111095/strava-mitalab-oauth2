package com.ducnh.oauth2_server.model;

public class ActivitySummary {
	private Long athleteId;
	private String athleteName;
	private double totalDistance;
	private int totalMovingTime;
	private int totalElapsedTime;
	private double paced;
	
	public ActivitySummary() {}
	public ActivitySummary(Long athleteId, 
			String athleteName, 
			double totalDistance, 
			int totalMovingTime,
			int totalElapsedTime,
			double paced) {
		this.athleteId = athleteId;
		this.athleteName = athleteName;
		this.totalDistance = totalDistance;
		this.totalMovingTime = totalMovingTime;
		this.totalElapsedTime = totalElapsedTime;
		this.paced = paced;
	}
	
	public void setAthleteId(Long id) {
		this.athleteId = id;
	}
	
	public Long getAthleteId() {
		return this.athleteId;
	}
	
	public void setAthleteName(String athleteName) { 
		this.athleteName = athleteName;
	}
	
	public String getAthleteName() {
		return this.athleteName;
	}
	
	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}
	
	public double getTotalDistance() {
		return this.totalDistance;
	}
	
	public void setTotalMovingTime(int totalMovingTime) {
		this.totalMovingTime = totalMovingTime;
	}
	
	public int getTotalMovingTime() {
		return this.totalMovingTime;
	}
	
	public void setTotalElapsedTime(int totalElapsedTime) {
		this.totalElapsedTime = totalElapsedTime;
	}
	
	public int getTotalElapsedTime() {
		return this.totalElapsedTime;
	}
	
	public String getFormattedMovingTime() {
		if (this.totalMovingTime < 3600) {
			return "" + (this.totalMovingTime / 60) + "m" + ("0" +(this.totalMovingTime % 60)).substring(1) + "s"; 
		}
		int hour = this.totalMovingTime / 3600;
		int minutes = (this.totalMovingTime % 3600) / 60;
		int second = this.totalMovingTime - hour * 3600 - 60 * minutes;
		return  "" + hour + "h" + minutes + "m" + second + "s"; 
	}
	
	public void setPaced(double paced) {
		this.paced = paced;
	}	
	
	public double getPaced() {
		return this.paced;
	}
	
	public String getFormattedPaced() {
		return String.format("%dm%ds", (int)(this.paced/60), Math.round(this.paced) % 60);
	}
}
