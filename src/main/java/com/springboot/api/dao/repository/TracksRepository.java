package com.springboot.api.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.api.domain.Track;

/**
 * 
 * @author anilt
 *
 */
@Repository
public interface TracksRepository extends JpaRepository<Track, String> {

	
}
