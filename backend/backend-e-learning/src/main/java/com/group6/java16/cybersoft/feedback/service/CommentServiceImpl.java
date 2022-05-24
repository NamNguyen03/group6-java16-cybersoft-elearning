package com.group6.java16.cybersoft.feedback.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

@Service
@PropertySources({ @PropertySource("classpath:/validation/message.properties") })
public class CommentServiceImpl implements CommentService{
	@Autowired
	private CommentRepository repository;
	
	@Autowired
	private ELLessonRepository lessonRepository;
	
	
	@Value("${comment.not-found}")
	private String errorsCommentNotFound;
	
	@Value("${entity.id.invalid}")
	private String errorsIdInvalid;
	
	@Value("${lesson.not-found}")
	private String errorLessonNotFound;
	
	@Override
	public List<CommentResponseDTO> search(String idLesson) {
		ELLesson lesson = getLessonById(idLesson);
		String userCurrent = UserPrincipal.getUsernameCurrent();
		List<ELComment> response = new ArrayList<ELComment>();
		
		if(userCurrent.equals(lesson.getCreatedBy())) {
			response = repository.findAll();
		}else {
			response = repository.findByStatusComment(UUID.fromString(idLesson));
		}
		
		
		return response.stream().map(CommentMapper.INSTANCE::toResponseDTO).collect(Collectors.toList());
	}

	@Override
	public CommentResponseDTO create(CommentCreateDTO dto) {
		return CommentMapper.INSTANCE.toResponseDTO(repository.save(CommentMapper.INSTANCE.toModel(dto)));
	}

	@Override
	public CommentResponseDTO update(String commentId, String contentUpdate) {
		ELComment comment = getById(commentId);
		if(isValidString(contentUpdate)) {
			comment.setContent(contentUpdate);
		}
		
		return CommentMapper.INSTANCE.toResponseDTO(repository.save(comment));
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
	
	 private boolean isValidString(String s) {
	        if (s == null || s.length() == 0) {
	            return false;
	        }
	        return true;
	    }
	 
	 private ELLesson getLessonById(String lessonId) {
			UUID uuid;
			try {
				uuid = UUID.fromString(lessonId);
			} catch (Exception e) {
				throw new BusinessException(errorsIdInvalid);
			}

			Optional<ELLesson> lessonOpt = lessonRepository.findById(uuid);

			if (lessonOpt.isEmpty()) {
				throw new BusinessException(errorLessonNotFound);
			}
			return lessonOpt.get();
		}

}
