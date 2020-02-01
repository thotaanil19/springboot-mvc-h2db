package com.springboot.api.domain;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "eqbdataapi_data_intercepts")
public class DataIntercept implements Serializable {

	private static final long serialVersionUID = 9119734740718609859L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
    @Column(name="cust_prod_id", nullable = false/*, columnDefinition="INT"*/)
    private Long custProdId;
    
    @Column(name="track_id", nullable = false, columnDefinition="CHAR")
    private String trackId;
    
    @Column(name="race_date", nullable = false)
    private Date raceDate;
    
    @Column(name="race_number", nullable = false, columnDefinition="SMALLINT")
    private Integer raceNumber;
    
    @Column(name="data_to_send", nullable = false, columnDefinition="TEXT")
    private String dataToSend;
    
    @Column(name="timestamp", nullable = false)
    private Date timeStamp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustProdId() {
		return custProdId;
	}

	public void setCustProdId(Long custProdId) {
		this.custProdId = custProdId;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public Date getRaceDate() {
		return raceDate;
	}

	public void setRaceDate(Date raceDate) {
		if (raceDate != null) {
			this.raceDate = new Date(raceDate.getTime());
		} else {
			this.raceDate = null;
		}
	}

	public Integer getRaceNumber() {
		return raceNumber;
	}

	public void setRaceNumber(Integer raceNumber) {
		this.raceNumber = raceNumber;
	}

	public String getDataToSend() {
		return dataToSend;
	}

	public void setDataToSend(String dataToSend) {
		this.dataToSend = dataToSend;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		if(timeStamp != null) {
			this.timeStamp = new Date(timeStamp.getTime());
		} else {
			this.timeStamp = null;
		}
	}
    
    

    
    

    
}
