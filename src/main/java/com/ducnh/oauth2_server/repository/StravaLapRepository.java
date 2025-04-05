package com.ducnh.oauth2_server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ducnh.oauth2_server.model.StravaLap;

@Repository
public interface StravaLapRepository extends CrudRepository<StravaLap, Long> {
    StravaLap findByAthleteIdAndActivityId(Long athleteId, Long activityId);
    Iterable<StravaLap> findByAthleteId(Long athleteId);
    Iterable<StravaLap> findByActivityId(Long activityId);
}
