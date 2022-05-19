package com.group6.java16.cybersoft.backedelearning.course.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.course.dto.CourseCreateDTO;
import com.group6.java16.cybersoft.course.dto.CourseResponseDTO;
import com.group6.java16.cybersoft.course.dto.CourseUpdateDTO;
import com.group6.java16.cybersoft.course.mapper.CourseMapper;
import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.course.repository.ELCourseRepository;
import com.group6.java16.cybersoft.course.service.CourseManagementService;
import com.group6.java16.cybersoft.course.service.CourseManagementServiceImpl;

@SpringBootTest
public class CourseManagementServiceIntegrationTest {
	@Mock
	private ELCourseRepository courseRepository;
	
	@Mock
	private CourseMapper mapper;
	
	@InjectMocks
	private CourseManagementService service = new CourseManagementServiceImpl();
	
	@Test
	 public void whenCreateCourseSussessfully_thenCourseResponseDTO() {
		
		UUID uuid = UUID.randomUUID();
		
		CourseCreateDTO course = new CourseCreateDTO("name1212122", 12, "description");
		ELCourse elcourse = ELCourse.builder()
				.courseName("name1212122")
				.courseTime(12)
				.description("description")
				.id(uuid)
				.lessons(null)
				.build();
		
		when(courseRepository.save(any())).thenReturn(elcourse);
		
		CourseResponseDTO expected = new CourseResponseDTO();
		expected.setId(uuid);
		expected.setCourseName("name1212122");
		expected.setCourseTime(12);
		expected.setDescription("description");
		expected.setLessons(null);
		
		CourseResponseDTO actual = service.createCourse(course);
		
		assertEquals(expected,actual);
		
		
	}
	
	@Test
	 public void whenUpdateCourseSussessfully_thenCourseResponseDTO() {
		
		UUID uuid = UUID.randomUUID();
		
		CourseUpdateDTO course = new CourseUpdateDTO("name1212122", 12, "description");
		ELCourse elcourse = ELCourse.builder()
				.courseName("name1212122")
				.courseTime(12)
				.description("description")
				.id(uuid)
				.lessons(null)
				.build();
		
		when(courseRepository.findById(uuid)).thenReturn(Optional.of(elcourse));
		when(courseRepository.save(any())).thenReturn(elcourse); 
		
		CourseResponseDTO expected = new CourseResponseDTO();
		expected.setId(uuid);
		expected.setCourseName("name1212122");
		expected.setCourseTime(12);
		expected.setDescription("description");
		expected.setLessons(null);
		
		CourseResponseDTO actual = service.updateCourse(course, uuid.toString());
		
		assertEquals(expected,actual);
		
		
	}
	
	@Test
	 public void whenNameExistsIsUsedToUpdateCourse_thenThrowBusinessException() {
		
		UUID uuid = UUID.randomUUID();
		
		CourseUpdateDTO course = new CourseUpdateDTO("nameeee", 12, "description");
		ELCourse elcourse = ELCourse.builder()
				.courseName("name1212122")
				.courseTime(12)
				.description("description")
				.id(uuid)
				.lessons(null)
				.build();
		
		when(courseRepository.findById(uuid)).thenReturn(Optional.of(elcourse));
		when(courseRepository.existsByCourseName(course.getCourseName())).thenReturn(true); 
		
		CourseResponseDTO expected = new CourseResponseDTO();
		expected.setId(uuid);
		expected.setCourseName("name1212122");
		expected.setCourseTime(12);
		expected.setDescription("description");
		expected.setLessons(null);
		
		assertThrows( BusinessException.class ,() -> service.updateCourse(course, uuid.toString()));
	}
	
	@Test
    public void whenUserExistedIsUsedToDeleteCourse_thenDeleteSuccessfully(){

        UUID id = UUID.randomUUID();

        ELCourse user =  ELCourse.builder()
            .courseName("Hello")
            .courseTime(32)
            .description("Nam")
            .build();

        when(courseRepository.findById(id)).thenReturn(Optional.of(user));


        assertDoesNotThrow( () ->service.deleteById(id.toString()));
    }
	
	@Test
	 public void whenGetDetailCourseSussessfully_thenCourseResponseDTO() {
		
		UUID uuid = UUID.randomUUID();
		
		ELCourse elcourse = ELCourse.builder()
				.courseName("name1212122")
				.courseTime(12)
				.description("description")
				.id(uuid)
				.lessons(null)
				.build();
		
		when(courseRepository.findById(uuid)).thenReturn(Optional.of(elcourse));
		when(courseRepository.save(any())).thenReturn(elcourse); 
		
		CourseResponseDTO expected = new CourseResponseDTO();
		expected.setId(uuid);
		expected.setCourseName("name1212122");
		expected.setCourseTime(12);
		expected.setDescription("description");
		expected.setLessons(null);
		
		CourseResponseDTO actual = service.getDetailCourse( uuid.toString());
		
		assertEquals(expected,actual);
		
		
	}
	
	@Test
	public void whenExistsCourseIsUsedToSearchAll_thenReturnPageResponseGroup() {
		PageRequestModel request = new PageRequestModel(1, 10, null, true, null, "value");

		Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

		Page<ELCourse> page = new PageImpl<ELCourse>(new ArrayList<ELCourse>(), pageable, 101);

		when(courseRepository.findAll(pageable)).thenReturn(page);

		PageResponseModel<CourseResponseDTO> expected = new PageResponseModel<CourseResponseDTO>(1, 11,
				new ArrayList<CourseResponseDTO>());

		PageResponseModel<CourseResponseDTO> response = service.search(request);

		assertEquals(expected.getItems(), response.getItems());
		assertEquals(expected.getTotalPage(), response.getTotalPage());
		assertEquals(expected.getPageCurrent(), response.getPageCurrent());

	}
	
	@Test
	public void whenExistsCourseIsUsedToSearchCourseName_thenReturnPageResponseGroup() {
		PageRequestModel request = new PageRequestModel(1, 10, null, true, "courseName", "value");

		Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

		Page<ELCourse> page = new PageImpl<ELCourse>(new ArrayList<ELCourse>(), pageable, 101);

		when(courseRepository.findAll(pageable)).thenReturn(page);

		PageResponseModel<CourseResponseDTO> expected = new PageResponseModel<CourseResponseDTO>(1, 11,
				new ArrayList<CourseResponseDTO>());

		PageResponseModel<CourseResponseDTO> response = service.search(request);

		assertEquals(expected.getItems(), response.getItems());
		assertEquals(expected.getTotalPage(), response.getTotalPage());
		assertEquals(expected.getPageCurrent(), response.getPageCurrent());

	}
	
	
}
