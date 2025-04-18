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
<<<<<<< HEAD

    public static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    @Autowired
    private ActivityService activityService;
=======
    
    public static final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);
>>>>>>> 556b2d188848280ecc0f8680f2622114abada2c1

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
        if (event == null) {
            return "No current event found.";   
        }
        AtomicInteger count = new AtomicInteger(0);
        Iterable<RegisterEvent> registerEvents = registerService.findAllByEventId(event.getId());
        for (RegisterEvent registered : registerEvents) {
            try {
                    Long id = registered.getAthleteId();
                    count.incrementAndGet();
                    activityService.saveActivitiesFromStravaResponse(id);
                }
            catch (Exception e) {
                logger.error("Error fetching athlete user: " + registered.getAthleteId() + " - " + e.getMessage());
            }
        }
        return "Fetched " + count.get() + " athlete users successfully.";
    }
}
