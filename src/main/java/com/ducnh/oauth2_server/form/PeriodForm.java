package com.ducnh.oauth2_server.form;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PeriodForm {
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	public PeriodForm() {}
	
	public PeriodForm(String startDate, String endDate) {
		this.startDate = LocalDateTime.parse(startDate, formatter);
		this.endDate = LocalDateTime.parse(endDate, formatter);
	}
	
	public LocalDateTime getStartDate() {
		return this.startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = LocalDateTime.parse(startDate, formatter);
	}
	
	public LocalDateTime getEndDate() {
		return this.endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = LocalDateTime.parse(endDate, formatter);
	}
}
