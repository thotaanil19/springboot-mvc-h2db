package com.springboot.api.domain;


import java.io.Serializable;
import java.util.Date;

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
@Table(name = "eqbdataapi_customer_api_keys")
public class CustomerApiKey implements Serializable {
	
	private static final long serialVersionUID = 4598820939211316665L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
    @Column(name="customer_id", unique = true)
    private Long customerId;
    
    @Column(name="api_key", unique = true, columnDefinition = "CHAR")
    private String apiKey;

    @Type(type="numeric_boolean")
    @Column(name="active", columnDefinition = "SMALLINT")
    private Boolean isActive=true;
    
    @Column(name="timestamp", nullable = false)
    private Date timeStamp;
    
    @Column(name="disabled_at")
    private Date disabledAt;
    
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

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		if (timeStamp != null) {
			this.timeStamp = new Date(timeStamp.getTime());
		} else {
			this.timeStamp = null;
		}
	}

	public Date getDisabledAt() {
		return disabledAt;
	}

	public void setDisabledAt(Date disabledAt) {
		if (disabledAt != null) {
			this.disabledAt = new Date(disabledAt.getTime());
		} else {
			this.disabledAt = null;
		}
	}
    
    
    
	
}
