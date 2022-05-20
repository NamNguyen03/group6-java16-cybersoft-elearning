package com.group6.java16.cybersoft.feedback.service;

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.feedback.dto.StatusCommentDTO;
import com.group6.java16.cybersoft.feedback.dto.StatusCommentResponseDTO;

public interface StatusCommentService {

    PageResponseModel<StatusCommentResponseDTO> search(PageRequestModel pageRequestModel);

    StatusCommentResponseDTO updateStatusComment(StatusCommentDTO rq);

    StatusCommentResponseDTO createStatusComment(StatusCommentDTO rq);
    
}
