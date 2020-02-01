package com.springboot.api.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 
 * @author anilt
 *
 */
@Component
public class DataSalesApiProperty {
	
	@Value("${CUSTOMER_BASE_ACCESS_LIMIT}")
	private Long customerBaseAccessLimit;

	public Long getCustomerBaseAccessLimit() {
		return customerBaseAccessLimit;
	}

}
