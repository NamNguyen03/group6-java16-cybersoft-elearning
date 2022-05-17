package com.group6.java16.cybersoft.role.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group6.java16.cybersoft.common.model.BaseEntity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "el_program")
public class ELProgram extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private ProgramModule module;

    @Enumerated(EnumType.STRING)
    @Column(name = "program_type")
    private ProgramType type;

    private String description;

    @Builder.Default
    @JsonIgnore
    @ManyToMany(mappedBy = "programs")
    private Set<ELRole> roles = new LinkedHashSet();

}
    
	