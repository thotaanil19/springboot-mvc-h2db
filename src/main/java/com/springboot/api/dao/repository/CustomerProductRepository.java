package com.springboot.api.dao.repository;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.api.domain.CustomerProduct;

/**
 * 
 * @author anilt
 *
 */
@Repository
public interface CustomerProductRepository extends JpaRepository<CustomerProduct, Long> {
	
	@Query(nativeQuery = true, value = "select (case when max(id) = null then 1 else max(id) + 1 end) from eqbdataapi_customer_products")
	Long getNextSequenceValue();

	@Query(nativeQuery = true, value = "select days_back from eqbdataapi_customer_products where customer_id= :customerId and product_id= :productId")
	Integer getDaysBackValue(@Param ("customerId") Long customerId, @Param ("productId") Long productId);
	
	@Query(nativeQuery = true, value = "select id from eqbdataapi_products where product_type = :producType and product_level= :productLevel")
	Long getProductId(@Param ("producType") String producType, @Param ("productLevel") String productLevel);
	
	List<CustomerProduct> getCustomerProductByCustomerId(Long customerId);
	
	List<CustomerProduct> findByCustomerId(Long customerId);
	
	List<CustomerProduct> findByCustomerIdAndProductId(Long customerId, Long productId);

	@Modifying
	@Query(value = "update CustomerProduct set isActive = false where customerId = :customerId)")
	Integer deleteCustomerProductByCustomerId(@Param("customerId") Long customerId);

	@Query(value = "select cp from CustomerProduct cp where isActive = true and customerId = :customerId")
	Page<CustomerProduct> findByCustomerId(@Param("customerId") Long customerId, Pageable pageable);

}
