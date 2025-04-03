package com.ducnh.oauth2_server.service;

import java.util.List;
import java.util.Optional;

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
	
	public Optional<AthleteUser> findById(Long id) {
		return athleteRepo.findById(id);
	}
	
	public Iterable<AthleteUser> findAll() {
		return athleteRepo.findAll();
	}

}
