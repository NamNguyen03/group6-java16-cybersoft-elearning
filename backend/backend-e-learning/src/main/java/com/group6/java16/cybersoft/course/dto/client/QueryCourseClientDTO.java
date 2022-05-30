package com.group6.java16.cybersoft.course.dto.client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.course.model.CategoryEnum;
import com.group6.java16.cybersoft.course.model.LevelEnum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
		if(dto.getRating() <0 || dto.getRating() >5) {
			throw new BusinessException("rating invalid");
		}
		
		if(dto.getFromTime() < 0 || dto.getFromTime()> dto.getToTime()) {
			throw new BusinessException("Course time invalid");
		}
		
		QueryCourseClientDTO rp = new QueryCourseClientDTO();
		
		rp.setCourseName(dto.getName()== null ? "": dto.getName());
		
		List<CategoryEnum> categories = new ArrayList<>();
		if(dto.getCategories() == null || dto.getCategories().size() ==0 || "NONE".equals(dto.getCategories().get(0))) {
			categories =  List.of(CategoryEnum.BUSINESS,CategoryEnum.DEVELOPMENT,CategoryEnum.DATA_SCIENCE,CategoryEnum.DESIGN,CategoryEnum.FINANCE,CategoryEnum.MARKETING,CategoryEnum.MANAGEMENT,CategoryEnum.PHOTOGRAPHY,CategoryEnum.MUSIC,CategoryEnum.ACADEMIC);
		}
		if(dto.getCategories() != null && dto.getCategories().size() > 0 && !"NONE".equals(dto.getCategories().get(0))) {
			categories = dto.getCategories().stream().map(CategoryEnum::valueOf).collect(Collectors.toList());
		}
		rp.setCategories(categories);
		
		List<LevelEnum> levels = new ArrayList<>();
		if(dto.getLevel()== null || dto.getLevel().size() == 0 || "ALL".equals(dto.getLevel().get(0)) ) {
			levels = List.of(LevelEnum.BEGINNER,LevelEnum.MIDDLE,LevelEnum.MASTER);
		}
		if(dto.getLevel() != null && dto.getLevel().size() > 0 && !"ALL".equals(dto.getLevel().get(0))) {
			levels = dto.getLevel().stream().map(LevelEnum::valueOf).collect(Collectors.toList());
		}
		rp.setLevels(levels);
		
		if(dto.getRating() == 0) {
			rp.setFromRating(0);
			rp.setToRating(5);
		}
		
		if(dto.getRating() != 0) {
			rp.setFromRating(dto.getRating());
			rp.setToRating(dto.getRating()+1);
		}
		
		rp.setFromTime(dto.getFromTime());
		rp.setToTime(dto.getToTime());
		System.out.println(rp.toString());
		return rp;
	}
}

