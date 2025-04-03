package com.ducnh.oauth2_server.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ducnh.oauth2_server.model.AthleteUser;
import com.ducnh.oauth2_server.model.PolylineMap;
import com.ducnh.oauth2_server.model.StravaActivity;
import com.ducnh.oauth2_server.repository.MapRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Service
public class SchedulerService {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MapRepository mapRepo;

    @Autowired
    private ActivityService activityService;

    @Value("${strava.url.athlete.activities}")
	private String activitiesUrl;

    @Autowired
    private AthleteUserService athleteUserService;

    public String getDataActivity() {
        Iterable<AthleteUser> athleteUsers = athleteUserService.findAll();
        try {
            for (AthleteUser athleteId : athleteUsers) {
                Long id = athleteId.getId();
                tokenService.getAccessToken(id);
                ArrayNode responseArray = (ArrayNode) mapper.readTree(tokenService.sendGetRequest(id, activitiesUrl).getBody());
    
                if (responseArray == null) {
                    System.out.println("Failed to fetch data for athlete ID: " + id);
                    continue;
                }
                if (responseArray.isArray() && responseArray.size() > 0) {
                    for (JsonNode root : responseArray) {
                        PolylineMap map = new PolylineMap();
                        map.setId(root.get("map").get("id") == null ? null : root.get("map").get("id").asText());
                        map.setSummaryPolyline(root.get("map").get("summary_polyline") == null ? null : root.get("map").get("summary_polyline").asText());
                        mapRepo.save(map);
                        StravaActivity activity = StravaActivity.createActivityFromResponse(root);
                        activityService.save(activity);
                    }	
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching athlete users: " + e.getMessage());
            return "Error fetching athlete users.";
        }
        

        return "Data activity fetched successfully!!!!!";
    }
}
