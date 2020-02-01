package com.springboot.api.domain;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * @author anilt
 *
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "eqbdataapi_requests")
public class ApiRequest implements Serializable {
	
	private static final long serialVersionUID = 3832541651617453086L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
    @Column(name="cust_prod_id", unique = true)
    private Long custProdId;
    
    @Column(name="ip4_address", columnDefinition = "CHAR")
    private String ip4;
    
    @Column(name="ip6_address", columnDefinition = "CHAR")
    private String ip6;
    
    @Column(name = "endpoint", nullable = false)
	private String endpoint;
    
    @Column(name = "response_code", nullable = false, columnDefinition = "INT")
   	private Long responseCode;

    @Column(name="timestamp", nullable = false)
    private Date timeStamp;
    
    @Transient
    private String timeStampStr;
    
    @Column(name = "api_key", nullable = false, columnDefinition = "CHAR")
	private String apiKey;
    
    @Column(name = "attribute1", columnDefinition = "CHAR")
   	private String attribute1;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustProdId() {
		return custProdId;
	}

	public void setCustProdId(Long custProdId) {
		this.custProdId = custProdId;
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

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public Long getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Long responseCode) {
		this.responseCode = responseCode;
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

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getTimeStampStr() {
		return timeStampStr;
	}

	public void setTimeStampStr(String timeStampStr) {
		this.timeStampStr = timeStampStr;
	}
    
	
}
