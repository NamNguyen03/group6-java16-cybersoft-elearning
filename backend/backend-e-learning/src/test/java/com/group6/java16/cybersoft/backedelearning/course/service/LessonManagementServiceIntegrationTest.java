package com.group6.java16.cybersoft.backedelearning.course.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
import org.springframework.boot.test.context.SpringBootTest;
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
import com.group6.java16.cybersoft.course.dto.LessonCreateDTO;
import com.group6.java16.cybersoft.course.dto.LessonReponseDTO;
import com.group6.java16.cybersoft.course.dto.LessonUpdateDTO;
import com.group6.java16.cybersoft.course.mapper.CourseMapper;
import com.group6.java16.cybersoft.course.mapper.LessonMapper;
import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.course.model.ELLesson;
import com.group6.java16.cybersoft.course.repository.ELCourseRepository;
import com.group6.java16.cybersoft.course.repository.ELLessonRepository;
import com.group6.java16.cybersoft.course.service.CourseManagementService;
import com.group6.java16.cybersoft.course.service.CourseManagementServiceImpl;
import com.group6.java16.cybersoft.course.service.LessonManagementService;
import com.group6.java16.cybersoft.course.service.LessonManagementSeviceImpl;

@SpringBootTest
public class LessonManagementServiceIntegrationTest {
	
	@Mock
	private ELLessonRepository repository;
	
	@Mock
	private LessonMapper mapper;
	
	@InjectMocks
	private LessonManagementService service = new LessonManagementSeviceImpl();
	
	@Test
	 public void whenCreateLessonSussessfully_thenCourseResponseDTO() {
		
		UUID uuid = UUID.randomUUID();
		
		LessonCreateDTO lsson = new LessonCreateDTO("name1212122", "Hello","Nam", "description");
		ELLesson elLesson = ELLesson.builder()
				.content("Hello")
				.description("nam")
				.name("Hallo")
				.id(uuid)
				.course(null)
				.build();
		
		when(repository.save(any())).thenReturn(elLesson);
		
		CourseResponseDTO expected = new CourseResponseDTO();
		expected.setId(uuid);
		expected.setCourseName("name1212122");
		expected.setCourseTime(12);
		expected.setDescription("description");
		expected.setLessons(null);
		
		LessonReponseDTO actual = service.createLesson(lsson);
		
		assertEquals(expected,actual);
		
		
	}
	
	@Test
	 public void whenUpdateLessonSussessfully_thenCourseResponseDTO() {
		
UUID uuid = UUID.randomUUID();
		
		LessonUpdateDTO lsson = new LessonUpdateDTO("name1212122", "Hello","description");
		ELLesson elLesson = ELLesson.builder()
				.content("Hello")
				.description("nam")
				.name("Hallo")
				.id(uuid)
				.build();
		
		when(repository.findById(uuid)).thenReturn(Optional.of(elLesson));
		when(repository.save(any())).thenReturn(elLesson); 
		
		LessonReponseDTO expected = new LessonReponseDTO();
		expected.setId(uuid);
		expected.setContent("Hello");
		expected.setDescription("nam");
		expected.setName("Hallo");
		
		LessonReponseDTO actual = service.updateLesson(lsson, uuid.toString());
		
		assertEquals(expected,actual);
		
		
	}
	
	@Test
	 public void whenNameExistsIsUsedToUpdateLesson_thenThrowBusinessException() {
		
		UUID uuid = UUID.randomUUID();
		
		LessonUpdateDTO lesson = new LessonUpdateDTO("nameeee", "hello", "description");
		ELLesson elcourse = ELLesson.builder()
				.content("Hello")
				.description("Nam ANh")
				.name("Huy Phần")
				.id(uuid)
				.build();
		
		when(repository.findById(uuid)).thenReturn(Optional.of(elcourse));
		when(repository.existsByName(lesson.getName())).thenReturn(true); 
		
		CourseResponseDTO expected = new CourseResponseDTO();
		expected.setId(uuid);
		expected.setCourseName("name1212122");
		expected.setCourseTime(12);
		expected.setDescription("description");
		expected.setLessons(null);
		
		assertThrows( BusinessException.class ,() -> service.updateLesson(lesson, uuid.toString()));
	}
	
	@Test
    public void whenUserExistedIsUsedToDeleteLesson_thenDeleteSuccessfully(){

        UUID id = UUID.randomUUID();

        ELLesson elcourse = ELLesson.builder()
				.content("Hello")
				.description("Nam ANh")
				.name("Huy Phần")
				.id(id)
				.build();

        when(repository.findById(id)).thenReturn(Optional.of(elcourse));


        assertDoesNotThrow( () ->service.deleteById(id.toString()));
    }
	
	
	@Test
	public void whenExistsCourseIsUsedToSearchAll_thenReturnPageResponseGroup() {
		PageRequestModel request = new PageRequestModel(1, 10, null, true, null, "value");

		Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

		Page<ELLesson> page = new PageImpl<ELLesson>(new ArrayList<ELLesson>(), pageable, 101);

		when(repository.findAll(pageable)).thenReturn(page);

		PageResponseModel<LessonReponseDTO> expected = new PageResponseModel<LessonReponseDTO>(1, 11,
				new ArrayList<LessonReponseDTO>());

		PageResponseModel<LessonReponseDTO> response = service.search(request);

		assertEquals(expected.getItems(), response.getItems());
		assertEquals(expected.getTotalPage(), response.getTotalPage());
		assertEquals(expected.getPageCurrent(), response.getPageCurrent());

	}
	
	@Test
	public void whenExistsCourseIsUsedToSearchCourseName_thenReturnPageResponseGroup() {
		PageRequestModel request = new PageRequestModel(1, 10, null, true, "name", "value");

		Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

		Page<ELLesson> page = new PageImpl<ELLesson>(new ArrayList<ELLesson>(), pageable, 101);

		when(repository.findAll(pageable)).thenReturn(page);

		PageResponseModel<LessonReponseDTO> expected = new PageResponseModel<LessonReponseDTO>(1, 11,
				new ArrayList<LessonReponseDTO>());

		PageResponseModel<LessonReponseDTO> response = service.search(request);

		assertEquals(expected.getItems(), response.getItems());
		assertEquals(expected.getTotalPage(), response.getTotalPage());
		assertEquals(expected.getPageCurrent(), response.getPageCurrent());

	}
	
	
}
