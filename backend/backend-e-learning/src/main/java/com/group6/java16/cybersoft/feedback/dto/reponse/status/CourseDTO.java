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
  
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CourseDTO other = (CourseDTO) obj;
        if (courseName == null) {
            if (other.courseName != null)
                return false;
        } else if (!courseName.equals(other.courseName))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (lessons == null) {
            if (other.lessons != null)
                return false;
        } else if (!lessons.equals(other.lessons))
            return false;
        return true;
    }

    
}
