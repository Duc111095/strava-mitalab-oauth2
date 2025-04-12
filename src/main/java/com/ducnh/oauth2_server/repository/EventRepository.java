package com.ducnh.oauth2_server.repository;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ducnh.oauth2_server.model.StravaEvent;


public interface EventRepository extends CrudRepository<StravaEvent, String> {
    @Query(value = "SELECT * FROM strava_event e WHERE e.end_date >= NOW() ORDER BY e.end_date asc", nativeQuery = true)
    Optional<Iterable<StravaEvent>> findCurrentEvent();

    @Query(value = "SELECT * FROM strava_event e WHERE e.end_date >= NOW() ORDER BY e.end_date desc limit 1", nativeQuery = true)
    Optional<StravaEvent> findExactCurrentEvent();

    @Query(value = "SELECT x.athlete_id, x.event_id, x.team_id, e.event_name, x.accepted FROM strava_register x join strava_event e on e.id = x.event_id WHERE e.end_date >= NOW() AND x.athlete_id = ?1 ORDER BY e.end_date desc LIMIT 1", nativeQuery = true)
    Map<String, Object> findCurrentEventByAthlete(Long athleteId);
}
