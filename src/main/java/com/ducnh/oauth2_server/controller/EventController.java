package com.ducnh.oauth2_server.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ducnh.oauth2_server.form.EventForm;
import com.ducnh.oauth2_server.model.StravaEvent;
import com.ducnh.oauth2_server.service.EventService;

@Controller
public class EventController {

	@Autowired
	public EventService eventService;
	
	@GetMapping("/events")
	public String getEvents(Model model) {
		Iterable<StravaEvent> events = eventService.findAll();
		model.addAttribute("events", events);
		EventForm eventForm = new EventForm();
		model.addAttribute("eventForm", eventForm);
		return "events";
	}
	
	@PostMapping("/events/create")
    public String createEvent(Model model, 
    		@ModelAttribute("eventForm") EventForm eventForm) {
        StravaEvent event = new StravaEvent(eventForm.getEventName(), eventForm.getTeamCount(), eventForm.getStartDate(), eventForm.getEndDate(), eventForm.getDescription());
        event = eventService.save(event);
		Iterable<StravaEvent> events = eventService.findAll();
		model.addAttribute("events", events);
		return "events";
    }
	
}
