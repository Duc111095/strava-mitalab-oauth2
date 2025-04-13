package com.ducnh.oauth2_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ducnh.oauth2_server.model.RegisterEvent;
import com.ducnh.oauth2_server.model.StravaEvent;

@Service
public class SchedulerService {
    @Autowired
    private ActivityService activityService;

    @Value("${strava.url.athlete.activities}")
	private String activitiesUrl;

	@Value("${strava.url.activities.lap}")
	private String lapUrl;

    @Autowired
    private AthleteUserService athleteUserService;

    @Autowired
    private EventService eventService;

    @Autowired
    private RegisterService registerService;

    public String getDataActivity() {
        StravaEvent event = eventService.findExactCurrentEvent().orElse(null);
        if (event == null) {
            return "No current event found.";   
        }
        Iterable<RegisterEvent> registerEvents = registerService.findAllByEventId(event.getId());
        try {
            for (RegisterEvent registered : registerEvents) {
                Long id = registered.getAthleteId();
                activityService.saveActivitiesFromStravaResponse(id);
            }
        } catch (Exception e) {
            return "Error fetching athlete users.";
        }
        return "Data activity fetched successfully!!!!!";
    }
}
