package com.springboot.api.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springboot.api.enums.ErrorCodesEnum;
import com.springboot.api.enums.UserRoleCodeEnum;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
	
	@Value(value = "${server.contextPath}")
	private String applicationContext;
	
	/**
	 * 
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws Exception
	 */

	@RequestMapping(value = "/formlogin", method = RequestMethod.GET)
	public String login(Model modal) throws Exception {
		return "login";

	}
	
	@RequestMapping(value = "/logoutSuccess", method = RequestMethod.GET)
	public String logout(Model modal) throws Exception {
		modal.addAttribute("message", "You logged out successfully.");
		return "redirect:/login/formlogin";

	}
	
	@RequestMapping(value = "/loginSuccess", method = RequestMethod.GET)
	public void home(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String role = Util.getLoggedUserRole();
		String invalidIpAddressErrorMsg = ErrorCodesEnum.INVALID_IP.getValue();
		if (UserRoleCodeEnum.LEVEL_1.getValue().equalsIgnoreCase(role)) {
			if(Util.isValidrequestByIpAddess(request)) {
				response.sendRedirect(applicationContext + "/admin/dashboard");
			} else {
				response.sendRedirect(applicationContext + "/error/403?errorMessage="+invalidIpAddressErrorMsg);
			}
		} else if(UserRoleCodeEnum.LEVEL_2.getValue().equalsIgnoreCase(role)) {
			if(Util.isValidrequestByIpAddess(request)) {
				response.sendRedirect(applicationContext + "/admin/dashboard");
			} else {
				response.sendRedirect(applicationContext + "/error/403?errorMessage="+invalidIpAddressErrorMsg);
			}
		}  else if(UserRoleCodeEnum.LEVEL_3.getValue().equalsIgnoreCase(role)) {
			if(Util.isValidrequestByIpAddess(request)) {
				response.sendRedirect(applicationContext + "/admin/dashboard");
			} else {
				response.sendRedirect(applicationContext + "/error/403?errorMessage="+invalidIpAddressErrorMsg);
			}
		} else if(UserRoleCodeEnum.LEVEL_4.getValue().equalsIgnoreCase(role)) {
			if(Util.isValidrequestByIpAddess(request)) {
				response.sendRedirect(applicationContext + "/admin/dashboard");
			} else {
				response.sendRedirect(applicationContext + "/error/403?errorMessage="+invalidIpAddressErrorMsg);
			}
		} else if(UserRoleCodeEnum.LEVEL_5.getValue().equalsIgnoreCase(role)) {
			if(Util.isValidrequestByIpAddess(request)) {
				response.sendRedirect(applicationContext + "/admin/dashboard");
			} else {
				response.sendRedirect(applicationContext + "/error/403?errorMessage="+invalidIpAddressErrorMsg);
			}
		} else if(UserRoleCodeEnum.CUSTOMER.getValue().equalsIgnoreCase(role)) {
			response.sendRedirect(applicationContext + "/customer/dashboard");
		} else {
			response.sendRedirect(applicationContext + "/login/formlogin");
		}

	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model modal) throws Exception {
		modal.addAttribute("error", "Access Denied");
		return "login";

	}
	
	@RequestMapping(value = "/loginFailure", method = RequestMethod.GET)
	public String loginFailure(Model modal) throws Exception {
		modal.addAttribute("error", "Invalid Crendentials");
		return "login";
		

	}

	@RequestMapping(value = "/active", method = RequestMethod.GET)
	public String active(Locale locale, Model model, HttpServletRequest request) {

		Date date = new Date();

		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		String remoteHost = request.getRemoteHost();

		model.addAttribute("remoteHost", remoteHost);

		return "active";

	}

}
