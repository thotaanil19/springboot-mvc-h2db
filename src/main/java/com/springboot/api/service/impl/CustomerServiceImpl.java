package com.springboot.api.service.impl;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.springboot.api.to.CustomerApiKeyTo;
import com.springboot.api.to.CustomerApiLimitTo;
import com.springboot.api.to.CustomerUserTo;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.ProductTo;
import com.springboot.api.to.ProductTrackTo;
import com.springboot.api.to.SortingCriteria;
import com.springboot.api.to.TrackTo;
import com.springboot.api.util.PasswordValidator;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private TracksDao tracksDao;
	
	@Autowired
	private PasswordValidator validator;
	
	/**
	 * Gets all Customers
	 * @return List<CustomerUser>
	 */
	@Override
	public List<CustomerUser> getAllCustomers() {
		LOGGER.info("Getting All Customers");
		List<CustomerUser> customersList = customerDao.getAllCustomers();
		for (CustomerUser customerUser : customersList) {
			customerUser.setPassword(null);
			customerUser.setPassword(null);
		}
		return customersList;
	}
	
	/**
	 * Gets all Customers for pagination based on sort criteria
	 * If Sort Criteria is null then sorts the Customers based on Customer Id in ASC order
	 * @param pageNum 
	 * @param pageSize
	 * @param sortCriteria
	 * @return List<CustomerUser>
	 */
	@Override
	public PaginationResultsTo<CustomerUserTo> getCustomers(Integer pageNum,
			Integer pageSize, SortingCriteria sortCriteria) {
		LOGGER.info("Getting All Customers");
		LOGGER.info("Page Number : " + pageNum);
		LOGGER.info("Page Size : " + pageSize);
		LOGGER.info("Sort Criteria : " + sortCriteria);
		PaginationResultsTo<CustomerUser> customersList = customerDao.getCustomers(pageNum, pageSize, sortCriteria);
		PaginationResultsTo<CustomerUserTo> customersListNew = new PaginationResultsTo<CustomerUserTo>();
		if(!Util.isNull(customersList)) {
			if(!Util.isNullOrEmptyCollection(customersList.getResults())) {
				LOGGER.debug("Do not send password field in response...");
				for (CustomerUser customerUser : customersList.getResults()) {
					customerUser.setPassword(null);
					customerUser.setPasswordStr(null);
				}
				BeanUtils.copyProperties(customersList, customersListNew, new String[]{"results"});
				LOGGER.info("Getting All Customer's api keys...");
				if(!Util.isNull(customersList) && !Util.isNullOrEmptyCollection(customersList.getResults())) {
					LOGGER.info("Found customer Users...");
					List<CustomerUserTo> results = new ArrayList<CustomerUserTo>();
					for (CustomerUser customerUser : customersList.getResults()) {
						if(!Util.isNull(customerUser)) {
							CustomerUserTo customerUserTo = new CustomerUserTo();
							BeanUtils.copyProperties(customerUser, customerUserTo);
							LOGGER.info("Getting api keys for customer id : " + customerUser.getId());
							List<CustomerApiKey> customerApiKeys = customerDao.getCustomerApiKeys(customerUser.getId());
							if(!Util.isNullOrEmptyCollection(customerApiKeys)) {
								List<CustomerApiKeyTo> customerApiKeyTos = new ArrayList<CustomerApiKeyTo>();
								for (CustomerApiKey customerApiKey : customerApiKeys) {
									CustomerApiKeyTo customerApiKeyTo = new CustomerApiKeyTo();
									BeanUtils.copyProperties(customerApiKey, customerApiKeyTo);
									customerApiKeyTos.add(customerApiKeyTo);
								}
								LOGGER.info("Adding Customer api keys list to customer object...");
								customerUserTo.setCustomerApiKeys(customerApiKeyTos);
							}
							results.add(customerUserTo);
						}
					}
					customersListNew.setResults(results);
				}
			}
		}
		return customersListNew;
	}

	/**
	 * Gets Customer all products
	 * @return List<CustomerUserTo>
	 */
	@Override
	public CustomerUserTo getCustomerProducts(Long customerId) {
		
		LOGGER.info("Getting All products and tracks for Customer Id : " + customerId);
		
		 CustomerUserTo customer = null;
		if(!Util.isNull(customerId)) {
			LOGGER.info("Getting Customer user...");
			CustomerUser customerUser = customerDao.getCustomer(customerId);
			if(!Util.isNull(customerUser)) {
				LOGGER.info("Found Customer user...");
				customer = new CustomerUserTo();
				BeanUtils.copyProperties(customerUser, customer, new String[]{"password","passwordStr"});
				LOGGER.info("Getting CustomerProducts for customer id : " + customerUser.getId());
				List<CustomerProduct> customerProducts = customerDao.getCustomerProducts(customerUser.getId());
				List<CustomerProductTo> customerProductTos = null;
				if(!Util.isNullOrEmptyCollection(customerProducts)) {
					LOGGER.info("Founded CustomerProducts count : " + customerProducts.size());
					customerProductTos = new ArrayList<CustomerProductTo>();
					for (CustomerProduct customerProduct : customerProducts) {
						CustomerProductTo customerProductTo = new CustomerProductTo();
						BeanUtils.copyProperties(customerProduct, customerProductTo);
						LOGGER.info("Getting Product by product id : " + customerProduct.getProductId());
						Product product = productDao.getProduct(customerProduct.getProductId());
						if(!Util.isNull(product)) {
							LOGGER.info("Found Product for product id : " + customerProduct.getProductId());
							ProductTo productTo = new ProductTo();
							BeanUtils.copyProperties(product, productTo);
							customerProductTo.setProduct(productTo);
							if(!Util.isTrue(customerProduct.getAllTracks())) {
								LOGGER.info("CustomerProduct has allTacls is false, so we need to get Tracks...");
								LOGGER.info("Getting Tracks for Product id : " + customerProduct.getId());
								List<ProductTrack> productTracks = productDao.getProductTracksBYCustProdsId(customerProduct.getId());
								List<ProductTrackTo> productTracksTos = null;
								if(!Util.isNullOrEmptyCollection(productTracks)) {
									productTracksTos = new ArrayList<ProductTrackTo>();
									for (ProductTrack productTrack : productTracks) {
										ProductTrackTo productTrackTo = new ProductTrackTo();
										LOGGER.info("Getting Track for Track id : " + productTrack.getTrackId());
										Track track = tracksDao.getTrack(productTrack.getTrackId());
										if(!Util.isNull(track)) {
											TrackTo trackTo = new TrackTo();
											BeanUtils.copyProperties(track, trackTo);
											productTrackTo.setTrack(trackTo);
											productTracksTos.add(productTrackTo);
										} else {
											LOGGER.info("No Track found for Track id : " + productTrack.getTrackId());
										}
									}
									customerProductTo.setProductTracks(productTracksTos);
								}
							} else {
								LOGGER.info("CustomerProduct has allTacls is True, so no need to get Tracks...");
							}
						} else {
							LOGGER.info("No Product found for product id : " + customerProduct.getProductId());
						}
						customerProductTos.add(customerProductTo);
					}
					customer.setCustomerProducts(customerProductTos);
				} else {
					LOGGER.info("No CustomerProducts found for customer id : " + customerUser.getId());
				}
			} else {
				LOGGER.info("No Customer User found for given customer id : " + customerId);
				throw new IllegalArgumentException("No Customer User found for given customer id : " + customerId);
			}
		}
		return customer;
	}
	
	/**
	 * Gets Customer product Tracks
	 * 
	 * @param customerId
	 * @param productId
	 * 
	 * @return CustomerProductTo
	 */
	@Override
	public CustomerProductTo getCustomerProductTracks(Long customerId, Long productId) {
		
		LOGGER.info("Getting Customer Product Tracks");
		LOGGER.info("Customer Id : " + customerId);
		LOGGER.info("Product Id : " + productId);
		
		CustomerProductTo customerProductTo = null;
		if(!Util.isNull(customerId)) {
			List<CustomerProduct> customerProducts = productDao.getCustomerProducts(customerId, productId);
				List<CustomerProductTo> customerProductTos = null;
				if(!Util.isNullOrEmptyCollection(customerProducts)) {
					LOGGER.info("Founded CustomerProducts count : " + customerProducts.size());
					customerProductTos = new ArrayList<CustomerProductTo>();
					CustomerProduct customerProduct = customerProducts.get(0);
					if (!Util.isNull(customerProduct)) {
						customerProductTo = new CustomerProductTo();
						BeanUtils.copyProperties(customerProduct, customerProductTo);
						LOGGER.info("Getting Product by product id : " + customerProduct.getProductId());
						Product product = productDao.getProduct(customerProduct.getProductId());
						if(!Util.isNull(product)) {
							LOGGER.info("Found Product for product id : " + customerProduct.getProductId());
							ProductTo productTo = new ProductTo();
							BeanUtils.copyProperties(product, productTo);
							customerProductTo.setProduct(productTo);
							if(!Util.isTrue(customerProduct.getAllTracks())) {
								LOGGER.info("CustomerProduct has allTacls is false, so we need to get Tracks...");
								LOGGER.info("Getting Tracks for Product id : " + customerProduct.getId());
								List<ProductTrack> productTracks = productDao.getProductTracksBYCustProdsId(customerProduct.getId());
								List<ProductTrackTo> productTracksTos = null;
								if(!Util.isNullOrEmptyCollection(productTracks)) {
									productTracksTos = new ArrayList<ProductTrackTo>();
									for (ProductTrack productTrack : productTracks) {
										ProductTrackTo productTrackTo = new ProductTrackTo();
										LOGGER.info("Getting Track for Track id : " + productTrack.getTrackId());
										Track track = tracksDao.getTrack(productTrack.getTrackId());
										if(!Util.isNull(track)) {
											TrackTo trackTo = new TrackTo();
											BeanUtils.copyProperties(track, trackTo);
											productTrackTo.setTrack(trackTo);
											productTracksTos.add(productTrackTo);
										} else {
											LOGGER.info("No Track found for Track id : " + productTrack.getTrackId());
										}
									}
									customerProductTo.setProductTracks(productTracksTos);
								}
							} else {
								LOGGER.info("CustomerProduct has allTacls is True, so no need to get Tracks...");
							}
						} else {
							LOGGER.info("No Product found for product id : " + customerProduct.getProductId());
						}
						customerProductTos.add(customerProductTo);
					}
				} else {
					LOGGER.info("No CustomerProducts found for ");
					LOGGER.info("Customer Id : " + customerId);
					LOGGER.info("Product Id : " + productId);
				}
		}
		return customerProductTo;
	}

	/**
	 * Edit the Customer details, Customer Products, Customer Product Tracks, Customer Api Keys, Customer Api Limits
	 * @param customer
	 */
	@Override
	@Transactional
	public void editCustomer(CustomerUserTo customer) {
		LOGGER.info("Editing customer...");
		if(Util.isNull(customer)) {
			LOGGER.warn("To Edit Customer, Customer object should not be null...");
			throw new IllegalArgumentException("To Edit Customer, customer object should not be null...");
		}
		LOGGER.info("Editing a customer...");
		customer = editCustomerDetails(customer);
		if(!Util.isNull(customer) && !Util.isNull(customer.getId())) {
			LOGGER.info("Customer Edited Successfully...");
			LOGGER.info("Editing Assiged Products to edited Customer...");
			editProducts(customer);
			LOGGER.info("Editing Assigned Api keys to edited Customer...");
			editApiKeys(customer);
		}
		LOGGER.info("Completed Editing customer...");
	}
	
	
	
	/**
	 * Reassigning api limits to a Customer
	 * @param customer
	 */
	@Override
	public Boolean editApiLimits(CustomerApiLimitTo apiLimitTo) {
		Boolean updateStatus = false;
		if(!Util.isNull(apiLimitTo)) {
			LOGGER.info("Reassigning api limits to customer : " + apiLimitTo.getCustomerId());
			LOGGER.info("From Date : " + apiLimitTo.getStartDateStr());
			LOGGER.info("To Date : " + apiLimitTo.getEndDateStr());

			Date startDate = Util.stringToDateConversion(apiLimitTo.getStartDateStr());
			Date endDate = Util.stringToDateConversion(apiLimitTo.getEndDateStr());

			if(!Util.isNull(startDate) && !Util.isNull(endDate)) {

				CustomerApiLimit limit = customerDao.getCustomerApiLimitsInCurrentMonth(apiLimitTo.getCustomerId());
				
				//Increasing the api limit count is only allowed
				if(!Util.isNull(limit) && limit.getCurrentAccessLimit() < apiLimitTo.getCurrentAccessLimit()) {
					limit.setCurrentAccessLimit(apiLimitTo.getCurrentAccessLimit());
					limit.setStartDate(startDate);
					limit.setEndDate(endDate);
					
					customerDao.addOrUpdateApiLimitsToCustomer(limit);
					updateStatus = true;
				} 
			}
		} else {
			LOGGER.info("To Update api limits to a Customer,  Customer api limits object should not be null...");
		}
		return updateStatus;

	}
	
	
	
	
	/**
	 * Reassign api keys to a Customer
	 * @param customer
	 */
	@Override
	public void editApiKeys(CustomerUserTo customer) {
		LOGGER.info("Reassigning api keys to customer...");
		if(!Util.isNull(customer)) { 
			if(!Util.isNullOrEmptyCollection(customer.getCustomerApiKeys())) {
				List<CustomerApiKeyTo> apiKeys = customer.getCustomerApiKeys();
				if(!Util.isNullOrEmptyCollection(apiKeys)) {
					for (CustomerApiKeyTo customerApiKeyTo : apiKeys) {
						if (!Util.isNull(customerApiKeyTo)) {
							
							LOGGER.info("Check same Api Key already exists for the customer id : " + customer.getId());
							
							CustomerApiKey customerApiKey = customerDao
									.getCustomerApiKeyByCustomerIdAndApiKey(customer.getId(), customerApiKeyTo
											.getApiKey());
							
							if(Util.isNull(customerApiKey)) {
								
								LOGGER.info("Same Api Key not exists for the customer id : " + customer.getId());
								LOGGER.debug("Inserting new api Key for Customer Id : " + customer.getId());
								
								customerApiKeyTo.setCustomerId(customer.getId());
								
								CustomerApiKey apiKey = new CustomerApiKey();
								
								BeanUtils.copyProperties(customerApiKeyTo, apiKey);
								
								apiKey = customerDao.addApiKeyToCustomer(apiKey);
								
								if(!Util.isNull(apiKey)) {
									customerApiKeyTo.setId(apiKey.getId());
								}
							} else {
								LOGGER.info("Same Api Key already exists for the customer id : " + customer.getId());
								if(!Util.isTrue(customerApiKey.getIsActive())) {
									LOGGER.info("But Api Key is not active");
									LOGGER.info("Updating is_active = true and disabled-at = null");
									customerApiKey.setIsActive(true);
									customerApiKey.setDisabledAt(null);
									customerDao.addApiKeyToCustomer(customerApiKey);
								}
							}
						}
					}
				}
			}
		} else {
			LOGGER.info("To Edit api keys for Customer,  Customer object should not be null...");
		}
	}
	
	/**
	 * Editing Assigned products to a Customer
	 * @param customer
	 */
	@Override
	public void editProducts(CustomerUserTo customer) {
		LOGGER.info("Edting customer products ...");
		if (!Util.isNull(customer) && !Util.isNull(customer.getId())) { 
			if(!Util.isNullOrEmptyCollection(customer.getCustomerProducts())) {
				List<CustomerProductTo> products = customer.getCustomerProducts();
				for (CustomerProductTo customerProductTo : products) {
					editCustomerProduct(customer.getId(), customerProductTo);
				}
			} else {
				LOGGER.info("No Products are Reassigned to a Customer in Edit customer, so removing existing Customers Products for Customer id : " + customer.getId());
				List<CustomerProduct> customerProducts = productDao.getCustomerProductsByCustomerId(customer.getId());
				if(!Util.isNullOrEmptyCollection(customerProducts)) {
					LOGGER.info("Found Customer_Products, for customer id : " + customer.getId());
					for (CustomerProduct customerProduct : customerProducts) {
						if(!Util.isNull(customerProduct)) {
							//If all tracks flag is false, then only we will have entries exits in Product_Tracks
							if(!Util.isTrue(customerProduct.getAllTracks())) {
								productDao.deleteProductTracks(customerProduct.getId());
							}
						}
					}
					LOGGER.info("Deleting Customer_Products, for customer id : " + customer.getId());
					productDao.deleteCustomerProductsByCustomerId(customer.getId());
				}
			}
		} else {
			LOGGER.info("To Edit Customer Products, Customer object or Customer Id should not be null");
		}
	}
	
	/**
	 * Edit Customer Product/Tracks 
	 * @param customerId
	 * @param customerProductTo
	 */
	@Override
	public void editCustomerProduct(Long customerId, CustomerProductTo customerProductTo) {

		if (!Util.isNull(customerProductTo)
				&& !Util.isNull(customerProductTo.getProductId())) {

			if (!Util.isNull(customerProductTo)
					&& !Util.isNull(customerProductTo.getProductId())) {
				
				LOGGER.info("Creating Customer Products to a customer...");
				LOGGER.info("Customer Id : " + customerId);
				
				LOGGER.info("Getting Customer Products  based on Customer id : "
						+ customerId
						+ "  Product id : "
						+ customerProductTo.getProductId());
				
				List<CustomerProduct> customerProducts = productDao
						.getCustomerProducts(customerId,
								customerProductTo.getProductId());
				
				if(!Util.isNullOrEmptyCollection(customerProducts)) {
					for (CustomerProduct customerProduct : customerProducts) {
						if(!Util.isNull(customerProduct)) {
							Long customerProductId = customerProduct.getId();
							//Deactivate product to a customer if product access is removed for the customer
							if(Util.isTrue(customerProductTo.getIsActive())) {
								customerProduct.setIsActive(true);
							} else {
								customerProduct.setIsActive(false);
							}
							//days back
							customerProduct.setDaysBack(customerProductTo.getDaysBack());
							if(Util.isTrue(customerProductTo.getAllTracks())) {
								customerProduct.setAllTracks(true);
								//Delete Product Tracks here
								LOGGER.info("all_tracks flag is true, so Remove old existing tracks for customer and Product(Customer_Products.id)");
								Integer deletedTrackCount = productDao.deleteProductTracks(customerProductId);
								LOGGER.debug("Deleted Track Count : " + deletedTrackCount);
							} else { 
								LOGGER.info("all_tracks flag is flase, so Reassign the tracks for customer and Product(Customer_Products.id)");
								//All Track flag is false, so Reassign Tracks again
								customerProduct.setAllTracks(false);
								//First Delete Existing Tracks
								LOGGER.debug("First Deleting all existing Tracks...");
								Integer deletedTrackCount = productDao.deleteProductTracks(customerProductId);
								LOGGER.debug("Deleted Tracks count : " + deletedTrackCount);
								//Reassign new Tracks again
								LOGGER.info("Reassigning the Tracks for Customer Products...");
								List<ProductTrackTo> productTracks = customerProductTo.getProductTracks();
								if(!Util.isNullOrEmptyCollection(productTracks)) {
									LOGGER.info("Reassigning Tracks to customer and product...");
									for (ProductTrackTo productTrackTo : productTracks) {
										if(!Util.isNull(productTrackTo)) {
											TrackTo track = productTrackTo.getTrack();
											if(!Util.isNull(track) && !Util.isNull(track.getId())) {
												LOGGER.info("Track id : " + track.getId());
												String trackId = track.getId();
												ProductTrack productTrack = new ProductTrack();
												productTrack.setCustomerProductId(customerProductId);
												productTrack.setTrackId(trackId);
												productTrack = productDao.createProductTracks(productTrack);
											}
										}
									}
								}
							}
						}
					}
				} else {
					//Product is new to Customer, So add this product in customer_products and product_tracks

					LOGGER.info("No Customer_Products exists for customer id : " + customerId);
					LOGGER.info("Assiging Products, Tracks to a customer id : " + customerId);
					
					customerProductTo.setCustomerId(customerId);
					CustomerProduct customerProduct = new CustomerProduct();
					BeanUtils.copyProperties(customerProductTo, customerProduct);
					LOGGER.info("creating Customer_Products to a customer...");
					customerProduct = productDao.createCustomerProduct(customerProduct);
					LOGGER.info("Completed creating Customer_Products to a customer...");
					if (!Util.isNull(customerProduct)) {
						customerProductTo.setId(customerProduct.getId());
						//If all tracks is false then assign particular tracks only
						LOGGER.info("Assign Tracks to a customer and product based on 'all_tracks' column...");
						if(!Util.isTrue(customerProductTo.getAllTracks())) {
							List<ProductTrackTo> productTracks = customerProductTo.getProductTracks();
							if(!Util.isNullOrEmptyCollection(productTracks)) {
								LOGGER.info("Assigning Tracks to customer and product...");
								for (ProductTrackTo productTrackTo : productTracks) {
									if(!Util.isNull(productTrackTo)) {
										TrackTo track = productTrackTo.getTrack();
										if(!Util.isNull(track) && !Util.isNull(track.getId())) {
											LOGGER.info("Track id : " + track.getId());
											String trackId = track.getId();
											Long customerProductId = customerProduct.getId();
											ProductTrack productTrack = new ProductTrack();
											productTrack.setCustomerProductId(customerProductId);
											productTrack.setTrackId(trackId);
											productTrack = productDao.createProductTracks(productTrack);
										}
									}
								}
							}
						} else {
							LOGGER.info("'all_tracks' flag is true, so no need to assign Tracks based on customer and product...");
						}
					}
				}
			}
		
		}
	
	}
	
	
	/**
	 * Edit Customer
	 * @param customer
	 * @return CustomerUserTo
	 */
	@Override
	public CustomerUserTo editCustomerDetails(CustomerUserTo customer) {
		if(Util.isNull(customer)) {
			LOGGER.error("To Edit Customer, Customer object should not be null...");
			throw new IllegalArgumentException("To Edit Customer, Customer object should not be null...");
		} 
		LOGGER.info("Getting Customer object by custimer id : " + customer.getId());
		CustomerUser customerUser = customerDao.getCustomer(customer.getId());
		if(!Util.isNull(customerUser) && !Util.isNull(customerUser.getId())) {
			LOGGER.info("Found Customer object...");
			//Email
			if(!Util.isEmptyString(customer.getEmail())) {
				customerUser.setEmail(customer.getEmail());
			}
			//Base Access Limit
			if(!Util.isNull(customer.getBaseAccessLimit())) {
				customerUser.setBaseAccessLimit(customer.getBaseAccessLimit());
			}
			//Reset Day of Month
			if(!Util.isNull(customer.getResetDayOfMonth())) {
				customerUser.setResetDayOfMonth(customer.getResetDayOfMonth());
			}
			//Customer Company
			if(!Util.isNull(customer.getCompanyName())) {
				customerUser.setCompanyName(customer.getCompanyName());
			}
			
			//Is Active
			if(!Util.isNull(customer.getIsActive())) {
				customerUser.setIsActive(customer.getIsActive());
			}
			customerUser = customerDao.createOrUpdateCustomer(customerUser);
		} else {
			LOGGER.info("Not Found Customer object for Customer id : " + customer.getId());
		}
		if(!Util.isNull(customerUser)) {
			LOGGER.info("Do not pass Password fileds in Response Json...");
			customer.setPassword(null);
			customer.setPasswordStr(null);
		}
		return customer;

	}
	
	
	@Override
	@Transactional
	public void addCustomer(CustomerUserTo customer) {
		LOGGER.info("Adding customer...");
		if(Util.isNull(customer)) {
			LOGGER.warn("To create Customer, Customer object should not be null...");
			throw new IllegalArgumentException("To Add Customer, customer object should not be null...");
		}
		LOGGER.info("Creating a customer...");
		customer = createCustomer(customer);
		if(!Util.isNull(customer) && !Util.isNull(customer.getId())) {
			LOGGER.info("Customer Created Successfully...");
			LOGGER.info("Assigning Products to created Customer...");
			createProducts(customer);
			LOGGER.info("Assigning Api keys to created Customer...");
			createApiKeys(customer);
		}
		LOGGER.info("Completed Adding customer...");
	}
	
	/**
	 * Create new Customer
	 * @param customer
	 * @return CustomerUserTo
	 */
	public CustomerUserTo createCustomer(CustomerUserTo customer) {
		if(Util.isNull(customer)) {
			LOGGER.error("To create Customer, Customer object should not be null...");
			throw new IllegalArgumentException("To create Customer, Customer object should not be null...");
		} else if(Util.isEmptyString(customer.getPasswordStr())) {
			LOGGER.error("To create Customer, Customer object password should not be null...");
			throw new IllegalArgumentException("To create Customer, Customer object password should not be null...");
		}
		String becryptedPwd = Util.getBCrptPassword(customer.getPasswordStr());
		if(!Util.isNull(becryptedPwd)) {
			customer.setPassword(becryptedPwd.getBytes(Charset.forName("UTF-8")));
		}
		CustomerUser customerUser = new CustomerUser();
		BeanUtils.copyProperties(customer, customerUser, new String[]{"customerProducts", "customerApiKeys"});
		customerUser = customerDao.createOrUpdateCustomer(customerUser);
		if(!Util.isNull(customerUser)) {
			customer.setId(customerUser.getId());
			customer.setPassword(null);
			customer.setPasswordStr(null);
		}
		return customer;

	}
	
	/**
	 * Assign products to a Customer
	 * @param customer
	 */
	private void createProducts(CustomerUserTo customer) {
		LOGGER.info("Assigning products to customer...");
		if (!Util.isNull(customer)
				&& !Util.isNullOrEmptyCollection(customer.getCustomerProducts())) {
			List<CustomerProductTo> products = customer.getCustomerProducts();
			for (CustomerProductTo customerProductTo : products) {
				if (!Util.isNull(customerProductTo)
						&& !Util.isNull(customerProductTo.getProductId())) {
					LOGGER.info("Creating Customer Products to a customer...");
					customerProductTo.setCustomerId(customer.getId());
					CustomerProduct customerProduct = new CustomerProduct();
					BeanUtils.copyProperties(customerProductTo, customerProduct);
					customerProduct = productDao.createCustomerProduct(customerProduct);
					LOGGER.info("Completed creating Customer Products to a customer...");
					if (!Util.isNull(customerProduct)) {
						customerProductTo.setId(customerProduct.getId());
						//If all tracks is false then assign particular tracks only
						LOGGER.info("Assign Tracks to a customer and product based on 'all_tracks' column...");
						if(!Util.isTrue(customerProductTo.getAllTracks())) {
							List<ProductTrackTo> productTracks = customerProductTo.getProductTracks();
							if(!Util.isNullOrEmptyCollection(productTracks)) {
								LOGGER.info("Assigning Tracks to customer and product...");
								for (ProductTrackTo productTrackTo : productTracks) {
									if(!Util.isNull(productTrackTo)) {
										TrackTo track = productTrackTo.getTrack();
										if(!Util.isNull(track) && !Util.isNull(track.getId())) {
											LOGGER.info("Track id : " + track.getId());
											String trackId = track.getId();
											Long customerProductId = customerProduct.getId();
											ProductTrack productTrack = new ProductTrack();
											productTrack.setCustomerProductId(customerProductId);
											productTrack.setTrackId(trackId);
											productTrack = productDao.createProductTracks(productTrack);
										}
									}
								}
							}
						} else {
							LOGGER.info("'all_tracks' flag is true, so no need to assign Tracks based on customer and product...");
						}
					}
				}
			}
		} else {
			LOGGER.info("To create Customer Products, Customer object should not be null...");
		}
	}
	
	/**
	 * Assign api keys to a Customer
	 * @param customer
	 */
	private void createApiKeys(CustomerUserTo customer) {
		LOGGER.info("Assigning api keys to customer...");
		if(!Util.isNull(customer) && !Util.isNullOrEmptyCollection(customer.getCustomerApiKeys())) {
			List<CustomerApiKeyTo> apiKeys = customer.getCustomerApiKeys();
			if(!Util.isNullOrEmptyCollection(apiKeys)) {
				for (CustomerApiKeyTo customerApiKeyTo : apiKeys) {
					if (!Util.isNull(customerApiKeyTo)) {
						customerApiKeyTo.setCustomerId(customer.getId());
						CustomerApiKey apiKey = new CustomerApiKey();
						BeanUtils.copyProperties(customerApiKeyTo, apiKey);
						apiKey = customerDao.addApiKeyToCustomer(apiKey);
						if(!Util.isNull(apiKey)) {
							customerApiKeyTo.setId(apiKey.getId());
						}
					}
				}
				
			}
		} else {
			LOGGER.info("To create api keys for Customer,  Customer/customer api keys object should not be null...");
		}
		
	}
	
	/**
	 * Deleting selected Customers 
	 * @param customerIds
	 * @return number of deleted customer records
	 */
	@Override
	@Transactional
	public Integer deleteSelectedCustomers(Set<Long> customerIds) {
		LOGGER.info("Deleting Customers ids : " + customerIds);
		Integer deletedCustomersCnt = 0;
		if (!Util.isNullOrEmptyCollection(customerIds)) {
			try {
				deletedCustomersCnt = customerDao
						.deleteSelectedCustomers(customerIds);
			} catch (Exception e) {
				LOGGER.error(
						"While executing deleting the selected Customers with customer ids : "
								+ customerIds, e);
			}
		}

		return deletedCustomersCnt;
	}

	/**
	 * Getting Customer Api Keys
	 * @param customerId
	 * @return List of Customer Api Keys
	 */
	@Override
	public List<CustomerApiKeyTo> getCustomerApiKeys(Long customerId) {
		List<CustomerApiKeyTo> customerApiKeys = null;
		LOGGER.info("Getting Customer Api Keys for Customer Id : " + customerId);
		if(!Util.isNull(customerId)) {
			List<CustomerApiKey> apiKeys = customerDao.getCustomerApiKeys(customerId);
			if(!Util.isNullOrEmptyCollection(apiKeys)) {
				customerApiKeys = new ArrayList<CustomerApiKeyTo>();
				for (CustomerApiKey apiKey : apiKeys) {
					if(!Util.isNull(apiKey)) {
						CustomerApiKeyTo apiKeyTo = new CustomerApiKeyTo();
						BeanUtils.copyProperties(apiKey, apiKeyTo);
						customerApiKeys.add(apiKeyTo);
					}
				}
			}
		}
		return customerApiKeys;
	}

	/**
	 * Getting Customer Api Limits for all time
	 * @param customerId
	 * @return List of Customer Api Keys
	 */
	@Override
	public CustomerApiLimitTo getCustomerApiAllLimits(Long customerId) {
		LOGGER.info("Getting Customer Api all time Limits for customer id : " + customerId);
		CustomerApiLimitTo customerApiLimit = null;
		if(!Util.isNull(customerId)) {
			CustomerApiLimit apiLimits = customerDao.getCustomerApiAllLimits(customerId);
					if(!Util.isNull(apiLimits)) {
				customerApiLimit = new CustomerApiLimitTo();
				BeanUtils.copyProperties(apiLimits, customerApiLimit);
			}
		}
		return customerApiLimit;
	}
	
	/**
	 * Getting Customer Api Limits Sum from start date to end date
	 * @param startDate
	 * @param endDate
	 * @param customerId
	 * @return Customer Api Limits count
	 */
	@Override
	public CustomerApiLimitTo getCustomerApiLimitsSum(Long customerId, Date startDate, Date endDate) {
	
		CustomerApiLimitTo customerApiLimit = null;
		
		LOGGER.info("Getting Customer Api Limits Sum for customer id : " + customerId);
		LOGGER.info("Start Date : " + startDate);
		LOGGER.info("End Date : " + endDate);
		
		if(!Util.isNull(customerId) && !Util.isNull(startDate) && !Util.isNull(endDate)) {
			CustomerApiLimit apiLimits = customerDao.getCustomerApiLimits(customerId, startDate, endDate);
					if(!Util.isNull(apiLimits)) {
				customerApiLimit = new CustomerApiLimitTo();
				BeanUtils.copyProperties(apiLimits, customerApiLimit);
			}
		}
		return customerApiLimit;
	}
	
	/**
	 * Getting Customer Api Limits In CurrentMonth
	 * @param customerId
	 * @return Customer Api Limits count
	 */
	@Override
	public CustomerApiLimitTo getCustomerCurrentMonthApiLimits(Long customerId) {
	
		CustomerApiLimitTo customerApiLimit = null;
		
		LOGGER.info("Getting Customer Api Limits In Current Month for customer id : " + customerId);
		
		if(!Util.isNull(customerId)) {
			CustomerApiLimit apiLimits = customerDao.getCustomerApiLimitsInCurrentMonth(customerId);
					if(!Util.isNull(apiLimits)) {
				customerApiLimit = new CustomerApiLimitTo();
				BeanUtils.copyProperties(apiLimits, customerApiLimit);
			}
		}
		return customerApiLimit;
	}
	
	/**
	 * Changing Customer password
	 * @param CustomerUser
	 * @return True is password is updated  Successfully otherwise false
	 */
	@Override
	@Transactional
	public Boolean changePassword(CustomerUser customer) {
		Boolean pwdUpdateStatus = null;
		if(!Util.isNull(customer) && !Util.isNull(customer.getId())) {
			LOGGER.info("Validating Password");
			Boolean isValidPwd = validator.validatePassword(customer.getPasswordStr());
			if(Util.isTrue(isValidPwd)) {
				String becryptedPwd = Util.getBCrptPassword(customer.getPasswordStr());
				customer.setPassword(becryptedPwd.getBytes(Charset.forName("UTF-8")));
				pwdUpdateStatus = customerDao.changePassword(customer);
				if(Util.isTrue(pwdUpdateStatus)) {
					LOGGER.info("Password Updated Successfully...");
				} else  {
					LOGGER.info("Password Updation is Un-Successfull...");
				}
			} else {
				throw new IllegalArgumentException("password validations failed");
			}
		} else {
			LOGGER.error("Customer User id is missing to update password...");
			throw new IllegalArgumentException("Customer User id is missing to update password...");
		}
		return pwdUpdateStatus;
	}
	
	/**
	 * Gets Customer Profile by customer id
	 * @return CustomerUser
	 */
	@Override
	public CustomerUserTo getCustomerUser(Long customerId) {
		LOGGER.info("Getting Customer for customer id : " + customerId);
		CustomerUserTo customerUserTo = null;
		if(!Util.isNull(customerId)) {
			CustomerUser customerUser = customerDao.getCustomer(customerId);
			if(!Util.isNull(customerUser)) {
				customerUserTo = new CustomerUserTo();
				BeanUtils.copyProperties(customerUser, customerUserTo, new String[]{"password", "passwordStr"});
			}
		}
		return customerUserTo;
	}

	/**
	 * Get the Customer Products in Pagination
	 * 
	 * @param customerId
	 * @param pageNum
	 * @param pageSize
	 * 
	 * Returns List of Customer Products - PaginationResultsTo<CustomerUserTo>
	 */
	@Override
	public PaginationResultsTo<CustomerProductTo> getCustomerProductsInPagination(
			Long customerId, Integer pageNum, Integer pageSize) {

		LOGGER.info("Getting All products and tracks for Customer Id : "
				+ customerId);

		PaginationResultsTo<CustomerProductTo> customerProductsResultsNew = null;

		if (!Util.isNull(customerId) && !Util.isNull(pageNum)
				&& !Util.isNull(pageSize)) {

			LOGGER.info("Getting Customer user...");
			
			CustomerUser customerUser = customerDao.getCustomer(customerId);
			
			if (!Util.isNull(customerUser)) {
				
				LOGGER.info("Found Customer user...");
				LOGGER.info("Getting CustomerProducts for customer id : "
						+ customerUser.getId());
				
				PaginationResultsTo<CustomerProduct> customerProductsResults = customerDao
						.getCustomerProducts(customerUser.getId(), pageNum,
								pageSize);

				customerProductsResultsNew = new PaginationResultsTo<CustomerProductTo>();

				List<CustomerProductTo> results = new ArrayList<CustomerProductTo>();

				if (!Util.isNull(customerProductsResults)
						&& !Util.isNullOrEmptyCollection(customerProductsResults
								.getResults())) {

					BeanUtils.copyProperties(customerProductsResults,
							customerProductsResultsNew, new String[]{"results"});

					List<CustomerProduct> customerProducts = customerProductsResults
							.getResults();

					LOGGER.info("Founded CustomerProducts count : "
							+ customerProducts.size());
					
					for (CustomerProduct customerProduct : customerProducts) {
						
						CustomerProductTo customerProductTo = new CustomerProductTo();
						BeanUtils.copyProperties(customerProduct,
								customerProductTo);
						
						LOGGER.info("Getting Product by product id : "
								+ customerProduct.getProductId());
						
						Product product = productDao.getProduct(customerProduct
								.getProductId());
						
						if (!Util.isNull(product)) {
							
							LOGGER.info("Found Product for product id : "
									+ customerProduct.getProductId());
							
							ProductTo productTo = new ProductTo();
							BeanUtils.copyProperties(product, productTo);
							customerProductTo.setProduct(productTo);
							
							if (!Util.isTrue(customerProduct.getAllTracks())) {
								
								LOGGER.info("CustomerProduct has allTacls is false, so we need to get Tracks...");
								LOGGER.info("Getting Tracks for Product id : "
										+ customerProduct.getId());
								
								List<ProductTrack> productTracks = productDao
										.getProductTracksBYCustProdsId(customerProduct
												.getId());
								
								List<ProductTrackTo> productTracksTos = null;
								
								if (!Util.isNullOrEmptyCollection(productTracks)) {
									
									productTracksTos = new ArrayList<ProductTrackTo>();
									
									for (ProductTrack productTrack : productTracks) {
										
										ProductTrackTo productTrackTo = new ProductTrackTo();
										
										LOGGER.info("Getting Track for Track id : "
												+ productTrack.getTrackId());
										
										Track track = tracksDao
												.getTrack(productTrack
														.getTrackId());
										
										if (!Util.isNull(track)) {
											TrackTo trackTo = new TrackTo();
											BeanUtils.copyProperties(track,trackTo);
											productTrackTo.setTrack(trackTo);
											productTracksTos.add(productTrackTo);
										} else {
											LOGGER.info("No Track found for Track id : "
													+ productTrack.getTrackId());
										}
									}
									customerProductTo.setProductTracks(productTracksTos);
								}
							} else {
								LOGGER.info("CustomerProduct has allTacls is True, so no need to get Tracks...");
							}
						} else {
							LOGGER.info("No Product found for product id : "
									+ customerProduct.getProductId());
						}
						results.add(customerProductTo);
					}
					customerProductsResultsNew.setResults(results);
				} else {
					LOGGER.info("No CustomerProducts found for customer id : "
							+ customerUser.getId());
				}
			} else {
				LOGGER.info("No Customer User found for given customer id : "
						+ customerId);
				throw new IllegalArgumentException(
						"No Customer User found for given customer id : "
								+ customerId);
			}
		} else {
			throw new IllegalArgumentException(
					"To get Customer Products customer Id/Page number/ Page Size method parameters should not be null...");
		}
		
		sortProductsByLabelInASC(customerProductsResultsNew);
		return customerProductsResultsNew;

	}
	
	/**
	 * Sort Customer Products In ASC ordetr by Label
	 * @param customerProductsResults
	 */
	private void sortProductsByLabelInASC (PaginationResultsTo<CustomerProductTo> customerProductsResults)  {
		if(customerProductsResults != null && !Util.isNullOrEmptyCollection(customerProductsResults.getResults())) {
			List<CustomerProductTo> customerProductTos = customerProductsResults.getResults();
			Collections.sort(customerProductTos, new Comparator<CustomerProductTo>() {
				@Override
				public int compare(CustomerProductTo o1, CustomerProductTo o2) {
					if(o1 != null && o2 != null && o1.getProduct() != null && o2.getProduct() != null) {
						return o1.getProduct().getLabel().compareToIgnoreCase(o2.getProduct().getLabel());
					}
					return 0;
				}
			});
		}
	}

	/**
	 * Saving Api Key For Customer
	 * @param apiKey 
	 * @return CustomerApiKeyTo - Saved api key object
	 */
	@Override
	@Transactional
	public CustomerApiKeyTo saveApiKey(CustomerApiKeyTo apiKey) {
		LOGGER.info("Saving api Key for Customer");
		if (!Util.isNull(apiKey) && !Util.isNull(apiKey.getCustomerId())
				&& !Util.isEmptyString(apiKey.getApiKey())) {
			LOGGER.info("Customer Id : " + apiKey.getCustomerId());
			CustomerApiKey apiKeyTemp = new CustomerApiKey();
			BeanUtils.copyProperties(apiKey, apiKeyTemp);
			apiKeyTemp = customerDao.saveApiKey(apiKeyTemp);
			if(!Util.isNull(apiKeyTemp)) {
				apiKey.setId(apiKeyTemp.getId());
			}
		} else {
			LOGGER.info("Missing CustomerApiKeyTo/customer id/api key in saving api key for Customer...");
			throw new IllegalArgumentException(
					"Invalid Argument CustomerApiKeyTo ...");
		}
		return apiKey;
	}

	/**
	 * Deleting Customer api Keys
	 * @param customerId
	 * @param apiKeyIds
	 * @return 
	 * 
	 */
	@Override
	@Transactional
	public Boolean deleteCustomerApiKeys(Long customerId, Set<Long> apiKeyIds) {
		
		Boolean deleteStatus = false;
		
		LOGGER.info("Deleting api Key for Customer : " + customerId);
		
		LOGGER.info("Api Key Ids : " + apiKeyIds);
		
		if (!Util.isNull(customerId)
				&& !Util.isNullOrEmptyCollection(apiKeyIds)) {
			
			deleteStatus = customerDao.deleteCustomerApiKeys(customerId, apiKeyIds);
			
		} else {
			LOGGER.info("Missing customer id in Deactivating Customer Api keys");
		}
		
		return deleteStatus;
	}
	
	/**
	 * Editing Assigned products to a Customer
	 * @param customer
	 */
	@Override
	@Transactional
	public void editCustomerProductTracks(CustomerUserTo customer) {
		LOGGER.info("Edting customer product Tracks ...");
		if (!Util.isNull(customer) && !Util.isNull(customer.getId())) { 
			if(!Util.isNullOrEmptyCollection(customer.getCustomerProducts())) {
				List<CustomerProductTo> products = customer.getCustomerProducts();
				for (CustomerProductTo customerProductTo : products) {
					editCustomerProduct(customer.getId(), customerProductTo);
				}
			} 
		} else {
			LOGGER.info("To Edit Customer Products, Customer object or Customer Id should not be null");
		}
	}

}
