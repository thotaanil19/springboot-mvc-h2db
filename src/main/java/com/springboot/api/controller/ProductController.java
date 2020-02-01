package com.springboot.api.controller;


import static com.springboot.api.util.Util.CONTENT_TYPE;
import static com.springboot.api.util.Util.CONTENT_VALUE;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.api.domain.Product;
import com.springboot.api.service.ActionLoggingService;
import com.springboot.api.service.ProductService;
import com.springboot.api.to.EquibaseDataSelesApiError;
import com.springboot.api.to.EquibaseDataSelesApiErrorResource;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.SortingCriteria;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
@Controller
@RequestMapping("/product")
public class ProductController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ActionLoggingService actionLoggingService;

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(Model model) {

		return "product";

	}

	/**
	 * Retrieve All Products
	 */
	@RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Product>> getAllProducts(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOGGER.info("Getting All Products...");
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);
		
		List<Product> products = productService.getAllProducts();
		
		return new ResponseEntity<List<Product>>(products, responseHeaders,
				HttpStatus.OK);
	}
	
	/**
	 * Retrieve All Products from Products table
	 * @param pageNum,
	 * @param pageSize,
	 * @param sortingCriteria
	 * @return - List of Products
	 */
	@RequestMapping(value = "/getProducts/{pageNum}/{pageSize}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PaginationResultsTo<Product>> getProducts(
			@PathVariable("pageNum") Integer pageNum,
			@PathVariable("pageSize") Integer pageSize,
			@RequestBody SortingCriteria sortingCriteria) throws Exception {
		
		LOGGER.info("Getting Products for page number : " + pageNum);
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);
		
		PaginationResultsTo<Product> products = productService.getProducts(pageNum, pageSize, sortingCriteria);
		
		return new ResponseEntity<PaginationResultsTo<Product>>(products, responseHeaders,
				HttpStatus.OK);
	}

	/**
	 * Create New Product Details
	 * 
	 * @param product
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyAuthority('LEVEL_1')")
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addProduct(@RequestBody Product product, HttpServletRequest request) {
		
		LOGGER.info("Addding New Product...");
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);

		LOGGER.info("Validating Product object in add Product...");
		List<EquibaseDataSelesApiError> errorMessages = productService.validateAddProduct(product);

		if (!Util.isNullOrEmptyCollection(errorMessages)) {
			LOGGER.info("Validation is done and validation errors exists in add Product...");

			EquibaseDataSelesApiErrorResource errorResource = new EquibaseDataSelesApiErrorResource(
					errorMessages);

			return new ResponseEntity<EquibaseDataSelesApiErrorResource>(
					errorResource, responseHeaders, HttpStatus.BAD_REQUEST);

		} else {
			LOGGER.info("Validation is done and no validation errors...");

			product = productService.addProduct(product);
			
			//Log the Admin action
			Long adminId = Util.getLoggedUserId();
			LOGGER.info("Logging Admin Action in Creating New Product...");
			actionLoggingService.logAdminActions(adminId, Util.INTERNAL_CUSTOMER_ID, request);
			
			return new ResponseEntity<Product>(product, responseHeaders,
					HttpStatus.OK);

		}
	}

	/**
	 * Fetches Product details
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getProduct/{productId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getProduct(@PathVariable Long productId) {

		if (Util.isNull(productId)) {
			throw new IllegalArgumentException("To get product, Product Id should not be null...");
		}
		LOGGER.info("Getting Product Details for product id : " + productId);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(Util.CONTENT_TYPE, Util.CONTENT_VALUE);
		// Retrieve Product details by product id
		Product product = productService.getProduct(productId);
		return new ResponseEntity<Product>(product, responseHeaders,
				HttpStatus.OK);
	}

	/**
	 * Update Product
	 * 
	 * @param product
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyAuthority('LEVEL_1,LEVEL_2,LEVEL_3')")
	@RequestMapping(value = "/editProduct", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity updateProduct(@RequestBody Product product, HttpServletRequest request) {

		LOGGER.info("Updating Product...");

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(Util.CONTENT_TYPE, Util.CONTENT_VALUE);

		LOGGER.info("Validating Product object in edit Product...");

		List<EquibaseDataSelesApiError> errorMessages = productService
				.validateUpdateProduct(product);

		if (!Util.isNullOrEmptyCollection(errorMessages)) {
			LOGGER.info("Validation is done and validation errors exists in edit Product...");

			EquibaseDataSelesApiErrorResource errorResource = new EquibaseDataSelesApiErrorResource(
					errorMessages);

			return new ResponseEntity<EquibaseDataSelesApiErrorResource>(
					errorResource, responseHeaders, HttpStatus.BAD_REQUEST);

		} else {
			LOGGER.info("Validation is done and no validation errors in edit Product...");

			Boolean isUpdated = productService.updateProduct(product);
			if (Util.isTrue(isUpdated)) {
				LOGGER.info("Product updated successfully...");
				
				//Log the Admin action
				Long adminId = Util.getLoggedUserId();
				LOGGER.info("Logging Admin Action in Updating existing Product...");
				actionLoggingService.logAdminActions(adminId, Util.INTERNAL_CUSTOMER_ID, request);
				
			}
			return new ResponseEntity<Product>(product, responseHeaders,
					HttpStatus.OK);
		}
	}

	/**
	 * Delete Product details by product id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/deleteProduct/{productId}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity deleteProduct(@PathVariable Long productId, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		LOGGER.info("Deleting product by id : " + productId);
		
		if(Util.isNull(productId)) {
			throw new IllegalArgumentException("Missing Product Id in deleting product");
		}
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(Util.CONTENT_TYPE, Util.CONTENT_VALUE);

		Boolean isDeleted = productService.deleteProduct(productId);
		
		if(Util.isTrue(isDeleted)) {
			//Log the Admin action
			Long adminId = Util.getLoggedUserId();
			LOGGER.info("Logging Admin Action in Deleting Existing Product...");
			actionLoggingService.logAdminActions(adminId, Util.INTERNAL_CUSTOMER_ID, request);
		}

		return new ResponseEntity<Boolean>(isDeleted, responseHeaders, HttpStatus.OK);
	}

	/**
	 * Delete List of Products by product ids
	 * @param productIds
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/deleteProducts", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity deleteProducts(
			@RequestBody Set<Long> productIds, HttpServletRequest request) {
		LOGGER.info("Deleting products ids : " + productIds);
		
		if(Util.isNullOrEmptyCollection(productIds)) {
			throw new IllegalArgumentException("Missing Product Ids set in deleting products");
		}
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);
		Integer status = productService
				.deleteSelectedProducts(productIds);
		
		if(status != null && status > 0 ) {
			//Log the Admin action
			Long adminId = Util.getLoggedUserId();
			LOGGER.info("Logging Admin Action in Deleting Existed List of Products...");
			actionLoggingService.logAdminActions(adminId, Util.INTERNAL_CUSTOMER_ID, request);
		}
		
		return new ResponseEntity<Integer>(status, responseHeaders,
				HttpStatus.OK);
	}
}
