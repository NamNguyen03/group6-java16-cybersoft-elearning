
package com.group6.java16.cybersoft.backedelearning.course.dto.client;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.course.dto.client.QueryCourseClientDTO;
import com.group6.java16.cybersoft.course.dto.client.SearchCourseRequestClientDTO;
import com.group6.java16.cybersoft.course.model.CategoryEnum;
import com.group6.java16.cybersoft.course.model.LevelEnum;

public class QueryCoureClientDTOIntegrationTest {

	@Test
	public void whenTimeNegativeIsUsedBuidQuery_thenThrowBusinessException() {
		SearchCourseRequestClientDTO rq = new SearchCourseRequestClientDTO();
		rq.setFromTime(-12);
		assertThrows(BusinessException.class , () -> QueryCourseClientDTO.buildQueryCourseClientDTO(rq));
	}
	
	
	@Test
	public void whenRatingNegativeIsUsedBuidQuery_thenThrowBusinessException() {
		SearchCourseRequestClientDTO rq = new SearchCourseRequestClientDTO();
		rq.setFromTime(2);
		rq.setToTime(5);
		rq.setRating(-2);
		assertThrows(BusinessException.class , () -> QueryCourseClientDTO.buildQueryCourseClientDTO(rq));
	}

	@Test
	public void whenLevelAllAndCategoryAllAndRatingAllIsUsedBuidQuery_thenReturnQueryCoureClientDTO() {
		SearchCourseRequestClientDTO rq = new SearchCourseRequestClientDTO();
		rq.setFromTime(2);
		rq.setToTime(5);
		rq.setRating(-2);
		rq.setCategories(Arrays.asList("NONE"));
		rq.setLevel(Arrays.asList("ALL"));
		rq.setRating(0);
		rq.setName("lessom");
		
		QueryCourseClientDTO actual = QueryCourseClientDTO.buildQueryCourseClientDTO(rq);
		
		assertEquals(0.0f,actual.getFromRating());
		assertEquals(5, actual.getToRating());
		assertEquals(List.of(LevelEnum.BEGINNER,LevelEnum.MIDDLE,LevelEnum.MASTER), actual.getLevels());
		assertEquals(List.of(CategoryEnum.BUSINESS,CategoryEnum.DEVELOPMENT,CategoryEnum.DATA_SCIENCE,CategoryEnum.DESIGN,CategoryEnum.FINANCE,CategoryEnum.MARKETING,CategoryEnum.MANAGEMENT,CategoryEnum.PHOTOGRAPHY,CategoryEnum.MUSIC,CategoryEnum.ACADEMIC), actual.getCategories());
	}
	
	@Test
	public void whenBuidQuerySuccess_thenReturnQueryCoureClientDTO() {
		SearchCourseRequestClientDTO rq = new SearchCourseRequestClientDTO();
		rq.setFromTime(2);
		rq.setToTime(5);
		rq.setRating(-2);
		rq.setCategories(Arrays.asList("BUSINESS"));
		rq.setLevel(Arrays.asList("BEGINNER"));
		rq.setRating(1);
		rq.setName("lessom");
		
		QueryCourseClientDTO actual = QueryCourseClientDTO.buildQueryCourseClientDTO(rq);
		
		assertEquals(1,actual.getFromRating());
		assertEquals(2, actual.getToRating());
		assertEquals(List.of(LevelEnum.BEGINNER), actual.getLevels());
		assertEquals(List.of(CategoryEnum.BUSINESS), actual.getCategories());
	}
}
