package com.springboot.api.dao;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.springboot.api.AbstractTest;
import com.springboot.api.dao.TracksDao;
import com.springboot.api.dao.impl.TracksDaoImpl;
import com.springboot.api.dao.repository.CustomerProductRepository;
import com.springboot.api.dao.repository.CustomerUserRepository;
import com.springboot.api.dao.repository.ProductRepository;
import com.springboot.api.dao.repository.ProductTracksRepository;
import com.springboot.api.dao.repository.TracksRepository;
import com.springboot.api.domain.Track;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.util.Util;

/**
 * @author menlo
 * 
 */

public class TracksDaoTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(TracksDaoImpl.class);

	@Autowired
	private TracksDao tracksDao;
	
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
	private ProductTracksRepository productTrackRepository;
	
	private Track track;
	
	@Before
	public void setUp() {
		
	}
	
	@After
	public void tearDown() {
	}
	
	/**
	 * Junit to GetAllTracks
	 */
	@Test
	public void testGetAllTracks(){
		String junitMethodName = "testGetAllProduct";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			List<Track> tracks = tracksDao.getAllTracks();
			Assert.assertNotNull(tracks);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		} catch (Exception ex) {
			String errMsg = "Error while executing the test case in "
					+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit to GetAllTracks
	 */
	@Test
	public void testGetAllTracksInSorting() {
		
		String methodName = "getAllTracks";
		LOGGER.info("Running JUnit for : " + methodName);
		
		try {
			Assert.assertFalse(Util.isNullOrEmptyCollection(tracksDao.getAllTracks("type", "ASC")));
			Assert.assertFalse(Util.isNullOrEmptyCollection(tracksDao.getAllTracks("country", "ASC")));
			Assert.assertFalse(Util.isNullOrEmptyCollection(tracksDao.getAllTracks("id", "ASC")));
			
			LOGGER.info("Done executing JUnit for : " + methodName);
			
		} catch (Exception ex) {
			String errMsg = "Error while executing the test case in "
					+ methodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit to getTrack
	 */
	@Test
	public void testGetTrack() {
		String junitMethodName = "testGetAllProduct";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try {
			List<Track> tracks = tracksRepository.findAll();
			if (tracks != null && !tracks.isEmpty()) {
				track = tracks.get(0);
			}
			Track trk = tracksDao.getTrack(track.getId());
			Assert.assertNotNull(trk);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		}catch (Exception ex) {
			String errMsg = "Error while executing the test case in "
				+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit to getTrackWithNullId
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testGetTrackWithNullId() {
		String junitMethodName = "testGetTrackWithNullId";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		tracksDao.getTrack(null);
		LOGGER.info("Done executing JUnit for : " + junitMethodName);
	}
	
	/**
	 * Junit to testGetTracksWithPageNumberNull
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testGetTracksWithPageNumberNull() {
		String junitMethodName = "testGetTracksWithPageNumberNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		tracksDao.getTracks(null,10);
		LOGGER.info("Done executing JUnit for : " + junitMethodName);
	}
	
	/**
	 * Junit to testGetTracksWithPageSizeNull
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testGetTracksWithPageSizeNull() {
		String junitMethodName = "testGetTracksWithPageSizeNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		tracksDao.getTracks(1,null);
		LOGGER.info("Done executing JUnit for : " + junitMethodName);
	}
	
	/**
	 * Junit to GetTracks
	 */
	@Test
	public void testGetTracks(){
		String junitMethodName = "testGetTracks";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try{
			PaginationResultsTo<Track> resultsTo = tracksDao.getTracks(1, 10);
			Assert.assertNotNull(resultsTo);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		}catch(Exception ex) {
			String errMsg = "Error while executing the test case in "
				+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit to testGetTracksWithPageSizeNull
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testGetTracksWithSortOrderNull() {
		String junitMethodName = "testGetTracksWithSortOrderNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		tracksDao.getTracks(1,10,"type",null);
		LOGGER.info("Done executing JUnit for : " + junitMethodName);
	}
	
	/**
	 * Junit to testGetTracksWithFeildNull
	 */
	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testGetTracksWithFeildNull() {
		String junitMethodName = "testGetTracksWithFeildNull";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		tracksDao.getTracks(1,10,null,"ASC");
		LOGGER.info("Done executing JUnit for : " + junitMethodName);
	}
	
	/**
	 * Junit to testGetTracksWithSortOrder_ASC_Feild_Type
	 */
	@Test
	public void testGetTracksWithSortOrder_ASC_Feild_Type(){
		String junitMethodName = "testGetTracksWithSortOrder_ASC_Feild_Type";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try{
			PaginationResultsTo<Track> resultsTo = tracksDao.getTracks(1, 10,"Type","ASC");
			Assert.assertNotNull(resultsTo);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		}catch(Exception ex) {
			String errMsg = "Error while executing the test case in "
				+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit to testGetTracksWithSortOrder_DESC_Feild_Type
	 */
	@Test
	public void testGetTracksWithSortOrder_DESC_Feild_Type(){
		String junitMethodName = "testGetTracksWithSortOrder_DESC_Feild_Type";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try{
			PaginationResultsTo<Track> resultsTo = tracksDao.getTracks(1, 10,"Type","DESC");
			Assert.assertNotNull(resultsTo);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		}catch(Exception ex) {
			String errMsg = "Error while executing the test case in "
				+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit to GetTracksWithSortOrder_ASC_Feild_Country
	 */
	@Test
	public void testGetTracksWithSortOrder_ASC_Feild_Country(){
		String junitMethodName = "testGetTracksWithSortOrder_ASC_Feild_Country";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try{
			PaginationResultsTo<Track> resultsTo = tracksDao.getTracks(1, 10,"country","ASC");
			Assert.assertNotNull(resultsTo);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		}catch(Exception ex) {
			String errMsg = "Error while executing the test case in "
				+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	/**
	 * Junit to GetTracksWithSortOrder_DESC_Feild_Country
	 */
	@Test
	public void testGetTracksWithSortOrder_DESC_Feild_Country(){
		String junitMethodName = "testGetTracksWithSortOrder_DESC_Feild_Country";
		LOGGER.info("Running JUnit for : " + junitMethodName);
		try{
			PaginationResultsTo<Track> resultsTo = tracksDao.getTracks(1, 10,"country","DESC");
			Assert.assertNotNull(resultsTo);
			LOGGER.info("Done executing JUnit for : " + junitMethodName);
		}catch(Exception ex) {
			String errMsg = "Error while executing the test case in "
				+ junitMethodName;
			LOGGER.error(errMsg, ex);
			fail(errMsg);
		}
	}
	
	
}
