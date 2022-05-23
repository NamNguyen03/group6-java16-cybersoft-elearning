package com.group6.java16.cybersoft.feedback.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.group6.java16.cybersoft.feedback.dto.CommentCreateDTO;
import com.group6.java16.cybersoft.feedback.dto.CommentResponseDTO;
import com.group6.java16.cybersoft.feedback.model.ELComment;


@Mapper
public interface CommentMapper {
	CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
	
	CommentResponseDTO toResponseDTO(ELComment model);
	
	ELComment toModel(CommentCreateDTO dto);

}
