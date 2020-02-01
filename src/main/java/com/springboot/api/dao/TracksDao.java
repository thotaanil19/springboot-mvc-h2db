package com.springboot.api.dao;

import java.util.List;

import com.springboot.api.domain.Track;
import com.springboot.api.to.PaginationResultsTo;

/**
 * 
 * @author anilt
 *
 */
public interface TracksDao {

	List<Track> getAllTracks();

	Track getTrack(String trackId);

	PaginationResultsTo<Track> getTracks(Integer pageNum, Integer pageSize);
	
	List<Track> getAllTracks(String field, String sortOrder);

	PaginationResultsTo<Track> getTracks(Integer pageNum, Integer pageSize,
			String field, String sortOrder);

}
