package com.springboot.api.endpoint.to;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuickStarter implements Serializable {
	private static final long serialVersionUID = 3028052311494634346L;
	
	private Horse horse;
	private String programNumber;
	private Integer postPosition;
	
	public Horse getHorse() {
		return horse;
	}
	public void setHorse(Horse horse) {
		this.horse = horse;
	}
	public String getProgramNumber() {
		return programNumber;
	}
	public void setProgramNumber(String programNumber) {
		this.programNumber = programNumber;
	}
	public Integer getPostPosition() {
		return postPosition;
	}
	public void setPostPosition(Integer postPosition) {
		this.postPosition = postPosition;
	}
}
