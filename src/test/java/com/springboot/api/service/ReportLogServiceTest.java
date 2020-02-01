package com.springboot.api.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.springboot.api.AbstractTest;
import com.springboot.api.dao.ReportLogDao;
import com.springboot.api.domain.ApiRequest;
import com.springboot.api.service.ReportLogService;
import com.springboot.api.service.impl.ReportLogServiceImpl;
import com.springboot.api.to.EquibaseDataSelesApiError;
import com.springboot.api.to.EquibaseDataSelesApiErrorCodes;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.ReportLogCriteria;
import com.springboot.api.util.Util;

public class ReportLogServiceTest  extends AbstractTest{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportLogServiceTest.class);	
	
	@InjectMocks
	private ReportLogService reportLogService;
	
	@Mock
	private ReportLogDao reportLogDaoMock;
	
	@Before
	public void setUp(){
		 reportLogService =new ReportLogServiceImpl();
		 MockitoAnnotations.initMocks(this);
	    }
	
	@Test
	public void testGetReportLog(){
		String methodName = "testGetReportLog";
		LOGGER.info("Running Junit for : " + methodName);
		
		List<ApiRequest> results = new ArrayList<ApiRequest>();
		  results.add(buildMockApiRequest());
		PaginationResultsTo<ApiRequest> requests = new PaginationResultsTo<ApiRequest>();
		requests.setResults(results);
		requests.setPageNumber(1);
		requests.setPageSize(1);
		requests.setTotalNumberOfPages(1);
		requests.setTotalNumberOfResults(1L);
		
		Mockito.when(reportLogDaoMock.getReportLog((ReportLogCriteria)Mockito.anyObject())).thenReturn(requests);
		
		PaginationResultsTo<ApiRequest> paginationResults=reportLogService.getReportLog((ReportLogCriteria)Mockito.anyObject());
		Assert.assertNotNull(paginationResults);
		Assert.assertTrue(!Util.isNullOrEmptyCollection(paginationResults.getResults()));
		
		LOGGER.info("Completed running Junit for : " + methodName);
		
	}
     
	@Test
	public void testvalidateReportLogCriteriaObjWithNull(){
    	  String methodName = "testvalidateReportLogCriteriaObjWithNull";
  		LOGGER.info("Running Junit for : " + methodName);
  		
  		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors=reportLogService.validateReportLogCriteriaObj(null); 
  		EquibaseDataSelesApiErrorCodes code=EquibaseDataSelesApiErrorCodes.MISSING_METHOD_ARGUMENTS;
  		 for(EquibaseDataSelesApiError apiError:equibaseDataSelesApiErrors){
  			 Assert.assertEquals(code, apiError.getCode());
  		 }
  		LOGGER.info("Completed running Junit for : " + methodName);  
      }
	@Test
	public void testvalidateReportLogCriteriaObjWithNullFromDateStr(){
		String methodName = "testvalidateReportLogCriteriaObjWithNullFromDateStr";
		LOGGER.info("Running Junit for : " + methodName);
		ReportLogCriteria criteria=buildMockReportLogCriteria();
		criteria.setFromDateStr(null);
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors=reportLogService.validateReportLogCriteriaObj(criteria); 
		EquibaseDataSelesApiErrorCodes code = EquibaseDataSelesApiErrorCodes.MISSING_SATRT_DATE;
		for(EquibaseDataSelesApiError apiError:equibaseDataSelesApiErrors){
			Assert.assertEquals(code, apiError.getCode());
		}
		LOGGER.info("Completed running Junit for : " + methodName);  
	}
	
	@Test
	public void testvalidateReportLogCriteriaObjWithNullToDateStr(){
		String methodName = "testvalidateReportLogCriteriaObjWithNullToDateStr";
		LOGGER.info("Running Junit for : " + methodName);
		ReportLogCriteria criteria=buildMockReportLogCriteria();
		criteria.setToDateStr(null);
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors=reportLogService.validateReportLogCriteriaObj(criteria); 
		EquibaseDataSelesApiErrorCodes code = EquibaseDataSelesApiErrorCodes.MISSING_END_DATE;
		for(EquibaseDataSelesApiError apiError:equibaseDataSelesApiErrors){
			Assert.assertEquals(code, apiError.getCode());
		}
		LOGGER.info("Completed running Junit for : " + methodName);  
	}
	@Test
	public void testvalidateReportLogCriteriaObjWithNullPageSize(){
		String methodName = "testvalidateReportLogCriteriaObjWithNullPageSize";
		LOGGER.info("Running Junit for : " + methodName);
		ReportLogCriteria criteria=buildMockReportLogCriteria();
		criteria.setPageSize(null);
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors=reportLogService.validateReportLogCriteriaObj(criteria); 
		EquibaseDataSelesApiErrorCodes code = EquibaseDataSelesApiErrorCodes.MISSING_PAGE_SIZE;
		for(EquibaseDataSelesApiError apiError:equibaseDataSelesApiErrors){
			Assert.assertEquals(code, apiError.getCode());
		}
		LOGGER.info("Completed running Junit for : " + methodName);  
	}
	@Test
	public void testvalidateReportLogCriteriaObjWithNullPageNumber(){
		String methodName = "testvalidateReportLogCriteriaObjWithNullPageNumber";
		LOGGER.info("Running Junit for : " + methodName);
		ReportLogCriteria criteria=buildMockReportLogCriteria();
		criteria.setPageNumber(null);
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors=reportLogService.validateReportLogCriteriaObj(criteria); 
		EquibaseDataSelesApiErrorCodes code = EquibaseDataSelesApiErrorCodes.MISSING_PAGE_NUMBER;
		for(EquibaseDataSelesApiError apiError:equibaseDataSelesApiErrors){
			Assert.assertEquals(code, apiError.getCode());
		}
		LOGGER.info("Completed running Junit for : " + methodName);  
	}
}
