package com.ducnh.oauth2_server.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ducnh.oauth2_server.service.TokenService;

@RestController
public class LoginController {

	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	private static String authorizationRequestBaseUri = "oauth2/authorization";
	
	Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;
	
	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;
	
	@Autowired
	private TokenService tokenService;

	@GetMapping("/oauth_login")
	public String getLoginPage(Model model, OAuth2AuthenticationToken authentication) {
		OAuth2User user = authentication.getPrincipal();
		OAuth2AuthorizedClient authorizedClient =
				this.authorizedClientService.loadAuthorizedClient(
					authentication.getAuthorizedClientRegistrationId(),
					authentication.getName());
		OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
		Iterable<ClientRegistration> clientRegistrations = null;
		ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
		
		if (type != ResolvableType.NONE &&
				ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
			clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
		}
		String accessTokenDb = tokenService.getAccessToken(Long.valueOf(user.getName()));
		String urls = "https://www.strava.com/api/v3/athlete";
		ResponseEntity<String> result = tokenService.sendGetRequest(accessTokenDb, urls);
		logger.info(result.getBody());
		
		clientRegistrations.forEach(registration -> 
				oauth2AuthenticationUrls.put(registration.getClientName(), authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
		
		model.addAttribute("urls", oauth2AuthenticationUrls);
		return accessToken.getTokenValue();
	}
}
