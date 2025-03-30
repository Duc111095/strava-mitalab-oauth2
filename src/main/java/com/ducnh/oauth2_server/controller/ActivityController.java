package com.ducnh.oauth2_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ducnh.oauth2_server.model.StravaActivity;
import com.ducnh.oauth2_server.service.ActivityService;

@Controller
public class ActivityController {
	
	@Autowired
	private ActivityService activityService;
	
	@ModelAttribute("allActivities")
	public Iterable<StravaActivity> populateActivities() {
		return activityService.findAll();
	}
	
	@RequestMapping("/activities")
	public String showActivities(final Model model) {
		return "activities";
	}
	
}
