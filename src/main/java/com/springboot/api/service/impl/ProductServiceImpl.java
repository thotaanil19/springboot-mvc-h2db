package com.springboot.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.api.dao.ProductDao;
import com.springboot.api.domain.Product;
import com.springboot.api.service.ProductService;
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
@Service
public class ProductServiceImpl implements ProductService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductDao productDao;

	/**
	 * validating adding product details
	 * @Param product object
	 * @return List<TrackmasterError>
	 */
	@Override
	public List<EquibaseDataSelesApiError> validateAddProduct(Product product) {
		
		LOGGER.info("Validating Product Object in Add Product...");
		List<EquibaseDataSelesApiError> trackmasterErrors = new ArrayList<EquibaseDataSelesApiError>();
		EquibaseDataSelesApiErrorCodes trackmasterErrorCode = null;
		// Validate adminUser
		if (null == product) {
			trackmasterErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_METHOD_ARGUMENTS;
			EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(trackmasterErrorCode,
					trackmasterErrorCode.getId(),
					trackmasterErrorCode.getDescription(), "Missing ProductDetails");
			trackmasterErrors.add(error);
		} else {
			
			LOGGER.info("Validating Each field of Product Object in add Product...");
			trackmasterErrors = validateProductObject(product,trackmasterErrors,trackmasterErrorCode);
		}
		LOGGER.info("Return total {} trackmasterErrors",
				trackmasterErrors.size());
		
		return trackmasterErrors;
	}

	/**
	 *Adding new product
	 * @param product
	 * @return product 
	 */
	@Override
	@Transactional
	public Product addProduct(Product product) {
		LOGGER.info("Adding new Product in products table...");
		if(!Util.isNull(product)) {
			try {
				product = productDao.createNewProduct(product);
			} catch(Exception e) {
				LOGGER.error("Error while Adding new product in DB", e);
			}
		}
		return product;
	}

	/**
	 * Validating to Product object in update product
	 * @param product
	 * @return List<TrackmasterError>
	 */
	@Override
	public List<EquibaseDataSelesApiError> validateUpdateProduct(Product product) {
		
		LOGGER.info("Validating Product Object in Update Product...");
		
		List<EquibaseDataSelesApiError> trackmasterErrors = new ArrayList<EquibaseDataSelesApiError>();
		EquibaseDataSelesApiErrorCodes trackmasterErrorCode = null;
		
        if (Util.isNull(product)) {
        	trackmasterErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_METHOD_ARGUMENTS;
        	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(trackmasterErrorCode ,
        			trackmasterErrorCode.getId() ,
        			trackmasterErrorCode.getDescription() ,
                    "Missing Product");
        	trackmasterErrors.add(error);
        } else {
            Long productId = product.getId();

            // Validate product id
            if (Util.isNull(productId)) {
            	trackmasterErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FIELD_PRODUCT_ID;
            	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(trackmasterErrorCode
                        , trackmasterErrorCode.getId()
                        , trackmasterErrorCode.getDescription()
                        , "Missing ProductId");
            	trackmasterErrors.add(error);
            } else {
                if (productId < 0L) {
                	trackmasterErrorCode = EquibaseDataSelesApiErrorCodes.INVALID_PRODUCT_ID;
                	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(trackmasterErrorCode
                            , trackmasterErrorCode.getId()
                            , trackmasterErrorCode.getDescription()
                            , "Invalid ProductId");
                    trackmasterErrors.add(error);
                }
            }
            
            LOGGER.info("Validating Each field of Product Object in Update Product...");
            trackmasterErrors = validateProductObject(product,trackmasterErrors,trackmasterErrorCode);
        }
        LOGGER.info("Return total {} trackmasterErrors", trackmasterErrors.size());
        return trackmasterErrors;
	}

	/**
	 * Update the product in DB
	 * @param product
	 * @return Boolean
	 */
	@Override
	@Transactional
	public Boolean updateProduct(Product product) {
		LOGGER.info("Updating Product object in DB...");
		Boolean isUpdated = false;
		if(!Util.isNull(product)) {
			LOGGER.info("Product Id : " + product.getId());
			try {
				isUpdated = productDao.updateProduct(product);
			} catch (Exception e) {
				LOGGER.error("Error while executing the update product with product details : " + product.toString(), e);
			}
		}
		return isUpdated;
	}

	/**
	 * Delete the product by product id
	 * @param productId
	 * @return Boolean
	 */
	@Override
	public Boolean deleteProduct(Long productId) {
		LOGGER.info("Deleting product by id : " + productId);
		try {
			return productDao.deleteProduct(productId);
		} catch(Exception e){
			LOGGER.error("Error while executing the delete product with productId : "+productId, e);
			return false;
		}
		
	}
	
	/**
	 * 
	 * @param product
	 * @param trackmasterErrors
	 * @param trackmasterErrorCode
	 * @return
	 */
	private List<EquibaseDataSelesApiError> validateProductObject(
			Product product, List<EquibaseDataSelesApiError> trackmasterErrors,
			EquibaseDataSelesApiErrorCodes trackmasterErrorCode) {
		
		 LOGGER.info("Validating Each field of Product Object...");
		
		 Long productId = product.getId();
		String productLabel = product.getLabel();
        String type = product.getType();
        Integer cacheTime = product.getCacheTime();
        String level = product.getLevel();
        
		 // Validate product label
        if (Util.isNull(productLabel)) {
        	trackmasterErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FEILD_PRODUCT_LABEL;
        	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(trackmasterErrorCode
                    , trackmasterErrorCode.getId()
                    , trackmasterErrorCode.getDescription()
                    , "Missing ProductLabel");
            trackmasterErrors.add(error);
        } else {
            if (Util.isEmptyString(productLabel.trim())) {
            	trackmasterErrorCode = EquibaseDataSelesApiErrorCodes.INVALID_PRODUCT_LABEL;
            	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(trackmasterErrorCode
                        , trackmasterErrorCode.getId()
                        , trackmasterErrorCode.getDescription()
                        , "Invalid ProductLabel");
                trackmasterErrors.add(error);
            }
        }
        //Validate product type
        if (Util.isNull(type)) {
        	trackmasterErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FIELD_TYPE;
        	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(trackmasterErrorCode
                    , trackmasterErrorCode.getId()
                    , trackmasterErrorCode.getDescription()
                    , "Missing Product Type");
            trackmasterErrors.add(error);
        } else {
            if (Util.isEmptyString(type.trim())) {
            	trackmasterErrorCode = EquibaseDataSelesApiErrorCodes.INVALID_PRODUCT_TYPE;
            	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(trackmasterErrorCode
                        , trackmasterErrorCode.getId()
                        , trackmasterErrorCode.getDescription()
                        , "Invalid Product Type");
                trackmasterErrors.add(error);
            }
        }

        //Validate product cache time
        if (Util.isNull(cacheTime)) {
        	trackmasterErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FIELD_CACHE_TIME;
        	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(trackmasterErrorCode
                    , trackmasterErrorCode.getId()
                    , trackmasterErrorCode.getDescription()
                    , "Missing CacheTime");
            trackmasterErrors.add(error);
        } else {
            if (cacheTime < 0 || cacheTime > 32767) {
            	trackmasterErrorCode = EquibaseDataSelesApiErrorCodes.INVALID_CACHE_TIME;
            	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(trackmasterErrorCode
                        , trackmasterErrorCode.getId()
                        , trackmasterErrorCode.getDescription()
                        , "Invalid CacheTime Range. The range should be in 0 TO 32767.");
                trackmasterErrors.add(error);
            }
        }
        
        //Validate product level
        if (Util.isNull(level)) {
        	trackmasterErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FIELD_LEVEL;
        	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(trackmasterErrorCode
                    , trackmasterErrorCode.getId()
                    , trackmasterErrorCode.getDescription()
                    , "Missing LEVEL");
            trackmasterErrors.add(error);
        } else {
            if (Util.isEmptyString(level.trim())) {
            	trackmasterErrorCode = EquibaseDataSelesApiErrorCodes.INVALID_PRODUCT_LEVEL;
            	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(trackmasterErrorCode
                        , trackmasterErrorCode.getId()
                        , trackmasterErrorCode.getDescription()
                        , "Invalid Product Level");
                trackmasterErrors.add(error);
            }
        }
        
        //Validate product type and level are already exists
		if (!Util.isEmptyString(level) && !Util.isEmptyString(type)) {
			Product existingProduct = productDao.getProductByTypeAndLevel(type,
					level);
			//In adding new product or edit existing product,  check whether any other product with type and level is exists 
			if ((productId == null && !Util.isNull(existingProduct) && !Util
					.isNull(existingProduct.getId()))
					|| (productId != null && !Util.isNull(existingProduct)
							&& !Util.isNull(existingProduct.getId()) && existingProduct
							.getId().longValue() != productId.longValue())) {
				trackmasterErrorCode = EquibaseDataSelesApiErrorCodes.PRODUCT_ALREADY_EXISTS_WITH_TYPE_AND_LEVEL;
				EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(
						trackmasterErrorCode, trackmasterErrorCode.getId(),
						trackmasterErrorCode.getDescription(),
						"Product already exists with type and level");
				trackmasterErrors.add(error);
			}

		}
        
		return trackmasterErrors;
	}

	/**
	 * Fetches all products 
	 * @return List<Product>
	 */
	@Override
	public List<Product> getAllProducts() {
		LOGGER.info("Fetching all products from DB...");
		List<Product> products = null;
		try {
			products = productDao.getAllProducts();
		} catch(Exception e){
			LOGGER.error("Error while retreiving all products ", e);
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
		
		PaginationResultsTo<Product> products = null;
		try {
			products = productDao.getProducts(pageNum, pageSize, sortingCriteria);
		} catch(Exception e){
			LOGGER.error("Error while retreiving all products for page number : " + pageNum, e);
		}
		return products;
	}

	/**
	 * retrieving Product details with product id
	 * @param productId
	 * @return product
	 */
	@Override
	public Product getProduct(Long productId) {
		Product product = null;
		LOGGER.info("Retrieving product details by product id : " + productId);
		if(!Util.isNull(productId)) {
			try {
				product = productDao.getProduct(productId);
			} catch (Exception e) {
				LOGGER.error("Error while executing or retrieving the getProduct with ProductId : "+productId, e);
			}
		}
		return product;
	}

	/**
	 * Deleting selected products 
	 * @param productIds
	 * @return integer selected records
	 */
	@Override
	@Transactional
	public Integer deleteSelectedProducts(Set<Long> productIds) {
		LOGGER.info("Deleting products ids : " + productIds);
		Integer deletedProductsCnt = 0;
		if (!Util.isNullOrEmptyCollection(productIds)) {
			try {
				deletedProductsCnt = productDao
						.deleteSelectedProducts(productIds);
			} catch (Exception e) {
				LOGGER.error(
						"While executing deleting the selected products with ProductIds : "
								+ productIds, e);
			}
		}

		return deletedProductsCnt;
	}

}
