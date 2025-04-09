package com.ducnh.oauth2_server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ducnh.oauth2_server.service.SchedulerService;

@Component
public class ScheduleConfig {

    public static final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);

    @Autowired
	private SchedulerService schedulerService;

    // @Scheduled(cron = "0 0 0 * * ?") //Fetch Data Daily

    // @Scheduled(fixedRate = 1000 * 60 * 15) //Fetch Data 15 minutes
	@Scheduled(fixedRate = 1000 * 60 * 15)
	public void scheduleTask() {
        logger.info(schedulerService.getDataActivity());	
    } 
}
