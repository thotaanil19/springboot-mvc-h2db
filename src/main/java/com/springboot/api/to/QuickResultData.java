package com.springboot.api.to;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.api.endpoint.to.DataApiObject;
import com.springboot.api.endpoint.to.QuickRaceInfo;
import com.springboot.api.endpoint.to.Starters;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuickResultData implements DataApiObject {
	
	private static final long serialVersionUID = 2691570823315764167L;
	
	private QuickRaceInfo quickRaceInfo;
	private Boolean officialIndicator;
	private List<Starters> starters;
	
	public Boolean getOfficialIndicator() {
		return officialIndicator;
	}
	public void setOfficialIndicator(Boolean officialIndicator) {
		this.officialIndicator = officialIndicator;
	}
	
	public QuickRaceInfo getQuickRaceInfo() {
		return quickRaceInfo;
	}
	public void setQuickRaceInfo(QuickRaceInfo quickRaceInfo) {
		this.quickRaceInfo = quickRaceInfo;
	}
	
	public List<Starters> getStarters() {
		return starters;
	}
	public void setStarters(List<Starters> starters) {
		this.starters = starters;
	}
}
