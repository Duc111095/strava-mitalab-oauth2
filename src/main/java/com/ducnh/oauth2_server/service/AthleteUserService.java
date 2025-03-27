package com.ducnh.oauth2_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ducnh.oauth2_server.model.AthleteUser;
import com.ducnh.oauth2_server.repository.AthleteUserRepository;

@Service
public class AthleteUserService {
	
	@Autowired
	private AthleteUserRepository athleteRepo;

	public void save(AthleteUser user) {
		athleteRepo.save(user);
	}
}
