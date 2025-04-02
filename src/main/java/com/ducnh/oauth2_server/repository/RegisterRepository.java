package com.ducnh.oauth2_server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ducnh.oauth2_server.model.RegisterEvent;
import com.ducnh.oauth2_server.model.keys.RegisterIdentity;

public interface RegisterRepository extends CrudRepository<RegisterEvent, RegisterIdentity>{
    @Query("SELECT r FROM strava_register r WHERE r.id.eventId = ?1")
    Iterable<RegisterEvent> findAllByEventId(String eventId);
}
