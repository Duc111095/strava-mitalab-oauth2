package com.ducnh.oauth2_server.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
	
	public void delete(String id) {
		eventRepository.deleteById(id);
	}
	
	public Optional<StravaEvent> findById(String id) {
		return eventRepository.findById(id);
	}

	public boolean existsById(String id) {
		return eventRepository.existsById(id);
	}
	
	public Iterable<StravaEvent> findCurrentEvent() {
		return eventRepository.findCurrentEvent();
	}

	public List<Map<String, Object>> findCurrentEventByAthlete(Long athleteId) {
		return eventRepository.findCurrentEventByAthlete(athleteId);
	}

	public Optional<StravaEvent> findExactCurrentEvent() {
		return eventRepository.findExactCurrentEvent();
	}
}
