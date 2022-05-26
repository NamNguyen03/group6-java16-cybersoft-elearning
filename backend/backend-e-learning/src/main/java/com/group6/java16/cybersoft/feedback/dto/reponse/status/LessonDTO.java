package com.group6.java16.cybersoft.feedback.dto.reponse.status;

import java.util.Set;
import java.util.UUID;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class LessonDTO {
    private UUID id;
    private String name;
    private Set<CommentDTO> comments;
}
