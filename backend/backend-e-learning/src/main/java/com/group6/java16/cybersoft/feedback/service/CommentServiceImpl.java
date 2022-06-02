package com.group6.java16.cybersoft.feedback.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.util.UserPrincipal;
import com.group6.java16.cybersoft.course.model.ELLesson;
import com.group6.java16.cybersoft.course.repository.ELLessonRepository;
import com.group6.java16.cybersoft.feedback.dto.CommentCreateDTO;
import com.group6.java16.cybersoft.feedback.dto.CommentResponseDTO;
import com.group6.java16.cybersoft.feedback.mapper.CommentMapper;
import com.group6.java16.cybersoft.feedback.model.ELComment;
import com.group6.java16.cybersoft.feedback.model.ELStatusComment;
import com.group6.java16.cybersoft.feedback.model.EnumStatusComment;
import com.group6.java16.cybersoft.feedback.repository.CommentRepository;
import com.group6.java16.cybersoft.feedback.repository.StatusCommentRepository;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;

@Service
@PropertySources({ @PropertySource("classpath:/validation/message.properties") })
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository repository;

	@Autowired
	private ELLessonRepository lessonRepository;

	@Autowired
	private ELUserRepository userRepository;

	@Autowired
	private StatusCommentRepository statusCommentRepository;

	@Value("${comment.not-found}")
	private String errorsCommentNotFound;

	@Value("${entity.id.invalid}")
	private String errorsIdInvalid;

	@Value("${lesson.not-found}")
	private String errorLessonNotFound;

	@Value("${user.not-found}")
	private String errorUserNotFound;

	@Value("${can-not-comment}")
	private String errorsCanNotComment;

	@Override
	public List<CommentResponseDTO> search(String idLesson) {
		ELLesson lesson = lessonRepository.findById(UUID.fromString(idLesson))
				.orElseThrow(() -> new BusinessException(errorLessonNotFound));
		String userCurrent = UserPrincipal.getUsernameCurrent();
		Page<ELComment> response = null;

		Pageable page = PageRequest.of(0, 100, Sort.by("createdAt").descending());

		UUID idCourse = lesson.getCourse().getId();

		if (null == userCurrent) {
			response = repository.findByIdLesson(UUID.fromString(idLesson), idCourse, page);
		}

		if (null != userCurrent && !userCurrent.equals(lesson.getCourse().getCreatedBy())) {
			response = repository.findByIdLessonAndUserCurrent(UUID.fromString(idLesson), idCourse, userCurrent, page);
		}

		if (null != userCurrent && userCurrent.equals(lesson.getCourse().getCreatedBy())) {
			response = repository.findByIdLesson(UUID.fromString(idLesson), page);
		}

		return response.stream().map(CommentMapper.INSTANCE::toResponseDTO).collect(Collectors.toList());
	}

	@Override
	public CommentResponseDTO create(CommentCreateDTO rq) {
		ELLesson lesson = lessonRepository.findById(UUID.fromString(rq.getLessonId()))
				.orElseThrow(() -> new BusinessException(errorLessonNotFound));
		ELUser user = userRepository.findByUsername(UserPrincipal.getUsernameCurrent()).get();

		Optional<ELStatusComment> statusCommentOpt = statusCommentRepository
				.findByIdCourseAndIdUser(lesson.getCourse().getId(), user.getId());

		// if status not exists then create status private
		if (statusCommentOpt.isEmpty()) {
			statusCommentRepository.save(
					ELStatusComment.builder()
					.id(UUID.randomUUID())
					.user(user)
					.course(lesson.getCourse())
					.status(EnumStatusComment.PRIVATE)
					.build());
		}

		if (statusCommentOpt.isPresent() && statusCommentOpt.get().getStatus().equals(EnumStatusComment.BLOCKED)) {
			throw new BusinessException(errorsCanNotComment);
		}

		ELComment comment = ELComment.builder()
				.id(UUID.randomUUID())
				.content(rq.getContent())
				.lesson(lesson)
				.user(user)
				.build();

		return CommentMapper.INSTANCE.toResponseDTO(repository.save(comment));

	}

	@Override
	public void delete(String commentId) {
		ELComment comment = getById(commentId);

		String user = UserPrincipal.getUsernameCurrent();

		if (user.equals(comment.getCreatedBy()) || user.equals(comment.getLesson().getCreatedBy())) {
			repository.delete(comment);
		}
	}

	private ELComment getById(String id) {
		return repository.findById(UUID.fromString(id))
				.orElseThrow(() -> new BusinessException(errorsCommentNotFound));
	}

}
