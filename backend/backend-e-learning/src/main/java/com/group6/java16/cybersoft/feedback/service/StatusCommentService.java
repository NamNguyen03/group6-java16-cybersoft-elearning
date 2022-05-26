package com.group6.java16.cybersoft.feedback.service;

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.feedback.dto.StatusCommentDTO;
import com.group6.java16.cybersoft.feedback.dto.reponse.status.StatusCommentResponseDTO;
import com.group6.java16.cybersoft.feedback.model.EnumStatusComment;

public interface StatusCommentService {

    PageResponseModel<StatusCommentResponseDTO> search(PageRequestModel pageRequestModel);

    StatusCommentResponseDTO updateStatusComment(String id, EnumStatusComment rq);

    StatusCommentResponseDTO createStatusComment(StatusCommentDTO rq);

    StatusCommentResponseDTO getById(String id);
    
}
