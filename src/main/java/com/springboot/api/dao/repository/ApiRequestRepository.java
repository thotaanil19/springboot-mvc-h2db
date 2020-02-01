package com.springboot.api.dao.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.api.domain.ApiRequest;

/**
 * 
 * @author anilt
 *
 */
@Repository
public interface ApiRequestRepository extends JpaRepository<ApiRequest, Long> {

	@Query(nativeQuery = true, value = "select (case when max(id) = null then 1 else max(id) + 1 end) from eqbdataapi_requests")
	Long getNextSequenceValue();

	Page<ApiRequest> findByTimeStampBetween(Pageable pageRequest, Date fromDate, Date toDate);	
	
	@Query(value = "select ar from ApiRequest ar where ar.custProdId IN :customerProductIds ")
	List<ApiRequest> findByCustProdIds(@Param("customerProductIds") Set<Long> customerProductIds);	
	
	
}
