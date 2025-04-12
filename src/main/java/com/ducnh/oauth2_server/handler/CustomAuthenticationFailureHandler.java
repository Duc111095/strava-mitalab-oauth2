package com.ducnh.oauth2_server.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = exception.getMessage();
        if (errorMessage.contains("Too Many Requests")) {
            errorMessage = "Hệ thống đang xử lý. Vui lòng thử lại sau.";
        } else {
            errorMessage = "";
        }
        logger.error(errorMessage, exception);
        response.sendRedirect("/runner/cuserror?error=true");            
    } 
}
