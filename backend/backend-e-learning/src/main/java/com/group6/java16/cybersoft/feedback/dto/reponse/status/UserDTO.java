package com.group6.java16.cybersoft.feedback.dto.reponse.status;

import java.util.Objects;
import java.util.UUID;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    private String displayName;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		return Objects.equals(displayName, other.displayName) && Objects.equals(id, other.id);
	}
    
    
}
