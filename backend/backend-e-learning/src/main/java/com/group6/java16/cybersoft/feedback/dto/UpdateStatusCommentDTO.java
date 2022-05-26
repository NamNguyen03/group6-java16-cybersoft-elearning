package com.group6.java16.cybersoft.feedback.dto;

import com.group6.java16.cybersoft.feedback.model.EnumStatusComment;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateStatusCommentDTO {
    EnumStatusComment status;
}
