package com.group6.java16.cybersoft.feedback.service;

import java.util.UUID;
import java.util.stream.Collectors;

import com.google.api.client.util.Value;
import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.common.util.UserPrincipal;
import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.course.repository.ELCourseRepository;
import com.group6.java16.cybersoft.feedback.dto.StatusCommentDTO;
import com.group6.java16.cybersoft.feedback.dto.StatusCommentResponseDTO;
import com.group6.java16.cybersoft.feedback.mapper.StatusCommentMapper;
import com.group6.java16.cybersoft.feedback.model.ELStatusComment;
import com.group6.java16.cybersoft.feedback.repository.StatusCommentRepository;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StatusCommentServiceImpl implements StatusCommentService{

    @Autowired
    private StatusCommentRepository statusCommentRepository;

	@Autowired
	private ELUserRepository userRepository;

	@Autowired
	private ELCourseRepository courseRepository;

	@Value("${user.not-found}")
    private String errorsUserNotFound;

	@Value("${course.not-found}")
    private String errorsCourseNotFound;

	@Value("${status-comment.existed}")
	private String errorsStatusCommentExisted;

	@Value("${status-comment.not-found}")
	private String errorsStatusCommentNotFound;

    @Override
    public PageResponseModel<StatusCommentResponseDTO> search(PageRequestModel pageRequestModel) {
        String usernameCurrent = UserPrincipal.getUsernameCurrent();
        int page = pageRequestModel.getPageCurrent() - 1;
		int size = pageRequestModel.getItemPerPage();
		boolean isAscending = pageRequestModel.isIncrementSort();
		String fieldNameSort = pageRequestModel.getFieldNameSort();
		String fieldNameSearch = pageRequestModel.getFieldNameSearch();
		String valueSearch = pageRequestModel.getValueSearch();
		Pageable pageable = PageRequest.of(page, size);
		Page<ELStatusComment> response = null;

		if (null != fieldNameSort && fieldNameSort.matches("status")) {
			pageable = PageRequest.of(page, size,
					isAscending ? Sort.by(fieldNameSort).ascending() : Sort.by(fieldNameSort).descending());

		} else {
			pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
		}

		if ("status".equals(fieldNameSearch)) {
			response = statusCommentRepository.searchByStatus(usernameCurrent, valueSearch, pageable);
		}

		if (response == null) {
			response = statusCommentRepository.searchAll(usernameCurrent, pageable);
		}


		return new PageResponseModel<>(response.getNumber() + 1, response.getTotalPages(),
				response.getContent().stream().map(StatusCommentMapper.INSTANCE::toResponseDTO)
						.collect(Collectors.toList()));
    }

	@Override
	public StatusCommentResponseDTO updateStatusComment(StatusCommentDTO rq) {

		ELStatusComment status = statusCommentRepository.findByUserAndCourse(rq.getIdUser(),rq.getIdCourse()).orElseThrow(
			() -> new BusinessException(errorsStatusCommentNotFound)
		);

		return StatusCommentMapper.INSTANCE.toResponseDTO(statusCommentRepository.save(status));
	}

	@Override
	public StatusCommentResponseDTO createStatusComment(StatusCommentDTO rq) {
		
		if(statusCommentRepository.existsByUserAndCourse(UUID.fromString(rq.getIdUser()),UUID.fromString(rq.getIdCourse()))){
			throw new BusinessException(errorsStatusCommentExisted);
		}

		ELUser user = userRepository.findById(UUID.fromString(rq.getIdUser())).orElseThrow(
			() -> new BusinessException(errorsUserNotFound)
		);

		ELCourse course = courseRepository.findById(UUID.fromString(rq.getIdCourse())).orElseThrow(
			() -> new BusinessException(errorsCourseNotFound)
		);

		ELStatusComment status = ELStatusComment.builder()
			.id(UUID.randomUUID())
			.user(user)
			.course(course)
			.status(rq.getStatus())
			.build();

		return StatusCommentMapper.INSTANCE.toResponseDTO(statusCommentRepository.save(status));
	}
    
}
