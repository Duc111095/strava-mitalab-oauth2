package com.ducnh.oauth2_server.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ducnh.oauth2_server.model.StravaToken;
import com.ducnh.oauth2_server.repository.TokenRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class TokenService {
	
	private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
	
	@Autowired
	private TokenRepository tokenRepo;
	
	@Value("${strava.url.athlete.accessToken}")
	private String urlPostRefreshToken;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Value("${spring.security.oauth2.client.registration.strava.clientId}")
	private String clientId;
	
	@Value("${spring.security.oauth2.client.registration.strava.clientSecret}")
	private String clientSecret;
	
	public StravaToken save(StravaToken token) {
		return tokenRepo.save(token);
	}
	
	public void update(StravaToken token) {
		tokenRepo.findById(token.getAtheleteId())
			.ifPresent(tokenFromDb -> {
				tokenFromDb.setAccessToken(token.getAccessToken());
				tokenFromDb.setAccessToken(token.getRefreshToken());
				tokenFromDb.setExpiresAt(token.getExpiresAt());
				tokenFromDb.setExpiresIn(token.getExpiresIn());
				tokenFromDb.setTokenType(token.getTokenType());
				tokenRepo.save(tokenFromDb);
			});
	}
	
	public void delele(Long tokenId) {
		tokenRepo.deleteById(tokenId);
	}
	
	public void delete(StravaToken token) {
		tokenRepo.delete(token);
	}
	
	public String getAccessToken(Long athleteId) {
		try {
			StravaToken token = tokenRepo.findById(athleteId).get();
			if (token.getExpiresAt() < Instant.now().getEpochSecond()) {
				ResponseEntity<String> refreshTokenResponse = getAccessTokenFromRefresh(urlPostRefreshToken, token.getRefreshToken());
				JsonNode root = mapper.readTree(refreshTokenResponse.getBody());
				token.setAccessToken(root.get("access_token").asText());
				token.setExpiresAt(root.get("expires_at").asLong());
				token.setExpiresIn(root.get("expires_in").asLong());
				token.setRefreshToken(root.get("refresh_token").asText());
				token.setTokenType(root.get("token_type").asText());
				token.setAtheleteId(athleteId);
				// Save the updated token to the database
				tokenRepo.save(token);
				return root.get("access_token").asText();
			} 
			return token.getAccessToken();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return null;
		}
	}
	
	private ResponseEntity<String> getAccessTokenFromRefresh(String url, String refreshToken) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


		Map<String, String> params = new HashMap<>();
		params.put("client_id", this.clientId);
		params.put("client_secret", this.clientSecret);
		params.put("grant_type", "refresh_token");
		params.put("refresh_token", refreshToken);

		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.setAll(params);

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);
		
		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}
	
	public ResponseEntity<String> sendGetRequest(String accessToken, String url) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		System.out.println("Bearer " + accessToken);
		System.out.println("URL: " + url);	
		HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", headers);
		return restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
	}
	
	public ResponseEntity<String> sendGetRequest(Long athleteId, String url) {
		try {
			String accessToken = this.getAccessToken(athleteId);
			return sendGetRequest(accessToken, url); 
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return new ResponseEntity<String>("ERROR!", HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
