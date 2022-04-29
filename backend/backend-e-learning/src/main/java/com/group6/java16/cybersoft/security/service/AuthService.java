package com.group6.java16.cybersoft.security.service;

import com.group6.java16.cybersoft.security.dto.LoginRequestDTO;

public interface AuthService {

    String login(LoginRequestDTO loginDTO);
    
}
