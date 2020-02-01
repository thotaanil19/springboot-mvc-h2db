package com.springboot.api.to;


import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerApiLimitTo implements Serializable {
	
	private static final long serialVersionUID = -7288158848870124094L;

	private Long id;
	
    private Long customerId;
    
    private Date startDate;
    private String startDateStr;
    
    private Date endDate;
    private String endDateStr;

    private Long currentAccessLimit;
    
    private Long currentAccessCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getCurrentAccessLimit() {
		return currentAccessLimit;
	}

	public void setCurrentAccessLimit(Long currentAccessLimit) {
		this.currentAccessLimit = currentAccessLimit;
	}

	public Long getCurrentAccessCount() {
		return currentAccessCount;
	}

	public void setCurrentAccessCount(Long currentAccessCount) {
		this.currentAccessCount = currentAccessCount;
	}

	public String getStartDateStr() {
		if(Util.isEmptyString(startDateStr)) {
			startDateStr = Util.dateToStringInMMDDYYYY(startDate);
		}
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		if(Util.isEmptyString(endDateStr)) {
			endDateStr = Util.dateToStringInMMDDYYYY(endDate);
		}
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
    
	public Date getStartDate() {
		if(Util.isNull(startDate) && !Util.isEmptyString(startDateStr)) {
			startDate = Util.stringToDateConversion(startDateStr);
		}
		return startDate;
	}

	public void setStartDate(Date startDate) {
		if (startDate != null) {
			this.startDate = new Date(startDate.getTime());
		} else {
			this.startDate = null;
		}
	}

	public Date getEndDate() {
		if(Util.isNull(endDate) && !Util.isEmptyString(endDateStr)) {
			endDate = Util.stringToDateConversion(endDateStr);
		}
		return endDate;
	}

	public void setEndDate(Date endDate) {
		if (endDate != null) {
			this.endDate = new Date(endDate.getTime());
		} else {
			this.endDate = null;
		}
	} 
	
}
