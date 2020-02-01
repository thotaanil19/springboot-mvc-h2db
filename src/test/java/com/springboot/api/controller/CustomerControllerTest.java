package com.springboot.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.api.AbstractTest;
import com.springboot.api.domain.CustomerUser;
import com.springboot.api.endpoint.to.CustomerProductTo;
import com.springboot.api.service.ActionLoggingService;
import com.springboot.api.service.CustomerService;
import com.springboot.api.service.JsonValidatorService;
import com.springboot.api.to.CustomerApiKeyTo;
import com.springboot.api.to.CustomerUserTo;
import com.springboot.api.to.EquibaseDataSelesApiError;
import com.springboot.api.to.EquibaseDataSelesApiErrorCodes;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.SortingCriteria;
import com.springboot.api.util.PasswordValidator;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
public class CustomerControllerTest extends AbstractTest {
	
	private MockMvc mockMvc;
	
	@InjectMocks
	private CustomerController customerController;
	
	@Mock
	private CustomerService customerService;
	
	@Mock
	private PasswordValidator passwordValidator;
	
	@Mock
	private JsonValidatorService jsonValidatorService;
	
	@Mock
	private ActionLoggingService actionLoggingService;

	@Before
	public void setUp() {
		customerController = new CustomerController();
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
		MockitoAnnotations.initMocks(this);
		getMockAuthentication();
	}
	
	@Test
	public void dashboard() throws Exception {
		mockMvc.perform(get("/customer/dashboard"))
		.andExpect(status().isOk())
		.andExpect(view().name("customer/customerProfile"));
	}

	@Test
	public void profileTab() throws Exception {
		mockMvc.perform(get("/customer/profileTab"))
		.andExpect(status().isOk())
		.andExpect(view().name("customer/customerProfile"));
	}
	
	@Test
	public void productsTab() throws Exception {
		mockMvc.perform(get("/customer/productsTab"))
		.andExpect(status().isOk())
		.andExpect(view().name("customer/customerProducts"));
	}
	
	@Test
	public void apiKeysTab() throws Exception {
		mockMvc.perform(get("/customer/apiKeysTab"))
		.andExpect(status().isOk())
		.andExpect(view().name("customer/customerApiKeys"));
	}
	
	@Test
	public void accessLevelsTab() throws Exception {
		mockMvc.perform(get("/customer/accessLevelsTab"))
		.andExpect(status().isOk())
		.andExpect(view().name("customer/customerAccessLevels"));
	}
	
	@Test
	public void testGetAllCustomers() throws Exception {
		
		List<CustomerUser> customerUsers = new ArrayList<CustomerUser>();
		customerUsers.add(buildMockCustomer());
		
		Mockito.when(customerService.getAllCustomers()).thenReturn(customerUsers);
		
		
		MvcResult result = mockMvc.perform(get("/customer/getAllCustomers"))
				.andExpect(status().isOk())
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		Assert.assertNotNull(content);
		
		ObjectMapper mapper = new ObjectMapper();
		
		List<CustomerUser> customerUsersNew = mapper.readValue(content, mapper.getTypeFactory().constructCollectionType(List.class, CustomerUser.class));
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(customerUsersNew));
		
	}
	
	@Test
	public void testGetCustomers() throws Exception {
		
		List<CustomerUserTo> results = new ArrayList<CustomerUserTo>();
		results.add(buildMockCustomerUserTo(false));
		
		PaginationResultsTo<CustomerUserTo> customerUsers = new PaginationResultsTo<CustomerUserTo>();
		customerUsers.setResults(results);
		
		Mockito.when(customerService.getCustomers(Mockito.anyInt(), Mockito.anyInt(), Mockito.any(SortingCriteria.class))).thenReturn(customerUsers);
		
		
		MvcResult result = mockMvc.perform(post("/customer/getCustomers/1/10").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isOk())
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		Assert.assertNotNull(content);
		
		ObjectMapper mapper = new ObjectMapper();
		
		PaginationResultsTo<CustomerUserTo> customerUsersNew = mapper
				.readValue(
						content,
						mapper.getTypeFactory()
								.constructParametrizedType(
										PaginationResultsTo.class,
										PaginationResultsTo.class,
										CustomerUserTo.class));
		
		Assert.assertNotNull(Util.isNull(customerUsersNew));
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(customerUsersNew.getResults()));
		
	}
	
	@Test
	public void testGetCustomerProducts() throws Exception {
	
		CustomerUserTo products = buildMockCustomerUserTo(false);
		
		Mockito.when(customerService.getCustomerProducts(Mockito.anyLong())).thenReturn(products);
		
		
		MvcResult result = mockMvc.perform(get("/customer/1/getCustomerProducts"))
				.andExpect(status().isOk())
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		Assert.assertNotNull(content);
		
		ObjectMapper mapper = new ObjectMapper();
		
		CustomerUserTo customerProducts = mapper.readValue(content, CustomerUserTo.class);
		
		Assert.assertNotNull(Util.isNull(customerProducts));
		
	}
	
	@Test
	public void testGetCustomerProductsInPagination() throws Exception {
	
		PaginationResultsTo<CustomerProductTo> products = new PaginationResultsTo<CustomerProductTo>();
		
		List<CustomerProductTo> results = new ArrayList<CustomerProductTo>();
		results.add(buildMockCustomerProductTo(false));
		
		products.setResults(results);
		
		Mockito.when(customerService.getCustomerProductsInPagination(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(products);
		
		
		MvcResult result = mockMvc.perform(get("/customer/1/getCustomerProducts/1/10"))
				.andExpect(status().isOk())
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		Assert.assertNotNull(content);
		
		ObjectMapper mapper = new ObjectMapper();
		
		PaginationResultsTo<CustomerProductTo> customerProducts = mapper.readValue(content, mapper.getTypeFactory()
				.constructParametrizedType(
						PaginationResultsTo.class,
						PaginationResultsTo.class,
						CustomerProductTo.class));
		
		Assert.assertNotNull(customerProducts);
		Assert.assertFalse(Util.isNullOrEmptyCollection(customerProducts.getResults()));
		
	}
	
	@Test
	public void testGetCustomerProductTracks() throws Exception {
		
		
		CustomerProductTo customerProductTo  = buildMockCustomerProductTo(false);
		
		
		Mockito.when(customerService.getCustomerProductTracks(Mockito.anyLong(), Mockito.anyLong())).thenReturn(customerProductTo);
		
		
		MvcResult result = mockMvc.perform(get("/customer/1/1/getCustomerProductTracks"))
				.andExpect(status().isOk())
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		Assert.assertNotNull(content);
		
		ObjectMapper mapper = new ObjectMapper();
		
		CustomerProductTo customerProduct = mapper.readValue(content, CustomerProductTo.class);
		
		Assert.assertNotNull(Util.isNull(customerProduct));
		
	}
	
	@Test
	public void testAddCustomer() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		CustomerUserTo customer  = buildMockCustomerUserTo(false);
		String requestBody = mapper.writeValueAsString(customer);
		
		
		Mockito.when(jsonValidatorService.validateCustomerSetupJson(Mockito.any(CustomerUserTo.class))).thenReturn(null);
		
		Mockito.doNothing().when(customerService).addCustomer(Mockito.any(CustomerUserTo.class));
		
		Mockito.doNothing().when(actionLoggingService).logAdminActions(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(HttpServletRequest.class));
		
		MvcResult result = mockMvc.perform(post("/customer/addCustomer").contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(status().isOk())
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		Assert.assertNotNull(content);
		
		
		
		CustomerUserTo customerNew = mapper.readValue(content, CustomerUserTo.class);
		
		Assert.assertNotNull(customerNew);
		
	}
	
	@Test
	public void testAddCustomerInvalidObject() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		CustomerUserTo customer  = buildMockCustomerUserTo(false);
		String requestBody = mapper.writeValueAsString(customer);
		
		ArrayList<EquibaseDataSelesApiError> errs = new ArrayList<EquibaseDataSelesApiError>();
		errs.add(new EquibaseDataSelesApiError(EquibaseDataSelesApiErrorCodes.MISSING_METHOD_ARGUMENTS, 100, "Test", "Test"));
		
		
		Mockito.when(jsonValidatorService.validateCustomerSetupJson(Mockito.any(CustomerUserTo.class))).thenReturn(errs);
		
		Mockito.doNothing().when(customerService).addCustomer(Mockito.any(CustomerUserTo.class));
		
		Mockito.doNothing().when(actionLoggingService).logAdminActions(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(HttpServletRequest.class));
		
		MvcResult result = mockMvc.perform(post("/customer/addCustomer").contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		Assert.assertNotNull(content);
		
		
	}
	
	@Test
	public void testGenerateNewApiKey() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		MvcResult result = mockMvc.perform(get("/customer/generateNewApiKey"))
				.andExpect(status().is(HttpStatus.OK.value()))
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		Assert.assertNotNull(content);
		
		CustomerApiKeyTo apiKey = mapper.readValue(content, CustomerApiKeyTo.class);
		
		Assert.assertNotNull(apiKey);
		
	}
	
	
	@Test
	public void testSaveApiKey() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		CustomerApiKeyTo apiKey = buildMockCustomerApiKeyTo();
		
		String requestBody = mapper.writeValueAsString(apiKey);
		
		Mockito.when(
				customerService.saveApiKey(Mockito.any(CustomerApiKeyTo.class)))
				.thenReturn(apiKey);
		
		Mockito.doNothing()
				.when(actionLoggingService)
				.logAdminActions(Mockito.anyLong(), Mockito.anyLong(),
						Mockito.any(HttpServletRequest.class));

		MvcResult result = mockMvc
				.perform(
						post("/customer/saveApiKey").contentType(
								MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		Assert.assertNotNull(content);
		
		CustomerApiKeyTo apiKeyNew = mapper.readValue(content, CustomerApiKeyTo.class);
		
		Assert.assertNotNull(apiKeyNew);
		
	}
	
		

}
