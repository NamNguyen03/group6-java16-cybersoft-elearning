package com.group6.java16.cybersoft.role.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.group6.java16.cybersoft.role.dto.RoleDTO;
import com.group6.java16.cybersoft.role.dto.RoleResponseDTO;
import com.group6.java16.cybersoft.role.model.ELRole;

@Mapper
public interface RoleMapper {
	
	RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
	
	RoleResponseDTO toResponseDTO (ELRole role);
	
	ELRole toModel(RoleDTO dto);
}
