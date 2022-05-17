package com.group6.java16.cybersoft.course.model;

public enum CategoryEnum {
	BUSINESS("Business"),
	DEVELOPMENT("Development"),
	DATA_SCIENCE("Data Science"),
	DESIGN("Design"),
	FINANCE("Finance"),
	MARKETING("Marketing"),
	MANAGEMENT("Management"),
	PHOTOGRAPHY("Photography"),
	MUSIC("Music"),
	ACADEMIC("Academic"),
	NONE("none");

	private String category;

	CategoryEnum(String category) {
		this.category = category;
	}

	public String category() {
		return category;
	}

	@Override
	public String toString() {
		return category;
	}

}
