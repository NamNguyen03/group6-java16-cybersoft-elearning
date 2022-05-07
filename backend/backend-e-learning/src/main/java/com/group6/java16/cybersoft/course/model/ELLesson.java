package com.group6.java16.cybersoft.course.model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "el_lesson")
public class ELLesson extends BaseEntity{
	
	@Column(name = "name",unique = true,nullable = false)
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "content",nullable = false)
	private String content;
	
	@JsonIgnore
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "course_id",referencedColumnName = "id")
	private ELCourse course;
}
