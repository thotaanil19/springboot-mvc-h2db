package com.springboot.api.service;

import java.util.List;
import java.util.Set;

import com.springboot.api.domain.Product;
import com.springboot.api.to.EquibaseDataSelesApiError;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.SortingCriteria;

/**
 * @author menlo
 *
 */
public interface ProductService {

	List<EquibaseDataSelesApiError> validateAddProduct(Product product);

	Product addProduct(Product product);

	List<EquibaseDataSelesApiError> validateUpdateProduct(Product product);

	Boolean updateProduct(Product product);

	Boolean deleteProduct(Long productId);

	List<Product> getAllProducts();

	Product getProduct(Long productId);

	Integer deleteSelectedProducts(Set<Long> productIds);

	PaginationResultsTo<Product> getProducts(Integer pageNum, Integer pageSize, SortingCriteria sortingCriteria);

}
