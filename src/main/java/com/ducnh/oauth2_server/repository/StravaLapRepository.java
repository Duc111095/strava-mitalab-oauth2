package com.ducnh.oauth2_server.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ducnh.oauth2_server.model.StravaLap;

@Repository
public interface StravaLapRepository extends CrudRepository<StravaLap, Long> {
    Iterable<StravaLap> findByAthleteId(Long athleteId);
    List<StravaLap> findByActivityId(Long activityId);
}
