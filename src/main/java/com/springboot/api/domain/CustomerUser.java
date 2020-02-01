package com.springboot.api.domain;


import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Arrays;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "eqbdataapi_customers")
public class CustomerUser implements Serializable {

	private static final long serialVersionUID = -1962928233541453855L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Access(AccessType.FIELD)
    @Column(name="login_id", unique = true, columnDefinition = "CHAR")
    private String loginId;
	
    @Column(name="password", nullable = false, columnDefinition = "BINARY")
    private byte[] password;
    
    @Transient
    private String passwordStr;
    
    @Email
    @Column(name="email", columnDefinition = "TEXT")
    private String email;
    
    @Type(type="numeric_boolean")
    @Column(name="active", columnDefinition = "SMALLINT")
    private Boolean isActive=true;
    
    @Column(name="base_access_limit")
    private Long baseAccessLimit;
    
    @Column(name="reset_day_of_month", columnDefinition = "SMALLINT")
    private Integer resetDayOfMonth = 1;
    
    @Column(name="company_name", columnDefinition = "TEXT")
    private String companyName;
    

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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
    
	

	

    
}
