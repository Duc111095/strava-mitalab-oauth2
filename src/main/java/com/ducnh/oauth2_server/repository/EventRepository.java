package com.ducnh.oauth2_server.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ducnh.oauth2_server.model.StravaEvent;


public interface EventRepository extends CrudRepository<StravaEvent, String> {
    @Query(value = "SELECT * FROM strava_event e WHERE e.start_date <= NOW() AND e.end_date >= NOW()", nativeQuery = true)
    Iterable<StravaEvent> findCurrentEvent();

    @Query(value = "SELECT * FROM strava_event e WHERE e.start_date <= NOW() AND e.end_date >= NOW() limit 1", nativeQuery = true)
    Optional<StravaEvent> findExactCurrentEvent();

    @Query(value = "SELECT x.athlete_id, x.event_id, x.team_id, e.event_name, x.accepted FROM strava_register x join strava_event e on e.id = x.event_id WHERE e.start_date <= NOW() AND e.end_date >= NOW() AND x.athlete_id = ?1 LIMIT 1", nativeQuery = true)
    Map<String, Object> findCurrentEventByAthlete(Long athleteId);
}
