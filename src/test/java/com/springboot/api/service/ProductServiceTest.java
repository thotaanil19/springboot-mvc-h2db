/**
 * 
 */
package com.springboot.api.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.springboot.api.AbstractTest;
import com.springboot.api.dao.ProductDao;
import com.springboot.api.dao.repository.ProductRepository;
import com.springboot.api.domain.Product;
import com.springboot.api.service.ProductService;
import com.springboot.api.service.impl.ProductServiceImpl;
import com.springboot.api.to.EquibaseDataSelesApiError;
import com.springboot.api.to.EquibaseDataSelesApiErrorCodes;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.SortingCriteria;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */

public class ProductServiceTest extends AbstractTest {

	private final static Logger LOGGER = LoggerFactory.getLogger(ProductServiceTest.class);

	@InjectMocks
	private ProductService productService;
	
	@Mock
	private ProductDao productDaoMock;

	

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	@Autowired
	private ProductRepository productRepository;

	

	@Before
	public void setUp() throws Exception {
		productService = new ProductServiceImpl();
		MockitoAnnotations.initMocks(this);
		getMockAuthentication();
	}
	
	
	@Test
	public void testValidateAddProductWithNullObject() {
		
		String methodName = "validateAddProduct";
		LOGGER.info("Running JUnit for : " + methodName);
		
		List<EquibaseDataSelesApiError> apiErrors = productService.validateAddProduct(null);
		
		Assert.assertNotNull(apiErrors);
		Assert.assertNotNull(apiErrors.get(0));
		Assert.assertTrue(apiErrors.get(0).getCode() == EquibaseDataSelesApiErrorCodes.MISSING_METHOD_ARGUMENTS);

		LOGGER.info("Done executing JUnit for : " + methodName);
	}
	
	@Test
	public void testValidateAddProductWithEmptyObject() {
		
		String methodName = "validateAddProduct";
		LOGGER.info("Running JUnit for : " + methodName);
		
		Product product = new Product();
		
		List<EquibaseDataSelesApiError> apiErrors = productService.validateAddProduct(product);
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(apiErrors));

		LOGGER.info("Done executing JUnit for : " + methodName);
	}
	
	@Test
	public void testValidateAddProductWithInvalidFields() {
		
		String methodName = "validateAddProduct";
		LOGGER.info("Running JUnit for : " + methodName);
		
		Product product = new Product();
		product.setCacheTime(-100);
		product.setLabel("  ");
		product.setLevel("  ");
		product.setType("  ");
		
		List<EquibaseDataSelesApiError> apiErrors = productService.validateAddProduct(product);
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(apiErrors));

		LOGGER.info("Done executing JUnit for : " + methodName);
	}
	
	
	@Test
	public void testValidateAddProductWithValidObject() {
		
		String methodName = "validateAddProduct";
		LOGGER.info("Running JUnit for : " + methodName);
		
		Product product = buildMockProduct();
		
		List<EquibaseDataSelesApiError> apiErrors = productService.validateAddProduct(product);
		
		Assert.assertTrue(Util.isNullOrEmptyCollection(apiErrors));

		LOGGER.info("Done executing JUnit for : " + methodName);
		
	}
	
	
	/***
	 * JUnit to add product
	 */
	@Test
	public void testAddProduct() {

		String methodName = "addProduct";
		LOGGER.info("Running JUnit for : " + methodName);

		Product product = buildMockProduct();

		Mockito.when(productDaoMock.createNewProduct(product)).thenReturn(product);

		Assert.assertNotNull(productService.addProduct(product));

		LOGGER.info("Done executing JUnit for : " + methodName);

	}
	
	/***
	 * JUnit to delete product
	 */
	@Test
	public void testDeleteProduct() {

		String methodName = "deleteProduct";
		LOGGER.info("Running JUnit for : " + methodName);


		Mockito.when(productDaoMock.deleteProduct(Mockito.anyLong())).thenReturn(true);

		Assert.assertNotNull(productService.deleteProduct(1L));

		LOGGER.info("Done executing JUnit for : " + methodName);

	}
	
	@Test
	public void testValidateUpdateProduct() {
		
		String methodName = "validateUpdateProduct";
		LOGGER.info("Running JUnit for : " + methodName);
		
		List<EquibaseDataSelesApiError> apiErrors = productService.validateUpdateProduct(null);
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(apiErrors));

		LOGGER.info("Done executing JUnit for : " + methodName);
	}
	
	@Test
	public void testValidateUpdateProductWithNullId() {
		
		String methodName = "validateUpdateProduct";
		LOGGER.info("Running JUnit for : " + methodName);
		
		Product product = buildMockProduct();
		product.setId(null);
		
		List<EquibaseDataSelesApiError> apiErrors = productService.validateUpdateProduct(product);
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(apiErrors));

		LOGGER.info("Done executing JUnit for : " + methodName);
	}
	
	@Test
	public void testValidateUpdateProductWithInvalidId() {
		
		String methodName = "validateUpdateProduct";
		LOGGER.info("Running JUnit for : " + methodName);
		
		Product product = buildMockProduct();
		product.setId(-100L);
			
		List<EquibaseDataSelesApiError> apiErrors = productService.validateUpdateProduct(product);
		
		Assert.assertFalse(Util.isNullOrEmptyCollection(apiErrors));

		LOGGER.info("Done executing JUnit for : " + methodName);
	}
	
	@Test
	public void testValidateUpdateProductWithValidProduct() {
		
		String methodName = "validateUpdateProduct";
		LOGGER.info("Running JUnit for : " + methodName);
		
		Product product = buildMockProduct();
			
		List<EquibaseDataSelesApiError> apiErrors = productService.validateUpdateProduct(product);
		
		Assert.assertTrue(Util.isNullOrEmptyCollection(apiErrors));

		LOGGER.info("Done executing JUnit for : " + methodName);
	}
	
	
	/***
	 * JUnit to Edit product
	 */
	@Test
	public void testUpdateProduct() {

		String methodName = "updateProduct";
		LOGGER.info("Running JUnit for : " + methodName);

		Product product = buildMockProduct();

		Mockito.when(productDaoMock.updateProduct(product)).thenReturn(true);

		Assert.assertTrue(productService.updateProduct(product));

		LOGGER.info("Done executing JUnit for : " + methodName);

	}
	
	
	/***
	 * JUnit to Get All Products
	 */
	@Test
	public void testGetAllProducts() {

		String methodName = "getAllProducts";
		LOGGER.info("Running JUnit for : " + methodName);

		List<Product> products = new ArrayList<Product>();
		Product product = buildMockProduct();
		products.add(product);

		Mockito.when(productDaoMock.getAllProducts()).thenReturn(products);

		Assert.assertFalse(Util.isNullOrEmptyCollection(productService.getAllProducts()));

		LOGGER.info("Done executing JUnit for : " + methodName);

	}
	
	/***
	 * JUnit to Get Products with page size/page number
	 */
	@Test
	public void testGetProductsWithPagenation() {

		String methodName = "getProducts";
		LOGGER.info("Running JUnit for : " + methodName);

		SortingCriteria sortingCriteria = buildMockSortingCriteria("company");

		PaginationResultsTo<Product> products = new PaginationResultsTo<Product>();
		
		List<Product> results = new ArrayList<Product>();
		results.add(buildMockProduct());
		
		products.setResults(results);

		Mockito.when(productDaoMock.getProducts(Mockito.anyInt(), Mockito.anyInt(), (SortingCriteria) Mockito.anyObject())).thenReturn(products);


		PaginationResultsTo<Product> products2 = productService.getProducts(1, 10, sortingCriteria);

		Assert.assertNotNull(products2);
		Assert.assertNotNull(products2.getResults());

		LOGGER.info("Done executing JUnit for : " + methodName);

	}
	
	/***
	 * JUnit to Get Product
	 */
	@Test
	public void testGetProduct() {
		
		String methodName = "getProduct";
		LOGGER.info("Running JUnit for : " + methodName);
		
		Product product = buildMockProduct();
		
		Mockito.when(productDaoMock.getProduct(Mockito.anyLong())).thenReturn(product);

		Assert.assertNotNull(productService.getProduct(1L));
		
		LOGGER.info("Done executing JUnit for : " + methodName);

	}
	
	
	/***
	 * JUnit to delete Product
	 */
	@Test
	public void testDeleteSelectedProductIds() {
		
		String methodName = "deleteSelectedProducts";
		LOGGER.info("Running JUnit for : " + methodName);
		
		Mockito.when(productDaoMock.deleteSelectedProducts(Mockito.anySetOf(Long.class))).thenReturn(10);
		
		
			Set<Long> productIds = new HashSet<Long>();
			productIds.add(1L);
			productIds.add(2L);
			productIds.add(3L);
			productIds.add(4L);
			
			Integer deletedRecords = productService.deleteSelectedProducts(productIds);
			
			Assert.assertNotNull(deletedRecords);
			
			LOGGER.info("Done executing JUnit for : " + methodName);
		
	}
	
		

}
