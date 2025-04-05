package com.ducnh.oauth2_server.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ducnh.oauth2_server.model.AthleteUser;
import com.ducnh.oauth2_server.service.AthleteUserService;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("")
public class UserController {

	@Autowired
	private AthleteUserService userService;

	@GetMapping("/athletes")
	public String getUser(Model model) {
		Iterable<AthleteUser> users = userService.findAll();
		model.addAttribute("users", users);
		return "user";
	}

	@GetMapping("/auth-details")
	@ResponseBody
	public Map<String, Object> getAuthDetails(@AuthenticationPrincipal OAuth2User authentication) {
		Map<String, Object> authDetails = new HashMap<>();
		if (authentication != null) {
			authDetails.put("name", authentication.getName());
			authDetails.put("attributes", authentication.getAttributes());
		} else {
			authDetails.put("error", "No authentication details found");
		}
		return authDetails;
	}
}
