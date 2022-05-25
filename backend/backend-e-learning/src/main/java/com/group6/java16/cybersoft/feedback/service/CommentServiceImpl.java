package com.group6.java16.cybersoft.feedback.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.util.UserPrincipal;
import com.group6.java16.cybersoft.course.model.ELLesson;
import com.group6.java16.cybersoft.course.repository.ELLessonRepository;
import com.group6.java16.cybersoft.feedback.dto.CommentCreateDTO;
import com.group6.java16.cybersoft.feedback.dto.CommentResponseDTO;
import com.group6.java16.cybersoft.feedback.mapper.CommentMapper;
import com.group6.java16.cybersoft.feedback.model.ELComment;
import com.group6.java16.cybersoft.feedback.repository.CommentRepository;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;

@Service
@PropertySources({ @PropertySource("classpath:/validation/message.properties") })
public class CommentServiceImpl implements CommentService{
	@Autowired
	private CommentRepository repository;
	
	@Autowired
	private ELLessonRepository lessonRepository;
	
	@Autowired
	private ELUserRepository userRepository;
		
	@Value("${comment.not-found}")
	private String errorsCommentNotFound;
	
	@Value("${entity.id.invalid}")
	private String errorsIdInvalid;
	
	@Value("${lesson.not-found}")
	private String errorsLessonNotFound;
	
	@Value("${user.not-found}")
	private String errorsUserNotFound;
	
	@Override
	public List<CommentResponseDTO> search(String idLesson) {
		ELLesson lesson = lessonRepository.findById(UUID.fromString(idLesson))
                .orElseThrow(() -> new BusinessException(errorsLessonNotFound));
		String userCurrent = UserPrincipal.getUsernameCurrent();
		List<ELComment> response = new ArrayList<ELComment>();
		
		if(userCurrent.equals( lesson.getCreatedBy())) {
			response = repository.findAll();
		}else {
			response = repository.findByIdLesson(UUID.fromString(idLesson),userCurrent);
			
		}
		
		
		return response.stream().map(CommentMapper.INSTANCE::toResponseDTO).collect(Collectors.toList());
	}

	@Override
	public CommentResponseDTO create(CommentCreateDTO dto) {
		ELUser user = userRepository.findById(UUID.fromString(dto.getUserId()))
                .orElseThrow(() -> new BusinessException(errorsUserNotFound));
		ELLesson lesson = lessonRepository.findById(UUID.fromString(dto.getLessonId()))
                .orElseThrow(() -> new BusinessException(errorsLessonNotFound));
		ELComment response = ELComment.builder()
				.content(dto.getContent())
				.user(user)
				.lesson(lesson).build();
		
		return CommentMapper.INSTANCE.toResponseDTO(repository.save(response)); 	 	
	}

	@Override
	public void delete(String commentId) {
		ELComment comment = getById(commentId);
		repository.delete(comment);
	}

	private ELComment getById(String id) {
        return repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new BusinessException(errorsCommentNotFound));
    }
	
	 	 

}
