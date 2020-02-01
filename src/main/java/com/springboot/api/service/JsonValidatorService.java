package com.springboot.api.service;

import java.util.List;

import com.springboot.api.to.CustomerUserTo;
import com.springboot.api.to.EquibaseDataSelesApiError;

/**
 * 
 * @author anilt
 *
 */
public interface JsonValidatorService {

	List<EquibaseDataSelesApiError> validateCustomerEditJson(
			CustomerUserTo customer);

	List<EquibaseDataSelesApiError> validateCustomerSetupJson(
			CustomerUserTo customer);

	List<EquibaseDataSelesApiError> validateCustomerProductJson(
			CustomerUserTo customer);

}
