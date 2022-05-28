package com.group6.java16.cybersoft.course.dto.client;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SearchCourseRequestClientDTO {

	private int pageCurrent;
	private int itemPerPage;
	private String name;
	private List<String> categories;
	private int rating;
	private int fromTime;
	private int toTime;
	private List<String> level;

}
