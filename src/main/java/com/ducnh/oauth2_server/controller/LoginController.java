package com.ducnh.oauth2_server.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ducnh.oauth2_server.service.ActivityService;
import com.ducnh.oauth2_server.service.AthleteUserService;

@Controller
@RequestMapping("")
public class LoginController {

	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private AthleteUserService athleteService;
	
	@Autowired
	private ActivityService activityService;
	
	@Value("${strava.url.athlete.activitiesafter0104}")
	private String activitiesUrl0104;
	
	@Value("${strava.url.activities.lap}")
	private String lapUrl;
	
	Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/oauth_login")
	@ResponseBody
	public String getActivitiesFromStrava(Model model, OAuth2AuthenticationToken authentication) {
		OAuth2User user = authentication.getPrincipal();
		Long athleteId = Long.valueOf(user.getName());
		athleteService.saveAthleteInfoFromStrava(athleteId);
		
		try {
			activityService.saveActivitiesFromStravaResponse(athleteId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
}
