package com.group6.java16.cybersoft.user.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.experimental.SuperBuilder;
import com.group6.java16.cybersoft.common.model.BaseEntity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "el_user")
public class ELUser extends BaseEntity {
	private String username;

	private String password;

	private String displayName;
	
	private String email;
	
    @Enumerated(EnumType.STRING)
	private UserStatus status;

    private String firstName;
 	
	private String lastName;

	private String avatar;
	
	private String department;
	
	private String major;
	
	private String hobbies;
	
	private String facebook;

	private String gender;

	private String phone;
}
