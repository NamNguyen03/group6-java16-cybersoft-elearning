package com.group6.java16.cybersoft.role.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.group6.java16.cybersoft.role.dto.ProgramDTO;
import com.group6.java16.cybersoft.role.dto.ProgramResponseDTO;
import com.group6.java16.cybersoft.role.model.ELProgram;

@Mapper
public interface ProgramMapper {
	 ProgramMapper INSTANCE = Mappers.getMapper(ProgramMapper.class);

	    ProgramResponseDTO toResponseDTO(ELProgram program);

	    ELProgram toModel(ProgramDTO program);
}
