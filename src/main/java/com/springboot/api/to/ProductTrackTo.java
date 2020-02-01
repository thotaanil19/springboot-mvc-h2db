package com.springboot.api.to;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * @author anilt
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductTrackTo implements Serializable {

	private static final long serialVersionUID = 45640247316344880L;

	private Long id;

	private Long customerProductId;

	private String trackId;
	
	private TrackTo track;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerProductId() {
		return customerProductId;
	}

	public void setCustomerProductId(Long customerProductId) {
		this.customerProductId = customerProductId;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public TrackTo getTrack() {
		return track;
	}

	public void setTrack(TrackTo track) {
		this.track = track;
	}



}
