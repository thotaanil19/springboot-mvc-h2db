package com.springboot.api.enums;

/**
 * 
 * @author anilt
 *
 */
public enum UserRoleCodeEnum {
	
	LEVEL_1(1,"LEVEL_1"),
	LEVEL_2(2,"LEVEL_2"),
	LEVEL_3(3,"LEVEL_3"),
	LEVEL_4(4,"LEVEL_4"),
	LEVEL_5(5,"LEVEL_5"),
	
	CUSTOMER(6,"CUSTOMER")
	;
	
	
	private Integer id;
	
	private String value;
	
	private UserRoleCodeEnum(Integer id,String value) {
		this.value = value;
		this.id= id;
	}

	public Integer getId() {
		return id;
	}
	
	public String getValue() {
		return value;
	}
	
	public static String lookupRoleStr(Integer id) {
		String result = "";
		for (UserRoleCodeEnum role : values()) {
			if (role.getId().equals(id)) {
				result = role.getValue();
			}
		}
		
		return result;
	}

	
}
