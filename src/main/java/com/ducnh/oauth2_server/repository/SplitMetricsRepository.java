package com.ducnh.oauth2_server.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ducnh.oauth2_server.model.StravaSplitMetrics;
import com.ducnh.oauth2_server.model.keys.SplitsMetricIdentity;

@Repository
public interface SplitMetricsRepository extends CrudRepository<StravaSplitMetrics, SplitsMetricIdentity> {
    // Custom query methods can be defined here if needed
    // For example, to find by activityId or splitId, etc.
    List<StravaSplitMetrics> findBySplitsMetricIdActivityId(Long activityId);
    Boolean existsBySplitsMetricIdActivityId(Long activityId);
}
