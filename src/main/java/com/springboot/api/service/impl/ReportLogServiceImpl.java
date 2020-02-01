package com.springboot.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.api.dao.ReportLogDao;
import com.springboot.api.domain.ApiRequest;
import com.springboot.api.service.ReportLogService;
import com.springboot.api.to.EquibaseDataSelesApiError;
import com.springboot.api.to.EquibaseDataSelesApiErrorCodes;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.ReportLogCriteria;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
@Service
public class ReportLogServiceImpl implements ReportLogService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportLogServiceImpl.class);
	
	@Autowired
	private ReportLogDao reportLogDao;

	/**
	 * Getting Api Requests(Report Logs)
	 * @param reportLogCriteria
	 * @return PaginationResultsTo<ApiRequest> - List of ApiRequest's
	 */
	@Override
	public PaginationResultsTo<ApiRequest> getReportLog(
			ReportLogCriteria reportLogCriteria) {
		LOGGER.info("Getting Api Requests(Report Logs) for Criteria : "
				+ reportLogCriteria);
		return reportLogDao.getReportLog(reportLogCriteria);
	}
	
	
	/**
	 * Validate ReportLogCriteria object 
	 * @param reportLogCriteria
	 * @return List<EquibaseDataSelesApiError>
	 */
	@Override
	public List<EquibaseDataSelesApiError> validateReportLogCriteriaObj(ReportLogCriteria reportLogCriteria) {
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors = new ArrayList<EquibaseDataSelesApiError>();
		EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = null;

        if (Util.isNull(reportLogCriteria)) {
        	equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_METHOD_ARGUMENTS;
        	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode ,
        			equibaseDataSelesApiErrorCode.getId() ,
        			equibaseDataSelesApiErrorCode.getDescription() ,
                    "Missing Report Log Criteria object in Fetching Report Logs(Api_Requests)");
        	equibaseDataSelesApiErrors.add(error);
        } else {
        	//From Date
        	if(Util.isEmptyString(reportLogCriteria.getFromDateStr())) {
        		equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_SATRT_DATE;
            	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode ,
            			equibaseDataSelesApiErrorCode.getId() ,
            			equibaseDataSelesApiErrorCode.getDescription() ,
                        "Missing From Date in Report Log Criteria object in Fetching Report Logs(Api_Requests)");
            	equibaseDataSelesApiErrors.add(error);
        	}
        	if(Util.isEmptyString(reportLogCriteria.getToDateStr())) {
        		equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_END_DATE;
            	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode ,
            			equibaseDataSelesApiErrorCode.getId() ,
            			equibaseDataSelesApiErrorCode.getDescription() ,
                        "Missing To Date in Report Log Criteria object in Fetching Report Logs(Api_Requests)");
            	equibaseDataSelesApiErrors.add(error);
        	}
        	if(Util.isNull(reportLogCriteria.getPageSize())) {
        		equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_PAGE_SIZE;
            	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode ,
            			equibaseDataSelesApiErrorCode.getId() ,
            			equibaseDataSelesApiErrorCode.getDescription() ,
                        "Missing Page Size in Report Log Criteria object in Fetching Report Logs(Api_Requests)");
            	equibaseDataSelesApiErrors.add(error);
        	}
        	if(Util.isNull(reportLogCriteria.getPageNumber())) {
        		equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_PAGE_NUMBER;
            	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode ,
            			equibaseDataSelesApiErrorCode.getId() ,
            			equibaseDataSelesApiErrorCode.getDescription() ,
                        "Missing Page Number in Report Log Criteria object in Fetching Report Logs(Api_Requests)");
            	equibaseDataSelesApiErrors.add(error);
        	}
        }
		return equibaseDataSelesApiErrors;
	}
	

	
	
	
}
