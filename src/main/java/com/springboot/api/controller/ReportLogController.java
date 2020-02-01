package com.springboot.api.controller;

import static com.springboot.api.util.Util.CONTENT_TYPE;
import static com.springboot.api.util.Util.CONTENT_VALUE;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.api.domain.ApiRequest;
import com.springboot.api.service.ReportLogService;
import com.springboot.api.to.EquibaseDataSelesApiError;
import com.springboot.api.to.EquibaseDataSelesApiErrorResource;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.ReportLogCriteria;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
@Controller
@RequestMapping("/reports")
public class ReportLogController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportLogController.class);
	
	@Autowired
	private ReportLogService reportLogService;
	
	/**
	 * Gets Report Logs(api_requests)
	 * @param reportLogCriteria
	 * @return ApiRequest
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getReports")
	@ResponseBody
	public ResponseEntity getReportLog(
			@RequestBody ReportLogCriteria reportLogCriteria) {
		LOGGER.info("Getting Api Requests(Report Logs) for Criteria : "
				+ reportLogCriteria);

		LOGGER.info("Validating ReportLogCriteria object in fetching Report Logs(Api_Requests)");

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);

		List<EquibaseDataSelesApiError> validationErrors = reportLogService
				.validateReportLogCriteriaObj(reportLogCriteria);

		if (!Util.isNullOrEmptyCollection(validationErrors)) {

			LOGGER.info("Validation is done and validation errors exists...");

			EquibaseDataSelesApiErrorResource errorResource = new EquibaseDataSelesApiErrorResource(
					validationErrors);

			return new ResponseEntity<EquibaseDataSelesApiErrorResource>(
					errorResource, responseHeaders, HttpStatus.BAD_REQUEST);

		} else {
			PaginationResultsTo<ApiRequest> reportLogs = reportLogService
					.getReportLog(reportLogCriteria);
			
			if(Util.isNull(reportLogs)) {
				reportLogs = new PaginationResultsTo<ApiRequest>();
			}

			return new ResponseEntity<PaginationResultsTo<ApiRequest>>(
					reportLogs, responseHeaders, HttpStatus.OK);
		}
	}
	
	
	
	
}
