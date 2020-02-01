package com.springboot.api.endpoint.to;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * @author anilt
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Jockey implements Serializable {

	private static final long serialVersionUID = 2895764708485578285L;
	
	private String whichType;
	private String keyType;
	private String key;
	private String firstName;
	private String middleName;
	private String lastName;
	
	public String getWhichType() {
		return whichType;
	}
	public void setWhichType(String whichType) {
		this.whichType = whichType;
	}
	public String getKeyType() {
		return keyType;
	}
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	

}
