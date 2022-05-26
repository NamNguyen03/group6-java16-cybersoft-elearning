package com.group6.java16.cybersoft.feedback.dto.reponse.status;

import java.util.UUID;
import com.group6.java16.cybersoft.feedback.model.EnumStatusComment;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class StatusCommentResponseDTO {

    private UUID id;
    
    private EnumStatusComment status = EnumStatusComment.PRIVATE;

    private UserDTO user;

    private CourseDTO course;
}
