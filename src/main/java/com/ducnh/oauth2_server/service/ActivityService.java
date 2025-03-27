package com.ducnh.oauth2_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ducnh.oauth2_server.model.StravaActivity;
import com.ducnh.oauth2_server.repository.ActivityRepository;

@Service
public class ActivityService {

	@Autowired
	private ActivityRepository activityRepo;
	
	public void save(StravaActivity activity) {
		activityRepo.save(activity);
	}
}
