package com.group6.java16.cybersoft.feedback.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.course.model.ELLesson;
import com.group6.java16.cybersoft.course.repository.ELCourseRepository;
import com.group6.java16.cybersoft.course.repository.ELLessonRepository;
import com.group6.java16.cybersoft.feedback.dto.CommentResponseDTO;
import com.group6.java16.cybersoft.feedback.dto.RatingCreateDTO;
import com.group6.java16.cybersoft.feedback.dto.RatingResponseDTO;
import com.group6.java16.cybersoft.feedback.mapper.RatingMapper;
import com.group6.java16.cybersoft.feedback.model.ELRating;
import com.group6.java16.cybersoft.feedback.repository.RatingRepository;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;

@Service
public class RatingServiceImpl implements RatingService{
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
	
	
	@Override
	public List<CommentResponseDTO> search(String lessonId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RatingResponseDTO create(RatingCreateDTO dto) {
		ELUser user = userRepository.findById(UUID.fromString(dto.getUserId()))
                .orElseThrow(() -> new BusinessException(errorsUserNotFound));

		ELLesson lesson = lessonRepository.findById(UUID.fromString(dto.getLessonId()))
                .orElseThrow(() -> new BusinessException(errorsLessonNotFound));
		
		ELCourse course = lesson.getCourse();
		
		lesson.setTotalStar(lesson.getTotalStar()+dto.getValue());
		lesson.setTotalRating(lesson.getTotalRating()+1);
		
		course.setTotalStar(course.getTotalStar()+lesson.getTotalStar());
		course.setTotalRating(course.getTotalRating()+lesson.getTotalRating());
		
		lessonRepository.save(lesson);
		
		courseRepository.save(course);

		
		ELRating response = ELRating.builder()
				.value(dto.getValue())
				.user(user)
				.lesson(lesson).build();
		
		return RatingMapper.INSTANCE.toResponseDTO(repository.save(response));
	}

}
