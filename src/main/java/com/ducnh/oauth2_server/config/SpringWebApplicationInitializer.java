package com.ducnh.oauth2_server.config;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Order(1)
public class SpringWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	public static final Logger logger = LoggerFactory.getLogger(SpringWebApplicationInitializer.class);
	
	public static final String CHARACTER_ENCODING = "UTF-8";
	
	public SpringWebApplicationInitializer() {
		super();
	}
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {AppConfig.class, SecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	@Override
	protected Filter[] getServletFilters() {
		logger.info("Starting SpringWebApplication...");
		final CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding(CHARACTER_ENCODING);
		encodingFilter.setForceEncoding(true);
		return new Filter[] {encodingFilter};
	}

}
