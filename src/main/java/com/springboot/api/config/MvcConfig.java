package com.springboot.api.config;



import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 
 * @author anilt
 *
 */
@Configuration
@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan
public class MvcConfig {
	
}
