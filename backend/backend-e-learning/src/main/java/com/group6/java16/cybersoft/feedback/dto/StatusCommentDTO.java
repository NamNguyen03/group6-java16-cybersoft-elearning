package com.group6.java16.cybersoft.feedback.dto;

import com.group6.java16.cybersoft.feedback.model.EnumStatusComment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusCommentDTO {
    private EnumStatusComment status = EnumStatusComment.PRIVATE;
    private String idUser;
    private String idCourse;
}
