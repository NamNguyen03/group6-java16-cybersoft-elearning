package com.group6.java16.cybersoft.user.mapper;

import com.group6.java16.cybersoft.user.dto.UserCreateDTO;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;
import com.group6.java16.cybersoft.user.model.ELUser;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    
    ELUser toModel(UserCreateDTO user);

    UserResponseDTO toUserResponseDTO(ELUser user);
}
