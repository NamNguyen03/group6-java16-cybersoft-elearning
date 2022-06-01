package com.group6.java16.cybersoft.user.dto.client;

import java.util.List;
import java.util.UUID;

import com.group6.java16.cybersoft.course.dto.client.CardCourseReponseClientDTO;
import com.group6.java16.cybersoft.course.dto.client.CourseDetailsReponseClientDTO;
import com.group6.java16.cybersoft.course.model.ELCourse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class InstructorCourseClientDTO {

	private String displayName;
	
	private String username;

	private String email;
	
	private String createBy;
	
	private String lastModifiedBy;
	
	private String lastModifiedAt;

	private String avatar;

	private String department;

	private String hobbies;

	private String facebook;

	private String gender;	

	private String phone;
	
	private List<ELCourse> courses;
}
