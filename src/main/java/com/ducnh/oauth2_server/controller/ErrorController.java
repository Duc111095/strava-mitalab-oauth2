package com.ducnh.oauth2_server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ErrorController {
    
    @GetMapping("/error")
    public String getMethodName(Model model, @RequestParam(value = "error", required = false) String error) {
        return new String("error");
    }   
}
