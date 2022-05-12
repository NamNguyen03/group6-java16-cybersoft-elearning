package com.group6.java16.cybersoft.role.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.group6.java16.cybersoft.role.model.ProgramModule;
import com.group6.java16.cybersoft.role.model.ProgramType;
import com.group6.java16.cybersoft.role.validation.annotation.UniqueProgramName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProgramDTO {
	@Size(min = 3, max = 100, message = "{program.name.size}")
	@UniqueProgramName(message = "{program.name.existed}")
	@NotBlank(message = "{program.name.not-blank}")
	private String name;

	private ProgramModule module = ProgramModule.ROLE;

	private ProgramType type= ProgramType.READ;

	@NotBlank(message = "{program.description.not-blank}")
	private String description;
	
	public static ProgramDTO builder() {
		return new ProgramDTO();
	}
	
}
