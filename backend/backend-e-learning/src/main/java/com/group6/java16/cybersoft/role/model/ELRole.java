package com.group6.java16.cybersoft.role.model;

import javax.persistence.Entity;
import javax.persistence.Table;

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

}
