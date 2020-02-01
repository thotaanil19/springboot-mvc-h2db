package com.springboot.api.domain;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * @author anilt
 *
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "eqbdataapi_customer_products")
public class CustomerProduct implements Serializable {

	private static final long serialVersionUID = 752721865701586040L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
    @Column(name="customer_id", nullable = false)
    private Long customerId;

    @Column(name="product_id", nullable = false)
    private Long productId;

    @Type(type="numeric_boolean")
    @Column(name="active", columnDefinition = "SMALLINT")
    private Boolean isActive = true;
    
    @Type(type="numeric_boolean")
    @Column(name="all_tracks", columnDefinition = "SMALLINT")
	private Boolean allTracks = true;
    
    @Column(name="days_back", columnDefinition = "SMALLINT")
  	private Integer daysBack = 7;

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
    

    
}
