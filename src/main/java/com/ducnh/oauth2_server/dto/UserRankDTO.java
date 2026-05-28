package com.ducnh.oauth2_server.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserRankDTO {
    private String id;
    private String name;
    private int teamId;
    private double totalDistance;
    private LocalDateTime tght;
    private String tghtStr;

    public void setTght(LocalDateTime time) {
        this.tght = time;
    }

    
    public void setTghtStr(LocalDateTime time) {
        this.tghtStr = time.toString();
    }
}
