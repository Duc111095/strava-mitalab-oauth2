package com.ducnh.oauth2_server.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.DefaultRefreshTokenTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2RefreshTokenGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.MapOAuth2AccessTokenResponseConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.client.RestTemplate;

import com.ducnh.oauth2_server.handler.CustomAuthenticationFailureHandler;
import com.ducnh.oauth2_server.model.StravaToken;
import com.ducnh.oauth2_server.repository.TokenRepository;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class SecurityConfig {
	public static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	
	private static String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.";
	private static List<String> clients  = Arrays.asList("strava");
	
	@Autowired
	private TokenRepository tokenRepo;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
			.authorizeRequests(requests -> requests
                .antMatchers("/",
						"/cuserror",
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
                .anyRequest().authenticated())
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .clientRegistrationRepository(clientRegistrationRepository())
                                .authorizedClientService(authorizedClientService())
                                .tokenEndpoint(c -> c.accessTokenResponseClient(accessTokenResponseClient()))
								.userInfoEndpoint(c -> c.userService(customOAuth2UserService()))
                                .defaultSuccessUrl("/register")
								.failureHandler(authenticationFailureHandler()));	
		return http.build();
	}
	
	@Autowired
	private Environment env;

	@Bean
	AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    } 

	@Bean
	CustomOAuth2UserService customOAuth2UserService() {
		return new CustomOAuth2UserService();
	}

    @Bean
    ClientRegistrationRepository clientRegistrationRepository() {
		List<ClientRegistration> registrations = clients.stream()
				.map(c -> getRegistration(c))
				.filter(registration -> registration != null)
				.collect(Collectors.toList());
		return new InMemoryClientRegistrationRepository(registrations);
	}
	
	private ClientRegistration getRegistration(String client) {
		String clientId = env.getProperty(CLIENT_PROPERTY_KEY + client + ".clientId");
		if (clientId == null) {
			return null;
		}
		
		String clientSecret = env.getProperty(CLIENT_PROPERTY_KEY + client + ".clientSecret");
		if (client.equals("github")) {
			return CommonOAuth2Provider.GITHUB.getBuilder(client)
					.clientId(clientId).clientSecret(clientSecret).build();
		}
		if (client.equals("strava")) {
			return stravaClientRegistration(clientId, clientSecret);
		}

		return null;
	}

    @Bean
    OAuth2AuthorizedClientService authorizedClientService() {
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
            .tokenUri("https://www.strava.com/api/v3/oauth/token?client_id="+ clientId + "&client_secret=" + clientSecret)
            .userInfoUri("https://www.strava.com/api/v3/athlete")
            .userNameAttributeName("id")
            .clientName("Strava")
            .build();
    }

    @Bean
    OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {
		OAuth2AuthorizedClientProvider authorizedClientProvider = 
				OAuth2AuthorizedClientProviderBuilder.builder()
						.authorizationCode()
						.refreshToken(configurer -> configurer.accessTokenResponseClient(refreshCustomResponseClient()))
						.clientCredentials()
						.password()
						.build();
		DefaultOAuth2AuthorizedClientManager authorizedClientManager = 
				new DefaultOAuth2AuthorizedClientManager(
						clientRegistrationRepository, authorizedClientRepository);
		authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
		return authorizedClientManager;
	}


    @Bean
    OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient(){
        DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient = 
          new DefaultAuthorizationCodeTokenResponseClient(); 
        accessTokenResponseClient.setRestOperations(customRestTemplate());
        return accessTokenResponseClient;
    }

    @Bean
    OAuth2AccessTokenResponseClient<OAuth2RefreshTokenGrantRequest> refreshCustomResponseClient() {
		DefaultRefreshTokenTokenResponseClient refreshTokenResponseClient = 
				new DefaultRefreshTokenTokenResponseClient();
		refreshTokenResponseClient.setRestOperations(customRestTemplate());
		return refreshTokenResponseClient;
	}
	
	private RestTemplate customRestTemplate() {
		OAuth2AccessTokenResponseHttpMessageConverter tokenResponseHttpMessageConverter = 
				new OAuth2AccessTokenResponseHttpMessageConverter();
		tokenResponseHttpMessageConverter.setTokenResponseConverter(customResponseConverter());
		RestTemplate restTemplate = new RestTemplate(Arrays.asList(
				new FormHttpMessageConverter(), tokenResponseHttpMessageConverter));
		restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
		return restTemplate;
	}
	
	private Converter<Map<String, String>, OAuth2AccessTokenResponse> customResponseConverter(){
	    MapOAuth2AccessTokenResponseConverter mapOAuth2AccessTokenResponseConverter = new MapOAuth2AccessTokenResponseConverter();
	    return tokenResponseParameters -> {
	    	OAuth2AccessTokenResponse original = mapOAuth2AccessTokenResponseConverter.convert(tokenResponseParameters);

	    	String athlete = tokenResponseParameters.getOrDefault("athlete", "");
	    	Long athleteId = Long.valueOf(athlete.substring(athlete.indexOf("=") + 1, athlete.indexOf(",")));
	    
	    	String accessToken = tokenResponseParameters.getOrDefault("access_token", "");
	    	String refreshToken = tokenResponseParameters.getOrDefault("refresh_token", "");
	    	String tokenType = tokenResponseParameters.getOrDefault("token_type", "");
	    	Long expiresIn = Long.valueOf(tokenResponseParameters.getOrDefault("expires_in", "0"));
	    	Long expiresAt = Long.valueOf(tokenResponseParameters.getOrDefault("expires_at", "0"));
	    	
	    	StravaToken token = new StravaToken.Builder(athleteId)
	    			.setAccessToken(accessToken)
	    			.setRefreshToken(refreshToken)
	    			.withTokenType(tokenType)
	    			.expiresIn(expiresIn)
	    			.expiresAt(expiresAt)
	    			.build();
	    	
	    	tokenRepo.save(token);
	    	return original;
	    };
	}
}
