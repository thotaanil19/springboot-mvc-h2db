package com.springboot.api.domain;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * @author anilt
 *
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "eqbdataapi_tracks")
public class Track implements Serializable {

	private static final long serialVersionUID = -877917544343407908L;

	@Id
	@Column(name="id", columnDefinition= "CHAR")
	private String id;
	
	@Column(name="country", columnDefinition= "CHAR")
	private String country;
	
    @Column(name="name")
    private String name;
    
    @Column(name="type", columnDefinition= "CHAR")
    private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

    
}
