package com.springboot.api.controller;


import static com.springboot.api.util.Util.CONTENT_TYPE;
import static com.springboot.api.util.Util.CONTENT_VALUE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.springboot.api.domain.CustomerUser;
import com.springboot.api.endpoint.to.CustomerProductTo;
import com.springboot.api.service.ActionLoggingService;
import com.springboot.api.service.CustomerService;
import com.springboot.api.service.JsonValidatorService;
import com.springboot.api.to.CustomerApiKeyTo;
import com.springboot.api.to.CustomerApiLimitTo;
import com.springboot.api.to.CustomerUserTo;
import com.springboot.api.to.EquibaseDataSelesApiError;
import com.springboot.api.to.EquibaseDataSelesApiErrorCodes;
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
@RequestMapping(value = "/customer")
public class CustomerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PasswordValidator passwordValidator;
	
	@Autowired
	private JsonValidatorService jsonValidatorService;
	
	@Autowired
	private ActionLoggingService actionLoggingService;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(Model model) {

		return "customer/customerProfile";

	}
	
	@RequestMapping(value = "/profileTab", method = RequestMethod.GET)
	public String profileTab(Model model) {

		return "customer/customerProfile";

	}
	
	@RequestMapping(value = "/productsTab", method = RequestMethod.GET)
	public String productsTab(Model model) {

		return "customer/customerProducts";

	}
	
	@RequestMapping(value = "/apiKeysTab", method = RequestMethod.GET)
	public String apiKeysTab(Model model) {

		return "customer/customerApiKeys";

	}
	
	@RequestMapping(value = "/accessLevelsTab", method = RequestMethod.GET)
	public String accessLevelsTab(Model model) {

		return "customer/customerAccessLevels";

	}
	
	/**
	 * Gets all Customers
	 * @return List<CustomerUser>
	 */
	@PreAuthorize("hasAnyAuthority('LEVEL_1,LEVEL_2,LEVEL_3,LEVEL_4,LEVEL_5')")
	@RequestMapping(value = "/getAllCustomers", method = RequestMethod.GET)
	public ResponseEntity<List<CustomerUser>> getAllCustomers() {
		LOGGER.info("Getting All Customers");

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);
		
		List<CustomerUser> customerUsers = customerService.getAllCustomers();
		
		return new ResponseEntity<List<CustomerUser>>(customerUsers, responseHeaders, HttpStatus.OK);

	}
	
	/**
	 * Gets Customers for page 
	 * @return List<CustomerUser>
	 */
	@PreAuthorize("hasAnyAuthority('LEVEL_1,LEVEL_2,LEVEL_3,LEVEL_4,LEVEL_5')")
	@RequestMapping(value = "/getCustomers/{pageNum}/{pageSize}", method = RequestMethod.POST)
	public ResponseEntity<PaginationResultsTo<CustomerUserTo>> getCustomers(
			@PathVariable("pageNum") Integer pageNum,
			@PathVariable("pageSize") Integer pageSize,
			@RequestBody SortingCriteria sortingCriteria) {
		
		LOGGER.info("Getting Customers for pagination...");
		LOGGER.info("Page Size : " + pageSize);
		LOGGER.info("Page Number : " + pageNum);
		LOGGER.info("Sorting Criteria : " + sortingCriteria);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);
		
		PaginationResultsTo<CustomerUserTo> customerUsers = customerService.getCustomers(pageNum, pageSize, sortingCriteria);
		
		return new ResponseEntity<PaginationResultsTo<CustomerUserTo>>(customerUsers, responseHeaders, HttpStatus.OK);

	}
	
	/**
	 * Gets Customer Products in pagination
	 * 
	 * @param customerId
	 * @param pageNum
	 * @param pageSize
	 * 
	 * @return PaginationResultsTo<CustomerProductTo>
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "{customerId}/getCustomerProducts/{pageNum}/{pageSize}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getCustomerProductsInPagination(
			@PathVariable("customerId") Long customerId,
			@PathVariable("pageNum") Integer pageNum,
			@PathVariable("pageSize") Integer pageSize) {

		LOGGER.info("Getting All products and tracks in pagination for Customer Id : "
				+ customerId);
		LOGGER.info("Page Number : " + pageNum);
		LOGGER.info("Page Size : " + pageSize);
 
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);

		PaginationResultsTo<CustomerProductTo> products = customerService
				.getCustomerProductsInPagination(customerId, pageNum, pageSize);

		return new ResponseEntity<PaginationResultsTo<CustomerProductTo>>(
				products, responseHeaders, HttpStatus.OK);
	}
	
	/**
	 * Gets Customer Products
	 * 
	 * @param customerId
	 * 
	 * @return List<Product>
	 */
	@RequestMapping(value = "{customerId}/getCustomerProducts", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<CustomerUserTo> getCustomerProducts(
			@PathVariable("customerId") Long customerId,
			HttpServletRequest request, HttpServletResponse response) {
		
		LOGGER.info("Getting All products and tracks for Customer Id : " + customerId);
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);
		
		CustomerUserTo products = customerService.getCustomerProducts(customerId);
		
		return new ResponseEntity<CustomerUserTo>(products, responseHeaders,
				HttpStatus.OK);
	}
	
	/**
	 * Gets Customer Products
	 * @return List<Product>
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "{customerId}/{productId}/getCustomerProductTracks", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getCustomerProductTracks(
			@PathVariable("customerId") Long customerId,
			@PathVariable("productId") Long productId,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.info("Getting Customer product tracks");
		LOGGER.info("Customer Id : " + customerId);
		LOGGER.info("Product Id : " + productId);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);

		CustomerProductTo customerProductTo = customerService
				.getCustomerProductTracks(customerId, productId);

		return new ResponseEntity<CustomerProductTo>(customerProductTo,
				responseHeaders, HttpStatus.OK);
	}
	

	/**
	 * Adds the new customer 
	 * @return Created customer object
	 */
	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyAuthority('LEVEL_1,LEVEL_2')")
	@RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addCustomer(@RequestBody CustomerUserTo customer,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.info("Crearting new Customer...");

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);

		LOGGER.info("Validating customer object in customer Seuup...");

		List<EquibaseDataSelesApiError> errorMessages = jsonValidatorService
				.validateCustomerSetupJson(customer);

		if (!Util.isNullOrEmptyCollection(errorMessages)) {
			LOGGER.info("Validation is done and validation errors exists...");

			EquibaseDataSelesApiErrorResource errorResource = new EquibaseDataSelesApiErrorResource(
					errorMessages);

			return new ResponseEntity<EquibaseDataSelesApiErrorResource>(
					errorResource, responseHeaders, HttpStatus.BAD_REQUEST);

		} else {
			LOGGER.info("Validation is done and no validation errors...");
			customerService.addCustomer(customer);
			
			//Log the Admin action
			if(customer != null && customer.getId() != null) {
				Long adminId = Util.getLoggedUserId();
				LOGGER.info("Logging Admin Action in Creating New Customer...");
				actionLoggingService.logAdminActions(adminId, customer.getId(), request);
			}

			return new ResponseEntity<CustomerUserTo>(customer,
					responseHeaders, HttpStatus.OK);
		}
	}
	
	/**
	 * Generates new api key
	 * @return ResponseEntity<CustomerApiKeyTo>
	 */
	@PreAuthorize("hasAnyAuthority('LEVEL_1,LEVEL_2,LEVEL_3,CUSTOMER')")
	@RequestMapping(value = "/generateNewApiKey", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<CustomerApiKeyTo> generateNewApiKey()
			throws Exception {
		LOGGER.info("Generating New Api Key...");

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);

		String apiKey = Util.generateDynamicApiKey();
		CustomerApiKeyTo apiKeyTo = new CustomerApiKeyTo();
		apiKeyTo.setApiKey(apiKey);

		return new ResponseEntity<CustomerApiKeyTo>(apiKeyTo, responseHeaders,
				HttpStatus.OK);
	}
	
	/**
	 * Generates new api key
	 * @return ResponseEntity<CustomerApiKeyTo>
	 */
	@PreAuthorize("hasAnyAuthority('LEVEL_1,LEVEL_2,LEVEL_3,CUSTOMER')")
	@RequestMapping(value = "/saveApiKey", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<CustomerApiKeyTo> saveApiKey(
			@RequestBody CustomerApiKeyTo apiKey, HttpServletRequest request) throws Exception {
		LOGGER.info("Saving new Api Key for customer...");

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);

		apiKey = customerService.saveApiKey(apiKey);
		
		//Log the Admin action
		Long customerId = Util.getLoggedUserId();
		LOGGER.info("Logging Admin Action in Creating New Api Key for Customer...");
		actionLoggingService.logAdminActions(Util.DEFAULT_ADMIN_ID, customerId, request);

		return new ResponseEntity<CustomerApiKeyTo>(apiKey, responseHeaders,
				HttpStatus.OK);
	}
	
	/**
	 * Deletes set of customer Api keys for Customer
	 * @param apiKeyIds,
	 * @param customerId
	 * Returns true if api keys deletions is success otherwise false
	 */
	@PreAuthorize("hasAnyAuthority('LEVEL_1,LEVEL_2,LEVEL_3,CUSTOMER')")
	@RequestMapping(value = "/{customerId}/deleteCustomerApiKeys", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> deleteCustomerApiKeys(
			@RequestBody Set<Long> apiKeyIds, @PathVariable("customerId") Long customerId, HttpServletRequest request) throws Exception {
		LOGGER.info("Deleting Customer Api Keys for Customer id : " + customerId);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);

		Boolean deleteStatus = customerService.deleteCustomerApiKeys(customerId, apiKeyIds);
		
		//Log the Admin/Customer action
		if(Util.isTrue(deleteStatus)) {
			LOGGER.info("Logging Admin Action in Deleting Api Key for Customer...");
			actionLoggingService.logAdminActions(Util.DEFAULT_ADMIN_ID, customerId, request);
		}

		return new ResponseEntity<Boolean>(deleteStatus, responseHeaders,
				HttpStatus.OK);
	}
	
	/**
	 * Delete List of Customers by customer ids
	 * @param Set<Long> customerIds
	 * @return Integer - Deleted Customers count
	 */
	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyAuthority('LEVEL_1,LEVEL_2,LEVEL_3')")
	@RequestMapping(value = "/deleteCustomers", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity deleteCustomers(
			@RequestBody Set<Long> customerIds) {
		LOGGER.info("Deleting Customer ids : " + customerIds);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);
		Integer status = customerService
				.deleteSelectedCustomers(customerIds);
		
		return new ResponseEntity<Integer>(status, responseHeaders,
				HttpStatus.OK);
	}
	
	/**
	 * Edit customer 
	 * @param CustomerUserTo
	 * @return edited customer object
	 */
	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyAuthority('LEVEL_1,LEVEL_2,LEVEL_3')")
	@RequestMapping(value = "/editCustomer", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity editCustomer(@RequestBody CustomerUserTo customer,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.info("Editing Customer...");

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);

		LOGGER.info("Validating customer object in customer Edit...");

		List<EquibaseDataSelesApiError> errorMessages = jsonValidatorService
				.validateCustomerEditJson(customer);

		if (!Util.isNullOrEmptyCollection(errorMessages)) {
			LOGGER.info("Validation is done and validation errors exists...");

			EquibaseDataSelesApiErrorResource errorResource = new EquibaseDataSelesApiErrorResource(
					errorMessages);

			return new ResponseEntity<EquibaseDataSelesApiErrorResource>(
					errorResource, responseHeaders, HttpStatus.BAD_REQUEST);
		} else {
			LOGGER.info("Validation is done and no validation errors...");
			customerService.editCustomer(customer);
			
			//Log the Admin/Customer action
			Long loggedInAdminId = Util.getLoggedUserId();
			LOGGER.info("Logging Admin Action in Editing existing Customer User...");
			actionLoggingService.logAdminActions(loggedInAdminId, customer.getId(), request);

			return new ResponseEntity<CustomerUserTo>(customer,
					responseHeaders, HttpStatus.OK);
		}
	}
	
	/**
	 * Edit customer 
	 * @param CustomerUserTo
	 * @return edited customer object
	 */
	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyAuthority('LEVEL_1,LEVEL_2,LEVEL_3')")
	@RequestMapping(value = "/updateCustomerProductTracks", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity updateCustomerProductTracks(@RequestBody CustomerUserTo customer,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.info("Editing Customer Product Tracks...");

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);

		LOGGER.info("Validating customer object in customer Edit...");

		List<EquibaseDataSelesApiError> errorMessages = jsonValidatorService
				.validateCustomerProductJson(customer);

		if (!Util.isNullOrEmptyCollection(errorMessages)) {
			LOGGER.info("Validation is done and validation errors exists...");

			EquibaseDataSelesApiErrorResource errorResource = new EquibaseDataSelesApiErrorResource(
					errorMessages);

			return new ResponseEntity<EquibaseDataSelesApiErrorResource>(
					errorResource, responseHeaders, HttpStatus.BAD_REQUEST);
		} else {
			LOGGER.info("Validation is done and no validation errors...");
			customerService.editCustomerProductTracks(customer);
			
			//Log the Admin/Customer action
			Long loggedInAdminId = Util.getLoggedUserId();
			LOGGER.info("Logging Admin Action in editing Customer Product Tracks...");
			actionLoggingService.logAdminActions(loggedInAdminId, customer.getId(), request);

			return new ResponseEntity<CustomerUserTo>(customer,
					responseHeaders, HttpStatus.OK);
		}
	}
	
	/**
	 * Update Customer Api Limits
	 * @param apiLimitTo
	 * @return True if api limits are updated successfully otherwise false
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/updateCustomerApiLimits", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity updateCustomerApiLimits(@RequestBody CustomerApiLimitTo apiLimitTo, HttpServletRequest request) {

		LOGGER.info("Updating Customer Api Limits...");
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);
		
		Boolean updateStatus = customerService.editApiLimits(apiLimitTo);
		
		if(Util.isTrue(updateStatus)) {
			//Log the Admin/Customer action
			Long loggedInAdminId = Util.getLoggedUserId();
			LOGGER.info("Logging Admin Action in editing Customer Monthly Api Limits...");
			actionLoggingService.logAdminActions(loggedInAdminId, apiLimitTo.getCustomerId(), request);
		}
		
		return new ResponseEntity<Boolean>(updateStatus,
				responseHeaders, HttpStatus.OK);
		
	}
	
	/**
	 * Getting Customer Api Keys
	 * @param customerId 
	 * @return List of Customer Api Keys
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{customerId}/getCustomerApiKeys", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getCustomerApiKeys(
			@PathVariable("customerId") Long customerId,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.info("Getting Customer Api Keys...");

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);

		if(Util.isNull(customerId)) {
			LOGGER.error("To Get Customer Api Keys, Customer Id should not be null");
			return new ResponseEntity<String>("Customer Id is missing", responseHeaders,
					HttpStatus.BAD_REQUEST);
		} else {
			
			List<CustomerApiKeyTo> apiKeys = customerService.getCustomerApiKeys(customerId);
			
			return new ResponseEntity<List<CustomerApiKeyTo>>(apiKeys, responseHeaders,
					HttpStatus.OK);
		}
	}
	
	/**
	 * Getting Customer Current Month Api Access limit
	 * @param customerId
	 * @return Customer Api Limits object
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{customerId}/getCustomerCurrentMonthApiLimits", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getCustomerCurrentMonthApiLimits(
			@PathVariable("customerId") Long customerId,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.info("Getting Customer Current Month Api Limits...");

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);

		if (Util.isNull(customerId)) {
			LOGGER.error("To Get Current Month Api Limits, Customer Id should not be null");

			return new ResponseEntity<String>("Customer Id is missing",
					responseHeaders, HttpStatus.BAD_REQUEST);
		} else {
			CustomerApiLimitTo apiLimits = customerService
					.getCustomerCurrentMonthApiLimits(customerId);
			
			if(apiLimits == null) {
				apiLimits = new CustomerApiLimitTo();
				apiLimits.setCustomerId(customerId);
			}

			return new ResponseEntity<CustomerApiLimitTo>(apiLimits,
					responseHeaders, HttpStatus.OK);
		}
	}
	
	/**
	 * Getting Customer All Time Api Access limit
	 * @param customerId
	 * @return Customer Api Limits object
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{customerId}/getCustomerAllTimeApiLimits", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getCustomerAllTimeApiLimits(
			@PathVariable("customerId") Long customerId,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.info("Getting Customer All Time Api Limits...");

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);

		if (Util.isNull(customerId)) {
			LOGGER.error("To Get All Time Api Limits, Customer Id should not be null");

			return new ResponseEntity<String>("Customer Id is missing",
					responseHeaders, HttpStatus.BAD_REQUEST);
		} else {
			CustomerApiLimitTo apiLimits = customerService
					.getCustomerApiAllLimits(customerId);

			return new ResponseEntity<CustomerApiLimitTo>(apiLimits,
					responseHeaders, HttpStatus.OK);
		}
	}
	
	/**
	 * Updates Customer Password in Customers table
	 * @param CustomerUser
	 * @return ResponseTo
	 */
	@PreAuthorize("hasAnyAuthority('LEVEL_1,LEVEL_2,LEVEL_3,CUSTOMER')")
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	@ResponseBody 
	public ResponseEntity<ResponseTo> changePassword(
			@RequestBody CustomerUser customerUser, HttpServletRequest request) {

		LOGGER.info("Changing Admin password...");

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(CONTENT_TYPE, CONTENT_VALUE);

		Boolean isValidPwd = passwordValidator.validatePassword(customerUser
				.getPasswordStr());
		ResponseTo responseTo = new ResponseTo();
		if (Util.isTrue(isValidPwd)) {
			Boolean response = customerService.changePassword(customerUser);
			if (Util.isTrue(response)) {
				responseTo.setSuccess("Password Updated Successfully");
				
				//Log the Admin/Customer action
				LOGGER.info("Logging Admin Action in editing Customer Password...");
				actionLoggingService.logAdminActions(Util.DEFAULT_ADMIN_ID, customerUser.getId(), request);
				
				return new ResponseEntity<ResponseTo>(responseTo,
						responseHeaders, HttpStatus.OK);
			} else {
				responseTo.setError("Password Updation Failed");
				return new ResponseEntity<ResponseTo>(responseTo,
						responseHeaders, HttpStatus.BAD_REQUEST);
			}
		} else {
			responseTo.setError("Password Validations Failed");
			return new ResponseEntity<ResponseTo>(responseTo, responseHeaders,
					HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Get Customer Profile by Customer Id
	 * @param customerId
	 * @return ResponseEntity<CustomerUserTo>
	 */
	@RequestMapping(value = "/{customerId}/customerProfile", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<CustomerUserTo> customerProfile(
			@PathVariable Long customerId) {
		LOGGER.info("Getting Customer Profile for Customer id : " + customerId);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(CONTENT_TYPE, CONTENT_VALUE);

		CustomerUserTo customerUserTo = customerService
				.getCustomerUser(customerId);

		return new ResponseEntity<CustomerUserTo>(customerUserTo,
				responseHeaders, HttpStatus.OK);
	}
	
	/**
	 * Getting Customer Api Access limit within start date and end date
	 * @param customerId
	 * @param startDateStr
	 * @param endDateStr
	 * @return Customer Api Limits object
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{customerId}/getCustomerApiLimits/{startDateStr}/{endDateStr}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getCustomerApiLimits(
			@PathVariable("customerId") Long customerId,
			@PathVariable("startDateStr") String startDateStr,
			@PathVariable("endDateStr") String endDateStr,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.info("Getting Customer Api Limits during Start Date : "
				+ startDateStr + " End Date : " + endDateStr);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);

		List<EquibaseDataSelesApiError> errorMessages = new ArrayList<EquibaseDataSelesApiError>();

		if(Util.isNull(customerId)) {
			EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FIELD_USER_ID;
			EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode ,
					equibaseDataSelesApiErrorCode.getId() ,
					equibaseDataSelesApiErrorCode.getDescription() ,
					"Missing Customer Id");
			errorMessages.add(error);
		}

		Date startDate = Util.stringToDateConversion(startDateStr, "MM-dd-yyyy");
		Date endDate = Util.stringToDateConversion(endDateStr, "MM-dd-yyyy");

		if(Util.isEmptyString(startDateStr)) {
			EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_SATRT_DATE;
			EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode ,
					equibaseDataSelesApiErrorCode.getId() ,
					equibaseDataSelesApiErrorCode.getDescription() ,
					"Missing Start Date");
			errorMessages.add(error);
		} else if(Util.isNull(startDate)) {
			EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.INVALID_API_LIMIT_SATRT_DATE;
			EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode ,
					equibaseDataSelesApiErrorCode.getId() ,
					equibaseDataSelesApiErrorCode.getDescription() ,
					"Invalid Start Date Format");
			errorMessages.add(error);
		}

		if(Util.isEmptyString(endDateStr)) {
			EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_END_DATE;
			EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode ,
					equibaseDataSelesApiErrorCode.getId() ,
					equibaseDataSelesApiErrorCode.getDescription() ,
					"Missing End Date");
			errorMessages.add(error);
		} else if(Util.isNull(endDate)) {
			EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.INVALID_API_LIMIT_END_DATE;
			EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode ,
					equibaseDataSelesApiErrorCode.getId() ,
					equibaseDataSelesApiErrorCode.getDescription() ,
					"Invalid End Date Format");
			errorMessages.add(error);
		}

		if(!Util.isNullOrEmptyCollection(errorMessages)) {
			EquibaseDataSelesApiErrorResource errorResource = new EquibaseDataSelesApiErrorResource(
					errorMessages);
			return new ResponseEntity<EquibaseDataSelesApiErrorResource>(errorResource, responseHeaders,
					HttpStatus.BAD_REQUEST);
		} else {
			CustomerApiLimitTo apiLimits = customerService.getCustomerApiLimitsSum(customerId, startDate, endDate);
			return new ResponseEntity<CustomerApiLimitTo>(apiLimits, responseHeaders,
					HttpStatus.OK);
		}
	}
	

}
