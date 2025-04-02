package com.ducnh.oauth2_server.controller;

import com.ducnh.oauth2_server.form.RegisterForm;
import com.ducnh.oauth2_server.model.RegisterEvent;
import com.ducnh.oauth2_server.model.keys.RegisterIdentity;
import com.ducnh.oauth2_server.service.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

	@Autowired
    private RegisterService registerService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String registerTeam(RegisterForm registerForm, Model model, @AuthenticationPrincipal OAuth2User principal) {
        RegisterEvent registerEvent = new RegisterEvent();
		RegisterIdentity registerIdentity = new RegisterIdentity();
		registerIdentity.setAthleteId(Long.parseLong(principal.getAttribute("id")));
		registerIdentity.setEventId(registerForm.getEventId());
		registerEvent.setRegisterId(registerIdentity);
        registerEvent.setTeamId(registerForm.getTeamId());
        registerService.save(registerEvent);
		Iterable<RegisterEvent> registerEvents = registerService.findAllByEventId(registerForm.getEventId());
        model.addAttribute("registerEvents", registerEvents);
        return "redirect:/register";
    }
}