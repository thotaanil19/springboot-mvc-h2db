package com.springboot.api;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import com.springboot.api.config.MvcConfig;
import com.springboot.api.config.SecurityConfig;
import com.springboot.api.config.TrackMasterViewResolver;
import com.springboot.api.config.WebStarter;

/**
 * 
 * @author anilt
 *
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(Application.class,
				TrackMasterViewResolver.class, SecurityConfig.class,
				MvcConfig.class, WebStarter.class);
	}

}
