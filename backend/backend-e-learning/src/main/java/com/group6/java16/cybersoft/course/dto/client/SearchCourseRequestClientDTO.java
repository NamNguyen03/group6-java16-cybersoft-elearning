package com.group6.java16.cybersoft.course.dto.client;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
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
