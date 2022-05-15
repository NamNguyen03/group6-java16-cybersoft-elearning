package com.group6.java16.cybersoft.security.service;

import com.group6.java16.cybersoft.security.dto.LoginRequestDTO;
import com.group6.java16.cybersoft.security.dto.RegisterDTO;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;

public interface AuthService {

    String login(LoginRequestDTO loginDTO);

    UserResponseDTO register(RegisterDTO rq);
    
}
