package com.group6.java16.cybersoft.user.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group6.java16.cybersoft.common.model.BaseEntity;
import com.group6.java16.cybersoft.role.model.ELGroup;
import com.group6.java16.cybersoft.user.model.ELUser;

import lombok.*;
import lombok.experimental.SuperBuilder;

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

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable(name = "el_group_user", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
	private Set<ELGroup> groups = new LinkedHashSet<ELGroup>();

	public void addGroup(ELGroup group) {
		groups.add(group);
		group.getUsers().add(this);
	}

	public void removeGroup(ELGroup group) {
		groups.remove(group);
		group.getUsers().remove(this);
	}
}
