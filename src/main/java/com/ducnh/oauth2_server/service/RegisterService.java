package com.ducnh.oauth2_server.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ducnh.oauth2_server.model.RegisterEvent;
import com.ducnh.oauth2_server.model.keys.RegisterIdentity;
import com.ducnh.oauth2_server.repository.RegisterRepository;

@Service
public class RegisterService {
    
    @Autowired
    private RegisterRepository registerRepository;
    
    public void save(RegisterEvent registerEvent) {
        registerRepository.save(registerEvent); 
    }

    public <Optional>RegisterEvent findById(String eventId, Long athleteId) {
        return registerRepository.findById(new RegisterIdentity(eventId, athleteId)).orElse(null);
    }

    public void deleteById(String eventId, Long athleteId) {
        registerRepository.deleteById(new RegisterIdentity(eventId, athleteId));
    }

    public boolean existsById(String eventId, Long athleteId) {
        return registerRepository.existsById(new RegisterIdentity(eventId, athleteId));
    }

    public Iterable<RegisterEvent> findAll() {
        return registerRepository.findAll();
    }

    public Iterable<RegisterEvent> findAllByEventId(String eventId) {
        return registerRepository.findAllByEventId(eventId);
    }

    public List<Map<String, Object>> findRegisteredAthlete(String eventId){
        return registerRepository.findRegisteredAthlete(eventId);
    }

    public List<Map<String, Object>> listUnacceptedAthletes(String eventId){
        return registerRepository.listUnacceptedAthletes(eventId);
    }

    public void acceptRegister(String eventId, Long athleteId) {
        RegisterEvent registerEvent = registerRepository.findById(new RegisterIdentity(eventId, athleteId)).orElse(null);
        if (registerEvent != null) {
            registerEvent.setAccepted(true);
            registerRepository.save(registerEvent);
        }
    }

    public boolean isRegisted(String eventId, Long athleteId) {
        return this.existsById(eventId, athleteId);
    }

    public boolean isAccepted(String eventId, Long athleteId) {
        RegisterEvent event = this.findById(eventId, athleteId);
        return event == null ? false : event.isAccepted();
    }
}
