package com.ducnh.oauth2_server.controller;

import com.ducnh.oauth2_server.dto.RegisteredAthleteDTO;
import com.ducnh.oauth2_server.form.RegisterForm;
import com.ducnh.oauth2_server.model.RegisterEvent;
import com.ducnh.oauth2_server.model.StravaEvent;
import com.ducnh.oauth2_server.model.keys.RegisterIdentity;
import com.ducnh.oauth2_server.service.EventService;
import com.ducnh.oauth2_server.service.RegisterService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class RegisterController {

	@Autowired
    private RegisterService registerService;

    @Autowired
    private EventService eventService;
    
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        Iterable<StravaEvent> events = eventService.findAll();
        model.addAttribute("events", events);
        return "register";
    }

    @PostMapping("/register")
    public String registerTeam(@ModelAttribute("registerForm") RegisterForm registerForm, Model model, @AuthenticationPrincipal OAuth2User principal) {
        RegisterEvent registerEvent = new RegisterEvent();
		RegisterIdentity registerIdentity = new RegisterIdentity();
		registerIdentity.setAthleteId(Long.parseLong(principal.getName()));
		registerIdentity.setEventId(registerForm.getEventId());
		registerEvent.setRegisterId(registerIdentity);
        registerEvent.setTeamId(registerForm.getTeamId());
        registerService.save(registerEvent);
        return "register";
    }

    @PostMapping("/registered-athletes")
    @ResponseBody
    public List<RegisteredAthleteDTO> getRegisteredAthletes(@RequestBody Map<String, String> request) {
        String eventId = request.get("eventId");
        List<Map<String, Object>> registerEvents = registerService.findRegisteredAthlete(eventId);

        return registerEvents.stream().map(item -> {
            RegisteredAthleteDTO registeredAthleteDTO = new RegisteredAthleteDTO();
            registeredAthleteDTO.setAthleteId(Long.parseLong(String.valueOf(item.get("athlete_id"))));
            registeredAthleteDTO.setAthleteName(item.get("athlete_name") == null ? "" : (String) item.get("athlete_name"));
            registeredAthleteDTO.setEventId((String) item.get("event_id"));
            registeredAthleteDTO.setTeamId(String.valueOf(item.get("team_id")));
            registeredAthleteDTO.setEventName((String) item.get("event_name"));
            Timestamp timestamp = (Timestamp) item.get("registered_at");
            registeredAthleteDTO.setRegisteredAt(timestamp == null ? null : timestamp.toLocalDateTime());
            return registeredAthleteDTO;
        }).collect(Collectors.toList());
    }
}