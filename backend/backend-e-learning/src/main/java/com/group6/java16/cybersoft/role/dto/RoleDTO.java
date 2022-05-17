package com.group6.java16.cybersoft.role.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.group6.java16.cybersoft.role.validation.annotation.UniqueRoleName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
	
	@Size(min = 3, max = 100, message = "{role.name.size}")
	@UniqueRoleName(message = "{role.name.existed}")
	@NotBlank(message = "{role.name.not-blank}")
	private String name;
	
	@NotBlank(message = "{role.description.not-blank}")
	private String description;
	
	public static RoleDTO builder() {
		return new RoleDTO();
	}

}
