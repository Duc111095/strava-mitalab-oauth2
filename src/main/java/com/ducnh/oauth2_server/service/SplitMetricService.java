package com.ducnh.oauth2_server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ducnh.oauth2_server.dto.MetricDTO;
import com.ducnh.oauth2_server.model.StravaEvent;
import com.ducnh.oauth2_server.model.StravaSplitMetrics;
import com.ducnh.oauth2_server.repository.SplitMetricsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class SplitMetricService {
    
    @Autowired
    private SplitMetricsRepository splitMetricsRepository;

    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private ObjectMapper mapper;

    @Value("${strava.url.athlete.activityDetails}")
	private String activityDetailsUrl;

    // Add methods to interact with the repository as needed
    public void saveSplitMetric(StravaSplitMetrics splitMetric) {
        splitMetricsRepository.save(splitMetric);
    }

    public Boolean existsByActivityId(Long activityId) {
        return splitMetricsRepository.existsBySplitsMetricIdActivityId(activityId);
    }

    public List<StravaSplitMetrics> findByActivityId(Long activityId) {
        return splitMetricsRepository.findBySplitsMetricIdActivityId(activityId);
    }

    public void saveSplitMetricFromStrava(Long activityId, Long athleteId) throws JsonProcessingException{
        String url = activityDetailsUrl + "/" + activityId;

        ResponseEntity<String> detailsActivity = tokenService.sendGetRequest(athleteId, url);
        JsonNode treeUserRoot = mapper.readTree(detailsActivity.getBody());
        JsonNode splitsMetric = treeUserRoot.get("splits_metric");
        if (splitsMetric != null && splitsMetric.isArray()) {
            for (JsonNode split : splitsMetric) {
                StravaSplitMetrics splitMetric = StravaSplitMetrics.createMetricsFromJsonNode(activityId, split);
                saveSplitMetric(splitMetric);
            }
        } else {
            System.out.println("No splits metric found for activity ID: " + activityId);
        }
    }

    public MetricDTO convertMetricDTOFromSplitsMetric(StravaEvent event, StravaSplitMetrics splitMetric) {
        MetricDTO metricDTO = new MetricDTO();
        metricDTO.setActivityId(splitMetric.getSplitsMetricId().getActivityId());
        metricDTO.setSplitId(splitMetric.getSplitsMetricId().getSplitId());
        metricDTO.setDistance(splitMetric.getDistance());
        metricDTO.setElapsedTime(splitMetric.getElapsedTime());
        metricDTO.setMovingTime(splitMetric.getMovingTime());
        metricDTO.setAverageHeartRate(splitMetric.getAverageHeartRate());
        if (event != null) {
            metricDTO.setViolated(event);
        }
        return metricDTO;
    }
}

