package com.springboot.api.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 
 * @author anilt
 *
 */
@Component
public class TrackmasterLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler   {
	
	@Value(value = "${server.contextPath}")
	private String applicationContext;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		response.sendRedirect(applicationContext + "/login/logoutSuccess");
		
	}
}
