package com.group6.java16.cybersoft.common.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PageResponseModel<T> {
	private int pageCurrent;
	private int totalPage;
	private List<T> items = new ArrayList<>();

}