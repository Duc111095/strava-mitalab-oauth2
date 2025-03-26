package com.ducnh.oauth2_server.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ducnh.oauth2_server.model.StravaToken;
import com.ducnh.oauth2_server.repository.TokenRepository;


@Service
public class TokenService {
	
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
}
