package com.group6.java16.cybersoft.user.dto;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.group6.java16.cybersoft.role.dto.GroupResponseDTO;
import com.group6.java16.cybersoft.user.model.UserStatus;

import lombok.*;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
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

	private Set<GroupResponseDTO> groups = new LinkedHashSet<GroupResponseDTO>();

	public static UserResponseDTO builder() {
		return new UserResponseDTO();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserResponseDTO other = (UserResponseDTO) obj;
		return Objects.equals(avatar, other.avatar) && Objects.equals(department, other.department)
				&& Objects.equals(displayName, other.displayName) && Objects.equals(email, other.email)
				&& Objects.equals(facebook, other.facebook) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(gender, other.gender) && Objects.equals(groups, other.groups)
				&& Objects.equals(hobbies, other.hobbies) && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(major, other.major)
				&& Objects.equals(phone, other.phone) && status == other.status
				&& Objects.equals(username, other.username);
	}

}
