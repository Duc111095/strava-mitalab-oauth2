package com.ducnh.oauth2_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ducnh.oauth2_server.model.StravaEvent;
import com.ducnh.oauth2_server.repository.EventRepository;

@Service
public class EventService {
	
	@Autowired
	public EventRepository eventRepository;
	
	public StravaEvent save(StravaEvent event) {
		return eventRepository.save(event);
	}
	
	public Iterable<StravaEvent> findAll() {
		return eventRepository.findAll();
	}
}
