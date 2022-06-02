/**
 * 
 */
package com.group6.java16.cybersoft.backedelearning.feedback.service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.course.model.ELLesson;
import com.group6.java16.cybersoft.course.repository.ELCourseRepository;
import com.group6.java16.cybersoft.course.repository.ELLessonRepository;
import com.group6.java16.cybersoft.feedback.dto.RatingCreateDTO;
import com.group6.java16.cybersoft.feedback.dto.RatingResponseDTO;
import com.group6.java16.cybersoft.feedback.dto.StatusCommentDTO;
import com.group6.java16.cybersoft.feedback.dto.reponse.status.CourseDTO;
import com.group6.java16.cybersoft.feedback.dto.reponse.status.LessonDTO;
import com.group6.java16.cybersoft.feedback.dto.reponse.status.StatusCommentResponseDTO;
import com.group6.java16.cybersoft.feedback.dto.reponse.status.UserDTO;
import com.group6.java16.cybersoft.feedback.model.ELComment;
import com.group6.java16.cybersoft.feedback.model.ELRating;
import com.group6.java16.cybersoft.feedback.model.ELStatusComment;
import com.group6.java16.cybersoft.feedback.model.EnumStatusComment;
import com.group6.java16.cybersoft.feedback.repository.RatingRepository;
import com.group6.java16.cybersoft.feedback.repository.StatusCommentRepository;
import com.group6.java16.cybersoft.feedback.service.*;
import com.group6.java16.cybersoft.user.dto.client.UserResponseClientDTO;
import com.group6.java16.cybersoft.user.mapper.UserMapper;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;


/**
 * @author nam
 *
 */
@SpringBootTest
public class FeedBackServiceIntegrationTest {

	@Mock
	private UserMapper mapper;

	@InjectMocks
	private RatingService ratingService = new RatingServiceImpl();

	@InjectMocks
	private StatusCommentService statusCommentService = new StatusCommentServiceImpl();

	@InjectMocks
	private CommentService commentService = new CommentServiceImpl();

	@Mock
	private RatingRepository ratingRepository;

	@Mock
	private ELLessonRepository lessonRepository;

	@Mock
	private ELUserRepository userRepository;

	@Mock
	private ELCourseRepository courseRepository;

	@Mock
	private StatusCommentRepository statusCommentRepository;

	@Test
	public void whenLessonNotExistsIsUsedToRating_thenThrowBusinessException() {
		UUID idLesson = UUID.randomUUID();
		when(lessonRepository.findById(idLesson)).thenReturn(Optional.empty());
		RatingCreateDTO rq = new RatingCreateDTO();
		rq.setLessonId(idLesson.toString());
		assertThrows(BusinessException.class, () -> ratingService.create(rq));
	}


	@Test
	public void whenUserWasRatingIsUsedToRating_thenThrowBusinessException() {

		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);

		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("nam");

		UUID idUser = UUID.randomUUID();
		UUID idLesson = UUID.randomUUID();

		RatingCreateDTO rq = new RatingCreateDTO();
		rq.setLessonId(idLesson.toString());

		ELLesson lesson = ELLesson.builder()
				.id(idLesson)
				.build();
		ELUser user = ELUser.builder()
				.id(idUser).build();

		when(userRepository.findByUsername("nam")).thenReturn(Optional.of(user));
		when(lessonRepository.findById(idLesson)).thenReturn(Optional.of(lesson));

		when(ratingRepository.existsByUserAndLesson(idUser, idLesson)).thenReturn(true);

		assertThrows(BusinessException.class, () -> ratingService.create(rq));
	}

	@Test
	public void whenRatingOneStar_thenReturnRatingResponse() {
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);

		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("nam");

		UUID idUser = UUID.randomUUID();
		UUID idLesson = UUID.randomUUID();
		UUID idRating = UUID.randomUUID();

		RatingCreateDTO rq = new RatingCreateDTO();
		rq.setLessonId(idLesson.toString());
		rq.setValue(1);
		ELLesson lesson = ELLesson.builder()
				.id(idLesson)
				.totalRating(0)
				.totalStar(0)
				.starAvg(0)
				.totalOneStar(0)
				.totalTwoStar(0)
				.totalThreeStar(0)
				.totalFourStar(0)
				.totalFiveStar(0)
				.course(
						ELCourse.builder()
						.totalRating(0)
						.totalStar(0)
						.starAvg(0)	
						.build()
						)
				.build();
		ELUser user = ELUser.builder()
				.displayName("Nam Nguyen")
				.avatar("avatar")
				.id(idUser).build();



		ELRating rating = ELRating.builder()
				.id(idRating)
				.value(rq.getValue())
				.user(user)
				.lesson(lesson).build();

		when(userRepository.findByUsername("nam")).thenReturn(Optional.of(user));
		when(lessonRepository.findById(idLesson)).thenReturn(Optional.of(lesson));

		when(ratingRepository.existsByUserAndLesson(idUser, idLesson)).thenReturn(false);
		when(ratingRepository.save(any())).thenReturn(rating);

		RatingResponseDTO expected = new RatingResponseDTO(idRating, rq.getValue() , 
				new UserResponseClientDTO(idUser,"Nam Nguyen", "avatar"));

		RatingResponseDTO actual = ratingService.create(rq);
		assertEquals(expected, actual);
	}

	@Test
	public void whenRatingTwoStar_thenReturnRatingResponse() {
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);

		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("nam");

		UUID idUser = UUID.randomUUID();
		UUID idLesson = UUID.randomUUID();
		UUID idRating = UUID.randomUUID();

		RatingCreateDTO rq = new RatingCreateDTO();
		rq.setLessonId(idLesson.toString());
		rq.setValue(2);
		ELLesson lesson = ELLesson.builder()
				.id(idLesson)
				.totalRating(0)
				.totalStar(0)
				.starAvg(0)
				.totalOneStar(0)
				.totalTwoStar(0)
				.totalThreeStar(0)
				.totalFourStar(0)
				.totalFiveStar(0)
				.course(
						ELCourse.builder()
						.totalRating(0)
						.totalStar(0)
						.starAvg(0)	
						.build()
						)
				.build();
		ELUser user = ELUser.builder()
				.displayName("Nam Nguyen")
				.avatar("avatar")
				.id(idUser).build();



		ELRating rating = ELRating.builder()
				.id(idRating)
				.value(rq.getValue())
				.user(user)
				.lesson(lesson).build();

		when(userRepository.findByUsername("nam")).thenReturn(Optional.of(user));
		when(lessonRepository.findById(idLesson)).thenReturn(Optional.of(lesson));

		when(ratingRepository.existsByUserAndLesson(idUser, idLesson)).thenReturn(false);
		when(ratingRepository.save(any())).thenReturn(rating);

		RatingResponseDTO expected = new RatingResponseDTO(idRating, rq.getValue() , 
				new UserResponseClientDTO(idUser,"Nam Nguyen", "avatar"));

		RatingResponseDTO actual = ratingService.create(rq);
		assertEquals(expected, actual);
	}

	@Test
	public void whenRatingThreeStar_thenReturnRatingResponse() {
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);

		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("nam");

		UUID idUser = UUID.randomUUID();
		UUID idLesson = UUID.randomUUID();
		UUID idRating = UUID.randomUUID();

		RatingCreateDTO rq = new RatingCreateDTO();
		rq.setLessonId(idLesson.toString());
		rq.setValue(3);
		ELLesson lesson = ELLesson.builder()
				.id(idLesson)
				.totalRating(0)
				.totalStar(0)
				.starAvg(0)
				.totalOneStar(0)
				.totalTwoStar(0)
				.totalThreeStar(0)
				.totalFourStar(0)
				.totalFiveStar(0)
				.course(
						ELCourse.builder()
						.totalRating(0)
						.totalStar(0)
						.starAvg(0)	
						.build()
						)
				.build();
		ELUser user = ELUser.builder()
				.displayName("Nam Nguyen")
				.avatar("avatar")
				.id(idUser).build();



		ELRating rating = ELRating.builder()
				.id(idRating)
				.value(rq.getValue())
				.user(user)
				.lesson(lesson).build();

		when(userRepository.findByUsername("nam")).thenReturn(Optional.of(user));
		when(lessonRepository.findById(idLesson)).thenReturn(Optional.of(lesson));

		when(ratingRepository.existsByUserAndLesson(idUser, idLesson)).thenReturn(false);
		when(ratingRepository.save(any())).thenReturn(rating);

		RatingResponseDTO expected = new RatingResponseDTO(idRating, rq.getValue() , 
				new UserResponseClientDTO(idUser,"Nam Nguyen", "avatar"));

		RatingResponseDTO actual = ratingService.create(rq);
		assertEquals(expected, actual);
	}

	@Test
	public void whenRatingFourStar_thenReturnRatingResponse() {
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);

		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("nam");

		UUID idUser = UUID.randomUUID();
		UUID idLesson = UUID.randomUUID();
		UUID idRating = UUID.randomUUID();

		RatingCreateDTO rq = new RatingCreateDTO();
		rq.setLessonId(idLesson.toString());
		rq.setValue(4);
		ELLesson lesson = ELLesson.builder()
				.id(idLesson)
				.totalRating(0)
				.totalStar(0)
				.starAvg(0)
				.totalOneStar(0)
				.totalTwoStar(0)
				.totalThreeStar(0)
				.totalFourStar(0)
				.totalFiveStar(0)
				.course(
						ELCourse.builder()
						.totalRating(0)
						.totalStar(0)
						.starAvg(0)	
						.build()
						)
				.build();
		ELUser user = ELUser.builder()
				.displayName("Nam Nguyen")
				.avatar("avatar")
				.id(idUser).build();



		ELRating rating = ELRating.builder()
				.id(idRating)
				.value(rq.getValue())
				.user(user)
				.lesson(lesson).build();

		when(userRepository.findByUsername("nam")).thenReturn(Optional.of(user));
		when(lessonRepository.findById(idLesson)).thenReturn(Optional.of(lesson));

		when(ratingRepository.existsByUserAndLesson(idUser, idLesson)).thenReturn(false);
		when(ratingRepository.save(any())).thenReturn(rating);

		RatingResponseDTO expected = new RatingResponseDTO(idRating, rq.getValue() , 
				new UserResponseClientDTO(idUser,"Nam Nguyen", "avatar"));

		RatingResponseDTO actual = ratingService.create(rq);
		assertEquals(expected, actual);
	}

	@Test
	public void whenRatingFiveStar_thenReturnRatingResponse() {
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);

		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("nam");

		UUID idUser = UUID.randomUUID();
		UUID idLesson = UUID.randomUUID();
		UUID idRating = UUID.randomUUID();

		RatingCreateDTO rq = new RatingCreateDTO();
		rq.setLessonId(idLesson.toString());
		rq.setValue(5);
		ELLesson lesson = ELLesson.builder()
				.id(idLesson)
				.totalRating(0)
				.totalStar(0)
				.starAvg(0)
				.totalOneStar(0)
				.totalTwoStar(0)
				.totalThreeStar(0)
				.totalFourStar(0)
				.totalFiveStar(0)
				.course(
						ELCourse.builder()
						.totalRating(0)
						.totalStar(0)
						.starAvg(0)	
						.build()
						)
				.build();
		ELUser user = ELUser.builder()
				.displayName("Nam Nguyen")
				.avatar("avatar")
				.id(idUser).build();



		ELRating rating = ELRating.builder()
				.id(idRating)
				.value(rq.getValue())
				.user(user)
				.lesson(lesson).build();

		when(userRepository.findByUsername("nam")).thenReturn(Optional.of(user));
		when(lessonRepository.findById(idLesson)).thenReturn(Optional.of(lesson));

		when(ratingRepository.existsByUserAndLesson(idUser, idLesson)).thenReturn(false);
		when(ratingRepository.save(any())).thenReturn(rating);

		RatingResponseDTO expected = new RatingResponseDTO(idRating, rq.getValue() , 
				new UserResponseClientDTO(idUser,"Nam Nguyen", "avatar"));

		RatingResponseDTO actual = ratingService.create(rq);
		assertEquals(expected, actual);
	}

	@Test
	public void whenIdLessonNotExistsIsUsedGetMyRatingById_thenThrowBusinessException() {
		UUID id = UUID.randomUUID();

		when(lessonRepository.existsById(id)).thenReturn(false);

		assertThrows(BusinessException.class, () -> ratingService.getMyRatingByLesson(id.toString()));
	}

	@Test
	public void whenGetMyRatingById_thenReturnRatingResponseDTO() {
		UUID idLesson = UUID.randomUUID();
		UUID idRating = UUID.randomUUID();
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("nam");
		when(lessonRepository.existsById(idLesson)).thenReturn(true);
		ELRating rating = ELRating.builder()
				.id(idRating)
				.value(1)
				.user(null)
				.lesson(null)
				.build();

		when(ratingRepository.getRatingByLessonAndUser(idLesson, "nam")).thenReturn(Optional.of(rating));

		RatingResponseDTO expected = new RatingResponseDTO();
		expected.setId(idRating);
		expected.setValue(1);
		expected.setUser(null);

		RatingResponseDTO actual = ratingService.getMyRatingByLesson(idLesson.toString());
		assertEquals(expected, actual);
	}


	@Test
	public void whenIdLessonNotExistsIsUsedSearcheLesson_thenThrowBusinessException() {
		UUID idLesson = UUID.randomUUID();
		when(lessonRepository.findById(idLesson)).thenReturn(Optional.empty());
		assertThrows(BusinessException.class, () -> commentService.search(idLesson.toString()));
	}

	@Test
	public void whenStatusCommentExistsIsUsedSearch_thenReturnStatus() {
		PageRequestModel rq = new PageRequestModel(1,10, null, true, null, null);
		
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("nam");

		Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());
		Page<ELStatusComment> page = new PageImpl<ELStatusComment>(new ArrayList<ELStatusComment>(), pageable, 10l);

		when(statusCommentRepository.searchAll("nam",pageable)).thenReturn(page);

		PageResponseModel<StatusCommentResponseDTO> expected = new PageResponseModel<StatusCommentResponseDTO>(1,1, new ArrayList<StatusCommentResponseDTO>());

		PageResponseModel<StatusCommentResponseDTO> rp = statusCommentService.search(rq);

		assertEquals(expected.getItems(), rp.getItems());
		assertEquals(expected.getTotalPage(), rp.getTotalPage());
		assertEquals(expected.getPageCurrent(), rp.getPageCurrent());
	}
	
	@Test
	public void whenStatusCommentExistsIsUsedSearchBySatustusAndSortByStatus_thenReturnStatus() {
		PageRequestModel rq = new PageRequestModel(1,10, "status", true, "status", "value");
		
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("nam");

		Pageable pageable = PageRequest.of(0, 10, Sort.by("status").ascending());
		Page<ELStatusComment> page = new PageImpl<ELStatusComment>(new ArrayList<ELStatusComment>(), pageable, 10l);

		when(statusCommentRepository.searchByStatus("nam", "value", pageable)).thenReturn(page);

		PageResponseModel<StatusCommentResponseDTO> expected = new PageResponseModel<StatusCommentResponseDTO>(1,1, new ArrayList<StatusCommentResponseDTO>());

		PageResponseModel<StatusCommentResponseDTO> rp = statusCommentService.search(rq);

		assertEquals(expected.getItems(), rp.getItems());
		assertEquals(expected.getTotalPage(), rp.getTotalPage());
		assertEquals(expected.getPageCurrent(), rp.getPageCurrent());
	}
	
	@Test
	public void whenStatusCommentExistsIsUsedSearchByDisplayName_thenReturnStatus() {
		PageRequestModel rq = new PageRequestModel(1,10, "none", true, "displayName", "value");
		
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("nam");

		Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());
		Page<ELStatusComment> page = new PageImpl<ELStatusComment>(new ArrayList<ELStatusComment>(), pageable, 10l);

		when(statusCommentRepository.searchByDisplayNameUser("nam", "value", pageable)).thenReturn(page);

		PageResponseModel<StatusCommentResponseDTO> expected = new PageResponseModel<StatusCommentResponseDTO>(1,1, new ArrayList<StatusCommentResponseDTO>());

		PageResponseModel<StatusCommentResponseDTO> rp = statusCommentService.search(rq);

		assertEquals(expected.getItems(), rp.getItems());
		assertEquals(expected.getTotalPage(), rp.getTotalPage());
		assertEquals(expected.getPageCurrent(), rp.getPageCurrent());
	}
	
	@Test
	public void whenStatusCommentExistsIsUsedSearchByCourseNameAndSort_thenReturnStatus() {
		PageRequestModel rq = new PageRequestModel(1,10, "status", false, "courseName", "value");
		
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("nam");

		Pageable pageable = PageRequest.of(0, 10, Sort.by("status").descending());

		Page<ELStatusComment> page = new PageImpl<ELStatusComment>(
			Arrays.asList(ELStatusComment.builder()
				.course(ELCourse.builder().lessons(null).build())
				.build()), pageable, 10l);

		when(statusCommentRepository.searchByNameCourse("nam", "value", pageable)).thenReturn(page);
		
		StatusCommentResponseDTO statusCommentResponseDTO = new StatusCommentResponseDTO();

		CourseDTO courseDTO = new CourseDTO();
		courseDTO.setLessons(null);
		
		statusCommentResponseDTO.setCourse(courseDTO);
		statusCommentResponseDTO.setStatus(null);

		PageResponseModel<StatusCommentResponseDTO> expected = new PageResponseModel<StatusCommentResponseDTO>(1,1, 
			Arrays.asList(statusCommentResponseDTO));

		PageResponseModel<StatusCommentResponseDTO> rp = statusCommentService.search(rq);

		assertEquals(expected.getItems().get(0), rp.getItems().get(0));
		assertEquals(expected.getTotalPage(), rp.getTotalPage());
		assertEquals(expected.getPageCurrent(), rp.getPageCurrent());
	}
	
	@Test
	public void whenGetById_thenReturnStatusCommentResponseDTO() {
		Set<ELLesson> lessons = new HashSet<>();
		
		Set<ELComment> comments = new HashSet<>();
		ELUser user = ELUser.builder()
						.username("username").build();
		
		comments.add(ELComment.builder()
			.user(user).build());
		
		comments.add(ELComment.builder()
				.user(ELUser.builder()
					.username("username").build()).build());
		
		lessons.add(ELLesson.builder()
			.comments(comments)
			.build());
		
		lessons.add(ELLesson.builder()
				.comments(new LinkedHashSet<ELComment>())
				.build());
		
		ELStatusComment statusComment = ELStatusComment.builder()
			.status(EnumStatusComment.PUBLIC)
			.course(
				ELCourse.builder()
					.lessons(lessons)
					.build()	
				)
			.user(user)
			.build();
		
		UUID idStatusComment = UUID.randomUUID();
		
		when(statusCommentRepository.findByAndUserId(idStatusComment)).thenReturn(Optional.of(statusComment));
		
		StatusCommentResponseDTO rp = statusCommentService.getById(idStatusComment.toString());
		
		List<LessonDTO> lessonsRp = List.copyOf(rp.getCourse().getLessons());
		
		assertEquals(EnumStatusComment.PUBLIC, rp.getStatus());
		assertEquals(1, lessonsRp.size());
		assertEquals(1, lessonsRp.get(0).getComments().size());
	}
	
	@Test
	public void whenStatusCommentNotExistsIsUsedToCreateStatusComment_thenReturnStatusCommentResponse() {
		UUID idUser = UUID.randomUUID();
		UUID idCourse = UUID.randomUUID();
		UUID idStatus = UUID.randomUUID();
		StatusCommentDTO rq = new StatusCommentDTO(EnumStatusComment.PRIVATE,idUser.toString(),idCourse.toString());
		
		when(statusCommentRepository.existsByUserAndCourse(idUser,idCourse)).thenReturn(false);
		
		ELUser user = ELUser.builder()
			.id(idUser)
			.displayName("nam")
			.build();
		
		when(userRepository.findById(idUser)).thenReturn(Optional.of(user));
		
		ELCourse course = ELCourse.builder()
			.id(idCourse)
			.courseName("Java")
			.build();
		
		when(courseRepository.findById(idCourse)).thenReturn(Optional.of(course));
		

		ELStatusComment status = ELStatusComment.builder()
			.id(idStatus)
			.user(user)
			.course(course)
			.status(rq.getStatus())
			.build();
		
		when(statusCommentRepository.save(any())).thenReturn(status);
		
		StatusCommentResponseDTO expected = new StatusCommentResponseDTO(
			idStatus,
			EnumStatusComment.PRIVATE,
			new UserDTO(idUser, "nam"),
			new CourseDTO(idCourse, "Java",new LinkedHashSet<>()));
		
		StatusCommentResponseDTO actual = statusCommentService.createStatusComment(rq);
		
		assertEquals(expected, actual);
	}

	@Test
	public void whenStatusExistsIsUsedToCreateStatusComment_thenThrowBusinessException() {
		UUID idCourse = UUID.randomUUID();
		UUID idUser = UUID.randomUUID();
		StatusCommentDTO rq = new StatusCommentDTO(EnumStatusComment.PRIVATE,idUser.toString(),idCourse.toString());
		
		when(statusCommentRepository.existsByUserAndCourse(idUser,idCourse)).thenReturn(true);
		
		assertThrows(BusinessException.class, () -> statusCommentService.createStatusComment(rq));
	}
	
	@Test 
	public void whenUpdateStatusCommentSuccess_thenReturnStatusCommentResponse() {
		UUID idStatus = UUID.randomUUID();
		
		ELStatusComment statusComment = ELStatusComment.builder()
			.status(EnumStatusComment.BLOCKED)
			.id(idStatus)
			.build();
		
		when(statusCommentRepository.findById(idStatus)).thenReturn(Optional.of(statusComment));
		
		when(statusCommentRepository.save(any())).thenReturn(statusComment);
	
		StatusCommentResponseDTO expected = new StatusCommentResponseDTO();
		expected.setId(idStatus);
		expected.setStatus(EnumStatusComment.BLOCKED);
		
		StatusCommentResponseDTO actual = statusCommentService.updateStatusComment(idStatus.toString(), EnumStatusComment.BLOCKED);
		
		assertEquals(expected, actual);
	}
	
}
