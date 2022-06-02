package com.group6.java16.cybersoft.course.dto.client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.course.model.CategoryEnum;
import com.group6.java16.cybersoft.course.model.LevelEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryCourseClientDTO {
	private String courseName;
	private List<CategoryEnum> categories;
	private List<LevelEnum> levels;
	private float fromRating;
	private float toRating;
	private int fromTime;
	private int toTime;
	
	private QueryCourseClientDTO() {
		
	}
	
	public static QueryCourseClientDTO buildQueryCourseClientDTO(SearchCourseRequestClientDTO dto) {
		
		if(dto.getFromTime() < 0 || dto.getFromTime() > dto.getToTime()) {
			throw new BusinessException("Course time invalid");
		}
		
		if(dto.getRating() <0 || dto.getRating() >5) {
			throw new BusinessException("Rating invalid");
		}
		
		QueryCourseClientDTO rp = new QueryCourseClientDTO();
		
		rp =  buildRating(dto.getRating() , rp);
		
		rp = buildCategories(dto.getCategories(), rp);
		
		rp = buildLevels(dto.getLevel(),rp);
		
		rp.setCourseName(dto.getName()== null ? "": dto.getName());
		
		rp.setFromTime(dto.getFromTime());
		
		rp.setToTime(dto.getToTime());
		
		return rp;
	}

	private static QueryCourseClientDTO buildLevels(List<String> levels, QueryCourseClientDTO rp) {
		List<LevelEnum> levelsResult = new ArrayList<>();
		if(levels == null || levels.size() == 0 || "ALL".equals(levels.get(0)) ) {
			levelsResult = List.of(LevelEnum.BEGINNER,LevelEnum.MIDDLE,LevelEnum.MASTER);
		}
		if(levels != null && levels.size() > 0 && !"ALL".equals(levels.get(0))) {
			levelsResult = levels.stream().map(LevelEnum::valueOf).collect(Collectors.toList());
		}
		rp.setLevels(levelsResult);
		
		return rp;
	}

	private static QueryCourseClientDTO buildCategories(List<String> categories, QueryCourseClientDTO rp) {
		List<CategoryEnum> categoriesResult = new ArrayList<>();
	
		
		if(categories == null || categories.size() ==0 || "NONE".equals(categories.get(0))) {
			categoriesResult =  List.of(CategoryEnum.BUSINESS,CategoryEnum.DEVELOPMENT,CategoryEnum.DATA_SCIENCE,CategoryEnum.DESIGN,CategoryEnum.FINANCE,CategoryEnum.MARKETING,CategoryEnum.MANAGEMENT,CategoryEnum.PHOTOGRAPHY,CategoryEnum.MUSIC,CategoryEnum.ACADEMIC);
		}
		
		if(categories != null && categories.size() > 0 && !"NONE".equals(categories.get(0))) {
			categoriesResult = categories.stream().map(CategoryEnum::valueOf).collect(Collectors.toList());
		}
		rp.setCategories(categoriesResult);
		
		return rp;
	}

	private static QueryCourseClientDTO buildRating(int rating, QueryCourseClientDTO rp) {
		
		if(rating == 0) {
			rp.setFromRating(0);
			rp.setToRating(5);
		}
		
		if(rating != 0) {
			rp.setFromRating(rating);
			rp.setToRating(rating + 1 );
		}
		
		return rp;
	}

	

}

