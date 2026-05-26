package com.ducnh.oauth2_server.dto;

import lombok.Data;

@Data
public class UserRankDTO {
    private String id;
    private String name;
    private int teamId;
    private double totalDistance;
}
