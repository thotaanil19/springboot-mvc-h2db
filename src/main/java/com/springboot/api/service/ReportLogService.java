package com.springboot.api.service;

import java.util.List;

import com.springboot.api.domain.ApiRequest;
import com.springboot.api.to.EquibaseDataSelesApiError;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.ReportLogCriteria;


/**
 * 
 * @author anilt
 *
 */
public interface ReportLogService {

	 PaginationResultsTo<ApiRequest> getReportLog(ReportLogCriteria reportLogCriteria);

	List<EquibaseDataSelesApiError> validateReportLogCriteriaObj(
			ReportLogCriteria reportLogCriteria);
	

}
