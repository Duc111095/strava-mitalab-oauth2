package com.ducnh.oauth2_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ducnh.oauth2_server.model.StravaLap;
import com.ducnh.oauth2_server.repository.StravaLapRepository;

@Service
public class StravaLapService {
        
        @Autowired
        private StravaLapRepository stravaLapRepo;
        
        public void save(StravaLap lap) {
            stravaLapRepo.save(lap);
        }
        
        public Iterable<StravaLap> findAll() {
            return stravaLapRepo.findAll();
        }
        
        public Iterable<StravaLap> findByAthleteId(Long athleteId) {
            return stravaLapRepo.findByAthleteId(athleteId);
        }
        
        public Iterable<StravaLap> findByActivityId(Long activityId) {
            return stravaLapRepo.findByActivityId(activityId);
        }
}
