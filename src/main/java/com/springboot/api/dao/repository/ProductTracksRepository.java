package com.springboot.api.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.api.domain.ProductTrack;

/**
 * 
 * @author anilt
 *
 */
@Repository
public interface ProductTracksRepository extends JpaRepository<ProductTrack, Long> {

	@Query(nativeQuery = true, value = "select (case when max(id) = null then 1 else max(id) + 1 end) from eqbdataapi_product_tracks")
	Long getNextSequenceValue();
	
	List<ProductTrack> findByCustomerProductId(Long customerProductId);
	
	@Modifying
	@Query(value = "delete from ProductTrack where customerProductId = :customerProductId)")
	Integer deleteProductTracksByCustomerProductId(@Param("customerProductId") Long customerProductId);
}
