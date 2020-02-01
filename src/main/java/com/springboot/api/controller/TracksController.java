package com.springboot.api.controller;

import static com.springboot.api.util.Util.CONTENT_TYPE;
import static com.springboot.api.util.Util.CONTENT_VALUE;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.api.domain.Track;
import com.springboot.api.service.TracksService;
import com.springboot.api.to.EquibaseDataSelesApiError;
import com.springboot.api.to.EquibaseDataSelesApiErrorResource;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.SortingCriteria;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
@Controller
@RequestMapping(value = "/tracks")
public class TracksController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TracksController.class);
	
	@Autowired
	private TracksService tracksService;
	
	/**
	 * Get all tracks from DB
	 * @return
	 */
	@RequestMapping(value = "/getAllTracks", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Track>> getAllTracks() {
		LOGGER.info("Getting Tracks...");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(CONTENT_TYPE, CONTENT_VALUE);
		List<Track> tracks = tracksService.getAllTracks();
		if(Util.isNull(tracks)) {
			tracks = new ArrayList<Track>();
		}
		return new ResponseEntity<List<Track>>(tracks, responseHeaders, HttpStatus.OK);

	}
	
	/**
	 * Get the tracks list in pagination
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getTracks/{pageNum}/{pageSize}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getTracks(
			@PathVariable("pageNum") Integer pageNum,
			@PathVariable("pageSize") Integer pageSize)
			throws Exception {
		LOGGER.info("Getting Tracks for page number : " + pageNum);
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);
		
		PaginationResultsTo<Track> tracks = tracksService.getTracks(pageNum, pageSize);
		
		if(Util.isNull(tracks)) {
			tracks = new PaginationResultsTo<Track>();
		}
		
		return new ResponseEntity<PaginationResultsTo<Track>>(tracks, responseHeaders,
				HttpStatus.OK);
	}
	
	
	/**
	 * Get the tracks list with sorting by field and Sort order
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getSortedTracks/{field}/{sortOrder}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getAllTracksBySorting(
			@PathVariable("field") String field,
			@PathVariable("sortOrder") String sortOrder) throws Exception {

		LOGGER.info("Getting Tracks list by Sorting");
		LOGGER.info("Sort Field : " + field);
		LOGGER.info("Sort Order : " + sortOrder);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);

		LOGGER.info("Validating Tracks Sorting field...");
		List<EquibaseDataSelesApiError> errorMessages = tracksService.validateTracksSortField(field);
		if (!Util.isNullOrEmptyCollection(errorMessages)) {
			LOGGER.info("Validation is done and validation errors exists...");

			EquibaseDataSelesApiErrorResource errorResource = new EquibaseDataSelesApiErrorResource(
					errorMessages);

			return new ResponseEntity<EquibaseDataSelesApiErrorResource>(
					errorResource, responseHeaders, HttpStatus.BAD_REQUEST);
		} else {
			List<Track> tracks = tracksService.getAllTracks(field, sortOrder);

			if (Util.isNull(tracks)) {
				tracks = new ArrayList<Track>();
			}

			return new ResponseEntity<List<Track>>(tracks, responseHeaders,
					HttpStatus.OK);
		}

	}
	
	/**
	 * Get the tracks list with sorting by field and Sort order
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getTracks", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity getAllTracksBySortingInPagination(
			@RequestBody SortingCriteria sortCriteria) throws Exception {

		LOGGER.info("Getting Tracks list with criteria : " + sortCriteria);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(CONTENT_TYPE, CONTENT_VALUE);

			PaginationResultsTo<Track> tracks = tracksService.getTracks(sortCriteria);

			if (Util.isNull(tracks)) {
				tracks = new PaginationResultsTo<Track>();
			}

			return new ResponseEntity<PaginationResultsTo<Track>>(tracks,
					responseHeaders, HttpStatus.OK);
	}
	

}
