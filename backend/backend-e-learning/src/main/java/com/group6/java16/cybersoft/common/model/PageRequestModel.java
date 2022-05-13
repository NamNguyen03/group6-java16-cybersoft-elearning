package com.group6.java16.cybersoft.common.model;

import lombok.Data;

@Data
public class PageRequestModel {
    private int pageCurrent = 1;
    private int itemPerPage = 10;
    private String fieldNameSort = null;
    private boolean isIncrementSort = true;
	private String fieldNameSearch = null;
	private String valueSearch = null;

	public PageRequestModel(int pageCurrent, int itemPerPage, String fieldNameSort, boolean isIncrementSort, String fieldNameSearch, String valueSearch){
		this.pageCurrent = pageCurrent <=0 ? 1 : pageCurrent;
		this.itemPerPage = itemPerPage <=0 ? 10 : itemPerPage;
		this.fieldNameSort = fieldNameSort;
		this.isIncrementSort = isIncrementSort;
		this.fieldNameSearch = fieldNameSearch;
		this.valueSearch = valueSearch;
	}
}
