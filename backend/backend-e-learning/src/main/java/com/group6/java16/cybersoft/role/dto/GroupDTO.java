package com.group6.java16.cybersoft.role.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.group6.java16.cybersoft.role.validation.annotation.UniqueGroupName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupDTO {
	@Size(min = 3, max = 100, message = "{group.name.size}")
	@UniqueGroupName(message = "{group.name.existed}")
	@NotBlank(message = "{group.name.not-blank}")
	private String name;
	
	@NotBlank(message = "{group.description.not-blank}")
	private String description;

}
