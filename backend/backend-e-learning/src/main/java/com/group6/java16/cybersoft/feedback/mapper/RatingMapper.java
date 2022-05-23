package com.group6.java16.cybersoft.feedback.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.group6.java16.cybersoft.feedback.dto.RatingCreateDTO;
import com.group6.java16.cybersoft.feedback.dto.RatingResponseDTO;
import com.group6.java16.cybersoft.feedback.model.ELRating;

@Mapper
public interface RatingMapper {
	RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);
	
	RatingResponseDTO toResponseDTO (ELRating model);
	
	ELRating toModelDTO (RatingCreateDTO dto);
	
}
