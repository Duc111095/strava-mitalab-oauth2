package com.ducnh.oauth2_server.controller;

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

import com.ducnh.oauth2_server.model.AthleteUser;
import com.ducnh.oauth2_server.model.PolylineMap;
import com.ducnh.oauth2_server.model.StravaActivity;
import com.ducnh.oauth2_server.repository.MapRepository;
import com.ducnh.oauth2_server.service.ActivityService;
import com.ducnh.oauth2_server.service.AthleteUserService;
import com.ducnh.oauth2_server.service.TokenService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Controller
@RequestMapping("")
public class LoginController {

	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private MapRepository mapRepo;
	
	@Autowired
	private AthleteUserService athleteService;
	
	@Autowired
	private ActivityService activityService;
	
	@Value("${strava.url.athlete.activities}")
	private String activitiesUrl;
	
	@Value("${strava.url.athlete.userinfo}")
	private String userInfoUrl;
	
	Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/oauth_login")
	public String getLoginPage(Model model, OAuth2AuthenticationToken authentication) {
		OAuth2User user = authentication.getPrincipal();
		Long athleteId = Long.valueOf(user.getName());
		
		ResponseEntity<String> resultActivites = tokenService.sendGetRequest(athleteId, activitiesUrl);
		ResponseEntity<String> userInfo = tokenService.sendGetRequest(athleteId, userInfoUrl);
		
		try {
			JsonNode treeUserRoot = mapper.readTree(userInfo.getBody());
			AthleteUser user1 = AthleteUser.createFromJsonString(treeUserRoot);
			athleteService.save(user1);
			ArrayNode treeActivityRoot = (ArrayNode) mapper.readTree(resultActivites.getBody());
			StravaActivity activity = new StravaActivity();
			if (treeActivityRoot.isArray() && treeActivityRoot.size() > 0) {
				for (JsonNode root : treeActivityRoot) {
					 PolylineMap map = new PolylineMap();
					 map.setId(root.get("map").get("id") == null ? null : root.get("map").get("id").asText());
					 map.setSummaryPolyline(root.get("map").get("summary_polyline") == null ? null : root.get("map").get("summary_polyline").asText());
					 mapRepo.save(map);
					 activity = StravaActivity.createActivityFromResponse(root);
					 activityService.save(activity);
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("activity", resultActivites.getBody());
		return "oauth_login";
	}
}
