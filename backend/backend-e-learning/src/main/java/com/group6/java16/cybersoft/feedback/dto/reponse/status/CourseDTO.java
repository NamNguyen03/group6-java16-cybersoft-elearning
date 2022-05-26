package com.group6.java16.cybersoft.feedback.dto.reponse.status;
import java.util.Set;
import java.util.UUID;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CourseDTO {
    private UUID id;
    private String courseName;
    private Set<LessonDTO> lessons;
}
