package com.ducnh.oauth2_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Oauth2ServerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2ServerApplication.class, args);
	}
}
