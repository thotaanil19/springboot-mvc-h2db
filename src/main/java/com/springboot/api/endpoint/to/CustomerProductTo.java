package com.springboot.api.endpoint.to;


import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.api.to.ProductTo;
import com.springboot.api.to.ProductTrackTo;

/**
 * 
 * @author anilt
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerProductTo implements Serializable {

	private static final long serialVersionUID = -1676651528409519076L;

	private Long id;

	private Long customerId;

    private Long productId;

    private Boolean isActive = true;
    
	private Boolean allTracks = true;
    
  	private Integer daysBack = 7;
  	
  	private ProductTo product;
  	
  	private List<ProductTrackTo> productTracks;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getAllTracks() {
		return allTracks;
	}

	public void setAllTracks(Boolean allTracks) {
		this.allTracks = allTracks;
	}

	public Integer getDaysBack() {
		return daysBack;
	}

	public void setDaysBack(Integer daysBack) {
		this.daysBack = daysBack;
	}

	public ProductTo getProduct() {
		return product;
	}

	public void setProduct(ProductTo product) {
		this.product = product;
	}

	public List<ProductTrackTo> getProductTracks() {
		return productTracks;
	}

	public void setProductTracks(List<ProductTrackTo> productTracks) {
		this.productTracks = productTracks;
	}
    

    
}
