package com.group6.java16.cybersoft.role.dto;

import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResponseDTO {
private UUID id;
	
	private String name;
		
	private String description;
	
	private Set<RoleResponseDTO> roles;

}
