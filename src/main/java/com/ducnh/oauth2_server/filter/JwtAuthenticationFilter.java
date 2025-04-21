package com.ducnh.oauth2_server.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ducnh.oauth2_server.repository.AthleteUserRepository;
import com.ducnh.oauth2_server.ulti.CookieUtils;
import com.ducnh.oauth2_server.ulti.JwtToken;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private CookieUtils cookieUtils;

    @Autowired
    private AthleteUserRepository userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                String token = cookieUtils.getAccessToken(request);
                if (token != null && jwtToken.validateToken(token)) {
                    Long athleteId = Long.parseLong(jwtToken.getUsernameFromToken(token));
                    if (athleteId != null) {
                        userRepo.findById(athleteId).ifPresent(user -> {
                            
                            // SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
                        });
                    }
                } else {
                    cookieUtils.deleteAccessToken(response);
                }
                filterChain.doFilter(request, response);
            }
}
