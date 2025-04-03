package com.ducnh.oauth2_server.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ducnh.oauth2_server.model.RegisterEvent;
import com.ducnh.oauth2_server.model.keys.RegisterIdentity;

public interface RegisterRepository extends CrudRepository<RegisterEvent, RegisterIdentity>{
    @Query(value = "select * from strava_register r where r.event_id = ?1", nativeQuery = true)
    Iterable<RegisterEvent> findAllByEventId(String eventId);

    @Query(value = "select r.athlete_id, concat(a.first_name, \' \', a.last_name) as athlete_name, r.event_id, e.event_name, r.team_id, r.created_at as registered_at from strava_register r left join strava_user a on r.athlete_id = a.id "
            + " left join strava_event e on r.event_id = e.id"
            + " where r.event_id = ?1", nativeQuery = true)
    List<Map<String, Object>> findRegisteredAthlete(String eventId);
}
