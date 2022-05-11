package com.group6.java16.cybersoft.role.dto;

import com.group6.java16.cybersoft.role.model.ProgramModule;
import com.group6.java16.cybersoft.role.model.ProgramType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProgramUpdateDTO {
	private String name;

	private ProgramModule module;

	private ProgramType type;

	private String description;

}
