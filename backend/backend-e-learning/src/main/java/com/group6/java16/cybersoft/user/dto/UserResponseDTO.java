package com.group6.java16.cybersoft.user.dto;

import java.util.UUID;

import com.group6.java16.cybersoft.user.model.UserStatus;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDTO {
    private UUID id;

	private String username;

	private String displayName;
	
	private String email;

    private String firstName;
 	
	private String lastName;

	private String avatar;
	
	private String department;
	
	private String major;
	
	private String hobbies;
	
	private String facebook;

    private UserStatus status;

	private String gender;

	private String phone;
}
