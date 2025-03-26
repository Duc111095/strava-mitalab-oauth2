package com.ducnh.oauth2_server.model.constants;

public enum MeasurementPreference {
	FEET("feet"),
	METERS("meters");
	
	private String measurement;
	
	public static MeasurementPreference create(String measurement) {
		MeasurementPreference[] measures = MeasurementPreference.values();
		for (MeasurementPreference measure : measures) {
			if (measure.getId().equals(measurement)) {
				return measure;
			}
		}
		return null;
	}
	
	private MeasurementPreference (String measurement) {
		this.measurement = measurement;
	}
	
	public String getValue() {
		return this.measurement;
	}
	
	public String getId() {
		return this.measurement;
	}
}
