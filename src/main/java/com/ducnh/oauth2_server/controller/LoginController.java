package com.ducnh.oauth2_server.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ducnh.oauth2_server.dto.ResponseUser;
import com.ducnh.oauth2_server.model.AthleteUser;
import com.ducnh.oauth2_server.model.StravaEvent;
import com.ducnh.oauth2_server.service.ActivityService;
import com.ducnh.oauth2_server.service.AthleteUserService;
import com.ducnh.oauth2_server.service.EventService;
import com.ducnh.oauth2_server.service.RegisterService;

@Controller
@RequestMapping("")
public class LoginController {

	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private ActivityService activityService;

	@Autowired
	private EventService eventService;

	@Autowired
	private AthleteUserService userService;

	@Autowired
	private RegisterService registerService;
	
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
		
		// athleteService.saveAthleteInfoFromStrava(athleteId);
		StravaEvent event = eventService.findExactCurrentEvent().orElse(null);
		try {
			if (event != null) {
				if (registerService.isAccepted(event.getId(), athleteId)) {
					activityService.saveActivitiesFromStravaResponse(athleteId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	@ResponseBody
	@RequestMapping("/user_info")
	public ResponseEntity<ResponseUser> getUserInfomation(OAuth2AuthenticationToken authentication) {
		OAuth2User user = authentication.getPrincipal();
		System.out.println(user);
		Long athleteId = Long.valueOf(user.getName());
		AthleteUser userInfo = userService.findById(athleteId).orElse(null);
		ResponseUser responseUser = new ResponseUser();
		if (userInfo != null) {
			Map<String, Object> data = new HashMap<>();
			data.put("first_name", userInfo.getFirstName());
			data.put("last_name", userInfo.getLastName());
			data.put("profile", userInfo.getProfile());
			data.put("profile_medium", userInfo.getProfileMedium());
			responseUser.setData(data);
			responseUser.setMessage("success");
			responseUser.setTime(LocalDateTime.now());
		} else {
			responseUser.setMessage("error");
			responseUser.setData(new HashMap<>());
			responseUser.setTime(LocalDateTime.now());
		}
		return ResponseEntity.ok(responseUser);
	}
}
