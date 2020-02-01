package com.springboot.api.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;

import com.springboot.api.AbstractTest;
import com.springboot.api.dao.ActionLoggingDao;
import com.springboot.api.dao.CustomerDao;
import com.springboot.api.domain.AdminAction;
import com.springboot.api.domain.CustomerApiLimit;
import com.springboot.api.domain.CustomerUser;
import com.springboot.api.service.ActionLoggingService;
import com.springboot.api.service.impl.ActionLoggingServiceImpl;
public class ActionLoggingServiceTest extends AbstractTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActionLoggingServiceTest.class);

	@InjectMocks
	private  ActionLoggingService actionLoggingService;

	@Mock
	private ActionLoggingDao actionLoggingDaoMock;

	@Mock
	private CustomerDao customerDaoMock;

	@Before
	public void setUp(){
		actionLoggingService =new ActionLoggingServiceImpl(); 
		MockitoAnnotations.initMocks(this);
		getMockAuthentication();
	}

	@Test
	public void testSaveAdminActions(){
		String methodName = "testSaveAdminActions";
		LOGGER.info("Running Junit for : " + methodName);
		AdminAction adminAction=buildMockAdminAction();
		Mockito.when(actionLoggingDaoMock.saveAdminActions((AdminAction)(Mockito.anyObject()))).thenReturn(adminAction);
		AdminAction adminAction2=actionLoggingService.saveAdminActions(adminAction);
		Assert.assertNotNull(adminAction2);
		LOGGER.info("Completed running Junit for : " + methodName);	
	}
	
	@Test
	public void testUpdateCustomerAccessCount(){
		
		String methodName = "updateCustomerAccessCount";
		LOGGER.info("Running Junit for : " + methodName);
		
		CustomerUser customerUser = buildMockCustomer();
		CustomerApiLimit customerApiLimit = buildMockCustomerApiLimit();
		
		Mockito.when(customerDaoMock.getCustomer(Mockito.anyLong())).thenReturn(customerUser);
		Mockito.when(customerDaoMock.getCustomerApiLimitsInCurrentMonth(Mockito.anyLong())).thenReturn(customerApiLimit);
		Mockito.when(customerDaoMock.addOrUpdateApiLimitsToCustomer((CustomerApiLimit)(Mockito.anyObject()))).thenReturn(customerApiLimit);
		
		actionLoggingService.updateCustomerAccessCount(Mockito.anyLong());
		
		LOGGER.info("Completed running Junit for : " + methodName);	
	}
	
	@Test
	public void testLogAdminActions(){
		
		String methodName = "logAdminActions";
		LOGGER.info("Running Junit for : " + methodName);
		
		CustomerUser customerUser = buildMockCustomer();
		CustomerApiLimit customerApiLimit = buildMockCustomerApiLimit();
		
		Mockito.when(customerDaoMock.getCustomer(Mockito.anyLong())).thenReturn(customerUser);
		Mockito.when(customerDaoMock.getCustomerApiLimitsInCurrentMonth(Mockito.anyLong())).thenReturn(customerApiLimit);
		Mockito.when(customerDaoMock.addOrUpdateApiLimitsToCustomer((CustomerApiLimit)(Mockito.anyObject()))).thenReturn(customerApiLimit);
		
		AdminAction adminAction=buildMockAdminAction();
		Mockito.when(actionLoggingDaoMock.saveAdminActions((AdminAction)(Mockito.anyObject()))).thenReturn(adminAction);
		
		for (String str : new String[] { "admin/changePassword",
				"admin/createAdmin", "admin/editAdmin", "product/addProduct",
				"product/deleteProduct", "produtc/deleteProducts",
				"product/editProduct", "customer/addCustomer",
				"customer/updateCustomerProductTracks",
				"customer/editCustomer", "customer/updateCustomerApiLimits",
				"customer/saveApiKey", "customer/deleteCustomerApiKeys" }) {
			
			MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
			mockHttpServletRequest.setRequestURI(str);
			
			actionLoggingService.logAdminActions(1L, 1L, mockHttpServletRequest);
		}
		
		LOGGER.info("Completed running Junit for : " + methodName);	
	}

}
