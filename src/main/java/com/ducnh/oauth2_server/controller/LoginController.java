package com.ducnh.oauth2_server.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ResolvableType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ducnh.oauth2_server.model.AthleteUser;
import com.ducnh.oauth2_server.service.AthleteUserService;
import com.ducnh.oauth2_server.service.TokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class LoginController {

	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AthleteUserService athleteService;
	
	@Value("${strava.url.athlete.activities}")
	private String activitiesUrl;
	
	@Value("${strava.url.athlete.userinfo}")
	private String userInfoUrl;
	
	Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

	@GetMapping("/oauth_login")
	public String getLoginPage(Model model, OAuth2AuthenticationToken authentication) {
		OAuth2User user = authentication.getPrincipal();
		String accessTokenDb = tokenService.getAccessToken(Long.valueOf(user.getName()));
		ResponseEntity<String> resultActivites = tokenService.sendGetRequest(accessTokenDb, activitiesUrl);
		logger.info(resultActivites.getBody());
		ResponseEntity<String> userInfo = tokenService.sendGetRequest(accessTokenDb, userInfoUrl);
		logger.info(userInfo.getBody());
		
		try {
			JsonNode treeNodeRoot = mapper.readTree(userInfo.getBody());
			AthleteUser user1 = AthleteUser.createFromJsonString(treeNodeRoot);
			athleteService.save(user1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("accessTokenDb", accessTokenDb);
		return accessTokenDb;
	}
}
