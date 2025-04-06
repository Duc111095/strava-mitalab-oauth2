package com.ducnh.oauth2_server.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ducnh.oauth2_server.dto.ActivitiesDTO;
import com.ducnh.oauth2_server.model.StravaLap;
import com.ducnh.oauth2_server.service.ActivityService;
import com.ducnh.oauth2_server.service.StravaLapService;
import com.ducnh.oauth2_server.service.TokenService;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("")
public class ActivityController {
	
	@Autowired
	private ActivityService activityService;

	@Autowired
	private StravaLapService lapService;

	@Autowired
	private TokenService tokenService;

	@Value("${strava.url.athlete.activities}")
	private String activitiesStravaUrl;
	
	@RequestMapping("/activities")
	public String showActivities(final Model model, @AuthenticationPrincipal OAuth2User principal, @Param("athleteId") Long athleteId) {
		if (athleteId == null) {
			athleteId = Long.parseLong(principal.getName());
		}
		model.addAttribute("athleteId", athleteId);
		List<Map<String, Object>> listExtendedActivities = activityService.listExtendedActivities(athleteId);
		List<ActivitiesDTO> listActivitiesDTO = activityService.convertToActivitiesDTO(listExtendedActivities);
		model.addAttribute("allActivities", listActivitiesDTO);
		return "activities";
	}

	@PostMapping("/activities/update")
	@ResponseBody
	public ResponseEntity<List<ActivitiesDTO>> updateActivites(@AuthenticationPrincipal OAuth2User principal) {
		try {
			Long athleteId = Long.parseLong(principal.getName());
			// Call Strava API to get activities
			ResponseEntity<String> activities = tokenService.sendGetRequest(athleteId, activitiesStravaUrl);
			
			if (activities.getStatusCode().is2xxSuccessful()) {
				activityService.saveActivitiesFromStravaResponse(activities, athleteId);
			} else {
				return ResponseEntity.status(activities.getStatusCode()).body(null);
			}
			List<Map<String, Object>> listExtendedActivities = activityService.listExtendedActivities(athleteId);
			listExtendedActivities.forEach( activity -> {
				System.out.println(activity.keySet());
			});
			List<ActivitiesDTO> listActivitiesDTO = activityService.convertToActivitiesDTO(listExtendedActivities);
			
			return ResponseEntity.ok(listActivitiesDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(null);
		}
	}
	
	@GetMapping("/activities/laps")
	@ResponseBody
	public ResponseEntity<List<StravaLap>> getInformationLap(@Param("activityId") Long activityId) {
		List<StravaLap> laps = lapService.findByActivityId(activityId);
		if (laps.isEmpty() || laps.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(laps);
	}
	
}
