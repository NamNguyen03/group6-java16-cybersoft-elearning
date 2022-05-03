package com.group6.java16.cybersoft.role.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.group6.java16.cybersoft.role.dto.GroupDTO;
import com.group6.java16.cybersoft.role.dto.GroupResponseDTO;
import com.group6.java16.cybersoft.role.model.ELGroup;

@Mapper
public interface GroupMapper {

	GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);
	
	GroupResponseDTO toGroupResponseDTO (ELGroup model);
	ELGroup toModel (GroupDTO dto);
	
}
