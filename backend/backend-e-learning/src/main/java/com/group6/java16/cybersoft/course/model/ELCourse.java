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

	private float starAvg;

	private int totalStar;

	private int totalRating;

	private String skill1;

	private String skill2;

	private String skill3;

	private String skill4;

	private String skill5;

	@Enumerated(EnumType.STRING)
	private CategoryEnum category;

}
