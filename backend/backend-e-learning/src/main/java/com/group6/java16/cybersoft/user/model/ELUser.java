package com.group6.java16.cybersoft.user.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.experimental.SuperBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group6.java16.cybersoft.common.model.BaseEntity;
import com.group6.java16.cybersoft.role.model.ELGroup;

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
	
    @JsonIgnore
	 @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	 @JoinTable(name = "el_user_group", joinColumns = @JoinColumn(name ="user_id"), inverseJoinColumns = @JoinColumn (name = "group_id"))
    private Set<ELGroup> groups = new LinkedHashSet<ELGroup>();
}
