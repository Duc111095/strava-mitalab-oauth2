package com.ducnh.oauth2_server.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("")
public class ErrorController {

    @GetMapping("/cuserror")
    public String getMethodName(Model model, @Param(value = "error") String error, @Param(value = "message") String message) {
        if (error.equals("true")){
            model.addAttribute("error", message);
        }
        return "customError";
    }

    @GetMapping("/error")
    public String errorHandler(Model model, @Param(value = "error") String error, @Param(value = "message") String message) {
        if (error.equals("true")){
            model.addAttribute("error", message);
        }
        return "customError";
    }   
}
