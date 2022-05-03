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
@SuperBuilder
@Entity
@Table(name = "el_program")
public class ELProgram extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private ELModule module;

    @Enumerated(EnumType.STRING)
    @Column(name = "program_type")
    private ELProgramType type;

    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "programs")
    @Builder.Default
    private Set<ELRole> roles = new LinkedHashSet();
}