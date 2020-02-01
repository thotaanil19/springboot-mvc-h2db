package com.springboot.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.api.dao.TracksDao;
import com.springboot.api.domain.Track;
import com.springboot.api.service.TracksService;
import com.springboot.api.to.EquibaseDataSelesApiError;
import com.springboot.api.to.EquibaseDataSelesApiErrorCodes;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.SortingCriteria;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
@Service
public class TracksServiceImpl implements TracksService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TracksServiceImpl.class);
	
	@Autowired
	private TracksDao tracksDao;

	@Override
	public List<Track> getAllTracks() {
		LOGGER.info("Getting All Tracks From DB...");
		return tracksDao.getAllTracks();
	}
	
	/**
	 * Getting all tracks in pagination
	 * @param pageNum
	 * @param pageSize
	 * @return PaginationResultsTo<Track> - list of tracks
	 */
	@Override
	public PaginationResultsTo<Track> getTracks(Integer pageNum, Integer pageSize) {
		LOGGER.info("Fetching all Tracks from DB for page number : " + pageNum);
		PaginationResultsTo<Track> tracks = null;
		try {
			tracks = tracksDao.getTracks(pageNum, pageSize);
		} catch(Exception e){
			LOGGER.error("Error while retreiving all tracks for page number : " + pageNum, e);
		}
		return tracks;
	}
	
	/**
	 * Getting all tracks in pagination with Sorting by field
	 * @param pageNum
	 * @param pageSize
	 * @param field
	 * @param sortOrder
	 * @return PaginationResultsTo<Track> - list of tracks
	 */
	@Override
	public PaginationResultsTo<Track> getTracks(SortingCriteria tracksCriteria) {
		LOGGER.info("Fetching all Tracks from DB with Criteris : " + tracksCriteria);
		PaginationResultsTo<Track> tracks = null;
		try {
			if(!Util.isNull(tracksCriteria)) {
				//All fields are available
				if (!Util.isEmptyString(tracksCriteria.getSortingField())
						&& !Util.isEmptyString(tracksCriteria.getSortOrder())
						&& !Util.isNull(tracksCriteria.getPageNumber())
						&& !Util.isNull(tracksCriteria.getPageSize())) {
					tracks = tracksDao.getTracks(tracksCriteria.getPageNumber(),
							tracksCriteria.getPageSize(),
							tracksCriteria.getSortingField(),
							tracksCriteria.getSortOrder());
				} //only Sorting columns are available and page sizes/numbers are not available 
				else if (!Util.isEmptyString(tracksCriteria.getSortingField())
						&& !Util.isEmptyString(tracksCriteria.getSortOrder())
						&& Util.isNull(tracksCriteria.getPageNumber())
						&& Util.isNull(tracksCriteria.getPageSize())) {
					List<Track> tracksList = tracksDao.getAllTracks(
							tracksCriteria.getSortingField(),
							tracksCriteria.getSortOrder());
					tracks = new PaginationResultsTo<Track>();
					tracks.setResults(tracksList);
				} 
				//only page sizes/numbers are available and Sorting columns are not available 
				else if (Util.isEmptyString(tracksCriteria.getSortingField())
						&& Util.isEmptyString(tracksCriteria.getSortOrder())
						&& !Util.isNull(tracksCriteria.getPageNumber())
						&& !Util.isNull(tracksCriteria.getPageSize())) {
					tracks = tracksDao.getTracks(tracksCriteria.getPageNumber(),
							tracksCriteria.getPageSize());
				} 
				//page size/number and sorting fields are not available
				else {
					List<Track> tracksList = tracksDao.getAllTracks();
					tracks = new PaginationResultsTo<Track>();
					tracks.setResults(tracksList);
				}
			} else {
				List<Track> tracksList = tracksDao.getAllTracks();
				tracks = new PaginationResultsTo<Track>();
				tracks.setResults(tracksList);
			}
		} catch (Exception e) {
			LOGGER.error("Error while retreiving all tracks with criteria : " + tracksCriteria, e);
		}
		return tracks;
	}

	/**
	 * Sort all Tracks by field and returns tracks list
	 * @param field
	 * @param sortOrder
	 * @return List<Track> - List of Sorted tracks
	 * 
	 */
	@Override
	public List<Track> getAllTracks(String field, String sortOrder) {
		LOGGER.info("Fetching Sorted Tracks list of all Tracks from DB ");
		LOGGER.info("Sort Field : " + field);
		LOGGER.info("Sort Order : " + sortOrder);
		return tracksDao.getAllTracks(field, sortOrder);
	}
	
	/**
	 * Validating Trcks Soring Field
	 * @param field
	 * @return List<EquibaseDataSelesApiError>
	 */
	@Override
	public List<EquibaseDataSelesApiError> validateTracksSortField(
			final String field) {

		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors = new ArrayList<EquibaseDataSelesApiError>();
		EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = null;

		if (Util.isNull(field)) {
			equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_METHOD_ARGUMENTS;
			EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(
					equibaseDataSelesApiErrorCode,
					equibaseDataSelesApiErrorCode.getId(),
					equibaseDataSelesApiErrorCode.getDescription(),
					"Missing Tracks Sorting field");
			equibaseDataSelesApiErrors.add(error);
		} else if (!("id".equalsIgnoreCase(field)
				|| "name".equalsIgnoreCase(field)
				|| "country".equalsIgnoreCase(field) || "type"
					.equalsIgnoreCase(field))) {
			LOGGER.info("Invalid Tracks Sorting filed. Field should be id/name/country/type");
			equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FIELD_CUSTOMER_ID;
			EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(
					equibaseDataSelesApiErrorCode,
					equibaseDataSelesApiErrorCode.getId(),
					equibaseDataSelesApiErrorCode.getDescription(),
					"Missing Customer ID in Customer Edit");
			equibaseDataSelesApiErrors.add(error);
		}

		return equibaseDataSelesApiErrors;
	}


}
