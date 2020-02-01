package com.springboot.api.dao;

import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.springboot.api.AbstractTest;
import com.springboot.api.dao.ActionLoggingDao;
import com.springboot.api.dao.repository.AdminActionsRepository;
import com.springboot.api.dao.repository.AdminUserRepository;
import com.springboot.api.dao.repository.CustomerUserRepository;
import com.springboot.api.domain.AdminAction;
import com.springboot.api.domain.AdminUser;
import com.springboot.api.domain.CustomerUser;

/**
 * 
 * @author anilt
 *
 */
public class ActionLoggingDAOTest extends AbstractTest{

	private final static Logger LOGGER = LoggerFactory
	.getLogger(ProductDAOTest.class);

	@Autowired
	private ActionLoggingDao actionLoggingDao;
	
	@Autowired
	private AdminActionsRepository adminActionsRepository;
	
	@Autowired
	private CustomerUserRepository customerRepository;
	
	@Autowired
	private AdminUserRepository adminUserRepository;
	
	private AdminUser admin;
	private CustomerUser customerUser;
	
	@Before
	public void setUp() {
		admin = insertTestAdmin();
		customerUser = insertTestCustomerUser();
	}
	
	@After
	public void tearDown() {
		adminUserRepository.delete(admin.getId());
		customerRepository.delete(customerUser.getId());
		
	}
	
	//Junit to SaveAdminActions 
	@Test
	public void testSaveAdminActions(){
		String junitMethodName = "testSaveAdminActions";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			AdminAction action = new AdminAction();
			action.setAdminId(admin.getId());
			action.setCustomerId(customerUser.getId());
			action.setIp4("192.168.1.1");
			action.setIp6("192.168.1.2.20");
			action.setTimeStamp(new Date());
			
			//Inserted
			action = actionLoggingDao.saveAdminActions(action);
			Assert.assertNotNull(action);
			if(action != null && action.getId() != null) {
				adminActionsRepository.delete(action.getId());
			}
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		}catch(Exception ex) {
			String errMsg = "Error while executing the test case in "
				+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	
}
