package com.springboot.api.to;

import java.io.Serializable;

/**
 * 
 * @author anilt
 *
 */
public class SortingCriteria implements Serializable {

	private static final long serialVersionUID = 7837534874094502030L;
	
	private Integer pageNumber;
	private Integer pageSize;
	
	private String sortingField;
	private String sortOrder;
	
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
	public String getSortingField() {
		return sortingField;
	}
	public void setSortingField(String sortingField) {
		this.sortingField = sortingField;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	@Override
	public String toString() {
		return "SortingCriteria [pageNumber=" + pageNumber + ", pageSize="
				+ pageSize + ", sortingField=" + sortingField + ", sortOrder="
				+ sortOrder + "]";
	}
	
	
}
