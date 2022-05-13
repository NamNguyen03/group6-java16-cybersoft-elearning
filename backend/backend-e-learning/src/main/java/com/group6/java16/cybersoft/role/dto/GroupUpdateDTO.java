package com.group6.java16.cybersoft.role.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class GroupUpdateDTO {
	private String name;
	private String description;

}
