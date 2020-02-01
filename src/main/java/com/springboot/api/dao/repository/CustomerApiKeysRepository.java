package com.springboot.api.dao.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.api.domain.CustomerApiKey;

/**
 * 
 * @author anilt
 *
 */
@Repository
public interface CustomerApiKeysRepository extends JpaRepository<CustomerApiKey, Long> {

	List<CustomerApiKey> findByCustomerId(Long customerId, Sort sort);
	
	@Query(nativeQuery = true, value = "select (case when max(id) = null then 1 else max(id) + 1 end) from eqbdataapi_customer_api_keys")
	Long getNextSequenceValue();

	CustomerApiKey findCustomerApiKeyByCustomerIdAndApiKey(Long customerId, String apiKey);
	
	CustomerApiKey findCustomerApiKeyByApiKey(String apiKey);
	

	@Modifying
	@Query("update CustomerApiKey set active = false, disabledAt=getdate()  where isActive = true and customerId = :customerId and id IN (:apiKeyIds)")
	int deleteCustomerApiKeys(@Param("customerId") Long customerId, @Param("apiKeyIds") Set<Long> apiKeyIds);


	
}
