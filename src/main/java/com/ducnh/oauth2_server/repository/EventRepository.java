package com.ducnh.oauth2_server.repository;

import org.springframework.data.repository.CrudRepository;

import com.ducnh.oauth2_server.model.StravaEvent;

public interface EventRepository extends CrudRepository<StravaEvent, String> {

}
