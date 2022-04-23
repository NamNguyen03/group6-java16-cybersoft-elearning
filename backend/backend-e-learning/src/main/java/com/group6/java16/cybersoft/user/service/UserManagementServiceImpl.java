package com.group6.java16.cybersoft.user.service;

import com.group6.java16.cybersoft.common.util.ServiceHelper;
import com.group6.java16.cybersoft.user.dto.UserCreateDTO;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;
import com.group6.java16.cybersoft.user.mapper.UserMapper;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@PropertySources({ @PropertySource("classpath:/validation/message.properties") })
public class UserManagementServiceImpl implements UserManagementService{
    
    @Autowired
    private ELUserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ServiceHelper<ELUser> serviceUserHelper;    

    @Value("${user.not-found}")
    private String errorsUserNotFound;


    @Override
    public UserResponseDTO createUser(UserCreateDTO user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return UserMapper.INSTANCE.toUserResponseDTO(userRepository.save(UserMapper.INSTANCE.toModel(user)));
    }


    @Override
    public void deleteUser(String id) {
        ELUser user = serviceUserHelper.getEntityById(id, userRepository, errorsUserNotFound);
        userRepository.delete(user);
    }
}
