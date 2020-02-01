package com.springboot.api.service;

import javax.servlet.http.HttpServletRequest;

import com.springboot.api.domain.AdminAction;


/**
 * 
 * @author anilt
 *
 */
public interface ActionLoggingService {

	AdminAction saveAdminActions(AdminAction adminAction);

	void updateCustomerAccessCount(Long customerId);

	void logAdminActions(Long adminId, Long customerId,
			HttpServletRequest request);
	
	void logApiRequest(HttpServletRequest request, 
			String apiKey, String productType, String productLevel, Integer responseStatusCode);


	

}
