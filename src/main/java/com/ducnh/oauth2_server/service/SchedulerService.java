package com.ducnh.oauth2_server.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ducnh.oauth2_server.config.ScheduleConfig;
import com.ducnh.oauth2_server.model.RegisterEvent;
import com.ducnh.oauth2_server.model.StravaEvent;

@Service
public class SchedulerService {
    
    public static final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);

    @Value("${strava.url.athlete.activities}")
	private String activitiesUrl;

	@Value("${strava.url.activities.lap}")
	private String lapUrl;

    @Autowired
    private EventService eventService;
    
    @Autowired
    private ActivityService activityService;

    @Autowired
    private RegisterService registerService;

    public String getDataActivity() {
        StravaEvent event = eventService.findExactCurrentEvent().orElse(null);
        logger.info("Fetching data for athlete ID: ");
        if (event == null) {
            System.out.println("Fetching data for event ID: ");
            return "No current event found.";   
        }
        Iterable<RegisterEvent> registerEvents = registerService.findAllByEventId(event.getId());
        AtomicInteger count = new AtomicInteger(0);
        try {
            for (RegisterEvent registered : registerEvents) {
                count.incrementAndGet();
                Long id = registered.getAthleteId();
                activityService.saveActivitiesFromStravaResponse(id);
            }
        } catch (Exception e) {
            return "Error fetching athlete users.";
        }
        logger.info("Total athletes fetched: " + count.get());
        return "Data activity fetched successfully!!!!!";
    }
}
