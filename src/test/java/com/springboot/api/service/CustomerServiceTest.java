package com.springboot.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.springboot.api.dao.ProductDao;
import com.springboot.api.dao.TracksDao;
import com.springboot.api.domain.CustomerApiKey;
import com.springboot.api.domain.CustomerApiLimit;
import com.springboot.api.domain.CustomerProduct;
import com.springboot.api.domain.CustomerUser;
import com.springboot.api.domain.Product;
import com.springboot.api.domain.ProductTrack;
import com.springboot.api.domain.Track;
import com.springboot.api.endpoint.to.CustomerProductTo;
import com.springboot.api.service.CustomerService;
import com.springboot.api.service.impl.CustomerServiceImpl;
import com.springboot.api.to.CustomerApiKeyTo;
import com.springboot.api.to.CustomerApiLimitTo;
import com.springboot.api.to.CustomerUserTo;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.SortingCriteria;
import com.springboot.api.util.PasswordValidator;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
public class CustomerServiceTest extends AbstractTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceTest.class);
	
	@InjectMocks
	private CustomerService customerService;
	
	@Mock
	private CustomerDao customerDaoMock;
	
	@Mock
	private ProductDao productDaoMock;
	
	@Mock
	private TracksDao tracksDaoMock;
	
	@Mock
	private PasswordValidator passwordValidatorMock;
	
	
	@Before
	public void setUp(){
		customerService = new CustomerServiceImpl();
		MockitoAnnotations.initMocks(this);
		getMockAuthentication();
	}
	
	/**
	 * Junit for customerService.getAllCustomers
	 */
	@Test
	public void testGetAllCustomers() {
	
		String methodName = "getAllCustomers";
		LOGGER.info("Running Junit for : " + methodName);
		
		List<CustomerUser> customers = new ArrayList<CustomerUser>();
		CustomerUser customer = buildMockCustomer();
		customers.add(customer);
		
		Mockito.when(customerDaoMock.getAllCustomers()).thenReturn(customers);
		
		List<CustomerUser> customerUsers = customerService.getAllCustomers();
		
		Assert.assertTrue(!Util.isNullOrEmptyCollection(customerUsers));
		
		LOGGER.info("Completed running Junit for : " + methodName);
		
	}
	
	/**
	 * Junit for customerService.getCustomers
	 */
	@Test
	public void testGetCustomers() {
	
		String methodName = "getCustomers";
		LOGGER.info("Running Junit for : " + methodName);
		
		List<CustomerUser> results = new ArrayList<CustomerUser>();
		CustomerUser customer = buildMockCustomer();
		results.add(customer);
		
		PaginationResultsTo<CustomerUser> customers = new PaginationResultsTo<CustomerUser>();
		customers.setResults(results);
		customers.setPageNumber(1);
		customers.setPageSize(1);
		customers.setTotalNumberOfPages(1);
		customers.setTotalNumberOfResults(1L);
		
		List<CustomerApiKey> customerApiKeys = new ArrayList<CustomerApiKey>();
		customerApiKeys.add(buildMockCustomerApiKey());
		
		Mockito.when(customerDaoMock.getCustomers(Mockito.anyInt(), Mockito.anyInt(), (SortingCriteria)Mockito.anyObject()))
		.thenReturn(customers);
		
		Mockito.when(customerDaoMock.getCustomerApiKeys(Mockito.anyLong()))
		.thenReturn(customerApiKeys);
		
		PaginationResultsTo<CustomerUserTo> customerUsers = customerService.getCustomers(1,10,null);
		
		Assert.assertNotNull(customerUsers);
		Assert.assertTrue(!Util.isNullOrEmptyCollection(customerUsers.getResults()));
		
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	/**
	 * Junit for customerService.getCustomerProducts
	 */
	@Test
	public void testGetCustomerProducts() {
	
		String methodName = "getCustomerProducts";
		LOGGER.info("Running Junit for : " + methodName);
		
		List<CustomerUser> results = new ArrayList<CustomerUser>();
		CustomerUser customer = buildMockCustomer();
		results.add(customer);
		
		PaginationResultsTo<CustomerUser> customers = new PaginationResultsTo<CustomerUser>();
		customers.setResults(results);
		customers.setPageNumber(1);
		customers.setPageSize(1);
		customers.setTotalNumberOfPages(1);
		customers.setTotalNumberOfResults(1L);
		
		List<CustomerApiKey> customerApiKeys = new ArrayList<CustomerApiKey>();
		customerApiKeys.add(buildMockCustomerApiKey());
		
		List<CustomerProduct> customerProducts = new ArrayList<CustomerProduct>();
		customerProducts.add(buildMockCustomerProduct(false));
		
		List<ProductTrack> productTracks = new ArrayList<ProductTrack>();
		productTracks.add(buildMockProductTrack());
		
		Product product = buildMockProduct();
		Track track = builMockTrack();
		
		Mockito.when(customerDaoMock.getCustomer(Mockito.anyLong()))
		.thenReturn(customer);
		
		Mockito.when(customerDaoMock.getCustomerProducts(Mockito.anyLong()))
		.thenReturn(customerProducts);
		
		Mockito.when(productDaoMock.getProduct(Mockito.anyLong()))
		.thenReturn(product);
		
		Mockito.when(productDaoMock.getProductTracksBYCustProdsId(Mockito.anyLong()))
		.thenReturn(productTracks);
		
		Mockito.when(tracksDaoMock.getTrack(Mockito.anyString()))
		.thenReturn(track);
		
		CustomerUserTo customerUserTo = customerService.getCustomerProducts(1L);
		
		Assert.assertNotNull(customerUserTo);
		Assert.assertTrue(!Util.isNullOrEmptyCollection(customerUserTo.getCustomerProducts()));
	
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	

	/**
	 * Junit for customerService.getCustomerProductTracks
	 */
	@Test
	public void testGetCustomerProductTracks() {
	
		String methodName = "getCustomerProductTracks";
		LOGGER.info("Running Junit for : " + methodName);
		
		List<CustomerUser> results = new ArrayList<CustomerUser>();
		CustomerUser customer = buildMockCustomer();
		results.add(customer);
		
		PaginationResultsTo<CustomerUser> customers = new PaginationResultsTo<CustomerUser>();
		customers.setResults(results);
		customers.setPageNumber(1);
		customers.setPageSize(1);
		customers.setTotalNumberOfPages(1);
		customers.setTotalNumberOfResults(1L);
		
		List<CustomerApiKey> customerApiKeys = new ArrayList<CustomerApiKey>();
		customerApiKeys.add(buildMockCustomerApiKey());
		
		List<CustomerProduct> customerProducts = new ArrayList<CustomerProduct>();
		customerProducts.add(buildMockCustomerProduct(false));
		
		List<ProductTrack> productTracks = new ArrayList<ProductTrack>();
		productTracks.add(buildMockProductTrack());
		
		Product product = buildMockProduct();
		Track track = builMockTrack();
		
		Mockito.when(productDaoMock.getCustomerProducts(Mockito.anyLong(), Mockito.anyLong()))
		.thenReturn(customerProducts);
		
		Mockito.when(productDaoMock.getProduct(Mockito.anyLong()))
		.thenReturn(product);
		
		
		Mockito.when(productDaoMock.getProductTracksBYCustProdsId(Mockito.anyLong()))
		.thenReturn(productTracks);
		

		Mockito.when(tracksDaoMock.getTrack(Mockito.anyString()))
		.thenReturn(track);
		
		CustomerProductTo customerProductTo = customerService.getCustomerProductTracks(1L,1L);
		
		Assert.assertNotNull(customerProductTo);
		Assert.assertTrue(!Util.isNullOrEmptyCollection(customerProductTo.getProductTracks()));
		
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	
	
	/**
	 * Junit for customerService.editCustomerApiLimits
	 */
	/*@Test
	public void testEditCustomerApiLimitsAdding() {
	
		String methodName = "editCustomerApiLimits";
		LOGGER.info("Running Junit for : " + methodName);
		
		List<CustomerUser> results = new ArrayList<CustomerUser>();
		CustomerUser customer = buildMockCustomer();
		results.add(customer);
		
		PaginationResultsTo<CustomerUser> customers = new PaginationResultsTo<CustomerUser>();
		customers.setResults(results);
		customers.setPageNumber(1);
		customers.setPageSize(1);
		customers.setTotalNumberOfPages(1);
		customers.setTotalNumberOfResults(1L);
		
		List<CustomerApiKey> customerApiKeys = new ArrayList<CustomerApiKey>();
		customerApiKeys.add(buildMockCustomerApiKey());
		
		List<CustomerProduct> customerProducts = new ArrayList<CustomerProduct>();
		CustomerProduct customerProduct = buildMockCustomerProduct(false);
		customerProducts.add(customerProduct);
		
		List<ProductTrack> productTracks = new ArrayList<ProductTrack>();
		ProductTrack productTrack = buildMockProductTrack();
		productTracks.add(productTrack);
		
		CustomerApiLimit customerApiLimit = buildMockCustomerApiLimit();
		
		
		Mockito.when(customerDaoMock.getCustomerApiLimits(Mockito.anyLong()))
		.thenReturn(null);
		
		Mockito.when(customerDaoMock.addOrUpdateApiLimitsToCustomer((CustomerApiLimit)Mockito.anyObject()))
		.thenReturn(customerApiLimit);
		
		
		CustomerUserTo customerUserTo = new CustomerUserTo();
		customerUserTo.setCustomerApiLimit(new CustomerApiLimitTo());
		
		customerService.editCustomerApiLimits(customerUserTo);
		
		LOGGER.info("Completed running Junit for : " + methodName);
	}*/
	
	/**
	 * Junit for customerService.editCustomerApiLimits
	 */
	/*@Test
	public void testEditCustomerApiLimitsUpdating() {
	
		String methodName = "editCustomerApiLimits";
		LOGGER.info("Running Junit for : " + methodName);
		
		List<CustomerUser> results = new ArrayList<CustomerUser>();
		CustomerUser customer = buildMockCustomer();
		results.add(customer);
		
		PaginationResultsTo<CustomerUser> customers = new PaginationResultsTo<CustomerUser>();
		customers.setResults(results);
		customers.setPageNumber(1);
		customers.setPageSize(1);
		customers.setTotalNumberOfPages(1);
		customers.setTotalNumberOfResults(1L);
		
		List<CustomerApiKey> customerApiKeys = new ArrayList<CustomerApiKey>();
		customerApiKeys.add(buildMockCustomerApiKey());
		
		List<CustomerProduct> customerProducts = new ArrayList<CustomerProduct>();
		CustomerProduct customerProduct = buildMockCustomerProduct(false);
		customerProducts.add(customerProduct);
		
		List<ProductTrack> productTracks = new ArrayList<ProductTrack>();
		ProductTrack productTrack = buildMockProductTrack();
		productTracks.add(productTrack);
		
		
		CustomerApiLimit customerApiLimit = buildMockCustomerApiLimit();
		
		
		Mockito.when(customerDaoMock.getCustomerApiLimits(Mockito.anyLong()))
		.thenReturn(customerApiLimit);
		
		Mockito.when(customerDaoMock.addOrUpdateApiLimitsToCustomer((CustomerApiLimit)Mockito.anyObject()))
		.thenReturn(customerApiLimit);
		
		
		CustomerUserTo customerUserTo = new CustomerUserTo();
		customerUserTo.setCustomerApiLimit(new CustomerApiLimitTo());
		
		customerService.editCustomerApiLimits(customerUserTo);
		
		LOGGER.info("Completed running Junit for : " + methodName);
	}*/
	
	
	
	/**
	 * Junit for customerService.editApiLimits
	 */
	@Test
	public void testEditApiLimits() {
	
		String methodName = "editApiLimits";
		LOGGER.info("Running Junit for : " + methodName);
		
		CustomerApiLimitTo customerApiLimitTo = buildMockCustomerApiLimitTo();
		
		CustomerApiLimit customerApiLimit = buildMockCustomerApiLimit();
		
		
		Mockito.when(customerDaoMock.getCustomerApiLimitsInCurrentMonth(Mockito.anyLong()))
		.thenReturn(customerApiLimit);
		
		Mockito.when(customerDaoMock.addOrUpdateApiLimitsToCustomer((CustomerApiLimit)Mockito.anyObject()))
		.thenReturn(customerApiLimit);
		
		
		customerService.editApiLimits(customerApiLimitTo);
		
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	
	/**
	 * Edit Api Keys - Generating New Api
	 */
	@Test
	public void TestEditApiKeysCase1() {
	
		String methodName = "editApiKeys";
		LOGGER.info("Running Junit for : " + methodName);
		
		CustomerApiKey customerApiKey = buildMockCustomerApiKey();
		
		
		Mockito.when(customerDaoMock.getCustomerApiKeyByCustomerIdAndApiKey(Mockito.anyLong(), Mockito.anyString()))
		.thenReturn(null);
		
		Mockito.when(customerDaoMock.addApiKeyToCustomer((CustomerApiKey)Mockito.anyObject()))
		.thenReturn(customerApiKey);
		
		
		CustomerUserTo customerUserTo = buildMockCustomerUserTo(false);
		
		customerService.editApiKeys(customerUserTo);
		
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	
	/**
	 * Edit Api Keys - Reactivate existing Api key
	 */
	@Test
	public void testEditApiKeysCase2() {
	
		String methodName = "editApiKeys";
		LOGGER.info("Running Junit for : " + methodName);
		
		CustomerApiKey customerApiKey = buildMockCustomerApiKey();
		
		customerApiKey.setIsActive(false);
		
		
		Mockito.when(customerDaoMock.getCustomerApiKeyByCustomerIdAndApiKey(Mockito.anyLong(), Mockito.anyString()))
		.thenReturn(customerApiKey);
		
		Mockito.when(customerDaoMock.addApiKeyToCustomer((CustomerApiKey)Mockito.anyObject()))
		.thenReturn(customerApiKey);
		
		
		CustomerUserTo customerUserTo = buildMockCustomerUserTo(false);
		
		customerService.editApiKeys(customerUserTo);
		
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	/**
	 * Junit for customerService.editProducts with all tracks false
	 */
	@Test
	public void testEditProductsWithAllTracksFalse() {
	
		String methodName = "editProducts";
		LOGGER.info("Running Junit for : " + methodName);
		
		List<CustomerProduct> customerProducts = new ArrayList<CustomerProduct>();
		CustomerProduct customerProduct = buildMockCustomerProduct(false);
		customerProducts.add(customerProduct);
		
		List<ProductTrack> productTracks = new ArrayList<ProductTrack>();
		ProductTrack productTrack = buildMockProductTrack();
		productTracks.add(productTrack);
		
		
		Mockito.when(productDaoMock.deleteProductTracks(Mockito.anyLong()))
		.thenReturn(1);
		
		Mockito.when(productDaoMock.deleteCustomerProductsByCustomerId(Mockito.anyLong()))
		.thenReturn(1);
		
		Mockito.when(productDaoMock.getCustomerProductsByCustomerId(Mockito.anyLong()))
		.thenReturn(customerProducts);
		
		Mockito.when(productDaoMock.getCustomerProducts(Mockito.anyLong(), Mockito.anyLong()))
		.thenReturn(customerProducts);
		
		
		Mockito.when(productDaoMock.createProductTracks((ProductTrack)Mockito.anyObject()))
		.thenReturn(productTrack);

		
		Mockito.when(productDaoMock.createCustomerProduct((CustomerProduct)Mockito.anyObject()))
		.thenReturn(customerProduct);
		
		Mockito.when(productDaoMock.createProductTracks((ProductTrack)Mockito.anyObject()))
		.thenReturn(productTrack);
		
		CustomerUserTo customerUserTo = buildMockCustomerUserTo(false);
		
		customerService.editProducts(customerUserTo);
		
		LOGGER.info("Completed running Junit for : " + methodName);
		
	}
	
	/**
	 * Junit for customerService.editProducts with all tracks true
	 */
	@Test
	public void testEditProductsWithAllTracksTrue() {
	
		String methodName = "editProducts";
		LOGGER.info("Running Junit for : " + methodName);
		
		List<CustomerProduct> customerProducts = new ArrayList<CustomerProduct>();
		CustomerProduct customerProduct = buildMockCustomerProduct(true);
		customerProducts.add(customerProduct);
		
		List<ProductTrack> productTracks = new ArrayList<ProductTrack>();
		ProductTrack productTrack = buildMockProductTrack();
		productTracks.add(productTrack);
		
		
		Mockito.when(productDaoMock.deleteProductTracks(Mockito.anyLong()))
		.thenReturn(1);
		
		Mockito.when(productDaoMock.deleteCustomerProductsByCustomerId(Mockito.anyLong()))
		.thenReturn(1);
		
		Mockito.when(productDaoMock.getCustomerProductsByCustomerId(Mockito.anyLong()))
		.thenReturn(customerProducts);
		
		Mockito.when(productDaoMock.getCustomerProducts(Mockito.anyLong(), Mockito.anyLong()))
		.thenReturn(customerProducts);
		
		
		Mockito.when(productDaoMock.createCustomerProduct((CustomerProduct)Mockito.anyObject()))
		.thenReturn(customerProduct);
		
		Mockito.when(productDaoMock.createProductTracks((ProductTrack)Mockito.anyObject()))
		.thenReturn(productTrack);
		
		CustomerUserTo customerUserTo = buildMockCustomerUserTo(true);
		
		customerService.editProducts(customerUserTo);
	
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	/**
	 * Test Editing Assigned products to a Customer
	 */
	@Test
	public void testEditProductsWithNoProducts() {
	
		String methodName = "editProducts";
		LOGGER.info("Running Junit for : " + methodName);
		
		List<CustomerProduct> customerProducts = new ArrayList<CustomerProduct>();
		CustomerProduct customerProduct = buildMockCustomerProduct(false);
		customerProducts.add(customerProduct);
		
		List<ProductTrack> productTracks = new ArrayList<ProductTrack>();
		ProductTrack productTrack = buildMockProductTrack();
		productTracks.add(productTrack);
		
		Mockito.when(productDaoMock.getCustomerProductsByCustomerId(Mockito.anyLong()))
		.thenReturn(customerProducts);
		
		Mockito.when(productDaoMock.deleteProductTracks(Mockito.anyLong()))
		.thenReturn(1);
		
		Mockito.when(productDaoMock.deleteCustomerProductsByCustomerId(Mockito.anyLong()))
		.thenReturn(1);
		
		CustomerUserTo customerUserTo = buildMockCustomerUserTo(false);
		customerUserTo.setCustomerProducts(null);
		
		customerService.editProducts(customerUserTo);
		
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	
	/**
	 * Test Editing Assigned products to a Customer
	 */
	@Test
	public void testEditCustomerProduct() {
	
		String methodName = "editCustomerProduct";
		LOGGER.info("Running Junit for : " + methodName);
		
		List<CustomerProduct> customerProducts = new ArrayList<CustomerProduct>();
		CustomerProduct customerProduct = buildMockCustomerProduct(false);
		customerProducts.add(customerProduct);
		
		List<ProductTrack> productTracks = new ArrayList<ProductTrack>();
		ProductTrack productTrack = buildMockProductTrack();
		productTracks.add(productTrack);
		
		
		Mockito.when(productDaoMock.getCustomerProducts(Mockito.anyLong(), Mockito.anyLong()))
		.thenReturn(null);
		
		Mockito.when(productDaoMock.deleteProductTracks(Mockito.anyLong()))
		.thenReturn(1);
		
		Mockito.when(productDaoMock.deleteCustomerProductsByCustomerId(Mockito.anyLong()))
		.thenReturn(1);
		
		Mockito.when(productDaoMock.getCustomerProductsByCustomerId(Mockito.anyLong()))
		.thenReturn(customerProducts);
		
		
		Mockito.when(productDaoMock.createProductTracks((ProductTrack)Mockito.anyObject()))
		.thenReturn(productTrack);

		
		Mockito.when(productDaoMock.createCustomerProduct((CustomerProduct)Mockito.anyObject()))
		.thenReturn(customerProduct);
		
		CustomerUserTo customerUserTo = buildMockCustomerUserTo(false);
		
		customerService.editCustomerProduct(1L, customerUserTo.getCustomerProducts().get(0));
		
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	/**
	 * Test Edit Customer profile details
	 */
	@Test
	public void testEditCustomerDetails() {
	
		String methodName = "editCustomerDetails";
		LOGGER.info("Running Junit for : " + methodName);
		
		List<CustomerUser> results = new ArrayList<CustomerUser>();
		CustomerUser customer = buildMockCustomer();
		results.add(customer);
		
		PaginationResultsTo<CustomerUser> customers = new PaginationResultsTo<CustomerUser>();
		customers.setResults(results);
		customers.setPageNumber(1);
		customers.setPageSize(1);
		customers.setTotalNumberOfPages(1);
		customers.setTotalNumberOfResults(1L);
		
		List<CustomerApiKey> customerApiKeys = new ArrayList<CustomerApiKey>();
		customerApiKeys.add(buildMockCustomerApiKey());
		
		List<CustomerProduct> customerProducts = new ArrayList<CustomerProduct>();
		CustomerProduct customerProduct = buildMockCustomerProduct(false);
		customerProducts.add(customerProduct);
		
		List<ProductTrack> productTracks = new ArrayList<ProductTrack>();
		ProductTrack productTrack = buildMockProductTrack();
		productTracks.add(productTrack);
		
		Mockito.when(customerDaoMock.getCustomer(Mockito.anyLong()))
		.thenReturn(customer);
		
		Mockito.when(customerDaoMock.createOrUpdateCustomer((CustomerUser)Mockito.anyObject()))
		.thenReturn(customer);
		
		CustomerUserTo customerUserTo = buildMockCustomerUserTo(true);
		
		CustomerUserTo customerTo = customerService.editCustomerDetails(customerUserTo);
		
		Assert.assertNotNull(customerTo);
		
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	
	/**
	 * Test Add customer
	 */
	@Test
	public void testAddCustomer() {
	
		String methodName = "addCustomer";
		LOGGER.info("Running Junit for : " + methodName);
		
		List<CustomerUser> results = new ArrayList<CustomerUser>();
		CustomerUser customer = buildMockCustomer();
		results.add(customer);
		
		PaginationResultsTo<CustomerUser> customers = new PaginationResultsTo<CustomerUser>();
		customers.setResults(results);
		customers.setPageNumber(1);
		customers.setPageSize(1);
		customers.setTotalNumberOfPages(1);
		customers.setTotalNumberOfResults(1L);
		
		List<CustomerApiKey> customerApiKeys = new ArrayList<CustomerApiKey>();
		customerApiKeys.add(buildMockCustomerApiKey());
		
		List<CustomerProduct> customerProducts = new ArrayList<CustomerProduct>();
		CustomerProduct customerProduct = buildMockCustomerProduct(false);
		customerProducts.add(customerProduct);
		
		List<ProductTrack> productTracks = new ArrayList<ProductTrack>();
		ProductTrack productTrack = buildMockProductTrack();
		productTracks.add(productTrack);
		
		CustomerApiKey customerApiKey = buildMockCustomerApiKey();
		
		CustomerApiLimit customerApiLimit = buildMockCustomerApiLimit();
		
		Mockito.when(customerDaoMock.createOrUpdateCustomer((CustomerUser)Mockito.anyObject()))
		.thenReturn(customer);
		

		Mockito.when(productDaoMock.createCustomerProduct((CustomerProduct)Mockito.anyObject()))
		.thenReturn(customerProduct);
		
		Mockito.when(productDaoMock.createProductTracks((ProductTrack)Mockito.anyObject()))
		.thenReturn(productTrack);
		
		
		Mockito.when(customerDaoMock.addApiKeyToCustomer((CustomerApiKey)Mockito.anyObject()))
		.thenReturn(customerApiKey);
		
		Mockito.when(customerDaoMock.getCustomer(Mockito.anyLong()))
		.thenReturn(customer);
		
		Mockito.when(customerDaoMock.addOrUpdateApiLimitsToCustomer((CustomerApiLimit)Mockito.anyObject()))
		.thenReturn(customerApiLimit);
		
		
		CustomerUserTo customerUserTo = buildMockCustomerUserTo(false);
		
		customerService.addCustomer(customerUserTo);
		
		Assert.assertNotNull(customerUserTo);
		
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	
	/**
	 * Test Delete set of customers
	 */
	@Test
	public void testDeleteSelectedCustomers() {
	
		String methodName = "deleteSelectedCustomers";
		LOGGER.info("Running Junit for : " + methodName);
		
		Mockito.when(customerDaoMock.deleteSelectedCustomers(Mockito.anySetOf(Long.class)))
		.thenReturn(1);
		
		Set<Long> set = new HashSet<Long>();
		set.add(1L);
		
		Integer cnt = customerService.deleteSelectedCustomers(set);
		
		Assert.assertNotNull(cnt);
		Assert.assertTrue(cnt > 0);
		
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	/**
	 * Test Get Customer Api Keys
	 */
	@Test
	public void testGetCustomerApiKeys() {
	
		String methodName = "getCustomerApiKeys";
		LOGGER.info("Running Junit for : " + methodName);
		
		List<CustomerApiKey> customerApiKeys = new ArrayList<CustomerApiKey>();
		customerApiKeys.add(buildMockCustomerApiKey());
		
		Mockito.when(customerDaoMock.getCustomerApiKeys(Mockito.anyLong()))
		.thenReturn(customerApiKeys);
		
		List<CustomerApiKeyTo> customerApiKeyTos = customerService.getCustomerApiKeys(1L);
		
		Assert.assertTrue(!Util.isNullOrEmptyCollection(customerApiKeyTos));
		
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	/**
	 * Test Get Customer All Api Limits
	 */
	@Test
	public void testGetCustomerApiAllLimits() {
	
		String methodName = "getCustomerApiAllLimits";
		LOGGER.info("Running Junit for : " + methodName);
		
		CustomerApiLimit apiLimits = buildMockCustomerApiLimit();
		
		Mockito.when(customerDaoMock.getCustomerApiAllLimits(Mockito.anyLong()))
		.thenReturn(apiLimits);
		
		CustomerApiLimitTo customerApiKeyTos = customerService.getCustomerApiAllLimits(1L);
		
		Assert.assertNotNull(customerApiKeyTos);
		
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	
	/**
	 * Test Get Customer Api Limits in time intervals
	 */
	@Test
	public void testGetCustomerApiLimitsSum() {
	
		String methodName = "getCustomerApiLimitsSum";
		LOGGER.info("Running Junit for : " + methodName);
		
		CustomerApiLimit apiLimits = buildMockCustomerApiLimit();
		
		Mockito.when(customerDaoMock.getCustomerApiLimits(Mockito.anyLong(), (Date)Mockito.anyObject(), (Date)Mockito.anyObject()))
		.thenReturn(apiLimits);
		
		CustomerApiLimitTo customerApiKeyTos = customerService.getCustomerApiLimitsSum(1L, Util.getFirstDateOfCurrentMonth(), Util.getLastDateOfCurrentMonth());
		
		Assert.assertNotNull(customerApiKeyTos);
		
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	
	/**
	 * Test Get Customer Current Month Api Limits
	 */
	@Test
	public void testGetCustomerCurrentMonthApiLimits() {
	
		String methodName = "getCustomerCurrentMonthApiLimits";
		LOGGER.info("Running Junit for : " + methodName);
		
		CustomerApiLimit apiLimits = buildMockCustomerApiLimit();
		
		Mockito.when(customerDaoMock.getCustomerApiLimitsInCurrentMonth(Mockito.anyLong()))
		.thenReturn(apiLimits);
		
		CustomerApiLimitTo customerApiKeyTos = customerService.getCustomerCurrentMonthApiLimits(1L);
		
		Assert.assertNotNull(customerApiKeyTos);
		
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	/**
	 * Test changePassword
	 */
	@Test
	public void testChangePassword() {
	
		String methodName = "changePassword";
		LOGGER.info("Running Junit for : " + methodName);
		
		CustomerUser customer = buildMockCustomer();
		
		Mockito.when(passwordValidatorMock.validatePassword(Mockito.anyString()))
		.thenReturn(true);
		
		Mockito.when(customerDaoMock.changePassword((CustomerUser)Mockito.anyObject()))
		.thenReturn(true);
		
		Boolean status = customerService.changePassword(customer);
		
		Assert.assertNotNull(status);
		Assert.assertTrue(status);
		
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	/**
	 * Test getCustomerUser By Id
	 */
	@Test
	public void testGetCustomerUser() {
	
		String methodName = "getCustomerUser";
		LOGGER.info("Running Junit for : " + methodName);
		
		CustomerUser customer = buildMockCustomer();
		
		Mockito.when(customerDaoMock.getCustomer(Mockito.anyLong()))
		.thenReturn(customer);
		
		CustomerUserTo customerUserTo = customerService.getCustomerUser(1L);
		
		Assert.assertNotNull(customerUserTo);
	
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	/**
	 *Test  getCustomerProductsInPagination
	 */
	@Test
	public void testGetCustomerProductsInPagination() {
	
		String methodName = "getCustomerProductsInPagination";
		LOGGER.info("Running Junit for : " + methodName);
		
		CustomerUser customer = buildMockCustomer();
		
		Mockito.when(customerDaoMock.getCustomer(Mockito.anyLong()))
		.thenReturn(customer);
		
		
		List<CustomerProduct> results = new ArrayList<CustomerProduct>();
		CustomerProduct customerProduct = buildMockCustomerProduct(false);
		results.add(customerProduct);
		
		PaginationResultsTo<CustomerProduct> customerProductsResults = new PaginationResultsTo<CustomerProduct>();
		customerProductsResults.setResults(results);
		customerProductsResults.setPageNumber(1);
		customerProductsResults.setPageSize(1);
		customerProductsResults.setTotalNumberOfPages(1);
		customerProductsResults.setTotalNumberOfResults(1L);
		
		Mockito.when(customerDaoMock.getCustomerProducts(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt()))
		.thenReturn(customerProductsResults);
		
		Product product = buildMockProduct();
		Mockito.when(productDaoMock.getProduct(Mockito.anyLong()))
		.thenReturn(product);
		
		List<ProductTrack> productTracks = new ArrayList<ProductTrack>();
		productTracks.add(buildMockProductTrack());
		Mockito.when(productDaoMock.getProductTracksBYCustProdsId(Mockito.anyLong()))
		.thenReturn(productTracks);
		
		Track track = builMockTrack();
		Mockito.when(tracksDaoMock.getTrack(Mockito.anyString()))
		.thenReturn(track);
		
		PaginationResultsTo<CustomerProductTo> customerProducts = customerService.getCustomerProductsInPagination(1L, 1, 10);
		
		Assert.assertNotNull(customerProducts);
		Assert.assertTrue(!Util.isNullOrEmptyCollection(customerProducts.getResults()));
		
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	
	/**
	 * Test save Api key
	 */
	@Test
	public void testSaveApiKey() {
	
		String methodName = "saveApiKey";
		LOGGER.info("Running Junit for : " + methodName);
		
		CustomerApiKey apiKey = buildMockCustomerApiKey();
		CustomerApiKeyTo apiKeyTo = buildMockCustomerApiKeyTo();
		Mockito.when(customerDaoMock.saveApiKey((CustomerApiKey)Mockito.anyObject()))
		.thenReturn(apiKey);
		
		CustomerApiKeyTo customerApiKeyTo = customerService.saveApiKey(apiKeyTo);
		
		Assert.assertNotNull(customerApiKeyTo);
	
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	/**
	 * Test delete Api key
	 */
	@Test
	public void testDeleteCustomerApiKeys() {
	
		String methodName = "deleteCustomerApiKeys";
		LOGGER.info("Running Junit for : " + methodName);
		
		Mockito.when(customerDaoMock.deleteCustomerApiKeys(Mockito.anyLong(), Mockito.anySetOf(Long.class)))
		.thenReturn(true);

		Set<Long> set = new HashSet<Long>();
		set.add(1L);
		
		Boolean status = customerService.deleteCustomerApiKeys(1L, set);
		
		Assert.assertNotNull(status);
		Assert.assertTrue(status);
	
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	/**
	 * Test editCustomerProductTracks
	 */
	@Test
	public void testEditCustomerProductTracks() {
	
		String methodName = "editCustomerProductTracks";
		LOGGER.info("Running Junit for : " + methodName);
		
		List<CustomerProduct> customerProducts = new ArrayList<CustomerProduct>();
		CustomerProduct customerProduct = buildMockCustomerProduct(false);
		customerProducts.add(customerProduct);
		
		List<ProductTrack> productTracks = new ArrayList<ProductTrack>();
		ProductTrack productTrack = buildMockProductTrack();
		productTracks.add(productTrack);
		
		
		Mockito.when(productDaoMock.getCustomerProducts(Mockito.anyLong(), Mockito.anyLong()))
		.thenReturn(customerProducts);
		
		Mockito.when(productDaoMock.deleteProductTracks(Mockito.anyLong()))
		.thenReturn(1);
		
		Mockito.when(productDaoMock.deleteCustomerProductsByCustomerId(Mockito.anyLong()))
		.thenReturn(1);
		
		Mockito.when(productDaoMock.getCustomerProductsByCustomerId(Mockito.anyLong()))
		.thenReturn(customerProducts);
		
		
		Mockito.when(productDaoMock.createProductTracks((ProductTrack)Mockito.anyObject()))
		.thenReturn(productTrack);

		
		Mockito.when(productDaoMock.createCustomerProduct((CustomerProduct)Mockito.anyObject()))
		.thenReturn(customerProduct);
		
		CustomerUserTo customerUserTo = buildMockCustomerUserTo(false);
		customerService.editCustomerProductTracks(customerUserTo);
		
		Assert.assertNotNull(customerUserTo);
		
		LOGGER.info("Completed running Junit for : " + methodName);
	}
	
	
	
	

}
