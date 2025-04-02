package com.ducnh.oauth2_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ducnh.oauth2_server.model.AthleteUser;
import com.ducnh.oauth2_server.service.AthleteUserService;

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
	
}
