package com.group6.java16.cybersoft.role.dto;

import java.util.UUID;

import com.group6.java16.cybersoft.role.model.ProgramModule;
import com.group6.java16.cybersoft.role.model.ProgramType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProgramResponseDTO {
	private UUID id;
	private String name;

	private ProgramModule module;

	private ProgramType type;

	private String description;

}
