package com.springboot.api.dao.repository;



import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.api.domain.CustomerUser;

/**
 * 
 * @author anilt
 *
 */
@Repository
public interface CustomerUserRepository extends JpaRepository<CustomerUser, Long> {


	@Query(nativeQuery = true, value = "select (case when max(id) = null then 1 else max(id) + 1 end) from eqbdataapi_customers")
	Long getNextSequenceValue();	
	
	
	@Query("select u from CustomerUser u where u.loginId = :userName")
	CustomerUser getUserByUserName(@Param("userName") String userName);


	@Modifying
	@Query("update CustomerUser cu set cu.isActive = false where cu.id in (:customerIds)")
	Integer deleteSelectedCustomers(@Param("customerIds") Set<Long> customerIds);
	
	CustomerUser findByLoginId(String loginId);
	
}
