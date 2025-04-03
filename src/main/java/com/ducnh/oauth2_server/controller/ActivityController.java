package com.ducnh.oauth2_server.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ducnh.oauth2_server.dto.ActivitiesDTO;
import com.ducnh.oauth2_server.service.ActivityService;

@Controller
@RequestMapping("")
public class ActivityController {
	
	@Autowired
	private ActivityService activityService;
	
	@RequestMapping("/activities")
	public String showActivities(final Model model, @AuthenticationPrincipal OAuth2User principal) {
		Long athleteId = Long.parseLong(principal.getName());
		System.out.println("Athlete ID: " + athleteId);
		model.addAttribute("athleteId", athleteId);
		List<Map<String, Object>> listExtendedActivities = activityService.listExtendedActivities(athleteId);
		List<ActivitiesDTO> listActivitiesDTO = new ArrayList<>();

		listExtendedActivities.stream().forEach(extendedActivity -> {
			ActivitiesDTO activitiesDTO = new ActivitiesDTO();
			activitiesDTO.setAthleteID(extendedActivity.get("athlete_id") != null ? Long.parseLong(extendedActivity.get("athlete_id").toString()) : null);
			activitiesDTO.setAthleteName(extendedActivity.get("athlete_name") != null ? extendedActivity.get("athlete_name").toString() : null);
			activitiesDTO.setDistance(extendedActivity.get("distance") != null ? Double.parseDouble(extendedActivity.get("distance").toString()) : null);
			activitiesDTO.setMovingTime(extendedActivity.get("moving_time") != null ? Integer.parseInt(extendedActivity.get("moving_time").toString()) : null);
			activitiesDTO.setStartDateLocal(extendedActivity.get("start_date_local") != null ? ((Timestamp)extendedActivity.get("start_date_local")).toLocalDateTime() : null);
			activitiesDTO.setActivityName(extendedActivity.get("name") != null ? extendedActivity.get("name").toString() : null);
			activitiesDTO.setActivityID(extendedActivity.get("id") != null ? Long.parseLong(extendedActivity.get("id").toString()) : null);
			listActivitiesDTO.add(activitiesDTO);
		});
		model.addAttribute("allActivities", listActivitiesDTO);
		return "activities";
	}
}
