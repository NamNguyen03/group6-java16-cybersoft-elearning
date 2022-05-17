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

	private String value;

	CategoryEnum(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}

}
