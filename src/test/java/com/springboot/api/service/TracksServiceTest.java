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
import com.springboot.api.dao.TracksDao;
import com.springboot.api.domain.Track;
import com.springboot.api.service.TracksService;
import com.springboot.api.service.impl.TracksServiceImpl;
import com.springboot.api.to.EquibaseDataSelesApiError;
import com.springboot.api.to.EquibaseDataSelesApiErrorCodes;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.SortingCriteria;
import com.springboot.api.util.Util;

public class TracksServiceTest extends AbstractTest  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TracksServiceTest.class);
	
	@InjectMocks
	private TracksService tracksService;
	
	@Mock
	private TracksDao tracksDaoMock;
	
	
	@Before
	public void setUp(){
		tracksService =new TracksServiceImpl();
		 MockitoAnnotations.initMocks(this);
	    }
	 
	@Test
	public void testGetAllTracks(){
        
		String methodName = "testGetAllTracks";
		LOGGER.info("Running Junit for : " + methodName);
		List<Track> tracks= new ArrayList<Track>();
		tracks.add(builMockTrack());

		Mockito.when(tracksDaoMock.getAllTracks()).thenReturn(tracks);

		List<Track> tracks2=tracksService.getAllTracks();
		Assert.assertNotNull(tracks2);
		Assert.assertNotNull(tracks2.size());
		LOGGER.info("Completed running Junit for : " + methodName);



	}
	@Test
	public void testGetAllTracksWithFieldAndSortOder(){
        
		String methodName = "testGetAllTracks";
		LOGGER.info("Running Junit for : " + methodName);
		List<Track> tracks= new ArrayList<Track>();
		tracks.add(builMockTrack());

		Mockito.when(tracksDaoMock.getAllTracks(Mockito.anyString(),Mockito.anyString())).thenReturn(tracks);

		List<Track> tracks2=tracksService.getAllTracks(Mockito.anyString(),Mockito.anyString());
		Assert.assertNotNull(tracks2);
		Assert.assertNotNull(tracks2.size());
		LOGGER.info("Completed running Junit for : " + methodName);



	}
	@Test
	public void testGetTracksWithPageNumAndPageSize(){
        
		String methodName = "testGetTracksWithPageNumAndPageSize";
		LOGGER.info("Running Junit for : " + methodName);
		PaginationResultsTo<Track> paginationResults=new PaginationResultsTo<Track>();
		List<Track> tracks= new ArrayList<Track>();
		tracks.add(builMockTrack());
		paginationResults.setResults(tracks);
		Mockito.when(tracksDaoMock.getTracks(Mockito.anyInt(), Mockito.anyInt())).thenReturn(paginationResults);

		PaginationResultsTo<Track> tracks2= tracksService.getTracks(Mockito.anyInt(), Mockito.anyInt());
		Assert.assertNotNull(tracks2);
		Assert.assertTrue(!Util.isNullOrEmptyCollection(tracks2.getResults()));
		LOGGER.info("Completed running Junit for : " + methodName);



	}
	
	@Test
	public void testGetTracksWithSortingCriteriaWithAllFields(){
		String methodName = "testGetTracksWithSortingCriteriaWithAllFields";
		LOGGER.info("Running Junit for : " + methodName);
		SortingCriteria sortingCriteria=buildMockSortingCriteria("company");
		PaginationResultsTo<Track> paginationResults=new PaginationResultsTo<Track>();
		List<Track> tracks= new ArrayList<Track>();
		tracks.add(builMockTrack());
		paginationResults.setResults(tracks);
		Mockito.when(tracksDaoMock.getTracks(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(), Mockito.anyString())).thenReturn(paginationResults);
		PaginationResultsTo<Track> tracks2= tracksService.getTracks(sortingCriteria);
		Assert.assertNotNull(tracks2);
		Assert.assertTrue(!Util.isNullOrEmptyCollection(tracks2.getResults()));
		LOGGER.info("Completed running Junit for : " + methodName);
		
	}
	@Test
	public void testGetTracksWithSortingCriteriaWithSortingFieldAndSortingOrder(){
		String methodName = "testGetTracksWithSortingCriteriaWithSortingFieldAndSortingOrder";
		LOGGER.info("Running Junit for : " + methodName);
		SortingCriteria sortingCriteria=buildMockSortingCriteria("company");
		sortingCriteria.setPageNumber(null);
		sortingCriteria.setPageSize(null);
		List<Track> tracks= new ArrayList<Track>();
		tracks.add(builMockTrack());
		Mockito.when(tracksDaoMock.getAllTracks(Mockito.anyString(), Mockito.anyString())).thenReturn(tracks);
		PaginationResultsTo<Track> tracks2= tracksService.getTracks(sortingCriteria);
		Assert.assertNotNull(tracks2);
		Assert.assertTrue(!Util.isNullOrEmptyCollection(tracks2.getResults()));
		LOGGER.info("Completed running Junit for : " + methodName);
		
	}
	@Test
	public void testGetTracksWithSortingCriteriaWithPageNumberAndPageSize(){
		String methodName = "testGetTracksWithSortingCriteriaWithPageNumberAndPageSize";
		LOGGER.info("Running Junit for : " + methodName);
		SortingCriteria sortingCriteria=buildMockSortingCriteria("company");
		sortingCriteria.setSortingField(null);
		sortingCriteria.setSortOrder(null);
		PaginationResultsTo<Track> paginationResults=new PaginationResultsTo<Track>();
		List<Track> tracks= new ArrayList<Track>();
		tracks.add(builMockTrack());
		paginationResults.setResults(tracks);
		Mockito.when(tracksDaoMock.getTracks(Mockito.anyInt(),Mockito.anyInt())).thenReturn(paginationResults);
		PaginationResultsTo<Track> tracks2= tracksService.getTracks(sortingCriteria);
		Assert.assertNotNull(tracks2);
		Assert.assertTrue(!Util.isNullOrEmptyCollection(tracks2.getResults()));
		LOGGER.info("Completed running Junit for : " + methodName);
		
	}
	@Test
	public void testvalidateTracksSortFieldWithNull(){
		String methodName = "testvalidateTracksSortFieldWithNull";
		LOGGER.info("Running Junit for : " + methodName);
		EquibaseDataSelesApiErrorCodes Code=EquibaseDataSelesApiErrorCodes.MISSING_METHOD_ARGUMENTS;
		List<EquibaseDataSelesApiError> apiErrors=tracksService.validateTracksSortField(null);
		for(EquibaseDataSelesApiError error:apiErrors){
			Assert.assertEquals(Code, error.getCode());
		}
		LOGGER.info("Completed running Junit for : " + methodName);	
	}
	@Test
	public void testvalidateTracksSortFieldOtherThanIdOrNameOrCountryOrType(){
		String methodName = "testvalidateTracksSortFieldOtherThanIdOrNameOrCountryOrType";
		LOGGER.info("Running Junit for : " + methodName);
		EquibaseDataSelesApiErrorCodes Code=EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FIELD_CUSTOMER_ID;
		List<EquibaseDataSelesApiError> apiErrors=tracksService.validateTracksSortField("hi");
		for(EquibaseDataSelesApiError error:apiErrors){
			Assert.assertEquals(Code, error.getCode());
		}
		LOGGER.info("Completed running Junit for : " + methodName);	
	}
	
	@Test
	public void testGetTracksWithSortingCriteriaWithEmptyObject(){
		String methodName = "testGetTracksWithSortingCriteriaWithEmptyObject";
		LOGGER.info("Running Junit for : " + methodName);
		
		SortingCriteria sortingCriteria = new SortingCriteria();
	
		PaginationResultsTo<Track> paginationResults=new PaginationResultsTo<Track>();
		List<Track> tracks= new ArrayList<Track>();
		tracks.add(builMockTrack());
		paginationResults.setResults(tracks);
		
		Mockito.when(tracksDaoMock.getAllTracks()).thenReturn(tracks);
		
		PaginationResultsTo<Track> tracks2= tracksService.getTracks(sortingCriteria);
		
		Assert.assertNotNull(tracks2);
		Assert.assertTrue(!Util.isNullOrEmptyCollection(tracks2.getResults()));
		LOGGER.info("Completed running Junit for : " + methodName);
		
	}
	
	@Test
	public void testGetTracksWithSortingCriteriaWithNullObject(){
		String methodName = "testGetTracksWithSortingCriteriaWithNullObject";
		LOGGER.info("Running Junit for : " + methodName);
		
		PaginationResultsTo<Track> paginationResults=new PaginationResultsTo<Track>();
		List<Track> tracks= new ArrayList<Track>();
		tracks.add(builMockTrack());
		paginationResults.setResults(tracks);
		
		Mockito.when(tracksDaoMock.getAllTracks()).thenReturn(tracks);
		
		PaginationResultsTo<Track> tracks2= tracksService.getTracks(null);
		
		Assert.assertNotNull(tracks2);
		Assert.assertTrue(!Util.isNullOrEmptyCollection(tracks2.getResults()));
		LOGGER.info("Completed running Junit for : " + methodName);
		
	}
	

}
