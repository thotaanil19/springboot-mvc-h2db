package com.springboot.api.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.springboot.api.domain.CustomerUser;
import com.springboot.api.endpoint.to.CustomerProductTo;
import com.springboot.api.to.CustomerApiKeyTo;
import com.springboot.api.to.CustomerApiLimitTo;
import com.springboot.api.to.CustomerUserTo;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.SortingCriteria;

/**
 * 
 * @author anilt
 *
 */
public interface CustomerService {

	List<CustomerUser> getAllCustomers();

	CustomerUserTo getCustomerProducts(Long customerId);

	PaginationResultsTo<CustomerProductTo> getCustomerProductsInPagination(
			Long customerId, Integer pageNum, Integer pageSize);

	PaginationResultsTo<CustomerUserTo> getCustomers(Integer pageNum,
			Integer pageSize, SortingCriteria sortCriteria);

	void addCustomer(CustomerUserTo customer);

	Integer deleteSelectedCustomers(Set<Long> customerIds);

	void editCustomer(CustomerUserTo customer);

	List<CustomerApiKeyTo> getCustomerApiKeys(Long customerId);

	CustomerApiLimitTo getCustomerApiAllLimits(Long customerId);

	Boolean changePassword(CustomerUser customer);

	CustomerUserTo getCustomerUser(Long customerId);

	CustomerApiLimitTo getCustomerApiLimitsSum(Long customerId, Date startDate,
			Date endDate);

	CustomerProductTo getCustomerProductTracks(Long customerId, Long productId);

	Boolean editApiLimits(CustomerApiLimitTo apiLimitTo);

	CustomerApiLimitTo getCustomerCurrentMonthApiLimits(Long customerId);

	CustomerApiKeyTo saveApiKey(CustomerApiKeyTo apiKey);

	Boolean deleteCustomerApiKeys(Long customerId, Set<Long> apiKeyIds);

	void editCustomerProductTracks(CustomerUserTo customer);

	void editProducts(CustomerUserTo customer);

	void editCustomerProduct(Long customerId,
			CustomerProductTo customerProductTo);

	CustomerUserTo editCustomerDetails(CustomerUserTo customer);

	void editApiKeys(CustomerUserTo customer);

}
