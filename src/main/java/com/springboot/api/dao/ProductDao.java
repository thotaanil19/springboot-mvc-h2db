package com.springboot.api.dao;

import java.util.List;
import java.util.Set;

import com.springboot.api.domain.CustomerProduct;
import com.springboot.api.domain.Product;
import com.springboot.api.domain.ProductTrack;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.SortingCriteria;

public interface ProductDao {
	
	Product createNewProduct(Product product);
	
	List<Product> getAllProducts();
	
	Boolean updateProduct(Product product);

	Product getProduct(Long productId);

	Boolean deleteProduct(Long productId);

	Integer deleteSelectedProducts(Set<Long> productIds);

	PaginationResultsTo<Product> getProducts(Integer pageNum, Integer pageSize, SortingCriteria sortingCriteria);

	List<ProductTrack> getProductTracksBYCustProdsId(Long id);

	CustomerProduct createCustomerProduct(CustomerProduct customerProduct);

	ProductTrack createProductTracks(ProductTrack productTrack);

	Integer deleteCustomerProductsByCustomerId(Long customerId);

	List<CustomerProduct> getCustomerProductsByCustomerId(Long customerId);

	List<CustomerProduct> getCustomerProducts(Long customerId, Long productId);

	Integer deleteProductTracks(Long customerProductId);

	Product getProductByTypeAndLevel(String productType, String productLevel);

	Long getProductIdByTypeAndLevel(String productType, String productLevel);

}
