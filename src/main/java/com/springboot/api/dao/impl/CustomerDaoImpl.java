package com.springboot.api.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import com.springboot.api.dao.CustomerDao;
import com.springboot.api.dao.repository.CustomerApiKeysRepository;
import com.springboot.api.dao.repository.CustomerApiLimitsRepository;
import com.springboot.api.dao.repository.CustomerProductRepository;
import com.springboot.api.dao.repository.CustomerUserRepository;
import com.springboot.api.domain.CustomerApiKey;
import com.springboot.api.domain.CustomerApiLimit;
import com.springboot.api.domain.CustomerProduct;
import com.springboot.api.domain.CustomerUser;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.SortingCriteria;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
@Repository
public class CustomerDaoImpl implements CustomerDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDaoImpl.class);

	@Autowired
	private CustomerUserRepository customerRepository;
	
	@Autowired
	private CustomerProductRepository customerProductRepository;
	
	@Autowired
	private CustomerApiKeysRepository customerApiKeysRepository;
	
	@Autowired
	private CustomerApiLimitsRepository customerApiLimitsRepository;
	
	/**
	 * Gets all Customers
	 * @return List<CustomerUser>
	 */
	@Override
	public List<CustomerUser> getAllCustomers() {
		LOGGER.info("Getting All Customers");
		Sort sort = new Sort(Sort.Direction.ASC, "companyName","loginId");
		return customerRepository.findAll(sort);
	}
	
	/**
	 * Gets all Customers for pagination
	 * @return List of Customers
	 */
	@Override
	public PaginationResultsTo<CustomerUser> getCustomers(Integer pageNum,
			Integer pageSize, SortingCriteria sortingCriteria) {

		LOGGER.info("Fetching all Customers from DB ");
		LOGGER.info("Page Number : " + pageNum);
		LOGGER.info("Page Size : " + pageSize);
		LOGGER.info("Sorting Criteria : " + sortingCriteria);
		PaginationResultsTo<CustomerUser> paginationResults = null;
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
					throw new IllegalArgumentException("Invalid Sort Order. Sort Order Should be ASC or DESC");
				}

				Order o1 = new Order(Sort.Direction.valueOf(sortingCriteria
						.getSortOrder().toUpperCase()), sortingCriteria.getSortingField())
						.ignoreCase();
				Order o2 = null;
				if ("email".equalsIgnoreCase(sortingCriteria.getSortingField())
						|| "isActive".equalsIgnoreCase(sortingCriteria
								.getSortingField())
						|| "baseAccessLimit".equalsIgnoreCase(sortingCriteria
								.getSortingField())
						|| "resetDayOfMonth".equalsIgnoreCase(sortingCriteria
								.getSortingField())) {
					o2 = new Order(Direction.ASC, "companyName").ignoreCase();
					sort = new Sort(new Order[] { o1, o2 });
				} else {
					sort = new Sort(new Order[] { o1 });
				}

			} else {
				Order orderByCompanyName = new Order(Direction.ASC, "companyName").ignoreCase();
				sort = new Sort(new Order[] {orderByCompanyName});
			}
			
			PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, sort);
			
			Page<CustomerUser> customers = customerRepository.findAll(pageRequest);
			if(!Util.isNull(customers)) {
				paginationResults = new PaginationResultsTo<CustomerUser>();
				paginationResults.setPageNumber(pageNum);
				paginationResults.setPageSize(pageSize);
				paginationResults.setTotalNumberOfPages(customers.getTotalPages());
				paginationResults.setTotalNumberOfResults(customers.getTotalElements());
				paginationResults.setResults(customers.getContent());
			}
		} catch(Exception e){
			LOGGER.error("Error while retreiving all Customers for Pagination -- page Number : " + pageNum + " , Page Size : " + pageSize, e);
		}

		return paginationResults;
	
	}

	/**
	 * Gets Customer all products in pagination
	 * @param customerId
	 * @param pageNum
	 * @param pageSize
	 * @return List<CustomerProduct>
	 */
	@Override
	public PaginationResultsTo<CustomerProduct> getCustomerProducts(
			Long customerId, Integer pageNum, Integer pageSize) {
		
		LOGGER.info("Getting all products in pagination for Customer id : " + customerId);
		
		LOGGER.info("Page Number : " + pageNum);
		LOGGER.info("Page Size : " + pageSize);
		
		if(Util.isNull(pageNum) || Util.isNull(pageNum)) {
			throw new IllegalAccessError("Missing pageNum/pageSize method arguments");
		}
		
		if(Util.isNull(customerId)) {
			LOGGER.error("To get Customer all products, customer id should not be null...");
			throw new IllegalArgumentException("To get Customer all products, customer id should not be null...");
		} 

		PaginationResultsTo<CustomerProduct> paginationResults = null;
		
		try {
			
			Sort sort = new Sort(Direction.DESC, "id");
			
			Pageable pageable = new PageRequest(pageNum - 1, pageSize, sort);
			
			Page<CustomerProduct> customers = customerProductRepository.findByCustomerId(customerId, pageable);
			
			if(!Util.isNull(customers)) {
				paginationResults = new PaginationResultsTo<CustomerProduct>();
				paginationResults.setPageNumber(pageNum);
				paginationResults.setPageSize(pageSize);
				paginationResults.setTotalNumberOfPages(customers.getTotalPages());
				paginationResults.setTotalNumberOfResults(customers.getTotalElements());
				paginationResults.setResults(customers.getContent());
			}
		} catch(Exception e){
			LOGGER.error("Error while retreiving all Products for Customer in Pagination -- page Number : " + pageNum + " , Page Size : " + pageSize, e);
		}
		
		return paginationResults;
	}
	
	
	/**
	 * Gets Customer by id
	 * @param customerId
	 * @return CustomerUser
	 */
	@Override
	public CustomerUser getCustomer(Long customerId) {
		LOGGER.info("Getting Customer by customer id : " + customerId);
		if(Util.isNull(customerId)) {
			LOGGER.error("To get Customer object, customer id should not be null...");
			throw new IllegalArgumentException("To get Customer object, customer id should not be null...");
		} 
		return customerRepository.findOne(customerId);
	}
	
	/**
	 * Gets Customer by Login Id
	 * @param LoginId
	 * @return CustomerUser
	 */
	@Override
	public CustomerUser getCustomerByLoginId(String LoginId) {
		LOGGER.info("Getting Customer by Login id : " + LoginId);
		if(Util.isNull(LoginId)) {
			LOGGER.error("To get Customer object, customer login id should not be null...");
			throw new IllegalArgumentException("To get Customer object, customer login id should not be null...");
		} 
		return customerRepository.findByLoginId(LoginId);
	}

	/**
	 * Gets Customer all products
	 * @param customerId
	 * @return List<CustomerProduct>
	 */
	@Override
	public List<CustomerProduct> getCustomerProducts(Long customerId) {
		LOGGER.info("Getting all products for Customer id : " + customerId);
		if(Util.isNull(customerId)) {
			LOGGER.error("To get Customer all products, customer id should not be null...");
			throw new IllegalArgumentException("To get Customer all products, customer id should not be null...");
		} 
		return customerProductRepository.findByCustomerId(customerId);
	}

	/**
	 * Gets customer all api keys by customer id based on sorting Criteria
	 * @param customerId
	 * @return List of customer api keys
	 */
	@Override
	public List<CustomerApiKey> getCustomerApiKeys(Long customerId) {
		LOGGER.info("Getting customer all api keys for Customer id : " + customerId);
		if(Util.isNull(customerId)) {
			LOGGER.error("To get Customer all api keys, customer id should not be null...");
			throw new IllegalArgumentException("To get Customer all api keys, customer id should not be null...");
		} 
		List<CustomerApiKey> apiKeys = null;
		try {
			Order o1 = new Order(Sort.Direction.DESC, "isActive");
			Order o2 = new Order(Sort.Direction.DESC, "id");
			Sort sort = new Sort(new Order[]{o1,o2});

			apiKeys = customerApiKeysRepository.findByCustomerId(customerId, sort);
		} catch(Exception e) {
			LOGGER.error("Error while getting Customer : "+ customerId +"  Api Keys from DB : ", e);
		}

		return apiKeys;
	}
	
	/**
	 * Creates new Customer
	 * @param CustomerUser
	 * @return CustomerUser
	 */
	@Override
	public CustomerUser createOrUpdateCustomer(CustomerUser customer) {
		LOGGER.info("Createting/Updating a Customer..." + customer);
		if(Util.isNull(customer)) {
			LOGGER.error("To Create/Update Customer , customer object should not be null...");
			throw new IllegalArgumentException("To Create/Update Customer , customer object should not be null...");
		} 
		customer = customerRepository.save(customer);
		return customer;
	}

	/**
	 * Assign Api key to a customer
	 * @return CustomerProduct
	 */
	@Override
	public CustomerApiKey addApiKeyToCustomer(CustomerApiKey apiKey) {
		LOGGER.info("Assigning Product to a customer");
		if(!Util.isNull(apiKey) && !Util.isNull(apiKey.getCustomerId())) {
			apiKey = customerApiKeysRepository.save(apiKey);
		} else {
			LOGGER.info("To assign a api key to customer, customer id or CustomerApiKey object should not be null...");
		}
		return apiKey;
	}

	/**
	 * Assign Api limits to a customer
	 * @return CustomerProduct
	 */
	@Override
	public CustomerApiLimit addOrUpdateApiLimitsToCustomer(CustomerApiLimit apiLimits) {
		LOGGER.info("Assigning/Updating limits to a customer");
		if(!Util.isNull(apiLimits) && !Util.isNull(apiLimits.getCustomerId())) {
			apiLimits = customerApiLimitsRepository.save(apiLimits);
		} else {
			LOGGER.info("To assign/update a api limits to customer, customer id or CustomerApiKey object should not be null...");
		}
		return apiLimits;
	}

	/**
	 * Deleting selected Customers 
	 * @param customerIds
	 * @return number of deleted customer records
	 */
	@Override
	public Integer deleteSelectedCustomers(Set<Long> customerIds) {
		Integer deletedCustomerCnt = null;
		LOGGER.info("Deleting Customer ids : " + customerIds);
		if(Util.isNullOrEmptyCollection(customerIds)) {
			throw new IllegalArgumentException("To Delete list of customers, customer ids should not be null...");
		}
		try{
			deletedCustomerCnt = customerRepository.deleteSelectedCustomers(customerIds);
		}catch (Exception e) {
			LOGGER.error("Error while deleting Customers with ids : " + customerIds, e);
		}
		return deletedCustomerCnt;
	}

	/**
	 * Get CustomerApiKey by customer id and api key 
	 * @param customerId
	 * @param apiKey
	 * @return CustomerApiKey
	 */
	@Override
	public CustomerApiKey getCustomerApiKeyByCustomerIdAndApiKey(Long customerId, String apiKey) {
		
		LOGGER.info("Getting customer api key Info for Customer id, apiKey");
		LOGGER.info("CustomerId : " + customerId);
		LOGGER.info("Api Key : " + apiKey);

		CustomerApiKey customerApikey = null; 
		if(!Util.isNull(customerId) && !Util.isEmptyString(apiKey)) {
			customerApikey = customerApiKeysRepository.findCustomerApiKeyByCustomerIdAndApiKey(customerId, apiKey);
		}
		return customerApikey;
	}
	
	/**
	 * Get CustomerApiKey by key 
	 * @param apiKey
	 * @return CustomerApiKey
	 */
	@Override
	public CustomerApiKey getCustomerApiKeyByApiKey(String apiKey) {
		
		LOGGER.info("Getting customer api key Info by apiKey");
		LOGGER.info("Api Key : " + apiKey);

		CustomerApiKey customerApikey = null;
		
		if(!Util.isEmptyString(apiKey)) {
			customerApikey = customerApiKeysRepository.findCustomerApiKeyByApiKey(apiKey);
		}
		return customerApikey;
	}

	/**
	 * Get Customer Api limits in Current Month
	 * @param customerId
	 * @return CustomerApiLimit
	 */
	@Override
	public CustomerApiLimit getCustomerApiLimits(Long customerId) {
		
		LOGGER.info("Getting customer api Limits for current Month for Customer Id : " + customerId);
		
		List<CustomerApiLimit> customerApiLimits = null;
		if(!Util.isNull(customerId)) {
			customerApiLimits = customerApiLimitsRepository.findCustomerApiLimitByCustomerIdOrderByStartDateDesc(customerId);
			if(!Util.isNullOrEmptyCollection(customerApiLimits)) {
				return customerApiLimits.get(0);
			}
		}
		return null;
	}
	
	/**
	 * Get Customer Api All Time limits 
	 * @param customerId
	 * @return CustomerApiLimit
	 */
	@Override
	public CustomerApiLimit getCustomerApiAllLimits(Long customerId) {
		
		LOGGER.info("Getting customer api Limits in All Time for Customer Id : " + customerId);

		CustomerApiLimit customerApiLimit = null;

		if (!Util.isNull(customerId)) {
			
			LOGGER.info("Getting customer current Access Limit...");
			
			Long currentAccessLimit = customerApiLimitsRepository
					.findCustomerApiAllAccessLimit(customerId);

			LOGGER.info("Getting customer current Access Count...");
			
			Long currentAccessCount = customerApiLimitsRepository
					.findCustomerApiAllAccessCount(customerId);
			
			customerApiLimit = new CustomerApiLimit();

			customerApiLimit.setStartDate(null);
			customerApiLimit.setEndDate(null);
			customerApiLimit.setCustomerId(customerId);
			customerApiLimit.setCurrentAccessCount(currentAccessCount);
			customerApiLimit.setCurrentAccessLimit(currentAccessLimit);

		}
		
		return customerApiLimit;
	}
	
	/**
	 * Get Customer Api limits 
	 * @param customerId
	 * @param startDate
	 * @param endDate
	 * @return CustomerApiLimit
	 */
	@Override
	public CustomerApiLimit getCustomerApiLimits(Long customerId,
			Date startDate, Date endDate) {
		
		LOGGER.info("Getting customer api Limits for a customer within start date and end date");
		LOGGER.info("CustomerId : " + customerId);
		LOGGER.info("Start Date : " + startDate);
		LOGGER.info("End Date : " + endDate);

		CustomerApiLimit customerApiLimit = null;

		if (!Util.isNull(customerId) && !Util.isNull(startDate)
				&& !Util.isNull(endDate)) {

			LOGGER.info("Getting customer current Access Limit within time intervals...");
			
			Long currentAccessLimit = customerApiLimitsRepository
					.findCustomerCurrentAccessLimit(customerId, startDate,
							endDate);
			
			LOGGER.info("Getting customer current Access Count  within time intervals...");

			Long currentAccessCount = customerApiLimitsRepository
					.findCustomerCurrentAccessCount(customerId, startDate,
							endDate);
			
			customerApiLimit = new CustomerApiLimit();

			customerApiLimit.setStartDate(startDate);
			customerApiLimit.setEndDate(endDate);
			customerApiLimit.setCustomerId(customerId);
			customerApiLimit.setCurrentAccessCount(currentAccessCount);
			customerApiLimit.setCurrentAccessLimit(currentAccessLimit);

		}
		
		return customerApiLimit;
	}
	
	/**
	 * Get Customer Api limits 
	 * @param customerId
	 * @param startDate
	 * @return CustomerApiLimit
	 */
	/*@Override
	public CustomerApiLimit getCustomerApiLimits(Long customerId,
			Date resetDayOfMonthDate) {

		LOGGER.info("Getting customer api Limits for a customer from start date ");
		LOGGER.info("CustomerId : " + customerId);
		LOGGER.info("Start Date : " + resetDayOfMonthDate);

		CustomerApiLimit customerApiLimit = null;

		if (!Util.isNull(customerId) && !Util.isNull(resetDayOfMonthDate)) {

			LOGGER.info("Getting customer current Access Limit By resetDayOfMonthDate ...");

			List<CustomerApiLimit> currentAccessLimits = customerApiLimitsRepository
					.findApiLimitByOnOrBeforeStartDate(customerId, resetDayOfMonthDate);

			if(!Util.isNullOrEmptyCollection(currentAccessLimits)) {
				customerApiLimit = currentAccessLimits.get(0);
			}
		}
		
		return customerApiLimit;
		
	}*/

	
	/**
	 * Admin password change
	 * @param CustomerUser
	 * @return
	 */
	@Override
	public Boolean changePassword(CustomerUser customerUser) {

		LOGGER.info("Changing password for customer");

		Boolean status = false;
		if (!Util.isNull(customerUser)) {

			LOGGER.info("Customer Id : " + customerUser.getId());

			CustomerUser au = customerRepository.findOne(customerUser.getId());
			if (!Util.isNull(au)) {
				au.setPassword(customerUser.getPassword());
				customerRepository.save(au);
				status = true;
			} else {
				LOGGER.info("No Customer found for customer id : "
						+ customerUser.getId());
			}
		}
		return status;
	}
	
	/**
	 * Returns Customer Api Limits in Current Month
	 * @param customerId
	 * @return CustomerApiLimit
	 */
	@Override
	public CustomerApiLimit getCustomerApiLimitsInCurrentMonth(Long customerId) {
		LOGGER.info("Getting CustomerApiLimit in Current Month for Customer id : " + customerId);
		CustomerApiLimit customerApiLimit = null;
		if(customerId != null) {
			
//			Date monthFirstDate = Util.getFirstDateOfCurrentMonth();
//			Date monthLastDate = Util.getNextOneMonthDate(monthFirstDate);
			
			CustomerUser customer = getCustomer(customerId);
			
			Integer resetDayOfMonth = customer.getResetDayOfMonth();
			
			if(resetDayOfMonth == null) {
				resetDayOfMonth = 1;
			}
			
			Date resetDayOfMonthDate = Util.getFullDateFromDayNumberInCurrentMonth(resetDayOfMonth);
			
			List<CustomerApiLimit> customerApiLimits = customerApiLimitsRepository
					.getCustomerApiLimits(customerId, resetDayOfMonthDate,
							Util.getNextOneMonthDate(resetDayOfMonthDate));
			
			if (!Util.isNullOrEmptyCollection(customerApiLimits)) {
				customerApiLimit = customerApiLimits.get(0);
			}
		}
		return customerApiLimit;
	}

	/**
	 * Saving Api Key For Customer
	 * @param apiKey 
	 * @return CustomerApiKey - Saved api key object
	 */
	@Override
	public CustomerApiKey saveApiKey(CustomerApiKey apiKey) {

		LOGGER.info("Saving api Key for Customer");
		if (!Util.isNull(apiKey) && !Util.isNull(apiKey.getCustomerId())
				&& !Util.isEmptyString(apiKey.getApiKey())) {
			LOGGER.info("Customer Id : " + apiKey.getCustomerId());
			apiKey = customerApiKeysRepository.save(apiKey);
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
	 * 
	 */
	@Override
	public Boolean deleteCustomerApiKeys(Long customerId, Set<Long> apiKeyIds) {
		
		Boolean deleteStatus = false;
		
		LOGGER.info("Deleting api Key for Customer : " + customerId);
		
		LOGGER.info("Api Key Ids : " + apiKeyIds);
		
		if (!Util.isNull(customerId)
				&& !Util.isNullOrEmptyCollection(apiKeyIds)) {
			
			int deletedRecordsCnt = customerApiKeysRepository.deleteCustomerApiKeys(customerId, apiKeyIds);
			
			if(deletedRecordsCnt > 0) {
				deleteStatus = true;
			}
			
			LOGGER.info("Deleted api Keys count : " + deletedRecordsCnt);
			
		} else {
			LOGGER.info("Missing customer id in Deactivating Customer Api keys");
		}
		return deleteStatus;
	}

}
