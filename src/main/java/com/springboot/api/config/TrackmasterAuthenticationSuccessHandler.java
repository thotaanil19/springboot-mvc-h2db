package com.springboot.api.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 
 * @author anilt
 *
 */
@Component
public class TrackmasterAuthenticationSuccessHandler implements AuthenticationSuccessHandler  {
	
	@Value(value = "${server.contextPath}")
	private String applicationContext;
	
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	                                    Authentication authentication) throws ServletException, IOException {
		response.sendRedirect(applicationContext + "/login/loginSuccess");
	}
}
