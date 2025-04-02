package com.ducnh.oauth2_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ducnh.oauth2_server.form.EventForm;
import com.ducnh.oauth2_server.model.StravaEvent;
import com.ducnh.oauth2_server.service.EventService;

@Controller
@RequestMapping("")
public class EventController {

	@Autowired
	public EventService eventService;
	
	@GetMapping("/events")
	public String getEvents(Model model) {
		Iterable<StravaEvent> events = eventService.findAll();
		model.addAttribute("events", events);
		EventForm eventForm = new EventForm();
		model.addAttribute("newEvent", eventForm);
		return "events";
	}
	
	@PostMapping("/events/create")
    public String createEvent(Model model, 
    		@ModelAttribute("newEvent") EventForm newEvent) {
        StravaEvent event = new StravaEvent(newEvent.getEventName(), newEvent.getTeamCount(), newEvent.getStartDate(), newEvent.getEndDate(), newEvent.getDescription(), newEvent.getLowPace(), newEvent.getHighPace());
        event = eventService.save(event);
		Iterable<StravaEvent> events = eventService.findAll();
		model.addAttribute("events", events);
		return "events";
    }
	
	@GetMapping("/events/{id}/delete")
	public ModelAndView  deleteEvent(ModelMap model, @PathVariable("id") String id) {
		eventService.delete(id);
		Iterable<StravaEvent> events = eventService.findAll();
		model.addAttribute("events", events);
        return new ModelAndView("redirect:/events", model);
     }
	
	@PostMapping("/events/{id}/edit")
	public ModelAndView updateEvent(ModelMap model, @PathVariable("id") String id, @ModelAttribute("newEvent") EventForm newEvent) {
		StravaEvent oldEvents = eventService.findById(id).get();
		oldEvents.setEventName(newEvent.getEventName());
		oldEvents.setStartDate(newEvent.getStartDate());
		oldEvents.setEndDate(newEvent.getEndDate());
		oldEvents.setTeamCount(newEvent.getTeamCount());
		oldEvents.setHighPace(newEvent.getHighPace());
		oldEvents.setLowPace(newEvent.getLowPace());
		oldEvents.setDescription(newEvent.getDescription());
		eventService.save(oldEvents);

		Iterable<StravaEvent> events = eventService.findAll();
		model.addAttribute("events", events);
        return new ModelAndView("redirect:/events", model);
	}
}
