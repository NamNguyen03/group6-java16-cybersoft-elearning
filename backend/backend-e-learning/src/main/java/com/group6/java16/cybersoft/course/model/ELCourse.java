package com.group6.java16.cybersoft.course.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.group6.java16.cybersoft.common.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "el_course")
public class ELCourse extends BaseEntity {
	
	@Column(name = "course_name",unique = true,nullable = false)
	private String courseName;
	
	@Column(name = "course_time",nullable = false)
	private int courseTime;
	
	@Column(name = "description")
	private String description;
	
	@OneToMany(mappedBy = "course")
	private Set<ELLesson> lessons = new LinkedHashSet<ELLesson>();

}
