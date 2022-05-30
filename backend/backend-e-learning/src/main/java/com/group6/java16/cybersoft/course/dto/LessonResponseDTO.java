package com.group6.java16.cybersoft.course.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class LessonResponseDTO {

	private UUID id;

	private String name;

	private String content;

	private String description;

	private float starAvg;

	private int totalStar;

	private int totalRating;

	private int totalOneStar;

	private int totalTwoStar;

	private int totalThreeStar;

	private int totalFourStar;

	private int totalFiveStar;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LessonResponseDTO other = (LessonResponseDTO) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Float.floatToIntBits(starAvg) != Float.floatToIntBits(other.starAvg))
			return false;
		if (totalFiveStar != other.totalFiveStar)
			return false;
		if (totalFourStar != other.totalFourStar)
			return false;
		if (totalOneStar != other.totalOneStar)
			return false;
		if (totalRating != other.totalRating)
			return false;
		if (totalStar != other.totalStar)
			return false;
		if (totalThreeStar != other.totalThreeStar)
			return false;
		if (totalTwoStar != other.totalTwoStar)
			return false;
		return true;
	}

}
