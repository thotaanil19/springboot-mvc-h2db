package com.springboot.api.domain;


import java.io.Serializable;
import java.util.Date;

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
@Table(name = "eqbdataapi_customer_api_limits")
public class CustomerApiLimit implements Serializable {
	
	private static final long serialVersionUID = -2156003704568666131L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
    @Column(name="customer_id", unique = true)
    private Long customerId;
    
    @Column(name="start_date", nullable = false)
    private Date startDate;
    
    @Column(name="end_date", nullable = false)
    private Date endDate;

    @Column(name="current_access_limit")
    private Long currentAccessLimit;
    
    @Column(name="current_access_count")
    private Long currentAccessCount;

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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		if (startDate != null) {
			this.startDate = new Date(startDate.getTime());
		} else {
			this.startDate =null;
		}
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		if (endDate != null) {
			this.endDate = new Date(endDate.getTime());
		} else {
			this.endDate = null;
		}
	}

	public Long getCurrentAccessLimit() {
		return currentAccessLimit;
	}

	public void setCurrentAccessLimit(Long currentAccessLimit) {
		this.currentAccessLimit = currentAccessLimit;
	}

	public Long getCurrentAccessCount() {
		return currentAccessCount;
	}

	public void setCurrentAccessCount(Long currentAccessCount) {
		this.currentAccessCount = currentAccessCount;
	}
    
	
}
