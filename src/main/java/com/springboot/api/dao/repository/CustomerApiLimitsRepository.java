package com.springboot.api.dao.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.api.domain.CustomerApiLimit;

/**
 * 
 * @author anilt
 *
 */
@Repository
public interface CustomerApiLimitsRepository extends JpaRepository<CustomerApiLimit, Long> {

	@Query(nativeQuery = true, value = "select (case when max(id) = null then 1 else max(id) + 1 end) from eqbdataapi_customer_api_limits")
	Long getNextSequenceValue();

	List<CustomerApiLimit> findCustomerApiLimitByCustomerIdOrderByStartDateDesc(Long customerId);
	
	
	@Query(value = "select sum(currentAccessLimit) from CustomerApiLimit "
			+ " where customerId = :customerId and startDate >= :startDate and endDate <= :endDate")
	Long findCustomerCurrentAccessLimit(
			@Param("customerId") Long customerId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	
	@Query(value = "select sum(currentAccessCount) from CustomerApiLimit "
			+ " where customerId = :customerId and startDate >= :startDate and endDate <= :endDate")
	Long findCustomerCurrentAccessCount(
			@Param("customerId") Long customerId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query(value = "select sum(currentAccessLimit) from CustomerApiLimit "
			+ " where customerId = :customerId")
	Long findCustomerApiAllAccessLimit(@Param("customerId") Long customerId);

	@Query(value = "select sum(currentAccessCount) from CustomerApiLimit "
			+ " where customerId = :customerId")
	Long findCustomerApiAllAccessCount(@Param("customerId") Long customerId);

	@Query(value = "select limit from CustomerApiLimit limit "
			+ " where customerId = :customerId and startDate >= :startDate and endDate <= :endDate")
	List<CustomerApiLimit> getCustomerApiLimits(@Param("customerId") Long customerId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query(value = "select limit from CustomerApiLimit limit "
			+ " where customerId = :customerId and startDate <= :startDate")
	List<CustomerApiLimit> findApiLimitByOnOrBeforeStartDate(@Param("customerId") Long customerId,
			@Param("startDate") Date resetDayOfMonthDate);
	
}
