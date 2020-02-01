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
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.springboot.api.AbstractTest;
import com.springboot.api.dao.ProductDao;
import com.springboot.api.dao.repository.CustomerProductRepository;
import com.springboot.api.dao.repository.CustomerUserRepository;
import com.springboot.api.dao.repository.ProductRepository;
import com.springboot.api.dao.repository.ProductTracksRepository;
import com.springboot.api.dao.repository.TracksRepository;
import com.springboot.api.domain.CustomerProduct;
import com.springboot.api.domain.CustomerUser;
import com.springboot.api.domain.Product;
import com.springboot.api.domain.ProductTrack;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.SortingCriteria;
import com.springboot.api.util.Util;


/**
 * 
 * @author anilt
 *
 */
@Transactional
public class ProductDAOTest extends AbstractTest {
	
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
	
	private Product product;
	private CustomerUser customer;
	private ProductTrack productTrack;
	CustomerProduct customerProduct;
	
	@Before
	public void setUp() {
		customer = insertTestCustomerUser();
		product = insertTestProduct();
		customerProduct = insertTestCustomerProduct();
		productTrack = insertTestProductTrack();
	}
	
	@After
	public void tearDown() {
		if(productTrack.getId() != null) {
			productTrackRepository.delete(productTrack.getId());
		}
		if(customerProduct.getId() != null) {
			customerProductRepository.delete(customerProduct.getId());
		}
		if(customer.getId() != null) {
			customerRepository.delete(customer.getId());
		} if(product.getId() != null) {
			productRepository.delete(product.getId());
		}
	}
	
	
	
	/**
	 * @throws java.lang.Exception
	 */

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ProductDAOTest.class);
	
	@Autowired
	private ProductDao productDAO;
	
	/***
	 * JUnit to Get All Product
	 */
	@Test
	public void testGetAllProduct() {
		String junitMethodName = "testGetAllProduct";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			List<Product> products = productDAO.getAllProducts();
			Assert.assertNotNull(products);
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch (Exception ex) {
			String errMsg = "Error while executing the test case in "
					+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/***
	 * JUnit to Get Products with page size/page number
	 */
	@Test
	public void testGetProductsWithPagenation() {
		String junitMethodName = "testGetProductsWithPagenation";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			PaginationResultsTo<Product> products = productDAO.getProducts(1,10,null);
			Assert.assertNotNull(products);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch (Exception ex) {
			String errMsg = "Error while executing the test case in "
					+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	
	/***
	 * JUnit to Get Products with page size/page number
	 */
	@Test
	public void testGetProductsWithPagenationWithSortCriteria() {
		String junitMethodName = "testGetProductsWithPagenationWithSortCriteria";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			SortingCriteria sortingCriteria1 = buildMockSortingCriteria("label");

			PaginationResultsTo<Product> products1 = productDAO.getProducts(1,10,sortingCriteria1);
			Assert.assertNotNull(products1);
			Assert.assertFalse(Util.isNullOrEmptyCollection(products1.getResults()));

			SortingCriteria sortingCriteria2 = buildMockSortingCriteria("level");

			PaginationResultsTo<Product> products2 = productDAO.getProducts(1,10,sortingCriteria2);
			Assert.assertNotNull(products2);
			Assert.assertFalse(Util.isNullOrEmptyCollection(products2.getResults()));


			SortingCriteria sortingCriteria3 = buildMockSortingCriteria("cacheTime");

			PaginationResultsTo<Product> products3 = productDAO.getProducts(1,10,sortingCriteria3);
			Assert.assertNotNull(products3);
			Assert.assertFalse(Util.isNullOrEmptyCollection(products3.getResults()));
			
			SortingCriteria sortingCriteria4 = buildMockSortingCriteria("type");

			PaginationResultsTo<Product> products4 = productDAO.getProducts(1,10,sortingCriteria4);
			Assert.assertNotNull(products4);
			Assert.assertFalse(Util.isNullOrEmptyCollection(products4.getResults()));

			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch (Exception ex) {
			String errMsg = "Error while executing the test case in "
					+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/***
	 * Negative test case JUnit to Get Products with page size/page number
	 */
	
	@Test(expected=IllegalAccessError.class)
	public void testGetProductsWithNullValues() {
		String junitMethodName = "testGetProductsWithNullValues";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		
		productDAO.getProducts(null,null,null);
		LOGGER.info("Done executing JUnit for : " + junitMethodName);
		
	}
	
	
	/***
	 * JUnit to add product
	 */
	@Test
	public void testAddProduct() {
		String junitMethodName = "testAddProduct";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			Product product = new Product();
			product.setLabel("product1");
			product.setType("type4" + new Date().getTime());
			product.setLevel("level4" + new Date().getTime());
			product.setCacheTime(123);

			product = productRepository.save(product);
			Assert.assertNotNull(product);
			if(product != null && product.getId() !=  null) {
				productRepository.delete(product.getId());
			}
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch (Exception ex) {
			String errMsg = "Error while executing the test case in "
					+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/***
	 * Negative TestCase as productLabel null
	 * JUnit to add product
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testAddProductWithProductLabelNull() throws Exception {
		String junitMethodName = "testAddProductWithProductLabelNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		
			Product product = new Product();
			product.setLabel(null);
			product.setType("type" + new Date().getTime());
			product.setLevel("level" + new Date().getTime());
			product.setCacheTime(123);

			product = productDAO.createNewProduct(product);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
	}
	
	/***
	 * Negative TestCase as product type null
	 * JUnit to add product
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testAddProductWithTypeNull() {
		String junitMethodName = "testAddProductWithTypeNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
			Product product = new Product();
			product.setLabel("product1");
			product.setType(null);
			product.setLevel("level" + new Date().getTime());
			product.setCacheTime(123);

			product = productDAO.createNewProduct(product);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
	}
	
	/***
	 * Negative TestCase as productLevel null
	 * JUnit to add product
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testAddProductWithLevelNull() {
		String junitMethodName = "testAddProductWithLEvelNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
			Product product = new Product();
			product.setLabel("product1");
			product.setType("type" + new Date().getTime());
			product.setLevel(null);
			product.setCacheTime(123);

			product = productDAO.createNewProduct(product);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
	}
	
	/***
	 * Negative TestCase as productCacheTime null
	 * JUnit to add product
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testAddProductWithCacheTimeNull() {
		String junitMethodName = "testAddProductWithCacheTimeNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
			Product product = new Product();
			product.setLabel("product1");
			product.setType("type" + new Date().getTime());
			product.setLevel("level" + new Date().getTime());
			product.setCacheTime(null);

			product = productDAO.createNewProduct(product);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
	}
	
	/***
	 * JUnit to Get Product
	 */
	@Test
	public void testGetProduct() {
		String junitMethodName = "testGetProduct";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			Product product = new Product();
			product.setLabel("product1");
			product.setType("type1" + new Date().getTime());
			product.setLevel("level1" + new Date().getTime());
			product.setCacheTime(123);

			product = productRepository.save(product);
			Assert.assertNotNull(product);

			product = productDAO.getProduct(product.getId());
			Assert.assertNotNull(product);
			
			if(product != null && product.getId() != null) {
				productRepository.delete(product.getId());
			}
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch (Exception ex) {
			String errMsg = "Error while executing the test case in "
					+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/***
	 * Negative Test case JUnit to Get Product
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testGetProductWithProductIdNull() {
		String junitMethodName = "testGetProductWithProductIdNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
			Long productId = null;
			productDAO.getProduct(productId);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
	}
	
	/***
	 * JUnit to update product
	 */
	
	@Test
	public void testUpdateProduct() {
		String junitMethodName = "testUpdateProduct";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			
			Boolean isUpdated = productDAO.updateProduct(product);
			
			Assert.assertTrue(isUpdated);

			LOGGER.info("Done executing JUnit for : " + junitMethodName);

		} catch (Exception ex) {
			String errMsg = "Error while executing the test case in "
					+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/***
	 * Negative TestCase as productId null
	 * JUnit to add product
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testUpdateProductWithProductIdNull() {
		String junitMethodName = "testUpdateProductWithProductLabelNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
			Product product = new Product();
			product.setId(null);
			product.setLabel("label1");
			product.setType("type" + new Date().getTime());
			product.setLevel("level" + new Date().getTime());
			product.setCacheTime(123);

			productDAO.updateProduct(product);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
	}
	
	/***
	 * Negative TestCase as productLabel null
	 * JUnit to add product
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testUpdateProductWithProductLabelNull() {
		String junitMethodName = "testUpdateProductWithProductLabelNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
			Product product = new Product();
			product.setLabel(null);
			product.setType("type" + new Date().getTime());
			product.setLevel("level" + new Date().getTime());
			product.setCacheTime(123);

			productDAO.updateProduct(product);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
	}
	
	/***
	 * Negative TestCase as product type null
	 * JUnit to add product
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testUpdateProductWithTypeNull() {
		String junitMethodName = "testUpdateProductWithTypeNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
			Product product = new Product();
			product.setLabel("product1");
			product.setType(null);
			product.setLevel("level" + new Date().getTime());
			product.setCacheTime(123);

			productDAO.updateProduct(product);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
	}
	
	/***
	 * Negative TestCase as productLevel null
	 * JUnit to add product
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testUpdateProductWithLevelNull() {
		String junitMethodName = "testUpdateProductWithLevelNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
			Product product = new Product();
			product.setLabel("product1");
			product.setType("type" + new Date().getTime());
			product.setLevel(null);
			product.setCacheTime(123);

			productDAO.updateProduct(product);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
	}
	
	/***
	 * Negative TestCase as productCacheTime null
	 * JUnit to add product
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testUpdateProductWithCacheTimeNull() {
		String junitMethodName = "testUpdateProductWithCacheTimeNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
			Product product = new Product();
			product.setLabel("product1");
			product.setType("type" + new Date().getTime());
			product.setLevel("level" + new Date().getTime());
			product.setCacheTime(null);

			productDAO.updateProduct(product);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
	}
	
	
	/***
	 * JUnit to delete Product
	 */
	@Test
	public void testDeleteProduct() {
		String junitMethodName = "testDeleteProduct";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		Long id = null;
		try {
			Product product = new Product();
			product.setLabel("product1");
			product.setType("type7" + new Date().getTime());
			product.setLevel("level7" + new Date().getTime());
			product.setCacheTime(123);

			product = productRepository.save(product);
			
			Assert.assertNotNull(product);
			Assert.assertNotNull(product.getId());
			
			if(product != null && product.getId() !=  null) {
				id = product.getId();
				Boolean bool = productDAO.deleteProduct( product.getId());
				Assert.assertTrue(bool);
			}
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch (Exception ex) {
			String errMsg = "Error while executing the test case in "
					+ junitMethodName;
			LOGGER.error(id + "-- " + errMsg, ex);
			fail(errMsg);
		}
	}
	/***
	 * Negative Test case JUnit to delete Product
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testDeleteProductWithProductIdNull() {
		String junitMethodName = "testDeleteProductWithProductIdNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
			Long productId = null;
			productDAO.deleteProduct(productId);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
	}
	
	/***
	 * JUnit to delete Product
	 */
	@Test
	public void testDeleteSelectedProductIds() {
		String junitMethodName = "testDeleteSelectedProductIds";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			Product product = new Product();
			product.setLabel("product1");
			product.setType("type6" + new Date().getTime());
			product.setLevel("level6" + new Date().getTime());
			product.setCacheTime(20);
			productRepository.save(product);
			
			Set<Long> productIds = new HashSet<Long>();
			productIds.add(product.getId());
			
			Integer deletedRecords = productDAO.deleteSelectedProducts(productIds);
			Assert.assertNotNull(deletedRecords);
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch (Exception ex) {
			String errMsg = "Error while executing the test case in "
					+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/***
	 * Negative JUnit to delete Product
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testDeleteSelectedProductIdsWithEmpty() {
		String junitMethodName = "testDeleteSelectedProductIdsWithEmpty";
		LOGGER.info("Running JUnit for : " + junitMethodName);
			Set<Long> productIds = new HashSet<Long>();
			productDAO.deleteSelectedProducts(productIds);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
	}
	
	/***
	 * JUnit to getProductTracksBYCustProdsId
	 */
	@Test
	public void testgetProductTracksBYCustProdsId() {
		String junitMethodName = "testgetProductTracksBYCustProdsId";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try{
			List<ProductTrack> productTracks = productDAO.getProductTracksBYCustProdsId(customerProduct.getId());
			Assert.assertNotNull(productTracks);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		}catch(Exception e){
			String errMsg = "Error while executing the test case in "
					+ junitMethodName;
			LOGGER.error(errMsg, e);
			fail(errMsg);
		}

	}
	
	/***
	 * Negative Test case JUnit to getProductTracksBYCustProdsIdNull
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testgetProductTracksBYCustProdsIdNull() {
		String junitMethodName = "testgetProductTracksBYCustProdsIdNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		Long productId = null;
		productDAO.getProductTracksBYCustProdsId(productId);
		LOGGER.info("Done executing JUnit for : " + junitMethodName);
	}
	
	
	/***
	 * JUnit to getProductTracksBYCustProdsId
	 */
	@Test
	public void testCreateCustomerProduct() {
		String junitMethodName = "testCreateCustomerProduct";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try{
			CustomerProduct customerProduct = new CustomerProduct();
			customerProduct.setAllTracks(true);
			customerProduct.setCustomerId(customer.getId());
			customerProduct.setDaysBack(3);
			customerProduct.setIsActive(true);
			customerProduct.setProductId(product.getId());
			
			customerProduct = productDAO.createCustomerProduct(customerProduct);
			Assert.assertNotNull(customerProduct);
			if(customerProduct != null && customerProduct.getId() != null){
				customerProductRepository.delete(customerProduct.getId());
			}
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		}catch(Exception e){
			String errMsg = "Error while executing the test case in "
				+ junitMethodName;
			LOGGER.error(errMsg, e);
			fail(errMsg);
		}
			
	}
	
	/**
	 * JUnit to DeleteCustomerProductsByCustomerId
	 */
	@Test
	public void testDeleteCustomerProductsByCustomerId(){
		String junitMethodName = "testDeleteCustomerProductsByCustomerId";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try{
			Integer deletedRecordCnt = null;
			CustomerProduct customerProduct = new CustomerProduct();
			customerProduct.setAllTracks(true);
			customerProduct.setCustomerId(customer.getId());
			customerProduct.setDaysBack(3);
			customerProduct.setIsActive(true);
			customerProduct.setProductId(product.getId());
			
			customerProduct = productDAO.createCustomerProduct(customerProduct);
			Assert.assertNotNull(customerProduct);
			if(customerProduct != null && customerProduct.getId() != null){
				deletedRecordCnt = productDAO.deleteCustomerProductsByCustomerId(customerProduct.getCustomerId());
			}
			Assert.assertNotNull(deletedRecordCnt);
			
		}catch(Exception e){
			String errMsg = "Error while executing the test case in "
				+ junitMethodName;
			LOGGER.error(errMsg, e);
			fail(errMsg);
		}
	}
	
	/**
	 * Negative test case JUnit to DeleteCustomerProductsByCustomerId
	 */
	@Test
	public void testDeleteCustomerProductsByCustomerIdNull(){
		String junitMethodName = "testDeleteCustomerProductsByCustomerId";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		Integer response = productDAO.deleteCustomerProductsByCustomerId(null);
		Assert.assertNull(response);
			
	}
	
	/***
	 * JUnit to getCustomerProducts
	 */
	@Test
	public void testgetCustomerProducts() {
		String junitMethodName = "testgetCustomerProducts";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try{
			List<CustomerProduct> customerProducts = productDAO.getCustomerProducts
				(customerProduct.getCustomerId(),customerProduct.getProductId());
			Assert.assertNotNull(customerProducts);
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		}catch(Exception e){
			String errMsg = "Error while executing the test case in "
				+ junitMethodName;
			LOGGER.error(errMsg, e);
			fail(errMsg);
		}
			
	}
	
	/***
	 * JUnit to testGetCustomerProductsByCustomerId
	 */
	@Test
	public void testGetCustomerProductsByCustomerId() {
		String junitMethodName = "testgetCustomerProducts";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try{
			List<CustomerProduct> customerProducts = productDAO.getCustomerProductsByCustomerId
				(customerProduct.getCustomerId());
			Assert.assertNotNull(customerProducts);
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		}catch(Exception e){
			String errMsg = "Error while executing the test case in "
				+ junitMethodName;
			LOGGER.error(errMsg, e);
			fail(errMsg);
		}
	}
	
	/***
	 * JUnit to GetCustomerProductsByCustomerIdNull
	 */
	@Test
	public void testGetCustomerProductsByCustomerIdNull() {
		String junitMethodName = "testgetCustomerProducts";
		LOGGER.info("Running JUnit for : " + junitMethodName);
			//retrieve list
		List<CustomerProduct> customerProducts = productDAO.getCustomerProductsByCustomerId(null);
		Assert.assertNull(customerProducts);
	}
	
	/***
	 * JUnit to DeleteProductTracksCustomerIdNull
	 */
	@Test
	public void testDeleteProductTracksCustomerIdNull() {
		String junitMethodName = "testDeleteProductTracksCustomerIdNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		Integer deletedRecordCnt = productDAO.deleteProductTracks(null);
		Assert.assertNull(deletedRecordCnt);
		LOGGER.info("Done executing JUnit for : " + junitMethodName);
	}
	
	
	/***
	 * JUnit to getProductByTypeAndLevel
	 */
	@Test
	public void testGetProductByTypeAndLevel() {

		String junitMethodName = "getProductByTypeAndLevel";
		LOGGER.info("Running JUnit for : " + junitMethodName);

		Product product = new Product();
		product.setLabel("product1");
		product.setType("type2" + new Date().getTime());
		product.setLevel("level2" + new Date().getTime());
		product.setCacheTime(123);

		product = productRepository.save(product);
		Assert.assertNotNull(product);

		Product p = productDAO.getProductByTypeAndLevel(product.getType(), product.getLevel());

		Assert.assertNotNull(p);
		
		if(product != null && product.getId() !=  null) {
			productRepository.delete(product.getId());
		}

		LOGGER.info("Done executing JUnit for : " + junitMethodName);
	}
}