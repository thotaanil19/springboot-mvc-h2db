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
public class CustomerApiKeyTo implements Serializable {
	
	private static final long serialVersionUID = 4652828423397973270L;

	private Long id;
	
    private Long customerId;
    
    private String apiKey;

    private Boolean isActive=true;
    
    private Date timeStamp = new Date();
    
    private Date disabledAt;
    
    private String disabledAtStr;

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

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		if (timeStamp != null) {
			this.timeStamp = new Date(timeStamp.getTime());
		} else {
			this.timeStamp = null;
		}
	}

	public Date getDisabledAt() {
		return disabledAt;
	}

	public void setDisabledAt(Date disabledAt) {
		if (disabledAt != null) {
			this.disabledAt = new Date(disabledAt.getTime());
		} else {
			this.disabledAt = null;
		}
	}

	public String getDisabledAtStr() {
		if(Util.isEmptyString(disabledAtStr)) {
			disabledAtStr = Util.dateToStringInYYYYMMDDHHMMSS(disabledAt);
		}
		return disabledAtStr;
	}

	public void setDisabledAtStr(String disabledAtStr) {
		this.disabledAtStr = disabledAtStr;
	}
    
    
	
}
