package com.group6.java16.cybersoft.user.service;

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;

public interface UserInformationService {

    PageResponseModel<UserResponseDTO> search(PageRequestModel pageRequestModel);

    UserResponseDTO getMyProfile();
}
