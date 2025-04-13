package com.ducnh.oauth2_server.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ducnh.oauth2_server.dto.EventDTO;
import com.ducnh.oauth2_server.dto.RegisteredAthleteDTO;
import com.ducnh.oauth2_server.form.RegisterForm;
import com.ducnh.oauth2_server.model.RegisterEvent;
import com.ducnh.oauth2_server.model.StravaEvent;
import com.ducnh.oauth2_server.model.keys.RegisterIdentity;
import com.ducnh.oauth2_server.service.EventService;
import com.ducnh.oauth2_server.service.RegisterService;


@Controller
public class RegisterController {

	@Autowired
    private RegisterService registerService;

    @Autowired
    private EventService eventService;
    
    @GetMapping("/register")
    public String showRegisterForm(Model model, @AuthenticationPrincipal OAuth2User principal) {
        model.addAttribute("registerForm", new RegisterForm());
        Iterable<StravaEvent> events = eventService.findCurrentEvent().orElseThrow(() -> new RuntimeException("No current event found"));
        model.addAttribute("events", events);
        return "register";
    }

    @GetMapping("register/init")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCurrentEvent(Model model, @AuthenticationPrincipal OAuth2User principal) {
        Long athleteId = Long.parseLong(principal.getName());
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> item = eventService.findCurrentEventByAthlete(athleteId);
        EventDTO eventDTO = new EventDTO();
        if (item.size() > 0) {
            eventDTO.setAthleteId(Long.parseLong((item.get("athlete_id").toString())));
            eventDTO.setEventId(item.get("event_id").toString());
            eventDTO.setTeamId((int) item.get("team_id"));
            eventDTO.setEventName(item.get("event_name").toString());
            eventDTO.setAccepted((Boolean) item.get("accepted"));
            eventDTO.setRegistered(true);
            model.addAttribute("event", eventDTO);
        }
        String eventId = eventDTO.getEventId();
        if (eventId == null) {
            eventId = eventService.findCurrentEvent().orElseThrow(() -> new RuntimeException("No current event found")).iterator().next().getId();
        }
        List<RegisteredAthleteDTO> registeredAthletes = getRegisteredAthletes(eventId);
        result.put("event", eventDTO);
        result.put("registeredAthletes", registeredAthletes);
        return ResponseEntity.ok(result);
    }
    

    @PostMapping("/register")
    public String registerTeam(@ModelAttribute("registerForm") RegisterForm registerForm, Model model, @AuthenticationPrincipal OAuth2User principal) {
        RegisterEvent registerEvent = new RegisterEvent();
		RegisterIdentity registerIdentity = new RegisterIdentity();
		registerIdentity.setAthleteId(Long.parseLong(principal.getName()));
		registerIdentity.setEventId(registerForm.getEventId());
		registerEvent.setRegisterId(registerIdentity);
        registerEvent.setTeamId(registerForm.getTeamId());
        registerEvent.setCreatedAt(LocalDateTime.now());
        registerEvent.setUpdatedAt(LocalDateTime.now());
        registerService.save(registerEvent);
        return "redirect:/register";
    }

    @PostMapping("/registered-athletes")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getRegisteredAthletes(@RequestBody Map<String, String> request, @AuthenticationPrincipal OAuth2User principal) {
        Long athleteId = Long.parseLong(principal.getName());
        String eventId = request.get("eventId");
        Map<String, Object> result = new HashMap<>();

        EventDTO eventDTO = new EventDTO();

        if (registerService.existsById(eventId, athleteId)) {
            RegisterEvent register = registerService.findById(eventId, athleteId);
            eventDTO.setAthleteId(register.getAthleteId());
            eventDTO.setEventId(register.getEventId());
            eventDTO.setTeamId(register.getTeamId());
            eventDTO.setAccepted(register.isAccepted());
            eventDTO.setRegistered(true);
        }
        List<RegisteredAthleteDTO> registeredAthletes = getRegisteredAthletes(eventId);
        result.put("event", eventDTO);
        result.put("registeredAthletes", registeredAthletes);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/register/unaccepted")
    public String getUnacceptedAthletes(Model model, @AuthenticationPrincipal OAuth2User principal) {
        Iterable<StravaEvent> currEvents = eventService.findCurrentEvent().orElseThrow(() -> new RuntimeException("No current event found"));
        if (currEvents == null) {
            return "redirect:/register";
        }
        
        List<RegisteredAthleteDTO> unacceptedAthletes = new ArrayList<>();
        
        for (StravaEvent currEvent : currEvents) {
            String eventId = currEvent.getId();
            System.out.println("Event ID: " + eventId);
            model.addAttribute("currEvent", currEvent);
    
            List<Map<String, Object>> unacceptedMap = registerService.listUnacceptedAthletes(eventId);
    
            unacceptedAthletes.addAll(unacceptedMap.stream().map(item -> {
                RegisteredAthleteDTO registeredAthleteDTO = new RegisteredAthleteDTO();
                registeredAthleteDTO.setAthleteId(Long.parseLong(String.valueOf(item.get("athlete_id"))));
                registeredAthleteDTO.setAthleteName(item.get("athlete_name") == null ? "" : (String) item.get("athlete_name"));
                registeredAthleteDTO.setEventId((String) item.get("event_id"));
                registeredAthleteDTO.setTeamId(String.valueOf(item.get("team_id")));
                registeredAthleteDTO.setEventName((String) item.get("event_name"));
                Timestamp registered_at = (Timestamp) item.get("registered_at");
                Timestamp updated_at = (Timestamp) item.get("updated_at");
                registeredAthleteDTO.setUpdatedAt(updated_at == null ? null : updated_at.toLocalDateTime());
                registeredAthleteDTO.setRegisteredAt(registered_at == null ? null : registered_at.toLocalDateTime().plusHours(7));
                return registeredAthleteDTO;
            }).collect(Collectors.toList()));
        }
        
        model.addAttribute("unRegisteredAthletes", unacceptedAthletes);
        return "unaccepted";
    }

    @PostMapping("/register/accept")
    @ResponseBody
    public String acceptAthlete(@RequestBody Map<String, String> request) {
        try {
            String eventId = request.get("eventId");
            Long athleteId = Long.parseLong(request.get("athleteId"));
            registerService.acceptRegister(eventId, athleteId);
            return ResponseEntity.ok("success").getBody();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error accepting athlete: " + e.getMessage()).getBody();
        }
    }

    @PostMapping("/register/delete")
    @ResponseBody
    public String deleteAthlete(@RequestBody Map<String, String> request) {
        try {
            String eventId = request.get("eventId");
            Long athleteId = Long.parseLong(request.get("athleteId"));
            registerService.deleteById(eventId, athleteId);
            return ResponseEntity.ok("success").getBody();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error accepting athlete: " + e.getMessage()).getBody();
        }
    }

    @GetMapping("/register/leave")
    public ResponseEntity<?> leaveRegistered(@Param("eventId") String eventId, @Param("athleteId") Long athleteId) {
        if (registerService.existsById(eventId, athleteId)) {
            registerService.deleteById(eventId, athleteId);
        }
        return ResponseEntity.ok().body(null);
    }

    private List<RegisteredAthleteDTO> getRegisteredAthletes(String eventId) {
        List<Map<String, Object>> registerEvents = registerService.findRegisteredAthlete(eventId);
        System.out.println("Debugging...."); 
        System.out.println("Event ID: " + eventId);
        return registerEvents.stream().map(item -> {
            RegisteredAthleteDTO registeredAthleteDTO = new RegisteredAthleteDTO();
            registeredAthleteDTO.setAthleteId(Long.parseLong(String.valueOf(item.get("athlete_id"))));
            registeredAthleteDTO.setAthleteName(item.get("athlete_name") == null ? "" : (String) item.get("athlete_name"));
            registeredAthleteDTO.setEventId((String) item.get("event_id"));
            registeredAthleteDTO.setTeamId(String.valueOf(item.get("team_id")));
            registeredAthleteDTO.setEventName((String) item.get("event_name"));
            Timestamp registered_at = (Timestamp) item.get("registered_at");
            Timestamp updated_at = (Timestamp) item.get("updated_at");
            registeredAthleteDTO.setUpdatedAt(updated_at == null ? null : updated_at.toLocalDateTime());
            registeredAthleteDTO.setRegisteredAt(registered_at == null ? null : registered_at.toLocalDateTime().plusHours(7));
            return registeredAthleteDTO;
        }).sorted((r1, r2) -> r2.getRegisteredAt().compareTo(r1.getRegisteredAt())).collect(Collectors.toList());
    }
}