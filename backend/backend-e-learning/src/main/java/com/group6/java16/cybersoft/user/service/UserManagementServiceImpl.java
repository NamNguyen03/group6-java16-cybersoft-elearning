package com.group6.java16.cybersoft.user.service;

import com.group6.java16.cybersoft.user.dto.UserCreateDTO;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;
import com.group6.java16.cybersoft.user.mapper.UserMapper;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserManagementServiceImpl implements UserManagementService{
    
    @Autowired
    private ELUserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;


    @Override
    public UserResponseDTO createUser(UserCreateDTO user) {
        user.setPassword(encoder.encode(user.getPassword()));
        
//        // map dto to user
//        ELUser u = UserMapper.INSTANCE.toModel(user);
//        // save user return user
//        
//        ELUser usered = userRepository.save(u);
//        
//        // map user to dto
//        
//        UserResponseDTO rp = UserMapper.INSTANCE.toUserResponseDTO(usered);
//        
//        return rp;
        
        return UserMapper.INSTANCE.toUserResponseDTO(userRepository.save(UserMapper.INSTANCE.toModel(user)));
    }
}
