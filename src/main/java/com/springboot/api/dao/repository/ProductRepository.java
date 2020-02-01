package com.springboot.api.dao.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.api.domain.Product;

/**
 * 
 * @author anilt
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Modifying
	@Query("update Product p set p.label = :label, p.type = :type, p.level = :level, "
			+ "p.cacheTime = :cacheTime where id = :productId")
	Integer updateProduct(@Param("productId") Long productId,
			@Param("label") String label,
			@Param("type") String type, @Param("level") String level,
			@Param("cacheTime") int cacheTime);

	@Modifying
	@Query("delete Product p where p.id in (:productIds)")
	Integer deleteSelectedProducts(@Param("productIds") Set<Long> productIds);

	@Modifying
	@Query(nativeQuery = true, value = "insert into eqbdataapi_products(id,product_type,product_level,label,cache_time) values "
			+ "(:id,:productType,:productLevel,:label,:cacheTime)")
	int save(@Param("id") Long id,
			@Param("productType") String productType,
			@Param("productLevel") String productLevel,
			@Param("label") String label,
			@Param("cacheTime") Integer cacheTime);
	
	@Query(nativeQuery = true, value = "select (case when max(id) = null then 1 else max(id) + 1 end) from eqbdataapi_products")
	Long getNextSequenceValue();

	Page<Product> findAll(Pageable pageable);

	Product findByTypeAndLevel(String productType, String productLevel);

}
