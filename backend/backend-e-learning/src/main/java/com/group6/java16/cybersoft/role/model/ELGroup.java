package com.group6.java16.cybersoft.role.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group6.java16.cybersoft.common.model.BaseEntity;
import com.group6.java16.cybersoft.user.model.ELUser;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "el_group")
public class ELGroup  extends BaseEntity {
    
    private String name;
    private String description;

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(name = "el_group_role", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<ELRole> roles = new LinkedHashSet<ELRole>();

    
    @JsonIgnore
    @ManyToMany(mappedBy = "groups")
    private Set<ELUser> users = new LinkedHashSet<ELUser>();
    
    
    public void addRole(ELRole role) {
		roles.add(role);
		role.getGroups().add(this);
	}
    
    public void removeRole(ELRole role) {
		roles.remove(role);
		role.getGroups().remove(this);
	}
    
    public void clearRole() {
	
    	this.roles.clear();
    }
}

