package com.group6.java16.cybersoft.course.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.group6.java16.cybersoft.common.model.BaseEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "el_session")
public class ELSession extends BaseEntity {
	
	@Column(name = "session_name",unique = true,nullable = false)
	private String sessionName;
	
	@Column(name = "img")
	private String img;
	
	@Column(name = "session_description")
	private String sessionDescription;
	
	@ManyToOne
	@JoinColumn(name = "course_id")
	private ELCourse course;
}
