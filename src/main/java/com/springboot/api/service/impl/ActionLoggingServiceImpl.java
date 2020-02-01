package com.springboot.api.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.springboot.api.dao.ActionLoggingDao;
import com.springboot.api.dao.CustomerDao;
import com.springboot.api.dao.ProductDao;
import com.springboot.api.domain.AdminAction;
import com.springboot.api.domain.ApiRequest;
import com.springboot.api.domain.CustomerApiKey;
import com.springboot.api.domain.CustomerApiLimit;
import com.springboot.api.domain.CustomerProduct;
import com.springboot.api.service.ActionLoggingService;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
@Service
public class ActionLoggingServiceImpl implements ActionLoggingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActionLoggingServiceImpl.class);

	@Autowired
	private ActionLoggingDao actionLoggingDao;

	@Autowired
	private CustomerDao customerDao;

	
	@Autowired
	private ProductDao productDao;
	
	/**
	 * Saves AdminAction
	 * @param adminAction
	 * @return AdminAction
	 */
	@Override
	public AdminAction saveAdminActions(AdminAction adminAction) {
		LOGGER.info("Logging Admin Actions...");
		if (!Util.isNull(adminAction)) {
			adminAction = actionLoggingDao.saveAdminActions(adminAction);
		}
		return adminAction;

	}

	/**
	 * Logs Customer End-point Api requests
	 * @param request
	 * @param apiKey
	 * @param productType
	 * @param productLevel
	 * @param responseStatusCode
	 */
	@Override
	public void logApiRequest(HttpServletRequest request, String apiKey,
			String productType, String productLevel, Integer responseStatusCode) {
		
		LOGGER.info("Logging Customer Api requests...");
		LOGGER.info("Api Key :" + apiKey);
		LOGGER.info("ProductType :" + productType);
		LOGGER.info("ProductLevel :" + productLevel);
		LOGGER.info("Response Code :" + responseStatusCode);
		
		if (!Util.isNull(apiKey) && !Util.isNull(productType)
				&& !Util.isNull(productLevel)
				&& !Util.isNull(responseStatusCode)) {
			CustomerApiKey customerApiKey = customerDao
					.getCustomerApiKeyByApiKey(apiKey);
			Long customerId = null;
			if (!Util.isNull(customerApiKey)) {
				customerId = customerApiKey.getCustomerId();
			}
			
			if (!Util.isNull(customerId)) {
				Long productId =  productDao.getProductIdByTypeAndLevel(productType, productLevel);
				if (!Util.isNull(customerId)) {
					List<CustomerProduct> customerProducts = productDao.getCustomerProducts(customerId, productId);
					if(!Util.isNullOrEmptyCollection(customerProducts) && !Util.isNull(customerProducts.get(0))) {
						CustomerProduct customerProduct = customerProducts.get(0);
						Long customerProductId = customerProduct.getId();
						String ip4 = request.getRemoteAddr();
						String endpoint = request.getServletPath();
						
						ApiRequest apiRequest = new ApiRequest();
						apiRequest.setIp4(ip4);
						apiRequest.setEndpoint(endpoint);
						apiRequest.setCustProdId(customerProductId);
						apiRequest.setResponseCode(responseStatusCode.longValue());
						apiRequest.setApiKey(apiKey);
						apiRequest.setTimeStamp(new Date());
						actionLoggingDao.saveCustomerApiRequest(apiRequest);
						
					}
				}
			}
		} else {
			LOGGER.warn("To log Customer Api requests, required fields are : apiKey, productType, productLevel, responseStatusCode");
		}

	}
	

	/**
	 * Increments Customer Access Count by 1 in Customer_Api_Limits table
	 * @param customerId
	 */
	@Override
	public void updateCustomerAccessCount(Long customerId) {
		LOGGER.info("Incrementing Customer Access Count by 1 in Customer_Api_Limits table");
		LOGGER.info("Customer Id : " + customerId);
		if(customerId != null) {
//			CustomerUser customer = customerDao.getCustomer(customerId);
//			Date resetDayOfMonthDate = Util.getFullDateFromDayNumberInCurrentMonth(customer.getResetDayOfMonth());

//			CustomerApiLimit customerApiLimit = customerDao.getCustomerApiLimits(customerId, resetDayOfMonthDate);
			CustomerApiLimit customerApiLimit = customerDao.getCustomerApiLimitsInCurrentMonth(customerId);
			
			if(customerApiLimit != null) {
				Long currentAccessCnt = customerApiLimit.getCurrentAccessCount();
				if(currentAccessCnt == null) {
					currentAccessCnt = 0L;
				}
				customerApiLimit.setCurrentAccessCount(++ currentAccessCnt);
				customerDao.addOrUpdateApiLimitsToCustomer(customerApiLimit);
			}
		}
	}

	@Override
	public void logAdminActions(Long adminId, Long customerId, HttpServletRequest request) {

		String adminActionDesc = getAdminActionDesc(request);

		if (!Util.isEmptyString(adminActionDesc)) {
			LOGGER.info("Admin Action : " + adminActionDesc);

			AdminAction adminAction = new AdminAction();
			adminAction.setIp4(request.getRemoteAddr());
			adminAction.setTimeStamp(new Date());
			adminAction.setDescription(adminActionDesc);

			adminAction.setAdminId(adminId);
			adminAction.setCustomerId(customerId);

			saveAdminActions(adminAction);

		}
	}

	/**
	 * Get Admin/Customer Action based on Request Url
	 * @param request
	 * @return
	 */
	private String getAdminActionDesc(HttpServletRequest request) {
		String adminActionDesc = null;
		String requestUri = request.getRequestURI();

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!Util.isNull(auth) && !"anonymousUser".equals(auth.getPrincipal())
				&& !requestUri.endsWith(".jsp")) {
			if(requestUri.endsWith("changePassword")) {
				adminActionDesc = "Password Reset";
			} else if(requestUri.endsWith("createAdmin")) {
				adminActionDesc = "Create New Admin";
			} else if(requestUri.endsWith("editAdmin")) {
				adminActionDesc = "Edit Existing Admin";
			} else if(requestUri.endsWith("addProduct")) {
				adminActionDesc = "Add New Product";
			} else if(requestUri.endsWith("deleteProduct")) {
				adminActionDesc = "Delete existing Product";
			} else if(requestUri.endsWith("deleteProducts")) {
				adminActionDesc = "Delete existing Products";
			} else if(requestUri.endsWith("editProduct")) {
				adminActionDesc = "Edit Existing Product";
			} else if(requestUri.endsWith("addCustomer")) {
				adminActionDesc = "Add New Customer";
			} else if(requestUri.endsWith("updateCustomerProductTracks")) {
				adminActionDesc = "Update Customer Product Tracks";
			} else if(requestUri.endsWith("editCustomer")) {
				adminActionDesc = "Edit Customer in Customer Edit Screen";
			} else if(requestUri.endsWith("updateCustomerApiLimits")) {
				adminActionDesc = "Update Customer Api Limit in Customer Edit Screen";
			} else if(requestUri.endsWith("saveApiKey")) {
				adminActionDesc = "Generate and Save new Api Key for Customer";
			} else if(requestUri.endsWith("deleteCustomerApiKeys")) {
				adminActionDesc = "Delete Api Key for Customer";
			}
		}
		return adminActionDesc;
	}



}
