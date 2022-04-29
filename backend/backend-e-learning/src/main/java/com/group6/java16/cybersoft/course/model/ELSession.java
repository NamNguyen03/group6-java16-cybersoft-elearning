package com.group6.java16.cybersoft.course.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

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
@Table(name = "el_session")
public class ELSession extends BaseEntity {
	
	
	private String sessionName;
	
	private String img;
	
	private String sessionDescription;
}
