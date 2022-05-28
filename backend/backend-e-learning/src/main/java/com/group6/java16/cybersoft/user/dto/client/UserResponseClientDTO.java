package com.group6.java16.cybersoft.user.dto.client;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserResponseClientDTO {
	private UUID id;
	
	private String displayName;
	
	private String avatar;
}
