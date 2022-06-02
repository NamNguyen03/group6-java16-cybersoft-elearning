package com.group6.java16.cybersoft.course.dto;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CourseResponseDTO {
	private UUID id;

	private String courseName;

	private String createdBy;

	private String description;

	private String img;

	private String level;

	private float starAvg;
	
	private int courseTime;

	private int totalRating;

	private String skill1;

	private String skill2;

	private String skill3;

	private String skill4;

	private String skill5;

	private String category;
	
	private Set<LessonResponseDTO> lessons;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseResponseDTO other = (CourseResponseDTO) obj;
		return Objects.equals(category, other.category) && Objects.equals(courseName, other.courseName)
				&& courseTime == other.courseTime && Objects.equals(createdBy, other.createdBy)
				&& Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(img, other.img) && Objects.equals(lessons, other.lessons)
				&& Objects.equals(level, other.level) && Objects.equals(skill1, other.skill1)
				&& Objects.equals(skill2, other.skill2) && Objects.equals(skill3, other.skill3)
				&& Objects.equals(skill4, other.skill4) && Objects.equals(skill5, other.skill5)
				&& Float.floatToIntBits(starAvg) == Float.floatToIntBits(other.starAvg)
				&& totalRating == other.totalRating;
	}
	
}
