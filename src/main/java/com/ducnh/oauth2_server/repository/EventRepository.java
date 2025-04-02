package com.ducnh.oauth2_server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ducnh.oauth2_server.model.StravaEvent;

public interface EventRepository extends CrudRepository<StravaEvent, String> {
    @Query(value = "SELECT * FROM strava_event e WHERE e.start_date <= NOW() AND e.end_date >= NOW()", nativeQuery = true)
    Iterable<StravaEvent> findCurrentEvent();
}
