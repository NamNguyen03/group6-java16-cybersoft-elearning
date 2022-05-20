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
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.common.service.storage.MyFirebaseService;
import com.group6.java16.cybersoft.course.dto.CourseCreateDTO;
import com.group6.java16.cybersoft.course.dto.CourseResponseDTO;
import com.group6.java16.cybersoft.course.dto.CourseUpdateDTO;
import com.group6.java16.cybersoft.course.dto.LessonCreateDTO;
import com.group6.java16.cybersoft.course.dto.LessonResponseDTO;
import com.group6.java16.cybersoft.course.dto.LessonUpdateDTO;
import com.group6.java16.cybersoft.course.mapper.CourseMapper;
import com.group6.java16.cybersoft.course.mapper.LessonMapper;
import com.group6.java16.cybersoft.course.model.CategoryEnum;
import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.course.model.ELLesson;
import com.group6.java16.cybersoft.course.model.LevelEnum;
import com.group6.java16.cybersoft.course.repository.ELCourseRepository;
import com.group6.java16.cybersoft.course.repository.ELLessonRepository;
import com.group6.java16.cybersoft.course.service.CourseManagementService;
import com.group6.java16.cybersoft.course.service.CourseManagementServiceImpl;
import com.group6.java16.cybersoft.course.service.LessonManagementService;
import com.group6.java16.cybersoft.course.service.LessonManagementSeviceImpl;
import com.group6.java16.cybersoft.role.dto.GroupUpdateDTO;
import com.group6.java16.cybersoft.role.model.ELGroup;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.model.UserStatus;

@SpringBootTest
public class LessonManagementServiceIntegrationTest {

	@Mock
	private ELLessonRepository repository;

	@Mock
	private ELCourseRepository courseRepository;

	@Mock
	private LessonMapper mapper;

	@Mock
	private MyFirebaseService firebaseFileService;

	@InjectMocks
	private LessonManagementService service = new LessonManagementSeviceImpl();

	@Test
	public void whenCreateLessonSussessfully_thenLessonResponseDTO() {

		UUID uuid = UUID.randomUUID();

		LessonCreateDTO lsson = new LessonCreateDTO("Hallo", "Hello", "Nam", uuid.toString());
		ELLesson elLesson = ELLesson.builder().content("Hello").description("nam").name("Hallo").id(uuid).build();

		ELCourse elcourse = ELCourse.builder().courseName("name").courseTime(3).description("description")
				.skill1("Java").img("anh.img").category(CategoryEnum.BUSINESS).level(LevelEnum.BEGINNER).skill2("HTML")
				.skill3("CSS").id(uuid).lessons(null).build();

		when(courseRepository.findById(uuid)).thenReturn(Optional.of(elcourse));
		when(repository.save(any())).thenReturn(elLesson);
		LessonResponseDTO actual = service.createLesson(lsson);

		LessonResponseDTO expected = new LessonResponseDTO();
		expected.setId(uuid);
		expected.setContent("Hello");
		expected.setName("Hallo");
		expected.setDescription("nam");

		assertEquals(expected, actual);

	}

	@Test
	public void whenUpdateLessonSussessfully_thenCourseResponseDTO() {

		UUID uuid = UUID.randomUUID();

		LessonUpdateDTO lsson = new LessonUpdateDTO("Hallo", "Hello", "nam");
		ELLesson elLesson = ELLesson.builder().content("Hello").description("nam").name("Hallo").id(uuid).build();

		when(repository.findById(uuid)).thenReturn(Optional.of(elLesson));
		when(repository.save(any())).thenReturn(elLesson);

		LessonResponseDTO expected = new LessonResponseDTO();
		expected.setId(uuid);
		expected.setContent("Hello");
		expected.setDescription("nam");
		expected.setName("Hallo");

		LessonResponseDTO actual = service.updateLesson(lsson, uuid.toString());

		assertEquals(expected, actual);

	}

	@Test
	public void whenUserExistedIsUsedToDeleteLesson_thenDeleteSuccessfully() {

		UUID id = UUID.randomUUID();

		ELCourse elcourse = ELCourse.builder().courseName("name").courseTime(3).description("description")
				.skill1("Java").img("anh.img").category(CategoryEnum.BUSINESS).level(LevelEnum.BEGINNER).skill2("HTML")
				.skill3("CSS").id(id).lessons(null).build();

		ELLesson less = ELLesson.builder().content("huy").description("phan").name("JavaHome").course(elcourse).build();

		when(courseRepository.findById(id)).thenReturn(Optional.of(elcourse));
		when(repository.findById(UUID.fromString(id.toString()))).thenReturn(Optional.of(less));

		assertDoesNotThrow(() -> service.deleteById(id.toString()));

	}

	@Test
	public void whenExistsLessonIsUsedToSearchAll_thenReturnPageResponseGroup() {
		PageRequestModel request = new PageRequestModel(1, 10, null, true, null, null);

		Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());

		Page<ELLesson> page = new PageImpl<ELLesson>(new ArrayList<ELLesson>(), pageable, 101);

		when(repository.findAll(pageable)).thenReturn(page);

		PageResponseModel<LessonResponseDTO> expected = new PageResponseModel<LessonResponseDTO>(1, 11,
				new ArrayList<LessonResponseDTO>());

		PageResponseModel<LessonResponseDTO> response = service.search(request);

		assertEquals(expected.getItems(), response.getItems());
		assertEquals(expected.getTotalPage(), response.getTotalPage());
		assertEquals(expected.getPageCurrent(), response.getPageCurrent());

	}

	@Test
	public void whenExistsLessonIsUsedToSearchLessonName_thenReturnPageResponseGroup() {
		PageRequestModel request = new PageRequestModel(1, 10, null, true, "name", "value");

		Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());

		Page<ELLesson> page = new PageImpl<ELLesson>(new ArrayList<ELLesson>(), pageable, 101);

		when(repository.searchByLessonName("value", pageable)).thenReturn(page);

		PageResponseModel<LessonResponseDTO> expected = new PageResponseModel<LessonResponseDTO>(1, 11,
				new ArrayList<LessonResponseDTO>());

		PageResponseModel<LessonResponseDTO> response = service.search(request);

		assertEquals(expected.getItems(), response.getItems());
		assertEquals(expected.getTotalPage(), response.getTotalPage());
		assertEquals(expected.getPageCurrent(), response.getPageCurrent());

	}

	@Test
	public void whenGetDetailLessonSussessfully_thenCourseResponseDTO() {

		UUID uuid = UUID.randomUUID();

		ELLesson ellesson = ELLesson.builder().content("Hello Pass").description("String Java Nam")
				.name("Java back end Huy Phan").id(uuid).build();

		when(repository.findById(uuid)).thenReturn(Optional.of(ellesson));
		when(repository.save(any())).thenReturn(ellesson);

		LessonResponseDTO expected = new LessonResponseDTO();
		expected.setName("Java back end Huy Phan");
		expected.setDescription("String Java Nam");
		expected.setContent("Hello Pass");
		expected.setId(uuid);

		LessonResponseDTO actual = service.getInfoLesson(uuid.toString());

		assertEquals(expected, actual);

	}

	@Test
	public void whenPostImg_thenUpdateSuccessfully() {
		String imageName = "filename";
		MockMultipartFile file = new MockMultipartFile(imageName, imageName + ".txt", MediaType.TEXT_PLAIN_VALUE,
				"Hello, World!".getBytes());

		String url = "https://firebasestorage.googleapis.com/v0/b/e-learning-5efea.appspot.com/o/" + imageName
				+ "?alt=media&token=" + imageName;

		when(firebaseFileService.saveFile(file)).thenReturn(url);

	}
	
	  @Test
	    public void whenExistsUserIsUsedToSearchAllAndSort_thenReturnPageResponseUser(){
	        PageRequestModel rq = new PageRequestModel(1,10, "name", true, null, null);

	        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

	        Page<ELLesson> page = new PageImpl<ELLesson>(new ArrayList<ELLesson>(), pageable, 10l);

	        when(repository.findAll(pageable)).thenReturn(page);

	        PageResponseModel<LessonResponseDTO> expected = new PageResponseModel<LessonResponseDTO>(1,1, new ArrayList<LessonResponseDTO>());

	        PageResponseModel<LessonResponseDTO> rp = service.search(rq);
	        assertEquals(expected.getItems(), rp.getItems());
	        assertEquals(expected.getTotalPage(), rp.getTotalPage());
	        assertEquals(expected.getPageCurrent(), rp.getPageCurrent());
	    }

}
