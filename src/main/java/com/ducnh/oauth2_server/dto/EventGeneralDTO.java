package com.ducnh.oauth2_server.dto;

import lombok.Data;

@Data
public class EventGeneralDTO {
    private String id;
    private String name;
    private String description;
    private double totalDistance;
    private double totalDistanceRun;
    private double totalDistanceRide;   
}
