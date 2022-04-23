package com.group6.java16.cybersoft.user.service;

import com.group6.java16.cybersoft.user.dto.UserCreateDTO;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;

public interface UserManagementService {

    UserResponseDTO createUser(UserCreateDTO user);

    void deleteUser(String id);
    
}
