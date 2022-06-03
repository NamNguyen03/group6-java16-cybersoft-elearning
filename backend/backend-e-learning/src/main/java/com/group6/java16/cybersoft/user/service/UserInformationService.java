package com.group6.java16.cybersoft.user.service;

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;
import com.group6.java16.cybersoft.user.dto.client.InstructorCourseClientDTO;

public interface UserInformationService {

    PageResponseModel<UserResponseDTO> search(PageRequestModel pageRequestModel);

    UserResponseDTO getMyProfile();

    UserResponseDTO getProfile(String id);

    InstructorCourseClientDTO getProfileFindById(String id);
}
