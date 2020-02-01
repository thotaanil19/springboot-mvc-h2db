package com.springboot.api.enums;

/**
 * 
 * @author anilt
 *
 */
public enum ErrorCodesEnum {
	
	INVALID_IP("Invalid IP Address Range")
	;
	
	private String value;
	
	private ErrorCodesEnum(String value) {
		this.value = value;
	}

	
	public String getValue() {
		return value;
	}
	
	
}
