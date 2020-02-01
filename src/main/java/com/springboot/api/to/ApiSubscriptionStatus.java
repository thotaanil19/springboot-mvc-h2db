package com.springboot.api.to;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.api.endpoint.to.DataApiObject;

/**
 * 
 * @author anilt
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiSubscriptionStatus implements DataApiObject {

	private static final long serialVersionUID = 9185165458861862288L;
	
	private Long callsMade=0L;
	private Long callsLeft=0L;
	private Boolean customerActive;
	private Boolean apiKeyActive;
	private String countResetDate;
	
	public Long getCallsMade() {
		return callsMade;
	}
	public void setCallsMade(Long callsMade) {
		this.callsMade = callsMade;
	}
	public Boolean getApiKeyActive() {
		return apiKeyActive;
	}
	public void setApiKeyActive(Boolean apiKeyActive) {
		this.apiKeyActive = apiKeyActive;
	}
	public Long getCallsLeft() {
		return callsLeft;
	}
	public void setCallsLeft(Long callsLeft) {
		this.callsLeft = callsLeft;
	}
	public Boolean getCustomerActive() {
		return customerActive;
	}
	public void setCustomerActive(Boolean customerActive) {
		this.customerActive = customerActive;
	}
	
	public String getCountResetDate() {
		return countResetDate;
	}
	public void setCountResetDate(String countResetDate) {
		this.countResetDate = countResetDate;
	}
	
	

	
    
    
}
