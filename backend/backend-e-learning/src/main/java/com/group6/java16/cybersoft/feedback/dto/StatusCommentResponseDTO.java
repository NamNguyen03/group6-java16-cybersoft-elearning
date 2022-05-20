package com.group6.java16.cybersoft.feedback.dto;

import com.group6.java16.cybersoft.course.dto.CourseResponseDTO;
import com.group6.java16.cybersoft.feedback.model.EnumStatusComment;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class StatusCommentResponseDTO {
    private EnumStatusComment status = EnumStatusComment.PRIVATE;

    private UserResponseDTO user;

    private CourseResponseDTO course;
}
