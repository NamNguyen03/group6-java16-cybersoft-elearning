package com.group6.java16.cybersoft.user.dto;

import java.util.Set;
import java.util.UUID;

import com.group6.java16.cybersoft.role.dto.GroupResponseDTO;
import com.group6.java16.cybersoft.role.dto.RoleResponseDTO;
import com.group6.java16.cybersoft.user.model.UserStatus;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponseDTO {
    private UUID id;

	private String username;

	private String displayName;
	
	private String email;

    private String firstName;
 	
	private String lastName;

	private String avatar;

	private String gender;
	
	private String department;
	
	private String major;
	
	private String phone;

	private String hobbies;
	
	private String facebook;

    private UserStatus status;
    
    private Set<GroupResponseDTO> groups;
}
