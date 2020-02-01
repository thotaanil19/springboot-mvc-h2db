package com.springboot.api.controller;

import static com.springboot.api.util.Util.CONTENT_TYPE;
import static com.springboot.api.util.Util.CONTENT_VALUE;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.api.domain.AdminUser;
import com.springboot.api.service.ActionLoggingService;
import com.springboot.api.service.AdminService;
import com.springboot.api.service.impl.AdminServiceImpl;
import com.springboot.api.to.EquibaseDataSelesApiError;
import com.springboot.api.to.EquibaseDataSelesApiErrorResource;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.ResponseTo;
import com.springboot.api.to.SortingCriteria;
import com.springboot.api.util.PasswordValidator;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
@Controller
@RequestMapping(value = "/admin")
@PreAuthorize("hasAnyAuthority('LEVEL_1,LEVEL_2,LEVEL_3,LEVEL_4,LEVEL_5')")
public class AdminController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private PasswordValidator validator;
	
	@Autowired
	private ActionLoggingService actionLoggingService;

	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(Model model) {

		return "admin/adminProfile";

	}
	
	@RequestMapping(value = "/profileTab", method = RequestMethod.GET)
	public String profile(Model model) {

		return "admin/adminProfile";

	}
	
	@RequestMapping(value = "/usersTab", method = RequestMethod.GET)
	public String users(Model model) {

		return "admin/adminUsers";

	}
	
	@RequestMapping(value = "/tracksTab", method = RequestMethod.GET)
	public String tracks(Model model) {

		return "admin/tracks";

	}
	
	@RequestMapping(value = "/customersTab", method = RequestMethod.GET)
	public String customers(Model model) {

		return "admin/customers";

	}
	
	@RequestMapping(value = "/productsTab", method = RequestMethod.GET)
	public String products(Model model) {

		return "admin/products";

	}
	
	@RequestMapping(value = "/reportsTab", method = RequestMethod.GET)
	public String reports(Model model) {

		return "admin/reports";

	}
	
	/**
	 * Create new Admin User
	 * @param adminUser
	 * @return
	 */
	@RequestMapping(value = "/getAdminUsers/{pageNum}/{pageSize}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PaginationResultsTo<AdminUser>> getAdminUsers(
			@PathVariable("pageNum") Integer pageNum,
			@PathVariable("pageSize") Integer pageSize,
			@RequestBody SortingCriteria sortingCriteria) {
		
		LOGGER.info("Getting Admin Users...");
		LOGGER.info("Page Number : " + pageNum);
		LOGGER.info("Page Size : " + pageSize);
		LOGGER.info("Sorting Criteria : " + sortingCriteria);
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(CONTENT_TYPE, CONTENT_VALUE);
		
		PaginationResultsTo<AdminUser> adminUsers = adminService.getAdminUsers(pageNum, pageSize, sortingCriteria);
		if(Util.isNull(adminUsers)) {
			adminUsers = new PaginationResultsTo<AdminUser>();
		}
		
		return new ResponseEntity<PaginationResultsTo<AdminUser>>(adminUsers, responseHeaders, HttpStatus.OK);

	}
	
	
	/**
	 * Edit Admin User
	 * @param adminUser
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('LEVEL_1,LEVEL_2,LEVEL_3')")
	@RequestMapping(value = "/editAdmin", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<AdminUser> editAdmin(@RequestBody AdminUser adminUser, HttpServletRequest request) {
		LOGGER.info("Editing Admin User...");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(CONTENT_TYPE, CONTENT_VALUE);
		adminUser = adminService.editAdminUser(adminUser);
		
		//Log the Admin action
		if(adminUser != null && adminUser.getId() != null) {
			Long loggedInAdminId = Util.getLoggedUserId();
			LOGGER.info("Logging Admin Action in Editing existing Admin...");
			actionLoggingService.logAdminActions(loggedInAdminId, Util.INTERNAL_CUSTOMER_ID, request);
		}
		
		return new ResponseEntity<AdminUser>(adminUser, responseHeaders, HttpStatus.OK);

	}
	
	/**
	 * Create New Admin User
	 * @param adminUser
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyAuthority('LEVEL_1')")
	@RequestMapping(value = "/createAdmin", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity cerateAdmin(@RequestBody AdminUser adminUser, HttpServletRequest request) {
		LOGGER.info("Crearting Admin...");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(CONTENT_TYPE, CONTENT_VALUE);
		List<EquibaseDataSelesApiError> dataSelesApiErrors = adminService.validateAdminUserObject(adminUser);
		if(!Util.isNullOrEmptyCollection(dataSelesApiErrors)) {
			EquibaseDataSelesApiErrorResource resource = new EquibaseDataSelesApiErrorResource(dataSelesApiErrors);
			
			ResponseTo responseTo = new ResponseTo();
			responseTo.setError("User already exists with login id : " + adminUser.getLoginId());
			return new ResponseEntity<EquibaseDataSelesApiErrorResource>(resource, responseHeaders, HttpStatus.BAD_REQUEST);
		} else {
			adminUser = adminService.createAdmin(adminUser);
			
			//Log the Admin action
			if(adminUser != null && adminUser.getId() != null) {
				Long loggedInAdminId = Util.getLoggedUserId();
				LOGGER.info("Logging Admin Action in Creating New Admin...");
				actionLoggingService.logAdminActions(loggedInAdminId, Util.INTERNAL_CUSTOMER_ID, request);
			}
			
			return new ResponseEntity<AdminUser>(adminUser, responseHeaders, HttpStatus.OK);
		}

	}
	
	
	/**
	 * Fetches Admin user profile
	 * @return
	 */
	@RequestMapping(value = "/adminProfile", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<AdminUser>  adminProfile() {
		LOGGER.info("Getting Admin profile...");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(CONTENT_TYPE, CONTENT_VALUE);
		AdminUser admin = adminService.getAdminProfile();
		return new ResponseEntity<AdminUser>(admin, responseHeaders, HttpStatus.OK);
	}
	
	/**
	 * Updates AdminUser Password in Admin_Users table
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity changePassword(@RequestBody AdminUser adminUser, HttpServletRequest request) {

		LOGGER.info("Changing Admin password...");

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(CONTENT_TYPE, CONTENT_VALUE);

		Boolean isValidPwd = validator.validatePassword(adminUser
				.getPasswordStr());

		ResponseTo responseTo = new ResponseTo();

		if (Util.isTrue(isValidPwd)) {
			LOGGER.info("Password meet the password Requirements...");
			Boolean response = adminService.changePassword(adminUser);
			if (Util.isTrue(response)) {
				//Log the Admin action
				LOGGER.info("Logging Admin Action in Reset Password...");
				actionLoggingService.logAdminActions(adminUser.getId(), Util.INTERNAL_CUSTOMER_ID, request);
				responseTo.setSuccess("Password Updated Successfully");
				LOGGER.info("Password Updated Successfully...");
				return new ResponseEntity<ResponseTo>(responseTo,
						responseHeaders, HttpStatus.OK);
			} else {
				responseTo.setError("Password Updation Failed");
				return new ResponseEntity<ResponseTo>(responseTo,
						responseHeaders, HttpStatus.BAD_REQUEST);
			}
		} else {
			LOGGER.info("Password does not meet password Requirements...");
			responseTo.setError("Password Validations Failed");
			return new ResponseEntity<ResponseTo>(responseTo, responseHeaders,
					HttpStatus.BAD_REQUEST);
		}
	}

}
