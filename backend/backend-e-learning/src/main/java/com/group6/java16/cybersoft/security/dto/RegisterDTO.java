package com.group6.java16.cybersoft.security.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.group6.java16.cybersoft.user.validation.annotation.UniqueEmail;
import com.group6.java16.cybersoft.user.validation.annotation.UniqueUsername;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class RegisterDTO {

	@Size(min = 3, max = 100, message = "{user.username.size}")
	@UniqueUsername(message = "{user.username.existed}")
	@NotBlank(message = "{user.username.not-blank}")
	private String username;

	@Size(min = 6, max = 100, message = "{user.password.size}")
	@NotBlank(message = "{user.password.not-blank}")
	private String password;

	@NotBlank(message = "{user.display-name.not-blank")
	private String displayName;

	@Email(message = "{user.email.invalid}")
	@UniqueEmail(message = "{user.email.existed}")
	@Size(min = 3, max = 100, message = "{user.email.size}")
	@NotBlank(message = "{user.email.not-blank}")
	private String email;

	@NotBlank(message = "{user.first-name.not-blank}")
	private String firstName;

	@NotBlank(message = "{user.last-name.not-blank}")
	private String lastName;
}
