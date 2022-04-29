package com.group6.java16.cybersoft.role.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group6.java16.cybersoft.common.model.BaseEntity;

import lombok.Builder;
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

	@Builder.Default
	@JsonIgnore
	@ManyToMany(mappedBy = "roles")
	private Set<ELGroup> groups = new LinkedHashSet<ELGroup>();
	
	@Builder.Default
	@JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	@JoinTable(name = "el_role_program",
				joinColumns = @JoinColumn(name = "role_id"),
				inverseJoinColumns = @JoinColumn(name = "program_id"))
    private Set<ELProgram> programs = new LinkedHashSet();

    
	public void addProgram(ELProgram program) {
        programs.add(program);
        program.getRoles().add(this);

    }

}
