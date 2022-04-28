package com.group6.java16.cybersoft.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.group6.java16.cybersoft.user.model.UserStatus;
import com.group6.java16.cybersoft.user.validation.annotation.UniqueEmail;
import com.group6.java16.cybersoft.user.validation.annotation.UniqueUsername;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserCreateDTO {
    @Size(min = 3, max = 100, message = "{user.username.size}")
	@UniqueUsername(message = "{user.username.existed}")
	private String username;

    @Size(min = 6, max = 100, message = "{user.password.size}")
	private String password;

	@NotBlank(message = "{user.display-name.not-blank")
	private String displayName;
	
    @Email(message = "{user.email.invalid}")
	@UniqueEmail(message = "{user.email.existed}")
    @Size(min = 3, max = 100, message = "{user.email.size}")
	private String email;

	private UserStatus status = UserStatus.ACTIVE;

	@NotBlank(message = "{user.first-name.not-blank}")
    private String firstName;

	@NotBlank(message = "{user.last-name.not-blank}")
    private String lastName;

	@NotBlank(message = "{user.department.not-blank}")
	private String department;
	
	@NotBlank(message = "{user.major.not-blank}")
	private String major;
}
