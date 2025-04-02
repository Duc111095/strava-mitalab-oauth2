package com.ducnh.oauth2_server.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ducnh.oauth2_server.form.PeriodForm;
import com.ducnh.oauth2_server.model.ActivitySummary;
import com.ducnh.oauth2_server.service.ActivityService;

@Controller
@RequestMapping("")
public class SummaryController {
	
	@Autowired
	private ActivityService activityService;
	
	@GetMapping("/summary")
	public String initSummary(Model model) {
		PeriodForm periodForm = new PeriodForm();
		model.addAttribute("periodForm", periodForm);
		return "summary";
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
