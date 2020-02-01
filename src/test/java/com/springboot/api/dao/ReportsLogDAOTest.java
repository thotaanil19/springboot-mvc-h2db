package com.springboot.api.dao;

import static org.junit.Assert.fail;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.springboot.api.AbstractTest;
import com.springboot.api.dao.ReportLogDao;
import com.springboot.api.dao.impl.ReportLogDaoImpl;
import com.springboot.api.dao.repository.ApiRequestRepository;
import com.springboot.api.dao.repository.CustomerProductRepository;
import com.springboot.api.dao.repository.CustomerUserRepository;
import com.springboot.api.dao.repository.ProductRepository;
import com.springboot.api.dao.repository.ProductTracksRepository;
import com.springboot.api.dao.repository.TracksRepository;
import com.springboot.api.domain.ApiRequest;
import com.springboot.api.domain.CustomerProduct;
import com.springboot.api.domain.CustomerUser;
import com.springboot.api.domain.Product;
import com.springboot.api.domain.ProductTrack;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.ReportLogCriteria;

/**
 * 
 * @author anilt
 *
 */
public class ReportsLogDAOTest extends AbstractTest {

	
private static final Logger LOGGER = LoggerFactory.getLogger(ReportLogDaoImpl.class);
	
	@Autowired
	private ApiRequestRepository apiRequestRepository;
	@Autowired
	private ReportLogDao reportLogDao;
	
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CustomerProductRepository customerProductRepository;
	@Autowired
	private ProductTracksRepository productTracksRepository;
	
	@Autowired
	private TracksRepository tracksRepository;
	
	@Autowired
	private CustomerUserRepository customerRepository;
	
	@Autowired
	private ProductTracksRepository ProductTrackRepository;
	
	private Product product;
	private CustomerUser customer;
	private ProductTrack productTrack;
	CustomerProduct customerProduct;
	
	@Before
	public void setUp() {
		customer = insertTestCustomerUser();
		product = insertTestProduct();
		customerProduct = insertTestCustomerProduct();
		productTrack = insertTestProductTrack();
	}
	
	@After
	public void tearDown() {
		ProductTrackRepository.delete(productTrack.getId());
		customerProductRepository.delete(customerProduct.getId());
		customerRepository.delete(customer.getId());
		productRepository.delete(product.getId());
	}
	
	
	/**
	 * Junit to testGetReportLog
	 */
	@Test
	@Transactional
	public void testGetReportLog() {
		String junitMethodName = "testGetReportLog";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			
			ApiRequest apiRequest = new ApiRequest();
			apiRequest.setApiKey("test");
			apiRequest.setAttribute1("test");
			apiRequest.setCustProdId(customerProduct.getId());
			apiRequest.setEndpoint("Test-junit");
			apiRequest.setTimeStamp(new Date());
			apiRequest.setIp4("localhost");
			apiRequest.setResponseCode(200L);
			apiRequestRepository.save(apiRequest);
			
			
			
			
			ReportLogCriteria reportLogCriteria =new ReportLogCriteria();
			reportLogCriteria.setPageNumber(1);
			reportLogCriteria.setPageSize(10);
			reportLogCriteria.setFromDateStr("24/02/2015");
			reportLogCriteria.setToDateStr(new Date().toString());
			
			
			PaginationResultsTo<ApiRequest> resultsTo = reportLogDao.getReportLog(reportLogCriteria);
			Assert.assertNotNull(resultsTo);
			
			if(apiRequest != null && apiRequest.getId() != null) {
				apiRequestRepository.delete( apiRequest.getId());
			}
			
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch (Exception ex) {
			String errMsg = "Error while executing the test case in "
					+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit to test GetReportLogPageNumberNull
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testGetReportLogPageNumberNull() {
		String junitMethodName = "testGetReportLogPageNumberNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		
		ReportLogCriteria reportLogCriteria =new ReportLogCriteria();
		reportLogCriteria.setPageNumber(null);
		reportLogCriteria.setPageSize(10);
		reportLogCriteria.setFromDateStr("24-02-2015");
		reportLogCriteria.setToDateStr(new Date().toString());
		reportLogDao.getReportLog(reportLogCriteria);
			
		LOGGER.info("Done executing JUnit for : " + junitMethodName);
		
	}
	
	/**
	 * Junit to test GetReportLogPageSizeNull
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testGetReportLogPageSizeNull() {
		String junitMethodName = "testGetReportLogPageSizeNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		
		ReportLogCriteria reportLogCriteria =new ReportLogCriteria();
		reportLogCriteria.setPageNumber(1);
		reportLogCriteria.setPageSize(null);
		reportLogCriteria.setFromDateStr("24-02-2015");
		reportLogCriteria.setToDateStr(new Date().toString());
		reportLogDao.getReportLog(reportLogCriteria);
			
		LOGGER.info("Done executing JUnit for : " + junitMethodName);
		
	}
	
	/**
	 * Junit to test GetReportLogPageSizeNull
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testGetReportLogFromDateNull() {
		String junitMethodName = "testGetReportLogFromDateNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		
		ReportLogCriteria reportLogCriteria =new ReportLogCriteria();
		reportLogCriteria.setPageNumber(1);
		reportLogCriteria.setPageSize(10);
		reportLogCriteria.setFromDateStr(null);
		reportLogCriteria.setToDateStr(new Date().toString());
		reportLogDao.getReportLog(reportLogCriteria);
			
		LOGGER.info("Done executing JUnit for : " + junitMethodName);
		
	}
	
	/**
	 * Junit to test GetReportLogPageSizeNull
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testGetReportLogToDateNull() {
		String junitMethodName = "testGetReportLogToDateNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		
		ReportLogCriteria reportLogCriteria =new ReportLogCriteria();
		reportLogCriteria.setPageNumber(1);
		reportLogCriteria.setPageSize(10);
		reportLogCriteria.setFromDateStr("24-02-2015");
		reportLogCriteria.setToDateStr(null);
		reportLogDao.getReportLog(reportLogCriteria);
			
		LOGGER.info("Done executing JUnit for : " + junitMethodName);
		
	}
}
