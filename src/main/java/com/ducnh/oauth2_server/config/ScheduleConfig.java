package com.ducnh.oauth2_server.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ducnh.oauth2_server.service.SchedulerService;


@Component
public class ScheduleConfig {

    public static final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);

    @Autowired
	private SchedulerService schedulerService;

    @Value("${sync.time.hour}")
    private String syncTimeHour;

    @Value("${sync.time.minute}")
    private int syncTimeMinute;

    // @Scheduled(cron = "0 0 0 * * ?") //Fetch Data Daily

    // @Scheduled(fixedRate = 1000 * 60 * 15) //Fetch Data 15 minutes
	@Scheduled(fixedRate = 1000 * 60 * 30)
	public void scheduleTask() {
        logger.info("Hour: " + LocalDateTime.now().getHour() + " minute: " + LocalDateTime.now().getMinute());   
        List<Integer> listHour = new ArrayList<>();
        
        Arrays.stream(syncTimeHour.split(",")).forEach(hour -> {
            listHour.add(Integer.parseInt(hour.trim()));
        });

        if (listHour.contains(LocalDateTime.now().getHour())) {
            logger.info("Scheduled task starting ....");
            logger.info(schedulerService.getDataActivity());
        }
    } 
}
