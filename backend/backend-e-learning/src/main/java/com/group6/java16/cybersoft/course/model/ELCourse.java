package com.group6.java16.cybersoft.course.model;

import javax.persistence.Entity;
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
	private String courseName;
	private int courseTime;
	private String description;

}
