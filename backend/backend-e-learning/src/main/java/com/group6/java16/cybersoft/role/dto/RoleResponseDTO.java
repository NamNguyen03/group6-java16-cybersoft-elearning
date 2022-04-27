package com.group6.java16.cybersoft.role.dto;


import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponseDTO {
	
	private UUID id;
	
	private String name;
	
	private String description;

}
