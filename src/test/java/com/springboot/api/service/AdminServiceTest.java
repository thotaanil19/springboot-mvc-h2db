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
import com.springboot.api.dao.AdminDao;
import com.springboot.api.domain.AdminUser;
import com.springboot.api.service.AdminService;
import com.springboot.api.service.impl.AdminServiceImpl;
import com.springboot.api.to.EquibaseDataSelesApiError;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.SortingCriteria;
import com.springboot.api.util.PasswordValidator;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
public class AdminServiceTest extends AbstractTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceTest.class);
	
	@InjectMocks
	private AdminService adminService;
	
	
	@Mock
	private AdminDao adminDaoMock;
	
	@Mock
	private PasswordValidator validatorMock;
	
	@Before
	public void setUp() {
		adminService = new AdminServiceImpl();
		MockitoAnnotations.initMocks(this);
		getMockAuthentication();
	}
	
	@Test
	public void testCreateAdmin() {
		String method = "createAdmin";
		LOGGER.info("Running JUnit for : " + method);
		
		AdminUser adminUser = buildMockAdminUser();
		
		Mockito.when(adminDaoMock.createAdmin((AdminUser)Mockito.anyObject())).thenReturn(adminUser);
		
		AdminUser au = adminService.createAdmin(adminUser);
		Assert.assertNotNull(au);
		LOGGER.info("Completed running JUnit for : " + method);
	}
	

	@Test(expected=IllegalArgumentException.class)
	public void testCreateAdminWithNullAdminObject() {
		String method = "createAdmin";
		LOGGER.info("Running JUnit for : " + method);
		
		AdminUser adminUser = buildMockAdminUser();
		
		Mockito.when(adminDaoMock.createAdmin((AdminUser)Mockito.anyObject())).thenReturn(adminUser);
		
		adminService.createAdmin(null);
		LOGGER.info("Completed running JUnit for : " + method);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateAdminWithEmptyPwd() {
		String method = "createAdmin";
		LOGGER.info("Running JUnit for : " + method);
		
		AdminUser adminUser = buildMockAdminUser();
		adminUser.setPassword(null);
		adminUser.setPasswordStr(null);
		
		Mockito.when(adminDaoMock.createAdmin((AdminUser)Mockito.anyObject())).thenReturn(adminUser);
		
		adminService.createAdmin(adminUser);
		LOGGER.info("Completed running JUnit for : " + method);
	}
	
	
	@Test
	public void testGetAdminUsers() {
		String method = "getAdminUsers";
		LOGGER.info("Running JUnit for : " + method);

		SortingCriteria sortingCriteria = new SortingCriteria();

		PaginationResultsTo<AdminUser> adminUsers = new PaginationResultsTo<AdminUser>();

		Mockito.when(
				adminDaoMock.getAdminUsers(Mockito.anyInt(), Mockito.anyInt(),
						(SortingCriteria) Mockito.anyObject())).thenReturn(
								adminUsers);

		PaginationResultsTo<AdminUser> paginationResultsTo = adminService.getAdminUsers(1, 10, sortingCriteria);

		Assert.assertNotNull(paginationResultsTo);

		LOGGER.info("Completed running JUnit for : " + method);
	}
	
	@Test
	public void testVerifyUserAlreadyExists() {
		String method = "verifyUserAlreadyExists";
		LOGGER.info("Running JUnit for : " + method);

		Mockito.when(adminDaoMock.verifyUserAlreadyExists(Mockito.anyString()))
				.thenReturn(true);

		Boolean isLoginIdAlreadyExists = adminService
				.verifyUserAlreadyExists("junit123");

		Assert.assertTrue(isLoginIdAlreadyExists);

		LOGGER.info("Completed running JUnit for : " + method);
	}
	
	
	@Test
	public void testGetAdminProfile() {
		String method = "getAdminProfile";
		LOGGER.info("Running JUnit for : " + method);
		
		AdminUser admin = buildMockAdminUser();

		Mockito.when(adminDaoMock.getAdminUserById(Mockito.anyLong()))
				.thenReturn(admin);

		AdminUser au = adminService
				.getAdminProfile();

		Assert.assertNotNull(au);

		LOGGER.info("Completed running JUnit for : " + method);
	}
	
	@Test
	public void testGetAdminUserByLoginId() {
		String method = "getAdminUserByLoginId";
		LOGGER.info("Running JUnit for : " + method);
		
		AdminUser admin = buildMockAdminUser();

		Mockito.when(adminDaoMock.getAdminUserByLoginId(Mockito.anyString()))
				.thenReturn(admin);

		AdminUser au = adminService
				.getAdminUserByLoginId(Mockito.anyString());

		Assert.assertNotNull(au);

		LOGGER.info("Completed running JUnit for : " + method);
	}

	@Test
	public void testChangePassword() {
		String method = "changePassword";
		LOGGER.info("Running JUnit for : " + method);
		
		AdminUser admin = buildMockAdminUser();

		Mockito.when(validatorMock.validatePassword(Mockito.anyString()))
				.thenReturn(true);
		
		Mockito.when(adminDaoMock.changePassword((AdminUser)Mockito.anyObject()))
		.thenReturn(true);

		Boolean status = adminService
				.changePassword(admin);

		Assert.assertNotNull(status);
		Assert.assertTrue(status);
		

		LOGGER.info("Completed running JUnit for : " + method);
	}
	
	@Test
	public void testUpdateAdmin() {
		String method = "updateAdmin";
		LOGGER.info("Running JUnit for : " + method);
		
		AdminUser admin = buildMockAdminUser();

		Mockito.when(adminDaoMock.updateAdmin((AdminUser)Mockito.anyObject()))
				.thenReturn(true);

		Boolean bool = adminService
				.updateAdmin(admin);

		Assert.assertNotNull(bool);
		Assert.assertTrue(bool);

		LOGGER.info("Completed running JUnit for : " + method);
	}
	
	@Test
	public void testValidateAdminUserObject() {
		String method = "validateAdminUserObject";
		LOGGER.info("Running JUnit for : " + method);
		
		AdminUser admin = buildMockAdminUser();
		
		Mockito.when(validatorMock.validatePassword(Mockito.anyString()))
		.thenReturn(true);

			List<EquibaseDataSelesApiError> validationErrors = adminService
				.validateAdminUserObject(admin);

		Assert.assertTrue(Util.isNullOrEmptyCollection(validationErrors));

		LOGGER.info("Completed running JUnit for : " + method);
	}
	
	@Test
	public void testValidateAdminUserObjectWithNull() {
		String method = "validateAdminUserObject";
		LOGGER.info("Running JUnit for : " + method);
		
			List<EquibaseDataSelesApiError> validationErrors = adminService
				.validateAdminUserObject(null);

		Assert.assertTrue(!Util.isNullOrEmptyCollection(validationErrors));

		LOGGER.info("Completed running JUnit for : " + method);
	}
	
	/*
	 * Validate AdminUser object with following properties
	 * Empty name
	 * Empty login id
	 * Empty pwd
	 * Empty null access level
	 * Is Active null
	 * 
	 */
	@Test
	public void testValidateAdminUserObjectInCase1() {
		String method = "validateAdminUserObject";
		LOGGER.info("Running JUnit for : " + method);
		
		AdminUser admin = new AdminUser();

			List<EquibaseDataSelesApiError> validationErrors = adminService
				.validateAdminUserObject(admin);

		Assert.assertTrue(!Util.isNullOrEmptyCollection(validationErrors));

		LOGGER.info("Completed running JUnit for : " + method);
	}
	
	/*
	 * Validate AdminUser object with following properties
	 * Spaces in login id
	 * Invalid pwd pwd
	 * Negative  access level
	 * 
	 */
	@Test
	public void testValidateAdminUserObjectInCase12() {
		String method = "validateAdminUserObject";
		LOGGER.info("Running JUnit for : " + method);
		
		AdminUser admin = buildMockAdminUser();
		admin.setLoginId("JUNIT TEST");
		admin.setAccessLevel(-2);
		admin.setPasswordStr("menlo@123");
		
		Mockito.when(validatorMock.validatePassword(Mockito.anyString()))
		.thenReturn(false);


			List<EquibaseDataSelesApiError> validationErrors = adminService
				.validateAdminUserObject(admin);

		Assert.assertTrue(!Util.isNullOrEmptyCollection(validationErrors));

		LOGGER.info("Completed running JUnit for : " + method);
	}
	
	@Test
	public void testEditAdminUser() {
		String method = "editAdminUser";
		LOGGER.info("Running JUnit for : " + method);

		AdminUser admin = buildMockAdminUser();

		Mockito.when(adminDaoMock.editAdminUser((AdminUser)Mockito.anyObject()))
		.thenReturn(admin);


		AdminUser au = adminService
				.editAdminUser(admin);

		Assert.assertNotNull(au);

		LOGGER.info("Completed running JUnit for : " + method);
	}
	
	
	
}
