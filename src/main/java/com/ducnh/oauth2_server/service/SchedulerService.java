package com.ducnh.oauth2_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ducnh.oauth2_server.model.AthleteUser;

@Service
public class SchedulerService {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private ActivityService activityService;

    @Value("${strava.url.athlete.activities}")
	private String activitiesUrl;

	@Value("${strava.url.activities.lap}")
	private String lapUrl;

    @Autowired
    private AthleteUserService athleteUserService;

    public String getDataActivity() {
        Iterable<AthleteUser> athleteUsers = athleteUserService.findAll();
        try {
            for (AthleteUser athleteId : athleteUsers) {
                Long id = athleteId.getId();
                ResponseEntity<String> response = tokenService.sendGetRequest(id, activitiesUrl);
                activityService.saveActivitiesFromStravaResponse(response, id);
            }
        } catch (Exception e) {
            return "Error fetching athlete users.";
        }
        return "Data activity fetched successfully!!!!!";
    }
}
