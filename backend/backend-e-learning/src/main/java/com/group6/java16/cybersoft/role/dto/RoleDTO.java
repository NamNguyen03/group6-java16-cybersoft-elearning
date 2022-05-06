package com.group6.java16.cybersoft.role.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.group6.java16.cybersoft.role.validation.annotation.UniqueRoleName;
import com.group6.java16.cybersoft.user.dto.UpdateUserDTO;
import com.group6.java16.cybersoft.user.model.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDTO {
	
	@Size(min = 3, max = 100, message = "{role.name.size}")
	@UniqueRoleName(message = "{role.name.existed}")
	@NotBlank(message = "{role.name.not-blank}")
	private String name;
	
	@NotBlank(message = "{role.description.not-blank}")
	private String description;

}
