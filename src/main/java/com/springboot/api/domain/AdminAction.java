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
@Table(name = "eqbdataapi_admin_actions")
public class AdminAction implements Serializable {
	
	private static final long serialVersionUID = -8331993253502988046L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
    @Column(name="admin_id", columnDefinition = "INT")
    private Long adminId;
    
    @Column(name="customer_id")
    private Long customerId;
    
    @Column(name="ip4_address", columnDefinition = "CHAR")
    private String ip4;
    
    @Column(name="ip6_address", columnDefinition = "CHAR")
    private String ip6;
    
    @Column(name = "description", columnDefinition = "TEXT")
	private String description;
    
    @Column(name="timestamp", nullable = false)
    private Date timeStamp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getIp4() {
		return ip4;
	}

	public void setIp4(String ip4) {
		this.ip4 = ip4;
	}

	public String getIp6() {
		return ip6;
	}

	public void setIp6(String ip6) {
		this.ip6 = ip6;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
    
    
	
}
