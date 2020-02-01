package com.springboot.api.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * 
 * @author anilt
 *
 */
@Component
public class TrackmasterAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	@Value(value = "${server.contextPath}")
	private String applicationContext;
	
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.sendRedirect(applicationContext + "/login/loginFailure");
	}
}
