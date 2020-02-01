package com.springboot.api.endpoint.to;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
 public class Starters implements Serializable {
	private static final long serialVersionUID = 3028052311494634346L;
	
	private	QuickStarter starter;
	private Integer mutuelFinish;
	private Integer officialFinish;
	private Integer winPayoff;
	private Integer placePayoff;
	private Integer showPayoff;
	private boolean disqualified;
	private boolean nonBetting;
	private Integer actualOdds;
	
	
	public Integer getActualOdds() {
		return actualOdds;
	}
	public void setActualOdds(Integer actualOdds) {
		this.actualOdds = actualOdds;
	}
	public QuickStarter getStarter() {
		return starter;
	}
	public void setStarter(QuickStarter quickStarter) {
		this.starter = quickStarter;
	}
	public Integer getMutuelFinish() {
		return mutuelFinish;
	}
	public void setMutuelFinish(Integer mutuelFinish) {
		this.mutuelFinish = mutuelFinish;
	}
	public Integer getOfficialFinish() {
		return officialFinish;
	}
	public void setOfficialFinish(Integer officialFinish) {
		this.officialFinish = officialFinish;
	}
	public Integer getWinPayoff() {
		return winPayoff;
	}
	public void setWinPayoff(Integer winPayoff) {
		this.winPayoff = winPayoff;
	}
	public Integer getPlacePayoff() {
		return placePayoff;
	}
	public void setPlacePayoff(Integer placePayoff) {
		this.placePayoff = placePayoff;
	}
	public Integer getShowPayoff() {
		return showPayoff;
	}
	public void setShowPayoff(Integer showPayoff) {
		this.showPayoff = showPayoff;
	}
	public boolean isDisqualified() {
		return disqualified;
	}
	public void setDisqualified(boolean disqualified) {
		this.disqualified = disqualified;
	}
	public boolean isNonBetting() {
		return nonBetting;
	}
	public void setNonBetting(boolean nonBetting) {
		this.nonBetting = nonBetting;
	}
}
