package com.springboot.api.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import com.springboot.api.dao.TracksDao;
import com.springboot.api.dao.repository.TracksRepository;
import com.springboot.api.domain.Track;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
@Repository
public class TracksDaoImpl implements TracksDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TracksDaoImpl.class);

	@Autowired
	private TracksRepository tracksRepository;

	/**
	 * Retrieving all Tracks 
	 * return list of tracks
	 */
	@Override
	public List<Track> getAllTracks() {
		LOGGER.info("Getting All Tracks From DB...");
		List<Track> tracks = null;
		try {
			
			Order o1 = new Order(Direction.DESC,"type").ignoreCase();
			Order o2 = new Order(Direction.DESC,"country").ignoreCase();
			Order o3 = new Order(Direction.ASC,"id").ignoreCase();

			Sort sort = new Sort(new Order[]{o1,o2,o3}) ;
			
			
			tracks = tracksRepository.findAll(sort);
		} catch (Exception e) {
			LOGGER.error("Error while fetching all Tracks ", e);
		}
		return tracks;
	}

	/**
	 * Retrieving Track by track id 
	 * @param trackId
	 * return Track
	 */
	@Override
	public Track getTrack(String trackId) {
		LOGGER.info("Getting Track by track id : " + trackId);
		if(Util.isNull(trackId)) {
			throw new IllegalArgumentException("To get Track object by track id, track id should not be null...");
		}
		return tracksRepository.findOne(trackId);
	}
	
	
	/**
	 * Fetches all Tracks for page
	 * @param pageNum
	 * @param pageSize
	 * @return PaginationResultsTo<Product>
	 */
	@Override
	public PaginationResultsTo<Track> getTracks(Integer pageNum, Integer pageSize) {
		LOGGER.info("Fetching all Tracks from DB for page number : " + pageNum);
		PaginationResultsTo<Track> paginationResults = null;
		if(Util.isNull(pageNum) || Util.isNull(pageSize)) {
			throw new IllegalArgumentException("Missing pageNum/pageSize method arguments");
		}
		try {
			
			Order o1 = new Order(Direction.DESC,"type").ignoreCase();
			Order o2 = new Order(Direction.DESC,"country").ignoreCase();
			Order o3 = new Order(Direction.ASC,"id").ignoreCase();

			Sort sort = new Sort(new Order[]{o1,o2,o3}) ;
			
			PageRequest request = new PageRequest(pageNum - 1, pageSize, sort);
			
			Page<Track> tracks = tracksRepository.findAll(request);
			if(!Util.isNull(tracks)) {
				paginationResults = new PaginationResultsTo<Track>();
				paginationResults.setPageNumber(pageNum);
				paginationResults.setPageSize(pageSize);
				paginationResults.setTotalNumberOfPages(tracks.getTotalPages());
				paginationResults.setTotalNumberOfResults(tracks.getTotalElements());
				paginationResults.setResults(tracks.getContent());
				tracks.getTotalPages();
			}
		} catch(Exception e){
			LOGGER.error("Error while retreiving all Tracks for Pagination --> page Number : " + pageNum + " , Page Size : " + pageSize, e);
		}

		return paginationResults;
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
	public PaginationResultsTo<Track> getTracks(Integer pageNum, Integer pageSize, String field, String sortOrder) {
		if (Util.isEmptyString(field)
				|| Util.isEmptyString(sortOrder)
				|| !("ASC".equalsIgnoreCase(sortOrder) || "DESC"
						.equalsIgnoreCase(sortOrder))) {
			throw new IllegalArgumentException(
					"Invalid Arguments to sort Tracks in pagination");
		}
		PaginationResultsTo<Track> paginationResults = null;
		try {
			
			Order o1 = new Order(Direction.fromString(sortOrder), field).ignoreCase();
			Order o2 = null;
			Order o3 = null;
			if("type".equalsIgnoreCase(field)) {
				o2 = new Order(Direction.DESC,"country").ignoreCase();
				o3 = new Order(Direction.ASC,"id").ignoreCase();
			} else if("country".equalsIgnoreCase(field)) {
				o2 = new Order(Direction.DESC,"type").ignoreCase();
				o3 = new Order(Direction.ASC,"id").ignoreCase();
			} else {
				o2 = new Order(Direction.ASC,"id").ignoreCase();
				o3 = new Order(Direction.DESC,"country").ignoreCase();
			}
			

			Sort sort = new Sort(new Order[]{o1,o2,o3}) ;
			
			PageRequest request = new PageRequest(pageNum - 1, pageSize, sort);

			Page<Track> tracks = tracksRepository.findAll(request);
			if (!Util.isNull(tracks)) {
				paginationResults = new PaginationResultsTo<Track>();
				paginationResults.setPageNumber(pageNum);
				paginationResults.setPageSize(pageSize);
				paginationResults.setTotalNumberOfPages(tracks.getTotalPages());
				paginationResults.setTotalNumberOfResults(tracks
						.getTotalElements());
				paginationResults.setResults(tracks.getContent());
				tracks.getTotalPages();
			}
		} catch (Exception e) {
			LOGGER.error(
					"Error while retreiving all Tracks with Sorting for Pagination --> page Number : "
							+ pageNum
							+ " , Page Size : "
							+ pageSize
							+ " , Sort field : "
							+ field
							+ " , Sort order : "
							+ sortOrder, e);
		}

		return paginationResults;
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
		if (Util.isEmptyString(field)
				|| Util.isEmptyString(sortOrder)
				|| !("ASC".equalsIgnoreCase(sortOrder) || "DESC"
						.equalsIgnoreCase(sortOrder))) {
			throw new IllegalArgumentException(
					"Invalid Arguments to sort Tracks");
		}
		List<Track> tracks = null;
		try {
			Order o1 = new Order(Direction.fromString(sortOrder), field).ignoreCase();
			Order o2 = null;
			Order o3 = null;
			if("type".equalsIgnoreCase(field)) {
				o2 = new Order(Direction.DESC,"country").ignoreCase();
				o3 = new Order(Direction.ASC,"id").ignoreCase();
			} else if("country".equalsIgnoreCase(field)) {
				o2 = new Order(Direction.DESC,"type").ignoreCase();
				o3 = new Order(Direction.ASC,"id").ignoreCase();
			} else {
				o2 = new Order(Direction.ASC,"type").ignoreCase();
				o3 = new Order(Direction.DESC,"country").ignoreCase();
			}
			
			Sort sort = new Sort(new Order[]{o1,o2,o3}) ;
			
			tracks = tracksRepository.findAll(sort);
		
		} catch (Exception e) {
			LOGGER.error(
					"Error while retreiving all Tracks With Sorting by field : "
							+ field + " , Sort Order : " + sortOrder, e);
		}
		return tracks;
	}

	

}
