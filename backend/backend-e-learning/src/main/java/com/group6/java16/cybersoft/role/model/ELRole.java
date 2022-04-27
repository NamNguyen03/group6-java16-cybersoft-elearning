package com.group6.java16.cybersoft.role.model;

import java.util.LinkedHashSet;
import java.util.Set;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
<<<<<<< HEAD
import javax.persistence.MappedSuperclass;
=======
>>>>>>> 7d52f7c9a05766648ea5891616f9e27ebd7cd2d3
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group6.java16.cybersoft.common.model.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "el_role")
public class ELRole extends BaseEntity {

	private String name;

	private String description;
	
	@JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<ELGroup> groups = new LinkedHashSet<ELGroup>();
   


}
