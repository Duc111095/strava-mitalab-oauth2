package com.ducnh.oauth2_server.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ducnh.oauth2_server.model.AthleteUser;
import com.ducnh.oauth2_server.repository.AthleteUserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class AthleteUserService {
	
	@Autowired
	private AthleteUserRepository athleteRepo;

	@Autowired
	private TokenService tokenService;

	@Value("${strava.url.athlete.userinfo}")
	private String userInfoUrl;

	@Autowired
	private ObjectMapper mapper;

	public void save(AthleteUser user) {
		athleteRepo.save(user);
	}
	
	public Optional<AthleteUser> findById(Long id) {
		return athleteRepo.findById(id);
	}
	
	public Iterable<AthleteUser> findAll() {
		return athleteRepo.findAll();
	}

	public void saveAthleteInfoFromStrava(Long athleteId) throws RuntimeException {
		AthleteUser athleteUser = athleteRepo.findById(athleteId).orElse(new AthleteUser());
		ResponseEntity<String> userInfo = tokenService.sendGetRequest(athleteId, userInfoUrl);
		try {
			JsonNode treeUserRoot = mapper.readTree(userInfo.getBody());
			athleteUser = AthleteUser.createFromJsonString(treeUserRoot, athleteUser);
			athleteRepo.save(athleteUser);
		} catch (Exception e) {
			throw new RuntimeException("Error parsing athlete user info: " + e.getMessage(), e);
		}
	}

	public void saveAthleteInfoFromStrava(ResponseEntity<Map<String, Object>> response) throws RuntimeException {
		try {
			AthleteUser athleteUser = AthleteUser.createFromJsonMapStringObject(response);
			athleteRepo.save(athleteUser);
		} catch (Exception e) {
			throw new RuntimeException("Error parsing athlete user info: " + e.getMessage(), e);
		}
	}
}
