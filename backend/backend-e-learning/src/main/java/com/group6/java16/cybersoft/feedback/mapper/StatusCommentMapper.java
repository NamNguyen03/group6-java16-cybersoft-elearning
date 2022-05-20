package com.group6.java16.cybersoft.feedback.mapper;

import com.group6.java16.cybersoft.feedback.dto.StatusCommentResponseDTO;
import com.group6.java16.cybersoft.feedback.model.ELStatusComment;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StatusCommentMapper {

	StatusCommentMapper INSTANCE = Mappers.getMapper(StatusCommentMapper.class);

    StatusCommentResponseDTO toResponseDTO(ELStatusComment model);
}
