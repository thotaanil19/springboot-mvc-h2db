package com.springboot.api.to;

import java.io.Serializable;

/**
 * 
 * @author anilt
 *
 */
public class ReportLogCriteria implements Serializable {

	
	private static final long serialVersionUID = -4146046992698546896L;

	private Integer pageNumber;
	private Integer pageSize;
	
	private String fromDateStr;
	private String toDateStr;
	
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getFromDateStr() {
		return fromDateStr;
	}
	public void setFromDateStr(String fromDateStr) {
		this.fromDateStr = fromDateStr;
	}
	public String getToDateStr() {
		return toDateStr;
	}
	public void setToDateStr(String toDateStr) {
		this.toDateStr = toDateStr;
	}
	
	

	@Override
	public String toString() {
		return "ReportLogCriteria [pageNumber=" + pageNumber + ", pageSize="
				+ pageSize + ", fromDateStr=" + fromDateStr + ", toDateStr="
				+ toDateStr + "]";
	}
	
}
