package com.springboot.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.api.dao.CustomerDao;
import com.springboot.api.domain.CustomerUser;
import com.springboot.api.service.JsonValidatorService;
import com.springboot.api.to.CustomerApiKeyTo;
import com.springboot.api.to.CustomerApiLimitTo;
import com.springboot.api.to.CustomerUserTo;
import com.springboot.api.to.EquibaseDataSelesApiError;
import com.springboot.api.to.EquibaseDataSelesApiErrorCodes;
import com.springboot.api.util.PasswordValidator;
import com.springboot.api.util.Util;
 
/**
 * 
 * @author anilt
 *
 */
@Service
public class JsonValidatorServiceImpl implements JsonValidatorService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonValidatorServiceImpl.class);
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private PasswordValidator passwordValidator;
	  
	/**
	 * Validate Customer setup json object
	 * @param customer
	 * @return List<EquibaseDataSelesApiError>
	 */
	@Override
	public List<EquibaseDataSelesApiError> validateCustomerSetupJson(CustomerUserTo customer) {
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors = new ArrayList<EquibaseDataSelesApiError>();

        if (Util.isNull(customer)) {
        	EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_METHOD_ARGUMENTS;
        	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode ,
        			equibaseDataSelesApiErrorCode.getId() ,
        			equibaseDataSelesApiErrorCode.getDescription() ,
                    "Missing customer object in Customer SetUp");
        	equibaseDataSelesApiErrors.add(error);
        } else {
            if (Util.isEmptyString(customer.getLoginId())) {
            	LOGGER.info("Customer Login Id should not be null for new customer creation");
            	EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FIELD_LOGIN_ID;
            	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
                        , equibaseDataSelesApiErrorCode.getId()
                        , equibaseDataSelesApiErrorCode.getDescription()
                        , "Missing Customer Login ID in Customer SetUp");
            	equibaseDataSelesApiErrors.add(error);
            } else {
            	Pattern whitespace = Pattern.compile("\\s");
        		Matcher matcher = whitespace.matcher(customer.getLoginId());
            	 if (matcher.find()) {
            			LOGGER.info("Customer Login Id is having white spaces, Remove spaces from Login Id String");
            			EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.WHITE_SPACES_IN_LOGIN_ID;
                 		EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
                 				, equibaseDataSelesApiErrorCode.getId()
                 				, equibaseDataSelesApiErrorCode.getDescription()
                 				, "Customer Login Id is having white spaces, Remove spaces from Login Id String");
                 		equibaseDataSelesApiErrors.add(error);
            	 } else {
            		 CustomerUser customerUser = customerDao.getCustomerByLoginId(customer.getLoginId());
                 	if(!Util.isNull(customerUser)) {
                 		LOGGER.info("Customer Login Id is already in Use, Choose diffent login id for new customer");
                 		EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.CUSTOMER_ALREADY_EXISTS_WITH_LOGIN_ID;
                 		EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
                 				, equibaseDataSelesApiErrorCode.getId()
                 				, equibaseDataSelesApiErrorCode.getDescription()
                 				, "Customer Login Id is already in Use, Choose diffent login id for new customer");
                 		equibaseDataSelesApiErrors.add(error);
                 	}
            	 }
            }
            //Password
            validateCustomerPassword(customer, equibaseDataSelesApiErrors);
           
            //Email
            validateCustomerEamil(customer, equibaseDataSelesApiErrors);
            
            //Company Name
            validateCustomerCompany(customer, equibaseDataSelesApiErrors);
            
            //Base Access Limit
			validateCustomerBaseAccessLimit(customer, equibaseDataSelesApiErrors);
            
            //Reset day of month(1-25)
			validateCustomerResetDay(customer, equibaseDataSelesApiErrors);
			
            //Customer Api Keys
			validateCustomerApiKeys(customer, equibaseDataSelesApiErrors);
           
        }
        return equibaseDataSelesApiErrors;
	}
	
	/**
	 * Validate Customer Edit json object
	 * @param customer
	 * @return List<EquibaseDataSelesApiError>
	 */
	@Override
	public List<EquibaseDataSelesApiError> validateCustomerEditJson(CustomerUserTo customer) {
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors = new ArrayList<EquibaseDataSelesApiError>();

        if (Util.isNull(customer)) {
        	EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_METHOD_ARGUMENTS;
        	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode ,
        			equibaseDataSelesApiErrorCode.getId() ,
        			equibaseDataSelesApiErrorCode.getDescription() ,
                    "Missing customer object in Customer Edit");
        	equibaseDataSelesApiErrors.add(error);
        } else {
        	 if (Util.isNull(customer.getId())) {
             	LOGGER.info("Customer Id should not be null in Edit customer");
             	EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FIELD_CUSTOMER_ID;
             	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
                         , equibaseDataSelesApiErrorCode.getId()
                         , equibaseDataSelesApiErrorCode.getDescription()
                         , "Missing Customer ID in Customer Edit");
             	equibaseDataSelesApiErrors.add(error);
             } else {
              	LOGGER.info("Verifying Customer available or not for given customer id : " + customer.getId());
              	CustomerUser customerUser = customerDao.getCustomer(customer.getId());
              	if(Util.isNull(customerUser)) {
              		EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.CUSTOMER_NOT_EXISTS_FOR_CUSTOMER_ID;
              		EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
              				, equibaseDataSelesApiErrorCode.getId()
              				, equibaseDataSelesApiErrorCode.getDescription()
              				, "No Customer Available with given Customer id for editing customer");
              		equibaseDataSelesApiErrors.add(error);
              	}
             }
            //Email
        	 validateCustomerEamil(customer, equibaseDataSelesApiErrors);
           
            //Company Name
        	 validateCustomerCompany(customer, equibaseDataSelesApiErrors);
            
            //Base Access Limit
        	 validateCustomerBaseAccessLimit(customer, equibaseDataSelesApiErrors);
            
            //Reset day of month(1-25)
			validateCustomerResetDay(customer, equibaseDataSelesApiErrors);
            
			//Customer Api Keys
			validateCustomerApiKeys(customer, equibaseDataSelesApiErrors);
            
            //Customer Api Limits
            if (!Util.isNull(customer.getCustomerApiLimit())) {
            	CustomerApiLimitTo customerApiLimit = customer.getCustomerApiLimit();
            	if(!Util.isNull(customerApiLimit)) {
            		if(Util.isEmptyString(customerApiLimit.getStartDateStr())) {
            			EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_SATRT_DATE;
            			EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
            					, equibaseDataSelesApiErrorCode.getId()
            					, equibaseDataSelesApiErrorCode.getDescription()
            					, "Missing Start Date in Customer Api Limits");
            			equibaseDataSelesApiErrors.add(error);
            		}
            		if(Util.isEmptyString(customerApiLimit.getEndDateStr())) {
            			EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_END_DATE;
            			EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
            					, equibaseDataSelesApiErrorCode.getId()
            					, equibaseDataSelesApiErrorCode.getDescription()
            					, "Missing End Date in Customer Api Limits");
            			equibaseDataSelesApiErrors.add(error);
            		}
            		if(Util.isNull(customerApiLimit.getCurrentAccessLimit())) {
            			EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_API_LIMITS;
            			EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
            					, equibaseDataSelesApiErrorCode.getId()
            					, equibaseDataSelesApiErrorCode.getDescription()
            					, "Missing Api limits field in Customer Api Limits");
            			equibaseDataSelesApiErrors.add(error);
            		}
            	}
            }
        }
        return equibaseDataSelesApiErrors;
	}
	
	
	/**
	 * Validates Customer Base Access Limit
	 * @param customer
	 * @param equibaseDataSelesApiErrors
	 */
	private void validateCustomerBaseAccessLimit(CustomerUserTo customer, List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors) {
		if (Util.isNull(customer.getBaseAccessLimit())) {
        	LOGGER.info("In Customer Edit, Verifying Base Acess Limit..");
        	EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_BASE_ACCESS_LIMIT;
        	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
        			, equibaseDataSelesApiErrorCode.getId()
        			, equibaseDataSelesApiErrorCode.getDescription()
        			, "Missing Base Access Limit");
        	equibaseDataSelesApiErrors.add(error);
        } else if(customer.getBaseAccessLimit() < 0) {
        	LOGGER.info("In Customer Edit, Verifying Base Acess Limit..");
        	EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.INVALID_BASE_ACCESS_LIMIT;
        	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
        			, equibaseDataSelesApiErrorCode.getId()
        			, equibaseDataSelesApiErrorCode.getDescription()
        			, "Invalid Base Access Limit value");
        	equibaseDataSelesApiErrors.add(error);
        }
	}
	
	/**
	 * Validates Customer Company Name
	 * @param customer
	 * @param equibaseDataSelesApiErrors
	 */
	private void validateCustomerCompany(CustomerUserTo customer, List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors) {
        if (Util.isEmptyString(customer.getCompanyName())) {
        	LOGGER.info("In Customer Edit, Customer Company name is missing");
        	EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_COMPANY_NAME;
        	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
        			, equibaseDataSelesApiErrorCode.getId()
        			, equibaseDataSelesApiErrorCode.getDescription()
        			, "Customer Company Name is missing");
        	equibaseDataSelesApiErrors.add(error);
        }
	}
	
	/**
	 * Validates Customer password
	 * @param customer
	 * @param equibaseDataSelesApiErrors
	 */
	private void validateCustomerPassword(CustomerUserTo customer, List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors) {
		 if (Util.isEmptyString(customer.getPasswordStr())) {
         	LOGGER.info("In Customer Creation, Customer login password is missing");
         	EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FIELD_PASSWORD;
         	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
                     , equibaseDataSelesApiErrorCode.getId()
                     , equibaseDataSelesApiErrorCode.getDescription()
                     , "Missing Customer Password in Customer SetUp");
         	equibaseDataSelesApiErrors.add(error);
         } else {
         	LOGGER.info("In Customer Creation, Validating Customer Password");
         	Boolean isValidPwd = passwordValidator.validatePassword(customer.getPasswordStr());
         	if(!Util.isTrue(isValidPwd)) {
         		LOGGER.info("Password Validations are failed");
         		EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.PASSWORD_VALIDATIONS_FAILED;
             	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
                         , equibaseDataSelesApiErrorCode.getId()
                         , equibaseDataSelesApiErrorCode.getDescription()
                         , "Password Validations are failed");
             	equibaseDataSelesApiErrors.add(error);
         	}
         }
	}
	
	/**
	 * Validates Customer Eamil
	 * @param customer
	 * @param equibaseDataSelesApiErrors
	 */
	private void validateCustomerEamil(CustomerUserTo customer, List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors){
		 if (!Util.isEmptyString(customer.getEmail())) {
         	LOGGER.info("In Customer Edit, Validating Customer Email");
         	customer.setEmail(customer.getEmail().trim());
         	boolean isValidEmail = passwordValidator.validateEmail(customer.getEmail());
         	if(!Util.isTrue(isValidEmail)) {
         		LOGGER.info("Customer Email Validations are failed");
         		EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.INVALID_EMAIL_FORMAT;
         		EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
         				, equibaseDataSelesApiErrorCode.getId()
         				, equibaseDataSelesApiErrorCode.getDescription()
         				, "Customer Email is in Invalid format");
         		equibaseDataSelesApiErrors.add(error);
         	}
         }
	}
	
	
	/**
	 * Validates Customer Apikeys
	 * @param customer
	 * @param equibaseDataSelesApiErrors
	 */
	private void validateCustomerApiKeys(CustomerUserTo customer, List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors) {
		  if (!Util.isNullOrEmptyCollection(customer.getCustomerApiKeys())) {
          	List<CustomerApiKeyTo> apiKeys = customer.getCustomerApiKeys();
          	for (CustomerApiKeyTo customerApiKeyTo : apiKeys) {
          		if(!Util.isNull(customerApiKeyTo)) {
          			if(Util.isEmptyString(customerApiKeyTo.getApiKey())) {
          				EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.INVALID_CUSTOMER_API_KEY;
  		            	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
  		                        , equibaseDataSelesApiErrorCode.getId()
  		                        , equibaseDataSelesApiErrorCode.getDescription()
  		                        , "Customer Api Key should not be null");
  		            	equibaseDataSelesApiErrors.add(error);
  					} else if(customerApiKeyTo.getApiKey().length() > 40) {
  						EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.INVALID_CUSTOMER_API_KEY_LENGTH;
  		            	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
  		                        , equibaseDataSelesApiErrorCode.getId()
  		                        , equibaseDataSelesApiErrorCode.getDescription()
  		                        , "Customer Api Key length should not exceed 40 characters");
  		            	equibaseDataSelesApiErrors.add(error);
  					}
          		}
				}
          }
	}
	
	/**
	 * Validates Customer Reset Day Of Month
	 * @param customer
	 * @param equibaseDataSelesApiErrors
	 */
	private void validateCustomerResetDay(CustomerUserTo customer, List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors) {
		if (Util.isNull(customer.getResetDayOfMonth())
				|| customer.getResetDayOfMonth() < 1
				|| customer.getResetDayOfMonth() > 25) {
        	LOGGER.info("In Customer Edit, Verifying Reset Day Of Month...");
        	 EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.INVALID_RESET_DAY_OF_MONTH;
        	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
        			, equibaseDataSelesApiErrorCode.getId()
        			, equibaseDataSelesApiErrorCode.getDescription()
        			, "Reset Day Of Month should be 1-25");
        	equibaseDataSelesApiErrors.add(error);
        }
	}
	
	
	/**
	 * Validate Customer Products json object
	 * @param customer
	 * @return List<EquibaseDataSelesApiError>
	 */
	@Override
	public List<EquibaseDataSelesApiError> validateCustomerProductJson(CustomerUserTo customer) {
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors = new ArrayList<EquibaseDataSelesApiError>();
		EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = null;

        if (Util.isNull(customer)) {
        	equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_METHOD_ARGUMENTS;
        	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode ,
        			equibaseDataSelesApiErrorCode.getId() ,
        			equibaseDataSelesApiErrorCode.getDescription() ,
                    "Missing customer object in Customer Product/tracks Edit");
        	equibaseDataSelesApiErrors.add(error);
        } else {
        	 if (Util.isNull(customer.getId())) {
             	LOGGER.info("Customer Id should not be null in Edit customer  Product/tracks");
             	equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FIELD_CUSTOMER_ID;
             	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
                         , equibaseDataSelesApiErrorCode.getId()
                         , equibaseDataSelesApiErrorCode.getDescription()
                         , "Missing Customer ID in Customer  Product/tracks Edit");
             	equibaseDataSelesApiErrors.add(error);
             } else {
              	LOGGER.info("Verifying Customer available or not for given customer id : " + customer.getId());
              	CustomerUser customerUser = customerDao.getCustomer(customer.getId());
              	if(Util.isNull(customerUser)) {
              		equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.CUSTOMER_NOT_EXISTS_FOR_CUSTOMER_ID;
              		EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
              				, equibaseDataSelesApiErrorCode.getId()
              				, equibaseDataSelesApiErrorCode.getDescription()
              				, "No Customer Available with given Customer id for editing customer Product/tracks");
              		equibaseDataSelesApiErrors.add(error);
              	}
             }
        	 if (Util.isNullOrEmptyCollection(customer.getCustomerProducts())) {
              	LOGGER.info("Customer Products should not be null in Edit customer  Product/tracks");
              	equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_CUSTOMER_PRODUCTS;
              	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
                          , equibaseDataSelesApiErrorCode.getId()
                          , equibaseDataSelesApiErrorCode.getDescription()
                          , "Missing Customer Products in Customer  Product/tracks Edit");
              	equibaseDataSelesApiErrors.add(error);
              } 
           
        }
        return equibaseDataSelesApiErrors;
	}
	
}