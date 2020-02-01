package com.springboot.api.dao.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import com.springboot.api.dao.ReportLogDao;
import com.springboot.api.dao.repository.ApiRequestRepository;
import com.springboot.api.domain.ApiRequest;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.ReportLogCriteria;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */

@Repository
public class ReportLogDaoImpl implements ReportLogDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportLogDaoImpl.class);
	
	@Autowired
	private ApiRequestRepository apiRequestRepository;

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

		PaginationResultsTo<ApiRequest> paginationResults = null;

		if (Util.isNull(reportLogCriteria)
				|| Util.isNull(reportLogCriteria.getPageNumber())
				|| Util.isNull(reportLogCriteria.getPageSize())
				|| Util.isNull(reportLogCriteria.getFromDateStr())
				|| Util.isNull(reportLogCriteria.getToDateStr())) {
			throw new IllegalArgumentException(
					"Missing pageNum/pageSize/From Date/To Date method arguments");
		}

		try {

			Order o1 = new Order(Direction.DESC, "timeStamp");
			Order o2 = new Order(Direction.ASC, "endpoint").ignoreCase();

			Sort sort = new Sort(new Order[] { o1, o2 });

			Pageable pageable = new PageRequest(
					reportLogCriteria.getPageNumber() - 1,
					reportLogCriteria.getPageSize(), sort);
			
			Date fromDate = Util.stringToDateConversion(reportLogCriteria.getFromDateStr());
			Date toDate = Util.stringToDateConversion(reportLogCriteria.getFromDateStr());


			Page<ApiRequest> apiRequests = apiRequestRepository
					.findByTimeStampBetween(pageable, fromDate, toDate);

			if (!Util.isNull(apiRequests)) {
				paginationResults = new PaginationResultsTo<ApiRequest>();
				paginationResults.setPageNumber(reportLogCriteria
						.getPageNumber());
				paginationResults.setPageSize(reportLogCriteria.getPageSize());
				paginationResults.setTotalNumberOfPages(apiRequests
						.getTotalPages());
				paginationResults.setTotalNumberOfResults(apiRequests
						.getTotalElements());
				paginationResults.setResults(apiRequests.getContent());
				apiRequests.getTotalPages();
			}
		} catch (Exception e) {
			LOGGER.error("Error while retreiving Reort Logs for criteria : "
					+ reportLogCriteria, e);
		}

		return paginationResults;
	}
	
	
	@Override
	public List<ApiRequest> getReportLogByCustomerProductIds(List<Long> customerProductIds) {
		List<ApiRequest> apiRequests = null;
		if(!Util.isNullOrEmptyCollection(customerProductIds)) {
			apiRequests = apiRequestRepository.findByCustProdIds(new HashSet<Long>(customerProductIds));
		}
		return apiRequests;
	}
	
	

}
