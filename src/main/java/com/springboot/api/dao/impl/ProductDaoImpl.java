package com.springboot.api.dao.impl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import com.springboot.api.dao.ProductDao;
import com.springboot.api.dao.repository.CustomerProductRepository;
import com.springboot.api.dao.repository.ProductRepository;
import com.springboot.api.dao.repository.ProductTracksRepository;
import com.springboot.api.domain.CustomerProduct;
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
@Repository
public class ProductDaoImpl implements ProductDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductDaoImpl.class);

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductTracksRepository productTracksRepository;
	
	@Autowired
	private CustomerProductRepository customerProductRepository;

	/**
	 * Creating new product
	 * @return Product
	 */
	@Override
//	@Transactional
	public Product createNewProduct(Product product) {
		
			if (null == product) {
				throw new IllegalArgumentException(Util.OBJECT_ERROR_MSG);
			}
			if (null == product.getLabel() || product.getLabel().isEmpty()) {
				throw new IllegalArgumentException(Util.PRODUCT_LABEL_ERROR_MSG);
			}
			if (null == product.getType() || product.getType().isEmpty()) {
				throw new IllegalArgumentException(Util.PRODUCT_TYPE_ERROR_MSG);
			}
			if (null == product.getLevel() || product.getLevel().isEmpty()) {
				throw new IllegalArgumentException(Util.PRODUCT_LEVEL_ERROR_MSG);
			}
			if (null == product.getCacheTime()) {
				throw new IllegalArgumentException(Util.PRODUCT_CACHETIME_ERROR_MSG);
			}
			LOGGER.info("Creating new procuct...");
			try{
				product = productRepository.save(product);
				return product;
			} catch (Exception e) {
			LOGGER.error("Error while cerating new product : " + product, e);
			return null;
		}
		
		
	}

	/**
	 * Retrieving all products 
	 * return list
	 */
	@Override
	public List<Product> getAllProducts() {
		List<Product> products = null;
		try {
			products = productRepository.findAll();
		} catch (Exception e) {
			LOGGER.error("Error while fetching all products ", e);
		}
		return products;
	}
	
	/**
	 * Fetches all products for page
	 * @param pageNum,
	 * @param pageSize,
	 * @param sortingCriteria
	 * @return - List Of Products 
	 */
	@Override
	public PaginationResultsTo<Product> getProducts(Integer pageNum,
			Integer pageSize, SortingCriteria sortingCriteria) {

		LOGGER.info("Fetching all products from DB for page number : " + pageNum);
		LOGGER.info("Page Size : " + pageSize);
		LOGGER.info("Sorting Criteria : " + sortingCriteria);
		
		PaginationResultsTo<Product> paginationResults = null;
		if(Util.isNull(pageNum) || Util.isNull(pageNum)) {
			throw new IllegalAccessError("Missing pageNum/pageSize method arguments");
		}
		try {
			
			Sort sort = null;
			if (sortingCriteria != null
					&& !Util.isEmptyString(sortingCriteria.getSortingField())
					&& !Util.isEmptyString(sortingCriteria.getSortOrder())) {

				if(!"ASC".equalsIgnoreCase(sortingCriteria
						.getSortOrder())
						&& !"DESC".equalsIgnoreCase(sortingCriteria
								.getSortOrder())) {
					LOGGER.warn("Invalid Sort Order. Sort Order Shold be ASC or DESC");
					throw new IllegalArgumentException("Invalid Sort Order. Sort Order Shold be ASC or DESC");
				}   

				Order o1 = new Order(Sort.Direction.valueOf(sortingCriteria
						.getSortOrder().toUpperCase()), sortingCriteria.getSortingField())
				.ignoreCase();
				Order o2 = null;
				if ("type".equalsIgnoreCase(sortingCriteria.getSortingField())
						|| "level".equalsIgnoreCase(sortingCriteria
								.getSortingField())
						|| "cacheTime".equalsIgnoreCase(sortingCriteria
								.getSortingField())) {
					o2 = new Order(Direction.ASC, "label").ignoreCase();
					sort = new Sort(new Order[] { o1, o2 });
				} else {
					sort = new Sort(new Order[] { o1 });
				}

			} else {
				Order orderByLabelAsc = new Order(Direction.ASC, "label").ignoreCase();
				sort = new Sort(new Order[]{orderByLabelAsc});
			}

			PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, sort);
			
			Page<Product> products = productRepository.findAll(pageRequest);
			if(!Util.isNull(products)) {
				paginationResults = new PaginationResultsTo<Product>();
				paginationResults.setPageNumber(pageNum);
				paginationResults.setPageSize(pageSize);
				paginationResults.setTotalNumberOfPages(products.getTotalPages());
				paginationResults.setTotalNumberOfResults(products.getTotalElements());
				paginationResults.setResults(products.getContent());
				products.getTotalPages();
			}
		} catch(Exception e){
			LOGGER.error("Error while retreiving all Products for Pagination --> page Number : " + pageNum + " , Page Size : " + pageSize, e);
		}

		return paginationResults;
	}
	
	/**
	 * Update product details in DB 
	 * @param product label
	 * @param type
	 * @param level
	 * @param cachetime
	 * @param productId
	 * 
	 * return boolean
	 */
	@Override
	public Boolean updateProduct(Product product) {
		LOGGER.info("updating product...");
		Integer updatedRecords = 0;
		
			if (null == product) {
				throw new IllegalArgumentException(Util.OBJECT_ERROR_MSG);
			}
			if(null == product.getId() || 0L > product.getId()) {
				throw new IllegalArgumentException(Util.PRODUCT_ID_ERROR_MSG);
			}
			if (null == product.getLabel() || product.getLabel().isEmpty()) {
				throw new IllegalArgumentException(Util.PRODUCT_LABEL_ERROR_MSG);
			}
			if (null == product.getType() || product.getType().isEmpty()) {
				throw new IllegalArgumentException(Util.PRODUCT_TYPE_ERROR_MSG);
			}
			if (null == product.getLevel() || product.getLevel().isEmpty()) {
				throw new IllegalArgumentException(Util.PRODUCT_LEVEL_ERROR_MSG);
			}
			if (null == product.getCacheTime()) {
				throw new IllegalArgumentException(Util.PRODUCT_CACHETIME_ERROR_MSG);
			}
			try {
				updatedRecords = productRepository.updateProduct(product.getId(), product.getLabel(), 
						product.getType(), product.getLevel(), product.getCacheTime());
			} catch (Exception e) {
				LOGGER.info("Product object : " + product);
				LOGGER.error("Error while updating product in Db ", e);
			}
		return updatedRecords > 0 ? true : false;
		
	}

	/**
	 * Get product by product id
	 * @param productId
	 * @return product 
	 */
	@Override
	public Product getProduct(Long productId) {
		LOGGER.info("Retriving Product by product id : " + productId);
		Product product = null;
		if(Util.isNull(productId)) {
			throw new IllegalArgumentException("To get Product object by product id, product id should not be null...");
		}
		try {
			product = productRepository.findOne(productId);
		} catch (Exception e) {
			LOGGER.error("Error while getting product object by product id : " + productId, e);
		}
		return product;
	}

	/**
	 * Delete product by product id
	 * @return Boolean
	 */
	@Override
	public Boolean deleteProduct(Long productId) {
		Boolean isDeleted = false;
		if(Util.isNull(productId)) {
			throw new IllegalArgumentException("To delete Product object by product id, product id should not be null...");
		}
		try {
			productRepository.delete(productId);
			isDeleted = true;
			LOGGER.info("Product has been deleted by product id : " + productId);
		} catch (Exception e) {
			LOGGER.error("While product is deleting .... with product Id : "+productId, e);
		}
		return isDeleted;
	}

	/**
	 * Deleted list of products by list of product ids
	 * @return 
	 */
	@Override
	public Integer deleteSelectedProducts(Set<Long> productIds) {
		LOGGER.info("Deleting procts ids : " + productIds);
		if(Util.isNullOrEmptyCollection(productIds)) {
			throw new IllegalArgumentException("To Delete list of products, product ids should not be null...");
		}
		Integer deletedProductsCnt = null;
		try{
			deletedProductsCnt = productRepository.deleteSelectedProducts(productIds);
		}catch (Exception e) {
			LOGGER.error("While selected products are deleting .... with product Ids : " + productIds, e);
		}
		return deletedProductsCnt;
	}

	/**
	 * Gets All tracks by customer_products id
	 * @return  List<ProductTrack>
	 */
	@Override
	public List<ProductTrack> getProductTracksBYCustProdsId(Long customerProductsId) {
		LOGGER.info("Getting all Tracks by customer_products id : " + customerProductsId);
		if(Util.isNull(customerProductsId)) {
			throw new IllegalArgumentException("To get all Tracks by customer_products id, customer_products id should not be null...");
		}
		return productTracksRepository.findByCustomerProductId(customerProductsId);
	}
	
	/**
	 * Assign Product to a customer
	 * @return CustomerProduct
	 */
	@Override
	public CustomerProduct createCustomerProduct(CustomerProduct customerProduct) {
		LOGGER.info("Assigning Product to a customer");
		if(!Util.isNull(customerProduct) && !Util.isNull(customerProduct.getCustomerId())) {
			customerProduct = customerProductRepository.save(customerProduct);
		} else {
			LOGGER.info("To assign a product to customer, customer id or CustomerProduct object should not be null...");
		}
		return customerProduct;
	}

	/**
	 * Assign Product tracks to a customer
	 * @return ProductTrack
	 */
	@Override
	public ProductTrack createProductTracks(ProductTrack productTrack) {
		LOGGER.info("Assigning Product Tracks to a customer");
		if(!Util.isNull(productTrack) && !Util.isNull(productTrack.getCustomerProductId()) && !Util.isNull(productTrack.getTrackId())) {
			productTrack = productTracksRepository.save(productTrack);
		} else {
			LOGGER.info("To assign a product tracks to customer, CustomerProduct id or track id or  productTrack object should not be null...");
		}
		return productTrack;
	}

	/**
	 * Deleting Customer Products by Customer Id 
	 * @return deleted Customer_Products records count
	 */
	@Override
	public Integer deleteCustomerProductsByCustomerId(Long customerId) {
		Integer deletedRecordCnt = null;
		LOGGER.info("Deleting Customer_Products for customer_id : " + customerId);
		if(!Util.isNull(customerId)) {
			deletedRecordCnt = customerProductRepository.deleteCustomerProductByCustomerId(customerId);
		}
		return deletedRecordCnt;
	}

	/**
	 * Getting Customer Products by Customer Id and Product Id
	 * @return List<CustomerProduct>
	 */
	@Override
	public List<CustomerProduct> getCustomerProducts(Long customerId,
			Long productId) {
		List<CustomerProduct> customerProducts = null;
		if(!Util.isNull(customerId) && !Util.isNull(productId)) {
			customerProducts = customerProductRepository.findByCustomerIdAndProductId(customerId, productId);
		}
		return customerProducts;
	}

	/**
	 * Getting Customer Products by Customer Id
	 * @return List<CustomerProduct>
	 */
	@Override
	public List<CustomerProduct> getCustomerProductsByCustomerId(Long customerId) {
		List<CustomerProduct> customerProducts = null;
		LOGGER.info("Getting Customer Products by customer_id : " + customerId);
		if (!Util.isNull(customerId)) {
			customerProducts = customerProductRepository
					.findByCustomerId(customerId);
		}
		return customerProducts;
	}

	/**
	 * Deleting Product_Tracks by CustomerProduct Id 
	 * @return deleted Product_Tracks records count
	 */
	@Override
	public Integer deleteProductTracks(Long customerProductId) {
		Integer deletedRecordCnt = null;
		LOGGER.info("Deleting Product Tracks by customer_Products_id : "
				+ customerProductId);
		if (!Util.isNull(customerProductId)) {
			deletedRecordCnt = productTracksRepository
					.deleteProductTracksByCustomerProductId(customerProductId);
			LOGGER.info("Deleted Product Tracks count : " + deletedRecordCnt);
		}
		return deletedRecordCnt;
	}

	/**
	 * Get the Product by Product type and product level
	 * @param productType
	 * @param productLevel
	 * @return Product obejct
	 */
	@Override
	public Product getProductByTypeAndLevel(String productType,
			String productLevel) {
		LOGGER.info("Getting Product by productType : " + productType
				+ ", productLevel : " + productLevel);
		Product product = null;
		if (!Util.isNull(productType) && !Util.isNull(productLevel)) {
			product = productRepository.findByTypeAndLevel(productType,
					productLevel);
		}
		return product;
	}
	
	@Override
	public Long getProductIdByTypeAndLevel(String productType,
			String productLevel) {
		LOGGER.info("Getting Product Id by productType : " + productType
				+ ", productLevel : " + productLevel);
		Long productId = null;
		if (!Util.isNull(productType) && !Util.isNull(productLevel)) {
			productId = customerProductRepository.getProductId(productType,
					productLevel);
		}
		return productId;
	}
	
	
	

}
