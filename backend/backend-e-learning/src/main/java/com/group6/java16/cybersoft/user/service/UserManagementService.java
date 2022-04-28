package com.group6.java16.cybersoft.user.service;

import com.group6.java16.cybersoft.user.dto.UpdateMyProfileDTO;
import com.group6.java16.cybersoft.user.dto.UpdateUserDTO;
import com.group6.java16.cybersoft.user.dto.UserCreateDTO;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;

import org.springframework.web.multipart.MultipartFile;

public interface UserManagementService {

    UserResponseDTO createUser(UserCreateDTO user);

    void deleteUser(String id);

    UserResponseDTO updateMyProfile(UpdateMyProfileDTO rq);

    UserResponseDTO updateMyAvatar(MultipartFile file);

    UserResponseDTO update(String id, UpdateUserDTO user);

    
}
