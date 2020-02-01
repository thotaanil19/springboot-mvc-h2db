package com.springboot.api.to;


import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.api.endpoint.to.CustomerProductTo;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerUserTo implements Serializable {

	private static final long serialVersionUID = -4071765413353724031L;


	private Long id;

    private String loginId;
	
    private byte[] password;
    
    private String passwordStr;
    
    private String email;
    
    private Boolean isActive=true;
    
    private Long baseAccessLimit=5000L;
    
    private Integer resetDayOfMonth=1;
    
    private String companyName;
    
    private List<CustomerProductTo> customerProducts;
    
    private List<CustomerApiKeyTo> customerApiKeys;
    
    private CustomerApiLimitTo customerApiLimit;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		if (password != null) {
			this.password = Arrays.copyOf(password, password.length);
		} else {
			this.password = null;
		}
	}

	public String getPasswordStr() {
		if(Util.isEmptyString(passwordStr) && password != null) {
			passwordStr = new String(password, Charset.forName("UTF-8"));
		}
		return passwordStr;
	}

	public void setPasswordStr(String passwordStr) {
		this.passwordStr = passwordStr;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Long getBaseAccessLimit() {
		return baseAccessLimit;
	}

	public void setBaseAccessLimit(Long baseAccessLimit) {
		this.baseAccessLimit = baseAccessLimit;
	}

	public Integer getResetDayOfMonth() {
		return resetDayOfMonth;
	}

	public void setResetDayOfMonth(Integer resetDayOfMonth) {
		this.resetDayOfMonth = resetDayOfMonth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public List<CustomerProductTo> getCustomerProducts() {
		return customerProducts;
	}

	public void setCustomerProducts(List<CustomerProductTo> customerProducts) {
		this.customerProducts = customerProducts;
	}

	public List<CustomerApiKeyTo> getCustomerApiKeys() {
		return customerApiKeys;
	}

	public void setCustomerApiKeys(List<CustomerApiKeyTo> customerApiKeys) {
		this.customerApiKeys = customerApiKeys;
	}

	public CustomerApiLimitTo getCustomerApiLimit() {
		return customerApiLimit;
	}

	public void setCustomerApiLimit(CustomerApiLimitTo customerApiLimit) {
		this.customerApiLimit = customerApiLimit;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	
    
    
}
