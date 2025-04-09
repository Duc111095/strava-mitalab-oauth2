package com.ducnh.oauth2_server.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.ducnh.oauth2_server.model.keys.SplitsMetricIdentity;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;

@Entity(name = "strava_split_metrics")
@Data
public class StravaSplitMetrics {
    @EmbeddedId
    private SplitsMetricIdentity splitsMetricId;
    private Integer elapsedTime;
    private Integer movingTime;
    private Double distance;
    private Double elevationDifference;
    private Double paceZone;
    private Double averageSpeed;
    private Double averageHeartRate; 

    public static StravaSplitMetrics createMetricsFromJsonNode(Long activityId, JsonNode jsonNode) {
        Integer splitId = jsonNode.get("split").asInt();
        SplitsMetricIdentity splitsMetricId = new SplitsMetricIdentity(activityId, splitId);
        StravaSplitMetrics metrics = new StravaSplitMetrics();
        metrics.setSplitsMetricId(splitsMetricId);
        metrics.setElapsedTime(jsonNode.get("elapsed_time") ==  null ? null : jsonNode.get("elapsed_time").asInt());
        metrics.setMovingTime(jsonNode.get("moving_time") ==  null ? null : jsonNode.get("moving_time").asInt());
        metrics.setDistance(jsonNode.get("distance") ==  null ? null : jsonNode.get("distance").asDouble());
        metrics.setElevationDifference(jsonNode.get("elevation_difference") ==  null ? null : jsonNode.get("elevation_difference").asDouble());
        metrics.setPaceZone(jsonNode.get("pace_zone") ==  null ? null : jsonNode.get("pace_zone").asDouble());
        metrics.setAverageSpeed(jsonNode.get("average_speed") ==  null ? null : jsonNode.get("average_speed").asDouble());
        metrics.setAverageHeartRate(jsonNode.get("average_heartrate") ==  null ? null : jsonNode.get("average_heartrate").asDouble());
        return metrics;
    }
}