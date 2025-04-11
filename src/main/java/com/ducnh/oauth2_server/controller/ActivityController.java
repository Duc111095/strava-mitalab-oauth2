package com.ducnh.oauth2_server.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.ducnh.oauth2_server.dto.MetricDTO;
import com.ducnh.oauth2_server.model.StravaEvent;
import com.ducnh.oauth2_server.model.StravaSplitMetrics;
import com.ducnh.oauth2_server.service.ActivityService;
import com.ducnh.oauth2_server.service.EventService;
import com.ducnh.oauth2_server.service.SplitMetricService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("")
public class ActivityController {
	
	@Autowired
	private ActivityService activityService;

	@Autowired
	private SplitMetricService metricService;

	@Autowired
	private EventService eventService;

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
	public ResponseEntity<List<ActivitiesDTO>> updateActivites(@Param("athleteId") Long athleteId,@AuthenticationPrincipal OAuth2User principal) {
		try {
			if (athleteId == null || athleteId == 0) {
				athleteId = Long.parseLong(principal.getName());
			} 
			
			activityService.saveActivitiesFromStravaResponse(athleteId);
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
	
	@GetMapping("/activities/metrics")
	@ResponseBody
	public ResponseEntity<List<MetricDTO>> getInformationLap(@Param("activityId") Long activityId) {
		StravaEvent currentEvent = eventService.findExactCurrentEvent().get();
		List<StravaSplitMetrics> listMetric = metricService.findByActivityId(activityId);
		List<MetricDTO> metrics = listMetric.stream().map(metric -> {
			MetricDTO metricDTO = metricService.convertMetricDTOFromSplitsMetric(currentEvent, metric);
			return metricDTO;
		}).collect(Collectors.toList());

		metrics.sort((a, b) -> a.getSplitId().compareTo(b.getSplitId()));	
		if (metrics.isEmpty() || metrics.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(metrics);
	}
}
