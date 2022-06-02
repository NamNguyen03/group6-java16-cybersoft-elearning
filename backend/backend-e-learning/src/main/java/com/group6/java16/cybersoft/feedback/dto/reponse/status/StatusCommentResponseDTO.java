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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StatusCommentResponseDTO other = (StatusCommentResponseDTO) obj;
        if (course == null) {
            if (other.course != null)
                return false;
        } else if (!course.equals(other.course))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (status != other.status)
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

    
}
