package com.springboot.api.domain;


import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "eqbdataapi_admin_users")
public class AdminUser implements Serializable {
	
	private static final long serialVersionUID = -203307441799101258L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

    @Column(name="login_id", unique = true, nullable = false, columnDefinition = "CHAR")
    private String loginId;
    
    @Column(name="password", nullable = false, columnDefinition = "BINARY")
    private byte[] password;
    
    @Transient
    private String passwordStr;
    
    @Type(type="numeric_boolean")
    @Column(name="active", columnDefinition = "SMALLINT")
    private Boolean isActive = true;
    
    @Column(name="access_level", columnDefinition = "SMALLINT")
	private Integer accessLevel;
    
    @Column(name="name", nullable = false, columnDefinition = "CHAR")
    private String name;

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

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Integer getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(Integer accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	
    
	

	

    
}
