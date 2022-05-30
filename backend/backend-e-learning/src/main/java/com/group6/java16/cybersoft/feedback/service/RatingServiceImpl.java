package com.group6.java16.cybersoft.feedback.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
=======

>>>>>>> 594915fbc0eac0c6c3c97642208013179e6a34d9
import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.util.UserPrincipal;
import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.course.model.ELLesson;
import com.group6.java16.cybersoft.course.repository.ELCourseRepository;
import com.group6.java16.cybersoft.course.repository.ELLessonRepository;
import com.group6.java16.cybersoft.feedback.dto.RatingCreateDTO;
import com.group6.java16.cybersoft.feedback.dto.RatingResponseDTO;
import com.group6.java16.cybersoft.feedback.mapper.RatingMapper;
import com.group6.java16.cybersoft.feedback.model.ELRating;
import com.group6.java16.cybersoft.feedback.repository.RatingRepository;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;

@Service
public class RatingServiceImpl implements RatingService {
	@Autowired
	private RatingRepository repository;

	@Autowired
	private ELLessonRepository lessonRepository;

	@Autowired
	private ELUserRepository userRepository;
	@Autowired
	private ELCourseRepository courseRepository;

	@Value("${rating.not-found}")
	private String errorsCommentNotFound;

	@Value("${entity.id.invalid}")
	private String errorsIdInvalid;

	@Value("${lesson.not-found}")
	private String errorsLessonNotFound;

	@Value("${user.not-found}")
	private String errorsUserNotFound;

	@Value("${rating.existed}")
	private String errorsRatingExists;

	@Override
	public List<RatingResponseDTO> search(String lessonId) {
		ELLesson lesson = lessonRepository.findById(UUID.fromString(lessonId))
				.orElseThrow(() -> new BusinessException(errorsLessonNotFound));
		String userCurrent = UserPrincipal.getUsernameCurrent();
		List<ELRating> response = new ArrayList<ELRating>();

		if (userCurrent.equals(lesson.getCreatedBy())) {
			response = repository.findAll();
		} else {
			response = repository.findByIdLesson(UUID.fromString(lessonId));

		}

		return response.stream().map(RatingMapper.INSTANCE::toResponseDTO).collect(Collectors.toList());

	}

	@Override
	public RatingResponseDTO create(RatingCreateDTO dto) {
		ELUser user = userRepository.findByUsername(UserPrincipal.getUsernameCurrent()).get();

		ELLesson lesson = lessonRepository.findById(UUID.fromString(dto.getLessonId()))
				.orElseThrow(() -> new BusinessException(errorsLessonNotFound));

		ELCourse course = lesson.getCourse();

		if (repository.existsByUserAndLesson(user.getId(), lesson.getId())) {
			throw new BusinessException(errorsRatingExists);
		}
		if (dto.getValue() == 1) {
			lesson.setTotalOneStar(lesson.getTotalOneStar() + 1);
		}
		if (dto.getValue() == 2) {
			lesson.setTotalTwoStar(lesson.getTotalTwoStar() + 1);
		}
		if (dto.getValue() == 3) {
			lesson.setTotalThreeStar(lesson.getTotalThreeStar() + 1);
		}
		if (dto.getValue() == 4) {
			lesson.setTotalFourStar(lesson.getTotalFourStar() + 1);
		}
		if (dto.getValue() == 5) {
			lesson.setTotalFiveStar(lesson.getTotalFiveStar() + 1);
		}

		lesson.setTotalStar(lesson.getTotalStar() + dto.getValue());
		lesson.setTotalRating(lesson.getTotalRating() + 1);
		lesson.setStarAvg(lesson.getTotalStar() * 1.0f / lesson.getTotalRating());

		course.setTotalStar(course.getTotalStar() + dto.getValue());
		course.setTotalRating(course.getTotalRating() + 1);
		course.setStarAvg(course.getTotalStar() * 1.0f / course.getTotalRating());

		lessonRepository.save(lesson);

		courseRepository.save(course);

		ELRating response = ELRating.builder()
				.value(dto.getValue())
				.user(user)
				.lesson(lesson).build();

		return RatingMapper.INSTANCE.toResponseDTO(repository.save(response));
	}

	@Override
	public RatingResponseDTO getMyRatingByLesson(String lessonId) {
		if (!lessonRepository.existsById(UUID.fromString(lessonId))) {
			throw new BusinessException(errorsLessonNotFound);
		}
		String userName = UserPrincipal.getUsernameCurrent();
		return RatingMapper.INSTANCE
				.toResponseDTO(
						repository.getRatingByLessonAndUser(UUID.fromString(lessonId), userName)
								.orElse(null));
	}

}
