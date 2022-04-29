package com.group6.java16.cybersoft.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SessionUpdateDTO {
	
	private String sessionName;
	
	private String img;
	
	private String sessionDescription;
}
