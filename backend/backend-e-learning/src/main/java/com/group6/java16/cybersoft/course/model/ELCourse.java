package com.group6.java16.cybersoft.course.model;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.group6.java16.cybersoft.common.model.BaseEntity;
import com.group6.java16.cybersoft.feedback.model.ELStatusComment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "el_course")
public class ELCourse extends BaseEntity {

	@Column(name = "course_name", unique = true, nullable = false)
	private String courseName;

	@Column(name = "description")
	private String description;

	@OneToMany(mappedBy = "course")
	@Builder.Default
	private Set<ELLesson> lessons = new LinkedHashSet<ELLesson>();
	
	@OneToMany(mappedBy = "course")
	@Builder.Default
	private Set<ELStatusComment> statuses = new LinkedHashSet<ELStatusComment>();

	private String img;

	@Enumerated(EnumType.STRING)
	private LevelEnum level;

	private float starAvg;
	
	@Builder.Default
	private int courseTime = 0;

	// totalStar = sum (star.value)
	@Builder.Default
	private int totalStar = 0;
	
	// count rating
	@Builder.Default
	private int totalRating = 0;

	private String skill1;

	private String skill2;

	private String skill3;

	private String skill4;

	private String skill5;

	@Enumerated(EnumType.STRING)
	private CategoryEnum category;

}
