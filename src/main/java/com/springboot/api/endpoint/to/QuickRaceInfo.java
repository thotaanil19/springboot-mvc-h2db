package com.springboot.api.endpoint.to;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * @author anilt
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuickRaceInfo implements Serializable {
	
	private static final long serialVersionUID = 408340109555204049L;
	
	private String trackId;
	private String raceDate;
	private Integer raceNumber;
	private String country;
	private String dayEvening;
	private String endpoint;
	private String postTime;
	private String raceBreed;
    
	public String getRaceBreed() {
		return raceBreed;
	}
	public void setRaceBreed(String raceBreed) {
		this.raceBreed = raceBreed;
	}
	public String getTrackId() {
		return trackId;
	}
	public void setTrackId(String trakcId) {
		this.trackId = trakcId;
	}
	public String getRaceDate() {
		return raceDate;
	}
	public void setRaceDate(String raceDate) {
		this.raceDate = raceDate;
	}
	public Integer getRaceNumber() {
		return raceNumber;
	}
	public void setRaceNumber(Integer raceNumber) {
		this.raceNumber = raceNumber;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDayEvening() {
		return dayEvening;
	}
	public void setDayEvening(String dayEvening) {
		this.dayEvening = dayEvening;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getPostTime() {
		return postTime;
	}
	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}
}
