package com.ducnh.oauth2_server.ulti;

import java.util.Arrays;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class CookieUtils {

    private final String ACCESS_TOKEN_COOKIE_NAME = "access_token";

    public void setAccessToken(HttpServletResponse response, String token, int maxAge) {
        response.addCookie(createCookie(ACCESS_TOKEN_COOKIE_NAME, token, maxAge));
    }

    private Cookie createCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        return cookie;
    }

    public String getAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String accessToken = Arrays.stream(cookies)
            .filter(cookie -> cookie.getName().equals(ACCESS_TOKEN_COOKIE_NAME))
            .findFirst()
            .map(Cookie::getValue)
            .orElse(null);
        return accessToken;
    }

    public void deleteAccessToken(HttpServletResponse response) {
        Cookie cookie = new Cookie(ACCESS_TOKEN_COOKIE_NAME, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
