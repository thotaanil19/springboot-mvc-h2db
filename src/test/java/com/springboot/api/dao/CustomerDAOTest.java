package com.springboot.api.dao;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.springboot.api.AbstractTest;
import com.springboot.api.dao.AdminDao;
import com.springboot.api.dao.CustomerDao;
import com.springboot.api.dao.impl.AdminDaoImpl;
import com.springboot.api.dao.repository.AdminUserRepository;
import com.springboot.api.dao.repository.CustomerApiKeysRepository;
import com.springboot.api.dao.repository.CustomerApiLimitsRepository;
import com.springboot.api.dao.repository.CustomerProductRepository;
import com.springboot.api.dao.repository.CustomerUserRepository;
import com.springboot.api.dao.repository.ProductRepository;
import com.springboot.api.dao.repository.ProductTracksRepository;
import com.springboot.api.dao.repository.TracksRepository;
import com.springboot.api.domain.CustomerApiKey;
import com.springboot.api.domain.CustomerApiLimit;
import com.springboot.api.domain.CustomerProduct;
import com.springboot.api.domain.CustomerUser;
import com.springboot.api.domain.Product;
import com.springboot.api.domain.ProductTrack;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.SortingCriteria;
import com.springboot.api.util.Util;

/**
 * @author menlo
 * 
 */

public class CustomerDAOTest extends AbstractTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminDaoImpl.class);
	
	@Autowired
	private AdminUserRepository adminUserRepository;
	@Autowired
	private AdminDao adminDAO;
	
	@Autowired
	private CustomerDao customerDAO;
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CustomerProductRepository customerProductRepository;
	@Autowired
	private ProductTracksRepository productTracksRepository;
	
	@Autowired
	private TracksRepository tracksRepository;
	
	@Autowired
	private CustomerUserRepository customerRepository;
	
	@Autowired
	private ProductTracksRepository productTrackRepository;
	
	@Autowired
	private CustomerApiKeysRepository customerApiKeyRepository;
	
	@Autowired
	private CustomerApiLimitsRepository customerApiLimitsRepository;
	
	private Product product;
	private CustomerUser customer;
	private ProductTrack productTrack;
	CustomerProduct customerProduct;
	private CustomerApiKey customerApiKey;
	private CustomerApiLimit customerApiLimit;
	
	@Before
	public void setUp() {
		customer = insertTestCustomerUser();
		product = insertTestProduct();
		customerProduct = insertTestCustomerProduct();
		productTrack = insertTestProductTrack();
		customerApiKey = insertTestCustomerApiKey(true);
		customerApiLimit = insertTestCustomerApiLimits();
	}
	
	@After
	public void tearDown() {
		productTrackRepository.delete(productTrack.getId());
		customerProductRepository.delete(customerProduct.getId());
		customerApiKeyRepository.delete(customerApiKey.getId());
		customerApiLimitsRepository.delete(customerApiLimit.getId());
		customerRepository.delete(customer.getId());
		productRepository.delete(product.getId());
	}
	
	
	
	
	/**
	 * Junit for getAllCustomers
	 */
	@Test
	public void testGetAllCustomers() {
		String junitMethodName = "getAllCustomers";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			List<CustomerUser> customers = customerDAO.getAllCustomers();
			Assert.assertFalse(Util.isNullOrEmptyCollection(customers));
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
				+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit for getCustomer
	 */
	@Test
	public void testGetCustomer() {
		String junitMethodName = "getCustomer";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			CustomerUser customerUser = customerDAO.getCustomer(customer.getId());
			Assert.assertNotNull(customerUser);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
					+ junitMethodName;
				LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	
	/**
	 * Junit for getCustomerProducts
	 */
	@Test
	public void testGetCustomerProductsByCustomerId() {
		String junitMethodName = "getCustomerProducts";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			List<CustomerProduct> customerProducts = customerDAO.getCustomerProducts(customer.getId());
			Assert.assertFalse(Util.isNullOrEmptyCollection(customerProducts));
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
					+ junitMethodName;
				LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit for getCustomerApiKeys
	 */
	@Test
	public void testGetCustomerApiKeys() {
		String junitMethodName = "getCustomerApiKeys";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			List<CustomerApiKey> customerApiKeys = customerDAO.getCustomerApiKeys(customer.getId());
			Assert.assertFalse(Util.isNullOrEmptyCollection(customerApiKeys));
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
					+ junitMethodName;
				LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit for createOrUpdateCustomer
	 */
	@Test
	public void testCreateOrUpdateCustomer() {
		String junitMethodName = "createOrUpdateCustomer";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			
			CustomerUser customer = new CustomerUser();
			customer.setIsActive(true);
			customer.setLoginId("junit" + new Date().getTime());
			customer.setEmail("test@gmail.com");
			customer.setBaseAccessLimit(2000L);
			customer.setCompanyName("Menlo");
			String becryptedPwd = Util.getBCrptPassword("MenloTechnologies@123");
			customer.setPassword(becryptedPwd.getBytes());
			customerRepository.save(customer);
			
			customer = customerDAO.createOrUpdateCustomer(customer);
			
			Assert.assertNotNull(customer);
			Assert.assertNotNull(customer.getId());
			
			if(customer != null && customer.getId() != null) {
				customerRepository.delete(customer.getId());
			}
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
					+ junitMethodName;
				LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit for addApiKeyToCustomer
	 */
	@Test
	public void testAddApiKeyToCustomer() {
		String junitMethodName = "addApiKeyToCustomer";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			CustomerApiKey customerApiKey = new CustomerApiKey();
			customerApiKey.setCustomerId(customer.getId());
			customerApiKey.setApiKey("Junit Test Api Key");
			customerApiKey.setIsActive(true);
			customerApiKey.setTimeStamp(new Date());
			
			CustomerApiKey customerApiKeys = customerDAO.addApiKeyToCustomer(customerApiKey);
			
			Assert.assertNotNull(customerApiKeys);
			Assert.assertNotNull(customerApiKeys.getId());
			
			if(customerApiKeys != null && customerApiKeys.getId() != null) {
				customerApiKeyRepository.delete(customerApiKeys.getId());
			}
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
					+ junitMethodName;
				LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	
	/**
	 * Junit for addOrUpdateApiLimitsToCustomer
	 */
	@Test
	public void testAddOrUpdateApiLimitsToCustomer() {
		String junitMethodName = "addOrUpdateApiLimitsToCustomer";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			CustomerApiLimit apiLimit = new CustomerApiLimit();
			apiLimit.setCurrentAccessCount(2000L);
			apiLimit.setCurrentAccessLimit(3000L);
			apiLimit.setCustomerId(customer.getId());
			apiLimit.setStartDate(Util.getFirstDateOfCurrentMonth());
			apiLimit.setEndDate(Util.getLastDateOfCurrentMonth());
			
			apiLimit = customerDAO.addOrUpdateApiLimitsToCustomer(apiLimit);
			
			Assert.assertNotNull(apiLimit);
			Assert.assertNotNull(apiLimit.getId());
			
			if(apiLimit != null && apiLimit.getId() != null) {
				customerApiLimitsRepository.delete(apiLimit.getId());
			}
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
					+ junitMethodName;
				LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit for deleteSelectedCustomers
	 */
	@Test
	@Transactional
	public void testDeleteSelectedCustomers() {
		String junitMethodName = "deleteSelectedCustomers";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			CustomerUser customer = new CustomerUser();
			customer.setIsActive(true);
			customer.setLoginId("junit" + new Date().getTime());
			customer.setEmail("test@gmail.com");
			customer.setBaseAccessLimit(2000L);
			customer.setCompanyName("Menlo");
			String becryptedPwd = Util.getBCrptPassword("MenloTechnologies@123");
			customer.setPassword(becryptedPwd.getBytes());
			customerRepository.save(customer);
			
			Assert.assertNotNull(customer);
			Assert.assertNotNull(customer.getId());
			
			Set<Long> customerIds = new HashSet<Long>();
			customerIds.add(customer.getId());
			
			Integer cnt = customerDAO.deleteSelectedCustomers(customerIds);
			
			Assert.assertNotNull(cnt);
			Assert.assertTrue(cnt > 0);
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
					+ junitMethodName;
				LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit for getCustomerByLoginId
	 */
	@Test
	public void testGetCustomerByLoginId() {
		String junitMethodName = "deleteSelectedCustomers";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			
			Assert.assertNotNull(customer);
			Assert.assertNotNull(customer.getId());
			
			CustomerUser cu = customerDAO.getCustomerByLoginId(customer.getLoginId());
			
			Assert.assertNotNull(cu);
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
					+ junitMethodName;
				LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit for getCustomerApiKeyByCustomerIdAndApiKey
	 */
	@Test
	public void testGetCustomerApiKeyByCustomerIdAndApiKey() {
		String junitMethodName = "deleteSelectedCustomers";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			
			Assert.assertNotNull(customer);
			Assert.assertNotNull(customer.getId());
			
			Assert.assertNotNull(customerApiKey);
			Assert.assertNotNull(customerApiKey.getId());
			
			CustomerApiKey  apiKey = customerDAO.getCustomerApiKeyByCustomerIdAndApiKey(customer.getId(), customerApiKey.getApiKey());
			
			Assert.assertNotNull(apiKey);
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
					+ junitMethodName;
				LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit for getCustomerApiLimits
	 */
	@Test
	public void testGetCustomerApiLimits() {
		String junitMethodName = "getCustomerApiLimits";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			
			Assert.assertNotNull(customer);
			Assert.assertNotNull(customer.getId());
			
			CustomerApiLimit  apiLimit = customerDAO.getCustomerApiLimits(customer.getId());
			
			Assert.assertNotNull(apiLimit);
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
					+ junitMethodName;
				LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit for getCustomerApiAllLimits
	 */
	@Test
	public void testGetCustomerApiAllLimits() {
		String junitMethodName = "getCustomerApiAllLimits";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			
			Assert.assertNotNull(customer);
			Assert.assertNotNull(customer.getId());
			
			CustomerApiLimit  apiLimit = customerDAO.getCustomerApiAllLimits(customer.getId());
			
			Assert.assertNotNull(apiLimit);
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
					+ junitMethodName;
				LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit for getCustomerApiLimits
	 */
	@Test
	public void testGetCustomerApiLimitsByDates() {
		String junitMethodName = "getCustomerApiLimits";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			
			Assert.assertNotNull(customer);
			Assert.assertNotNull(customer.getId());
			
			CustomerApiLimit apiLimit = customerDAO.getCustomerApiLimits(
					customer.getId(), Util.getFirstDateOfCurrentMonth(),
					Util.getLastDateOfCurrentMonth());
			
			Assert.assertNotNull(apiLimit);
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
					+ junitMethodName;
				LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	
	/**
	 * Junit for changePassword
	 */
	@Test
	public void testChangePassword() {
		String junitMethodName = "changePassword";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			
			Assert.assertNotNull(customer);
			Assert.assertNotNull(customer.getId());
			customer.setPassword(Util.getBCrptPassword("MenloTechnologies@123").getBytes());
			
			Boolean status = customerDAO.changePassword(customer);
			
			Assert.assertTrue(Util.isTrue(status));
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
					+ junitMethodName;
				LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit for getCustomerProducts
	 */
	@Test
	public void testGetCustomerProductsByCustomerIdInPagination() {
		String junitMethodName = "getCustomerProducts";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			
			Assert.assertNotNull(customer);
			Assert.assertNotNull(customer.getId());
			
			PaginationResultsTo<CustomerProduct> customerProducts = customerDAO.getCustomerProducts(customer.getId(),1, 10);
			
			Assert.assertNotNull(customerProducts);
			Assert.assertFalse(Util.isNullOrEmptyCollection(customerProducts.getResults()));
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
					+ junitMethodName;
				LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit for getCustomerApiKeyByApiKey
	 */
	@Test
	public void testGetCustomerApiKeyByApiKey() {
		String junitMethodName = "getCustomerProducts";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			
			Assert.assertNotNull(customer);
			Assert.assertNotNull(customer.getId());
			
			CustomerApiKey apiKey = customerDAO.getCustomerApiKeyByApiKey(customerApiKey.getApiKey());
			
			Assert.assertNotNull(apiKey);
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
					+ junitMethodName;
				LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit for getCustomers
	 */
	@Test
	public void testGetCustomers() {
		String junitMethodName = "getCustomers";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			
			Assert.assertNotNull(customer);
			Assert.assertNotNull(customer.getId());
			
			SortingCriteria sortCriteria = new SortingCriteria();
			sortCriteria.setSortingField("email");
			sortCriteria.setSortOrder("ASC");
			
			PaginationResultsTo<CustomerUser> users = customerDAO.getCustomers(1,10,sortCriteria);
			
			Assert.assertNotNull(users);
			Assert.assertNotNull(users.getResults());
			Assert.assertNotNull(users.getResults().isEmpty());
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
					+ junitMethodName;
				LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit for getCustomers
	 */
	@Test
	public void testGetCustomersDefaultSorting() {
		String junitMethodName = "getCustomers";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			
			Assert.assertNotNull(customer);
			Assert.assertNotNull(customer.getId());
			
			PaginationResultsTo<CustomerUser> users = customerDAO.getCustomers(1,10,null);
			
			Assert.assertNotNull(users);
			Assert.assertNotNull(users.getResults());
			Assert.assertNotNull(users.getResults().isEmpty());
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
					+ junitMethodName;
				LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	/**
	 * Junit for saveApiKey
	 */
	@Test
	public void testSaveApiKey() {
		String junitMethodName = "saveApiKey";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			
			CustomerApiKey customerApiKey = new CustomerApiKey();
			customerApiKey.setCustomerId(customer.getId());
			customerApiKey.setApiKey("Junit Test Api Key");
			customerApiKey.setIsActive(true);
			customerApiKey.setTimeStamp(new Date());
			
			Assert.assertNotNull(customer);
			Assert.assertNotNull(customer.getId());
			
			CustomerApiKey apiKey = customerDAO.saveApiKey(customerApiKey);
			
			Assert.assertNotNull(apiKey);
			Assert.assertNotNull(apiKey.getId());
			if(apiKey != null && apiKey.getId() != null) {
				customerApiKeyRepository.delete( apiKey.getId());
			}
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
					+ junitMethodName;
				LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit for deleteCustomerApiKeys
	 */
	@Test
	@Transactional
	public void testDeleteCustomerApiKeys() {
		String junitMethodName = "deleteCustomerApiKeys";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			Assert.assertNotNull(customer);
			Assert.assertNotNull(customer.getId());

			CustomerApiKey apiKey = new CustomerApiKey();
			apiKey.setCustomerId(customer.getId());
			apiKey.setApiKey("Junit Test Api Key");
			apiKey.setIsActive(true);
			apiKey.setTimeStamp(new Date());
			
			Assert.assertNotNull(customer);
			Assert.assertNotNull(customer.getId());
			
			apiKey = customerDAO.saveApiKey(apiKey);
			
			Assert.assertNotNull(apiKey);
			Assert.assertNotNull(apiKey.getId());
			
			
			 Set<Long> apiKeyIds = new HashSet<Long>();
			 apiKeyIds.add(apiKey.getId());
			
			Boolean status = customerDAO.deleteCustomerApiKeys(customer.getId(), apiKeyIds);
			
			Assert.assertTrue(Util.isTrue(status));
			
			if(apiKey != null && apiKey.getId() != null) {
				customerApiKeyRepository.delete( apiKey.getId());
			}
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
					+ junitMethodName;
				LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit for getCustomerApiLimits
	 */
	/*@Test
	public void testGetCustomerApiLimitsByCustomerIdAndResetDayDate() {
		String junitMethodName = "getCustomerApiLimits";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			Assert.assertNotNull(customer);
			Assert.assertNotNull(customer.getId());

			CustomerApiLimit apiLimits = customerDAO.getCustomerApiLimits(customer.getId(), Util.getFullDateFromDayNumberInCurrentMonth(1));
			
			Assert.assertNotNull(apiLimits);
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch(Exception ex) {
			String errMsg = "Error while executing the Junits for method : "
					+ junitMethodName;
				LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}*/
	
	
}
