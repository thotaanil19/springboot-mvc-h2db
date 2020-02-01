package com.springboot.api.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springboot.api.dao.ActionLoggingDao;
import com.springboot.api.dao.repository.AdminActionsRepository;
import com.springboot.api.dao.repository.ApiRequestRepository;
import com.springboot.api.domain.AdminAction;
import com.springboot.api.domain.ApiRequest;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */

@Repository
public class ActionLoggingDaoImpl implements ActionLoggingDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActionLoggingDaoImpl.class);
	
	@Autowired
	private AdminActionsRepository adminActionsRepository;
	
	@Autowired
	private ApiRequestRepository apiRequestRepository;
	
	
	/**
	 * Saves AdminAction
	 * @param adminAction
	 * @return AdminAction
	 */
	@Override
	public AdminAction saveAdminActions(AdminAction adminAction) {
		LOGGER.info("Logging Admin Actions...");
		if (!Util.isNull(adminAction)) {
			adminAction = adminActionsRepository.save(adminAction);
		}
		return adminAction;

	}
	
	/**
	 * Saves ApiRequest
	 * @param apiRequest
	 * @return {@link ApiRequest}
	 */
	@Override
	public ApiRequest saveCustomerApiRequest(ApiRequest apiRequest) {
		LOGGER.info("Logging Customer Api Requests...");
		if (!Util.isNull(apiRequest)) {
			apiRequest = apiRequestRepository.save(apiRequest);
		}
		return apiRequest;
	}
	

}
