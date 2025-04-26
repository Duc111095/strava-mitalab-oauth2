package com.ducnh.oauth2_server.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ResponseUser {
    private String message;
    private LocalDateTime time;
    private Map<String, Object> data;
    public ResponseUser() {}

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, Object> getData() {
        return this.data;
    }
}
