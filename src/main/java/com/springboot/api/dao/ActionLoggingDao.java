package com.springboot.api.dao;

import com.springboot.api.domain.AdminAction;
import com.springboot.api.domain.ApiRequest;

/**
 * 
 * @author anilt
 *
 */
public interface ActionLoggingDao {

	public AdminAction saveAdminActions(AdminAction adminAction);
	
	public ApiRequest saveCustomerApiRequest(ApiRequest apiRequest);

}
