package com.group6.java16.cybersoft.course.dto.client;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {
	
	private UUID id;
	
	private String avatar;
	
	private String displayName;
	
	private String username;
	
}
