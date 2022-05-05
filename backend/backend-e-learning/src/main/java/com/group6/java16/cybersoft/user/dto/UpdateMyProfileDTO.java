package com.group6.java16.cybersoft.user.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateMyProfileDTO {

	private String displayName;
	
	private String email;

    private String firstName;
 	
	private String lastName;
	
	private String hobbies;
	
	private String facebook;

	private String gender;

	private String phone;
}
