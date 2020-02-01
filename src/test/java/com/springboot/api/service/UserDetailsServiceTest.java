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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.springboot.api.AbstractTest;
import com.springboot.api.dao.repository.AdminUserRepository;
import com.springboot.api.dao.repository.CustomerUserRepository;
import com.springboot.api.domain.AdminUser;
import com.springboot.api.domain.CustomerUser;
import com.springboot.api.service.impl.UserDetailServiceImpl;

/**
 * 
 * @author anilt
 *
 */
public class UserDetailsServiceTest extends AbstractTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceTest.class);
	
	@InjectMocks
	private UserDetailsService detailsService;
	
	
	@Mock
	private AdminUserRepository adminUserRepository;
	
	@Mock
	private CustomerUserRepository customerUserRepository;
	
	@Before
	public void setUp() {
		detailsService = new UserDetailServiceImpl();
		MockitoAnnotations.initMocks(this);
		getMockAuthentication();
	}
	
	@Test
	public void testLoadUserByUsernameAdminUser() {
		String method = "loadUserByUsername";
		LOGGER.info("Running JUnit for : " + method);
		
		AdminUser adminUser = buildMockAdminUser();
		
		Mockito.when(adminUserRepository.findByLoginId(Mockito.anyString())).thenReturn(adminUser);
		
		UserDetails userDetails = detailsService.loadUserByUsername("admin");
		
		Assert.assertNotNull(userDetails);
		LOGGER.info("Completed running JUnit for : " + method);
	}
	
	@Test(expected=UsernameNotFoundException.class)
	public void testLoadUserByUsernameInactiveAdminUser() {
		String method = "loadUserByUsername";
		LOGGER.info("Running JUnit for : " + method);
		
		AdminUser adminUser = buildMockAdminUser();
		adminUser.setIsActive(false);
		
		Mockito.when(adminUserRepository.findByLoginId(Mockito.anyString())).thenReturn(adminUser);
		
		detailsService.loadUserByUsername("admin");
		
		LOGGER.info("Completed running JUnit for : " + method);
	}
	
	@Test
	public void testLoadUserByUsernameCustomer() {
		String method = "loadUserByUsername";
		LOGGER.info("Running JUnit for : " + method);
		
		CustomerUser customerUser = buildMockCustomer();
		
		Mockito.when(adminUserRepository.findByLoginId(Mockito.anyString())).thenReturn(null);
		Mockito.when(customerUserRepository.findByLoginId(Mockito.anyString())).thenReturn(customerUser);
		
		UserDetails userDetails = detailsService.loadUserByUsername("customer");
		
		Assert.assertNotNull(userDetails);
		LOGGER.info("Completed running JUnit for : " + method);
	}
	
	
	@Test(expected=UsernameNotFoundException.class)
	public void testLoadUserByUsernameCustomerInactiveCustomer() {
		String method = "loadUserByUsername";
		LOGGER.info("Running JUnit for : " + method);
		
		CustomerUser customerUser = buildMockCustomer();
		customerUser.setIsActive(false);
		
		Mockito.when(adminUserRepository.findByLoginId(Mockito.anyString())).thenReturn(null);
		Mockito.when(customerUserRepository.findByLoginId(Mockito.anyString())).thenReturn(customerUser);
		
		detailsService.loadUserByUsername("customer");
		
		LOGGER.info("Completed running JUnit for : " + method);
	}
	
	@Test(expected=UsernameNotFoundException.class)
	public void testLoadUserByUsername() {
		String method = "loadUserByUsername";
		LOGGER.info("Running JUnit for : " + method);
		
		
		Mockito.when(adminUserRepository.findByLoginId(Mockito.anyString())).thenReturn(null);
		Mockito.when(customerUserRepository.findByLoginId(Mockito.anyString())).thenReturn(null);
		
		detailsService.loadUserByUsername("customer");
		
		LOGGER.info("Completed running JUnit for : " + method);
	}
	
	
	@Test
	public void testLoadUserByUsernameAdminUserLevel1() {
		String method = "loadUserByUsername";
		LOGGER.info("Running JUnit for : " + method);
		
		AdminUser adminUser = buildMockAdminUser();
		adminUser.setAccessLevel(1);
		
		Mockito.when(adminUserRepository.findByLoginId(Mockito.anyString())).thenReturn(adminUser);
		
		UserDetails userDetails = detailsService.loadUserByUsername("admin");
		
		Assert.assertNotNull(userDetails);
		LOGGER.info("Completed running JUnit for : " + method);
	}
	@Test
	public void testLoadUserByUsernameAdminUserLevel2() {
		String method = "loadUserByUsername";
		LOGGER.info("Running JUnit for : " + method);
		
		AdminUser adminUser = buildMockAdminUser();
		adminUser.setAccessLevel(2);
		
		Mockito.when(adminUserRepository.findByLoginId(Mockito.anyString())).thenReturn(adminUser);
		
		UserDetails userDetails = detailsService.loadUserByUsername("admin");
		
		Assert.assertNotNull(userDetails);
		LOGGER.info("Completed running JUnit for : " + method);
	}
	@Test
	public void testLoadUserByUsernameAdminUserLevel3() {
		String method = "loadUserByUsername";
		LOGGER.info("Running JUnit for : " + method);
		
		AdminUser adminUser = buildMockAdminUser();
		adminUser.setAccessLevel(3);
		
		Mockito.when(adminUserRepository.findByLoginId(Mockito.anyString())).thenReturn(adminUser);
		
		UserDetails userDetails = detailsService.loadUserByUsername("admin");
		
		Assert.assertNotNull(userDetails);
		LOGGER.info("Completed running JUnit for : " + method);
	}
	@Test
	public void testLoadUserByUsernameAdminUserLevel4() {
		String method = "loadUserByUsername";
		LOGGER.info("Running JUnit for : " + method);
		
		AdminUser adminUser = buildMockAdminUser();
		adminUser.setAccessLevel(4);
		
		Mockito.when(adminUserRepository.findByLoginId(Mockito.anyString())).thenReturn(adminUser);
		
		UserDetails userDetails = detailsService.loadUserByUsername("admin");
		
		Assert.assertNotNull(userDetails);
		LOGGER.info("Completed running JUnit for : " + method);
	}
	@Test
	public void testLoadUserByUsernameAdminUserLevel5() {
		String method = "loadUserByUsername";
		LOGGER.info("Running JUnit for : " + method);
		
		AdminUser adminUser = buildMockAdminUser();
		adminUser.setAccessLevel(5);
		
		Mockito.when(adminUserRepository.findByLoginId(Mockito.anyString())).thenReturn(adminUser);
		
		UserDetails userDetails = detailsService.loadUserByUsername("admin");
		
		Assert.assertNotNull(userDetails);
		LOGGER.info("Completed running JUnit for : " + method);
	}
	
	@Test(expected=RuntimeException.class)
	public void testLoadUserByUsernameAdminUserInvalidAccessLevel() {
		String method = "loadUserByUsername";
		LOGGER.info("Running JUnit for : " + method);
		
		AdminUser adminUser = buildMockAdminUser();
		adminUser.setAccessLevel(1000);
		
		Mockito.when(adminUserRepository.findByLoginId(Mockito.anyString())).thenReturn(adminUser);
		
		detailsService.loadUserByUsername("admin");
		
		LOGGER.info("Completed running JUnit for : " + method);
	}
	
	
	
	
	
	
	
	
	
	

	
	
	
	
}
