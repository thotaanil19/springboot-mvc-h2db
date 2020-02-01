package com.springboot.api.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.springboot.api.domain.CustomerApiKey;
import com.springboot.api.domain.CustomerApiLimit;
import com.springboot.api.domain.CustomerProduct;
import com.springboot.api.domain.CustomerUser;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.SortingCriteria;

/**
 * 
 * @author anilt
 *
 */
public interface CustomerDao {

	List<CustomerUser> getAllCustomers();

	CustomerUser getCustomer(Long customerId);

	List<CustomerProduct> getCustomerProducts(Long customerId);

	List<CustomerApiKey> getCustomerApiKeys(Long customerId);

	CustomerUser createOrUpdateCustomer(CustomerUser customer);

	CustomerApiKey addApiKeyToCustomer(CustomerApiKey apiKey);

	CustomerApiLimit addOrUpdateApiLimitsToCustomer(CustomerApiLimit apiLimits);

	Integer deleteSelectedCustomers(Set<Long> customerIds);

	CustomerUser getCustomerByLoginId(String LoginId);

	CustomerApiKey getCustomerApiKeyByCustomerIdAndApiKey(Long customerId, String apiKey);

	CustomerApiLimit getCustomerApiLimits(Long customerId);
	
	CustomerApiLimit getCustomerApiAllLimits(Long customerId);
	
	CustomerApiLimit getCustomerApiLimits(Long customerId, Date startDate, Date endDate);

	Boolean changePassword(CustomerUser customerUser);

	PaginationResultsTo<CustomerProduct> getCustomerProducts(Long customerId,
			Integer pageNum, Integer pageSize);

	CustomerApiKey getCustomerApiKeyByApiKey(String apiKey);

	PaginationResultsTo<CustomerUser> getCustomers(Integer pageNum,
			Integer pageSize, SortingCriteria sortCriteria);

	CustomerApiLimit getCustomerApiLimitsInCurrentMonth(Long customerId);

	CustomerApiKey saveApiKey(CustomerApiKey apiKey);

	Boolean deleteCustomerApiKeys(Long customerId, Set<Long> apiKeyIds);


}
