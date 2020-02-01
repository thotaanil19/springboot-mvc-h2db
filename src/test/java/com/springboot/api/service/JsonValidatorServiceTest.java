package com.springboot.api.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.springboot.api.AbstractTest;
import com.springboot.api.dao.CustomerDao;
import com.springboot.api.domain.CustomerUser;
import com.springboot.api.service.JsonValidatorService;
import com.springboot.api.service.impl.JsonValidatorServiceImpl;
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
public class JsonValidatorServiceTest extends AbstractTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonValidatorServiceTest.class);

	@InjectMocks
	private JsonValidatorService jsonValidatorService;
	
	@Mock
	private CustomerDao customerDao;
	
	@Mock
	private PasswordValidator passwordValidator;
	
	@Before
	public void setUp() {
		jsonValidatorService = new JsonValidatorServiceImpl();
		MockitoAnnotations.initMocks(this);
		getMockAuthentication();
	}
	
	@Test
	public void testValidateCustomerSetupJsonWithNullObj() {
		String methodName = "validateCustomerSetupJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerSetupJson(null);
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.MISSING_METHOD_ARGUMENTS.getId(), equibaseDataSelesApiErrors.get(0).getId());
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
	
	@Test
	public void testValidateCustomerSetupJsonWithNullLoginId() {
		String methodName = "validateCustomerSetupJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		CustomerUserTo customer = buildMockCustomerUserTo(false);
		customer.setLoginId(null);
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerSetupJson(customer);
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FIELD_LOGIN_ID.getId(), equibaseDataSelesApiErrors.get(0).getId());
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
	
	@Test
	public void testValidateCustomerSetupJsonWithInvalidLoginId() {
		String methodName = "validateCustomerSetupJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		CustomerUserTo customer = buildMockCustomerUserTo(false);
		customer.setLoginId("JUnit Test");
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerSetupJson(customer);
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.WHITE_SPACES_IN_LOGIN_ID.getId(), equibaseDataSelesApiErrors.get(0).getId());
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
	
	@Test
	public void testValidateCustomerSetupJsonWithDuplicateLoginId() {
		String methodName = "validateCustomerSetupJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		CustomerUserTo customer = buildMockCustomerUserTo(false);
		
		 Mockito.when(customerDao.getCustomerByLoginId(Mockito.anyString())).thenReturn(new CustomerUser());
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerSetupJson(customer);
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.CUSTOMER_ALREADY_EXISTS_WITH_LOGIN_ID.getId(), equibaseDataSelesApiErrors.get(0).getId());
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
	
	@Test
	public void testValidateCustomerSetupJsonWithNullPwd() {
		String methodName = "validateCustomerSetupJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		CustomerUserTo customer = buildMockCustomerUserTo(false);
		customer.setPassword(null);
		customer.setPasswordStr(null);
		
		 Mockito.when(customerDao.getCustomerByLoginId(Mockito.anyString())).thenReturn(null);
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerSetupJson(customer);
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FIELD_PASSWORD.getId(), equibaseDataSelesApiErrors.get(0).getId());
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
	
	@Test
	public void testValidateCustomerSetupJsonWithInvalidPwd() {
		String methodName = "validateCustomerSetupJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		CustomerUserTo customer = buildMockCustomerUserTo(false);
		
		 Mockito.when(customerDao.getCustomerByLoginId(Mockito.anyString())).thenReturn(null);
		 Mockito.when(passwordValidator.validatePassword(Mockito.anyString())).thenReturn(false);
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerSetupJson(customer);
		
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.PASSWORD_VALIDATIONS_FAILED.getId(), equibaseDataSelesApiErrors.get(0).getId());
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
	
	@Test
	public void testValidateCustomerSetupJsonWithInvalidEamil() {
		String methodName = "validateCustomerSetupJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		CustomerUserTo customer = buildMockCustomerUserTo(false);
		
		 Mockito.when(customerDao.getCustomerByLoginId(Mockito.anyString())).thenReturn(null);
		 Mockito.when(passwordValidator.validateEmail(Mockito.anyString())).thenReturn(false);
		 Mockito.when(passwordValidator.validatePassword(Mockito.anyString())).thenReturn(true);
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerSetupJson(customer);
		
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.INVALID_EMAIL_FORMAT.getId(), equibaseDataSelesApiErrors.get(0).getId());
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
	
	@Test
	public void testValidateCustomerSetupJsonWithNullCompany() {
		String methodName = "validateCustomerSetupJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		CustomerUserTo customer = buildMockCustomerUserTo(false);
		customer.setCompanyName(null);
		
		 Mockito.when(customerDao.getCustomerByLoginId(Mockito.anyString())).thenReturn(null);
		 Mockito.when(passwordValidator.validatePassword(Mockito.anyString())).thenReturn(true);
		 Mockito.when(passwordValidator.validateEmail(Mockito.anyString())).thenReturn(true);
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerSetupJson(customer);
		
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_COMPANY_NAME.getId(), equibaseDataSelesApiErrors.get(0).getId());
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
	
	@Test
	public void testValidateCustomerSetupJsonWithNullBaseAccessLimit() {
		String methodName = "validateCustomerSetupJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		CustomerUserTo customer = buildMockCustomerUserTo(false);
		customer.setBaseAccessLimit(null);
		
		 Mockito.when(customerDao.getCustomerByLoginId(Mockito.anyString())).thenReturn(null);
		 Mockito.when(passwordValidator.validatePassword(Mockito.anyString())).thenReturn(true);
		 Mockito.when(passwordValidator.validateEmail(Mockito.anyString())).thenReturn(true);
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerSetupJson(customer);
		
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.MISSING_BASE_ACCESS_LIMIT.getId(), equibaseDataSelesApiErrors.get(0).getId());
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
	
	@Test
	public void testValidateCustomerSetupJsonWithInvalidBaseAccessLimit() {
		String methodName = "validateCustomerSetupJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		CustomerUserTo customer = buildMockCustomerUserTo(false);
		customer.setBaseAccessLimit(-100L);
		
		 Mockito.when(customerDao.getCustomerByLoginId(Mockito.anyString())).thenReturn(null);
		 Mockito.when(passwordValidator.validatePassword(Mockito.anyString())).thenReturn(true);
		 Mockito.when(passwordValidator.validateEmail(Mockito.anyString())).thenReturn(true);
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerSetupJson(customer);
		
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.INVALID_BASE_ACCESS_LIMIT.getId(), equibaseDataSelesApiErrors.get(0).getId());
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
	
	@Test
	public void testValidateCustomerSetupJsonWithInvalidResetDay() {
		String methodName = "validateCustomerSetupJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		CustomerUserTo customer = buildMockCustomerUserTo(false);
		customer.setResetDayOfMonth(-100);
		
		 Mockito.when(customerDao.getCustomerByLoginId(Mockito.anyString())).thenReturn(null);
		 Mockito.when(passwordValidator.validatePassword(Mockito.anyString())).thenReturn(true);
		 Mockito.when(passwordValidator.validateEmail(Mockito.anyString())).thenReturn(true);
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerSetupJson(customer);
		
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.INVALID_RESET_DAY_OF_MONTH.getId(), equibaseDataSelesApiErrors.get(0).getId());
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
	
	@Test
	public void testValidateCustomerSetupJsonWithInvalidApiKeys1() {
		String methodName = "validateCustomerSetupJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		CustomerUserTo customer = buildMockCustomerUserTo(false);
		List<CustomerApiKeyTo> apiKeys = customer.getCustomerApiKeys();
		CustomerApiKeyTo apiKeyTo = buildMockCustomerApiKeyTo();
		apiKeyTo.setApiKey(null);
		apiKeys.add(apiKeyTo);

		customer.setCustomerApiKeys(apiKeys);
		
		 Mockito.when(customerDao.getCustomerByLoginId(Mockito.anyString())).thenReturn(null);
		 Mockito.when(passwordValidator.validatePassword(Mockito.anyString())).thenReturn(true);
		 Mockito.when(passwordValidator.validateEmail(Mockito.anyString())).thenReturn(true);
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerSetupJson(customer);
		
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.INVALID_CUSTOMER_API_KEY.getId(), equibaseDataSelesApiErrors.get(0).getId());
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
	
	@Test
	public void testValidateCustomerSetupJsonWithInvalidApiKeys2() {
		String methodName = "validateCustomerSetupJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		CustomerUserTo customer = buildMockCustomerUserTo(false);
		List<CustomerApiKeyTo> apiKeys = customer.getCustomerApiKeys();
		CustomerApiKeyTo apiKeyTo = buildMockCustomerApiKeyTo();
		apiKeyTo.setApiKey("wgfbhfgyghejgheriyeugngyneg489gnvioet8034f3jg4ye4y85kltvntf834t4thn5jivnhwerf6734t8btnh54gt8634thk6ji5gwebfhgpi-fuofvb54y56hetgy4gnetrwejky45fu46y4jt34hf4gy3rwh8o57wghjp53834ug");
		apiKeys.add(apiKeyTo);

		customer.setCustomerApiKeys(apiKeys);
		
		 Mockito.when(customerDao.getCustomerByLoginId(Mockito.anyString())).thenReturn(null);
		 Mockito.when(passwordValidator.validatePassword(Mockito.anyString())).thenReturn(true);
		 Mockito.when(passwordValidator.validateEmail(Mockito.anyString())).thenReturn(true);
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerSetupJson(customer);
		
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.INVALID_CUSTOMER_API_KEY_LENGTH.getId(), equibaseDataSelesApiErrors.get(0).getId());
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
	
	@Test
	public void testValidateCustomerProductJsonWithNullCustomerObj() {
		String methodName = "validateCustomerProductJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerProductJson(null);
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.MISSING_METHOD_ARGUMENTS.getId(), equibaseDataSelesApiErrors.get(0).getId());
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
	
	@Test
	public void testValidateCustomerProductJsonWithNullCustomerId() {
		String methodName = "validateCustomerProductJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		CustomerUserTo customer = buildMockCustomerUserTo(false);
		
		customer.setId(null);
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerProductJson(customer);
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FIELD_CUSTOMER_ID.getId(), equibaseDataSelesApiErrors.get(0).getId());
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
	
	@Test
	public void testValidateCustomerProductJsonWithInvalidCustomerId() {
		String methodName = "validateCustomerProductJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		CustomerUserTo customer = buildMockCustomerUserTo(false);
		
		Mockito.when(customerDao.getCustomer(Mockito.anyLong())).thenReturn(null);
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerProductJson(customer);
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.CUSTOMER_NOT_EXISTS_FOR_CUSTOMER_ID.getId(), equibaseDataSelesApiErrors.get(0).getId());
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
	
	@Test
	public void testValidateCustomerProductJsonWithNullCustomerProducts() {
		String methodName = "validateCustomerProductJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		CustomerUserTo customer = buildMockCustomerUserTo(false);
		
		customer.setCustomerProducts(null);
		
		Mockito.when(customerDao.getCustomer(Mockito.anyLong())).thenReturn(new CustomerUser());
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerProductJson(customer);
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.MISSING_CUSTOMER_PRODUCTS.getId(), equibaseDataSelesApiErrors.get(0).getId());
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
	
	@Test
	public void testvalidateCustomerEditJsonWithNullObj() {
		String methodName = "validateCustomerSetupJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerEditJson(null);
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.MISSING_METHOD_ARGUMENTS.getId(), equibaseDataSelesApiErrors.get(0).getId());
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
	
	@Test
	public void testvalidateCustomerEditJsonWithNullCustomerId() {
		String methodName = "validateCustomerSetupJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		CustomerUserTo customer = buildMockCustomerUserTo(false);
		customer.setId(null);
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerEditJson(customer);
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FIELD_CUSTOMER_ID.getId(), equibaseDataSelesApiErrors.get(0).getId());
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
	
	@Test
	public void testvalidateCustomerEditJsonWithInvalidCustomerId() {
		String methodName = "validateCustomerSetupJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		CustomerUserTo customer = buildMockCustomerUserTo(false);
		
		Mockito.when(customerDao.getCustomer(Mockito.anyLong())).thenReturn(null);
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerEditJson(customer);
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.CUSTOMER_NOT_EXISTS_FOR_CUSTOMER_ID.getId(), equibaseDataSelesApiErrors.get(0).getId());
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
	
	
	@Test
	public void testvalidateCustomerEditJsonWithInvalidCustomerApiLimits() {
		String methodName = "validateCustomerSetupJson";
		LOGGER.info("Running Junit for method : " + methodName);
		
		CustomerUserTo customer = buildMockCustomerUserTo(false);

		CustomerApiLimitTo customerApiLimit = new CustomerApiLimitTo();
		
		customer.setCustomerApiLimit(customerApiLimit);
		
	  	Mockito.when(customerDao.getCustomer(Mockito.anyLong())).thenReturn(new CustomerUser());
	  	Mockito.when(passwordValidator.validateEmail(Mockito.anyString())).thenReturn(true);
	  	
		
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors  = jsonValidatorService.validateCustomerEditJson(customer);
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(equibaseDataSelesApiErrors));
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.MISSING_SATRT_DATE.getId(), equibaseDataSelesApiErrors.get(0).getId());
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.MISSING_END_DATE.getId(), equibaseDataSelesApiErrors.get(1).getId());
		Assert.assertEquals(EquibaseDataSelesApiErrorCodes.MISSING_API_LIMITS.getId(), equibaseDataSelesApiErrors.get(2).getId());
		
		
		LOGGER.info("Completed running Junit for method : " + methodName);
	}
}
