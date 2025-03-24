package com.ducnh.oauth2_server;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class SecurityConfig {
	public static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	
	private static String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.";
	private static List<String> clients  = Arrays.asList("github", "strava");
	
	@Autowired
	private Environment env;
	
	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		List<ClientRegistration> registrations = clients.stream()
				.map(c -> getRegistration(c))
				.filter(registration -> registration != null)
				.toList();
		return new InMemoryClientRegistrationRepository(registrations);
	}
	
	private ClientRegistration getRegistration(String client) {
		logger.info(client);
		String clientId = env.getProperty(CLIENT_PROPERTY_KEY + client + ".clientId");
		if (clientId == null) {
			return null;
		}
		logger.info("Client Id: " + clientId);

		String clientSecret = env.getProperty(CLIENT_PROPERTY_KEY + client + ".clientSecret");
		if (client.equals("github")) {
			return CommonOAuth2Provider.GITHUB.getBuilder(client)
					.clientId(clientId).clientSecret(clientSecret).build();
		}
		if (client.equals("strava")) {
			return stravaClientRegistration(clientId, clientSecret);
		}
		logger.info("Client Secret: " + clientSecret);

		return null;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/",
				"/info",
				"/error",
                "/favicon.ico",
                "/**/*.png",
                "/**/*.gif",
                "/**/*.svg",
                "/**/*.jpg",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js")
			.permitAll()
		.antMatchers("/auth/**", "/oauth2/**")
			.permitAll()
		.anyRequest().authenticated()
		.and()
		.oauth2Login(oauth2Login ->
			oauth2Login
                /*.authorizationEndpoint(authorizationEndpoint ->
	                authorizationEndpoint
	                    .authorizationRequestResolver(
	                        new CustomAuthorizationRequestResolver(
	                                clientRegistrationRepository()))
                		)*/
                .clientRegistrationRepository(clientRegistrationRepository())
        		.authorizedClientService(authorizedClientService())
        		.defaultSuccessUrl("/oauth_login"));
		return http.build();
	}
	
	@Bean
	public OAuth2AuthorizedClientService authorizedClientService() {
		return new InMemoryOAuth2AuthorizedClientService(
				clientRegistrationRepository());
	}
	
	private ClientRegistration stravaClientRegistration(String clientId, String clientSecret) {
        return ClientRegistration.withRegistrationId("strava")
            .clientId(clientId)
            .clientSecret(clientSecret)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
            .scope("activity:read_all")
            .authorizationUri("https://www.strava.com/oauth/authorize")
            .tokenUri("https://www.strava.com/api/v3/oauth/token?client_id=152115&client_secret=27e1f519e4f98eac360feee381e74ace57c93df0")
            .userInfoUri("https://www.strava.com/api/v3/athlete")
            .userNameAttributeName("id")
            .clientName("Strava")
            .build();
    }
	/*
	@Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient(){
        DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient = 
          new DefaultAuthorizationCodeTokenResponseClient(); 
        accessTokenResponseClient.setRequestEntityConverter(new CustomRequestEntityConverter()); 

        OAuth2AccessTokenResponseHttpMessageConverter tokenResponseHttpMessageConverter = 
          new OAuth2AccessTokenResponseHttpMessageConverter(); 
        tokenResponseHttpMessageConverter.setAccessTokenResponseConverter(new CustomTokenResponseConverter()); 
        RestTemplate restTemplate = new RestTemplate(Arrays.asList(
          new FormHttpMessageConverter(), tokenResponseHttpMessageConverter)); 
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler()); 
        
        accessTokenResponseClient.setRestOperations(restTemplate); 
        return accessTokenResponseClient;
    }
	*/
}
