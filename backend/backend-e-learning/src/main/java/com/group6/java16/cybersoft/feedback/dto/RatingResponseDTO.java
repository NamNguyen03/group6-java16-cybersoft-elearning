package com.group6.java16.cybersoft.feedback.dto;

import java.util.UUID;

import com.group6.java16.cybersoft.user.dto.client.UserResponseClientDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponseDTO {
	private UUID id;
	
	private String value;
	
	private UserResponseClientDTO user;
		

}
