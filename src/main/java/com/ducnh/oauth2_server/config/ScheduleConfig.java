package com.ducnh.oauth2_server.config;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private Map<Object, Object> generalInfo;

    @Value("${sync.time.hour}")
    private String syncTimeHour;

    @Value("${sync.time.minute}")
    private int syncTimeMinute;

    // @Scheduled(fixedRate = 1000 * 60 * 15) //Fetch Data 15 minutes
	@Scheduled(fixedRate = 1000 * 60 * 60)
	public void scheduleTask() {
        List<Integer> listHour = new ArrayList<>();
        
        Arrays.stream(syncTimeHour.split(",")).forEach(hour -> {
            listHour.add(Integer.parseInt(hour.trim()));
        });

        if (listHour.contains(LocalDateTime.now().getHour())) {
            logger.info(schedulerService.getDataActivity());
        }
    } 
    
    //@CacheEvict(value = "results", allEntries = true)
    @Scheduled(fixedRateString = "60000")
    public void refreshGeneralInfoCache() {
        try {
            Map<Object, Object> temp = schedulerService.getSummaryCurrent();
            generalInfo.clear();
            generalInfo.putAll(temp);
        } catch (SQLException e) {
            logger.error("Error when refreshGeneralInfoCache ", e);
        }
    }
}
