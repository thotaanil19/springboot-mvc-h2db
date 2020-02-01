package com.springboot.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.api.enums.ErrorCodesEnum;
import com.springboot.api.util.Util;


/**
 * 
 * @author anilt
 *
 */
@Controller
@RequestMapping(value = "/error")
//@PreAuthorize("@errorController")
public class ErrorController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);
	
	@RequestMapping(value = "/403")
	public String accessDenied(Model modal, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Access Denied...");
		StringBuilder error = new StringBuilder("Access Denied");
		
		String errorMsg = request.getParameter("errorMessage");
		if(!Util.isEmptyString(errorMsg)) {
			error.append(" Due to " + errorMsg);
		}
		if(ErrorCodesEnum.INVALID_IP.getValue().equalsIgnoreCase(errorMsg)) {
			SecurityContextHolder.getContext()
			.setAuthentication(null);
		}
		modal.addAttribute("error", error.toString());
		return "errorPage";

	}
	
	

}
