package com.group6.java16.cybersoft.course.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	
	private String img;
	
	private String level;
	
	private float star_avg;
	
	private int total_star;
	
	private int total_rating;
	
	private String skill_1;
	
	private String skill_2;
	
	private String skill_3;
	
	private String skill_4;
	
	private String skill_5;

	@Enumerated(EnumType.STRING)
	private CategoryEnum category;

}
