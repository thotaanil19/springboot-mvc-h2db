package com.springboot.api.dao;

import static org.junit.Assert.fail;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.springboot.api.AbstractTest;
import com.springboot.api.dao.AdminDao;
import com.springboot.api.dao.impl.AdminDaoImpl;
import com.springboot.api.dao.repository.AdminUserRepository;
import com.springboot.api.domain.AdminUser;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.util.Util;

/**
 * @author menlo
 * 
 */

public class AdminDAOTest extends AbstractTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminDaoImpl.class);
	
	@Autowired
	private AdminUserRepository adminUserRepository;
	@Autowired
	private AdminDao adminDAO;
	
	
	
	/**
	 * Junit for createAdmin
	 */
	@Test
	public void testCreateAdmin() {
		String junitMethodName = "testCreateAdmin";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			AdminUser adminUser = new AdminUser();
			adminUser.setAccessLevel(2);
			adminUser.setIsActive(true);
			adminUser.setLoginId("junit" + new Date().getTime());
			adminUser.setName("admin");
			String becryptedPwd = Util.getBCrptPassword("MenloTechnologies@123");
			adminUser.setPassword(becryptedPwd.getBytes());
			//inserted
			adminUser = adminDAO.createAdmin(adminUser);
			Assert.assertNotNull(adminUser);
			if(adminUser != null && adminUser.getId() != null) {
				adminUserRepository.delete(adminUser.getId());
			}
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		}catch(Exception ex) {
			String errMsg = "Error while executing the test case in "
				+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit test GetAdminUsers
	 */
	@Test
	public void testGetAdminUsers() {
		String junitMethodName = "testGetAdminUsers";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try{
			PaginationResultsTo<AdminUser> adminUsers = adminDAO.getAdminUsers(1,10, null);
			Assert.assertNotNull(adminUsers);
			Assert.assertNotNull(adminUsers.getResults());
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		}catch(Exception ex){
			String errMsg = "Error while executing the test case in "
				+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit for UpdateAdmin
	 */
	@Test
	@Transactional
	public void testUpdateAdmin() {
		String junitMethodName = "testUpdateAdmin";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			AdminUser adminUser = new AdminUser();
			adminUser.setAccessLevel(2);
			adminUser.setIsActive(true);
			adminUser.setLoginId("admintest" + new Date().getTime());
			adminUser.setName("admin");
			String becryptedPwd = Util.getBCrptPassword("MenloTechnologies@123");
			adminUser.setPassword(becryptedPwd.getBytes("UTF-8"));
			//inserted
			adminUser = adminDAO.createAdmin(adminUser);
			Assert.assertNotNull(adminUser);
			//updated
			Boolean isUpdated = adminDAO.updateAdmin(adminUser);
			Assert.assertTrue(isUpdated);
			
			if(adminUser != null && adminUser.getId() != null) {
				adminUserRepository.delete(adminUser.getId());
			}
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		}catch(Exception ex) {
			String errMsg = "Error while executing the test case in "
				+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}

	
	/**
	 * Junit for testUpdateAdminWithNullLoginId
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testUpdateAdminWithNullLoginId() {
		String junitMethodName = "testUpdateAdminWithNullLoginId";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		AdminUser adminUser = new AdminUser();
		adminUser.setAccessLevel(2);
		adminUser.setIsActive(true);
		adminUser.setName("admin");
		String pwd = Util.getBCrptPassword("MenloTechnologies@123");
		adminUser.setPassword(pwd.getBytes());
		
		adminDAO.updateAdmin(adminUser);
			
		LOGGER.info("Done executing JUnit for : " + junitMethodName);
		
	}
	
	/**
	 * Junit for testUpdateAdminWithNullName
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testUpdateAdminWithNullName() {
		String junitMethodName = "testUpdateAdminWithNullName";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		AdminUser adminUser = new AdminUser();
		adminUser.setId(1L);
		adminUser.setAccessLevel(2);
		adminUser.setIsActive(true);
		adminUser.setLoginId("junit" + new Date().getTime());
		adminUser.setName(null);
		String becryptedPwd = Util.getBCrptPassword("MenloTechnologies@123");
		adminUser.setPassword(becryptedPwd.getBytes());
		
		adminDAO.updateAdmin(adminUser);
			
		LOGGER.info("Done executing JUnit for : " + junitMethodName);
		
	}
	
	/**
	 * Junit for ChangePassword
	 */
	@Test
	public void testChangePassword() {
		String junitMethodName = "testChangePassword";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			AdminUser adminUser = new AdminUser();
			adminUser.setAccessLevel(2);
			adminUser.setIsActive(true);
			adminUser.setLoginId("junit" + new Date().getTime());
			adminUser.setName("admin");
			adminUser.setPasswordStr("admintest");
			adminUser.setPassword("admintest".getBytes());
			//inserted
			adminUser = adminDAO.createAdmin(adminUser);
			Assert.assertNotNull(adminUser);
			//updated
			Boolean isUpdated = adminDAO.changePassword(adminUser);
			Assert.assertTrue(isUpdated);
			
			if(adminUser != null && adminUser.getId() != null) {
				adminUserRepository.delete(adminUser.getId());
			}
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		}catch(Exception ex) {
			String errMsg = "Error while executing the test case in "
				+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}

	/**
	 * 
	 */
	@Test
	public void testGetAdminUserById(){
		String junitMethodName = "testGetAdminUserById";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try{
			AdminUser adminUser = new AdminUser();
			adminUser.setAccessLevel(2);
			adminUser.setIsActive(true);
			adminUser.setLoginId("junit" + new Date().getTime());
			adminUser.setName("admin");
			adminUser.setPasswordStr("admintest");
			adminUser.setPassword("admintest".getBytes());
			//inserted
			adminUser = adminDAO.createAdmin(adminUser);
			Assert.assertNotNull(adminUser);
			
			adminUser = adminDAO.getAdminUserById(adminUser.getId());
			Assert.assertNotNull(adminUser);
			
			if(adminUser != null && adminUser.getId() != null) {
				adminUserRepository.delete(adminUser.getId());
			}
		}catch(Exception ex) {
			String errMsg = "Error while executing the test case in "
				+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit for EditAdminUser
	 */
	@Test
	public void testEditAdminUser() {
		String junitMethodName = "testEditAdminUser";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			AdminUser adminUser = new AdminUser();
			adminUser.setAccessLevel(2);
			adminUser.setIsActive(true);
			adminUser.setLoginId("junit" + new Date().getTime());
			adminUser.setName("admin");
			adminUser.setPasswordStr("admintest");
			adminUser.setPassword("admintest".getBytes());
			//inserted
			adminUser = adminDAO.createAdmin(adminUser);
			Assert.assertNotNull(adminUser);
			//updated
			adminUser = adminDAO.editAdminUser(adminUser);
			Assert.assertNotNull(adminUser);
			//deleted
			if(adminUser != null && adminUser.getId() != null) {
				adminUserRepository.delete(adminUser.getId());
			}
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		}catch(Exception ex) {
			String errMsg = "Error while executing the test case in "
				+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit for verifyUserAlreadyExists
	 */
	@Test
	public void testVerifyUserAlreadyExists() {
		String junitMethodName = "testVerifyUserAlreadyExists";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			AdminUser adminUser = new AdminUser();
			adminUser.setAccessLevel(2);
			adminUser.setIsActive(true);
			adminUser.setLoginId("junit100");
			adminUser.setName("admin");
			String becryptedPwd = Util.getBCrptPassword("MenloTechnologies@123");
			adminUser.setPassword(becryptedPwd.getBytes("UTF-8"));
			//inserted
			adminUser = adminDAO.createAdmin(adminUser);
			Assert.assertNotNull(adminUser);
			//updated
			Boolean isExist = adminDAO.verifyUserAlreadyExists(adminUser.getLoginId());
			Assert.assertTrue(isExist);
			//deleted
			if(adminUser != null && adminUser.getId() != null) {
				adminUserRepository.delete(adminUser.getId());
			}
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		}catch(Exception ex) {
			String errMsg = "Error while executing the test case in "
				+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit for getAdminUserByLoginId
	 */
	@Test
	public void testGetAdminUserByLoginId() {
		String junitMethodName = "testGetAdminUserByLoginId";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			AdminUser adminUser = new AdminUser();
			adminUser.setAccessLevel(2);
			adminUser.setIsActive(true);
			adminUser.setLoginId("junit" + new Date().getTime());
			adminUser.setName("admin");
			adminUser.setPasswordStr("admintest");
			adminUser.setPassword("admintest".getBytes());
			//inserted
			adminUser = adminDAO.createAdmin(adminUser);
			Assert.assertNotNull(adminUser);
			//updated
			adminUser = adminDAO.getAdminUserByLoginId(adminUser.getLoginId());
			Assert.assertNotNull(adminUser);
			//deleted
			if(adminUser != null && adminUser.getId() != null) {
				adminUserRepository.delete(adminUser.getId());
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
