package com.ducnh.oauth2_server.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ducnh.oauth2_server.dto.DetailsResultDTO;
import com.ducnh.oauth2_server.dto.SummaryEventDTO;
import com.ducnh.oauth2_server.form.PeriodForm;
import com.ducnh.oauth2_server.model.ActivitySummary;
import com.ducnh.oauth2_server.model.StravaEvent;
import com.ducnh.oauth2_server.service.ActivityService;
import com.ducnh.oauth2_server.service.EventService;

@Controller
@RequestMapping("")
public class SummaryController {
	
	@Autowired
	private ActivityService activityService;

	@Autowired
	private EventService eventService;

	@GetMapping("/summary")
	public String getTeamSummary(Model model) {
		StravaEvent currEvent = eventService.findExactCurrentEvent().get();
		String eventId = currEvent.getId();
		List<SummaryEventDTO> summaryEvents = new ArrayList<>();
		List<Object[]> results = activityService.getSummaryEvent(eventId, 0, 0);
		for (Object[] result : results) {
			int teamId = ((Number) result[0]).intValue();
			int totalAthlete = ((Number) result[1]).intValue();
			double totalDistance = ((Number) result[2]).doubleValue();
			double totalCurrentDistance = ((Number) result[3]).doubleValue();
			int stt = ((Number) result[4]).intValue();

			SummaryEventDTO summaryEvent = new SummaryEventDTO();
			summaryEvent.setEventId(eventId);
			summaryEvent.setTeamName("Đội " + teamId);
			summaryEvent.setTeamId(teamId);
			summaryEvent.setTeamCount(totalAthlete);
			summaryEvent.setTotalDistance(totalDistance);
			summaryEvent.setTotalCurrentDistance(totalCurrentDistance);
			summaryEvent.setSTT(stt);
			summaryEvents.add(summaryEvent);
		}
		LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd/MM"));
        model.addAttribute("currentDate", formattedDate);
		model.addAttribute("summaryEvents", summaryEvents);
		model.addAttribute("currEvent", currEvent);
		return "summary";
	}
	
	@GetMapping("/summary/team")
	public String initSummary(Model model, @Param("teamId") int teamId, @Param("eventId") String eventId) {
		List<DetailsResultDTO> detailsSummary = new ArrayList<>();
		List<Object[]> results = activityService.getSummaryEvent(eventId, teamId, 1);
		
		if (results.isEmpty()) {
			model.addAttribute("message", "Không có dữ liệu cho đội này");
			return "detailsSummary";
		}
		for (Object[] result : results) {
			Long athleteId = ((Number) result[0]).longValue();
			String athleteName = (String) result[2];
			int team_id = ((Number) result[1]).intValue();
			String teamName = "Đội " + team_id;
			double totalDistance = ((Number) result[3]).doubleValue();
			double totalCurrentDistance = ((Number) result[4]).doubleValue();
			int stt = ((Number) result[5]).intValue();
			DetailsResultDTO detailResult = new DetailsResultDTO();
			
			detailResult.setAthleteId(athleteId);
			detailResult.setAthleteName(athleteName);
			detailResult.setTeamId(teamId);
			detailResult.setTotalDistance(totalDistance);
			detailResult.setTotalCurrentDistance(totalCurrentDistance);
			detailResult.setStt(stt);
			detailResult.setTeamName(teamName);
			detailsSummary.add(detailResult);
		}
		LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd/MM"));
        model.addAttribute("teamId", teamId);
		model.addAttribute("currentDate", formattedDate);
		model.addAttribute("detailsSummary", detailsSummary);
		model.addAttribute("teamId", teamId);
		return "detailsSummary";
	}

	@PostMapping("/summary")
	public String getActivitySummary(Model model, @ModelAttribute PeriodForm periodForm) {
		LocalDateTime startDate = periodForm.getStartDate();
		LocalDateTime endDate = periodForm.getEndDate();
		List<ActivitySummary> allActivitiesSummary = activityService.getActivitySummaryByPeriodDate(startDate, endDate); 
		model.addAttribute("allActivitiesSummary", allActivitiesSummary);
		return "summary";
	}
}
