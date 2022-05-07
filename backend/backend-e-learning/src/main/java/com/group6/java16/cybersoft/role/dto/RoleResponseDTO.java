package com.group6.java16.cybersoft.role.dto;


import java.util.UUID;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleResponseDTO {
	
	private UUID id;
	
	private String name;
	
	private String description;

}
