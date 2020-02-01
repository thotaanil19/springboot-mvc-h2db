package com.springboot.api.domain;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * @author anilt
 *
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "eqbdataapi_product_tracks")
public class ProductTrack implements Serializable {

	private static final long serialVersionUID = -4103686711598659704L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="customer_product_id", nullable = false/*, columnDefinition="INT"*/)
	private Long customerProductId;

	@Column(name="track_id", nullable = false, columnDefinition="CHAR")
	private String trackId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerProductId() {
		return customerProductId;
	}

	public void setCustomerProductId(Long customerProductId) {
		this.customerProductId = customerProductId;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}



}
