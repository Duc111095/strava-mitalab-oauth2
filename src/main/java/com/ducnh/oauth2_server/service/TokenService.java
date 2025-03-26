package com.ducnh.oauth2_server.service;

import java.security.Principal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ducnh.oauth2_server.model.StravaToken;
import com.ducnh.oauth2_server.repository.TokenRepository;


@Service
public class TokenService {
	
	private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
	
	@Autowired
	private TokenRepository tokenRepo;
	
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
				// TODO
				//getAccessTokenFromRefresh();
				return "abc";
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
		
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("client_id", "{client_id}")
				.queryParam("client_secret", "{client_secret}")
				.queryParam("grant_type", "{grant_type}")
				.queryParam("refresh_token", "{refresh_token}")
				.encode()
				.toString();
		
		Map<String, String> params = new HashMap<>();
		params.put("client_id", "152115");
		params.put("client_secret", "27e1f519e4f98eac360feee381e74ace57c93df0");
		params.put("grant_type", "refresh_token");
		params.put("refresh_token", refreshToken);
		
		return restTemplate.exchange(urlTemplate, HttpMethod.POST, entity, String.class, params);
	}
	
	public ResponseEntity<String> sendGetRequest(String accessToken, String url) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", headers);
		return restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
	}
}
