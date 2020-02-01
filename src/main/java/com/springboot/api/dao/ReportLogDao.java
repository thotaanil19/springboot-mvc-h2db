package com.springboot.api.dao;

import java.util.List;

import com.springboot.api.domain.ApiRequest;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.ReportLogCriteria;

/**
 * 
 * @author anilt
 *
 */
public interface ReportLogDao {

	PaginationResultsTo<ApiRequest> getReportLog(ReportLogCriteria reportLogCriteria);
	
	List<ApiRequest> getReportLogByCustomerProductIds(
			List<Long> customerProductIds);

}
