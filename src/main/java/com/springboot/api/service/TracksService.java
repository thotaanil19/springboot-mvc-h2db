package com.springboot.api.service;

import java.util.List;

import com.springboot.api.domain.Track;
import com.springboot.api.to.EquibaseDataSelesApiError;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.SortingCriteria;

/**
 * 
 * @author anilt
 *
 */
public interface TracksService {

	List<Track> getAllTracks();
	
	List<Track> getAllTracks(String field, String sortOrder);

	PaginationResultsTo<Track> getTracks(Integer pageNum, Integer pageSize);
	
	List<EquibaseDataSelesApiError> validateTracksSortField(
			final String field);

	PaginationResultsTo<Track> getTracks(SortingCriteria tracksCriteria);

}
