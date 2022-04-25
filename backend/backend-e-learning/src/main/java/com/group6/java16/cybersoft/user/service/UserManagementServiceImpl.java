package com.group6.java16.cybersoft.user.service;

import java.io.IOException;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.service.storage.MyFirebaseService;
import com.group6.java16.cybersoft.common.util.ServiceHelper;
import com.group6.java16.cybersoft.common.util.UserPrincipal;
import com.group6.java16.cybersoft.user.dto.UpdateMyProfileDTO;
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
import org.springframework.web.multipart.MultipartFile;

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

    @Value("${user.email.existed}")
    private String errorsEmailExisted;

    @Autowired
    private MyFirebaseService firebaseFileService;

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


    @Override
    public UserResponseDTO updateMyProfile(UpdateMyProfileDTO rq) {
        String username = UserPrincipal.getUsernameCurrent();
        ELUser user = userRepository.findByUsername(username).get();

        if(serviceUserHelper.isValidString(rq.getDisplayName())){
            user.setDisplayName(rq.getDisplayName());
        }

        if(serviceUserHelper.isValidString(rq.getEmail())){
            if(!rq.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(rq.getEmail())){
                throw new BusinessException(errorsEmailExisted);    
            }
            user.setEmail(rq.getEmail());
        }

        if(serviceUserHelper.isValidString(rq.getFirstName())){
            user.setFirstName(rq.getFirstName());
        }

        if(serviceUserHelper.isValidString(rq.getLastName())){
            user.setLastName(rq.getLastName());
        }

        if(serviceUserHelper.isValidString(rq.getHobbies())){
            user.setHobbies(rq.getHobbies());
        }

        if(serviceUserHelper.isValidString(rq.getFacebook())){
            user.setFacebook(rq.getFacebook());
        }

        if(serviceUserHelper.isValidString(rq.getGender())){
            user.setGender(rq.getGender());
        }

        if(serviceUserHelper.isValidString(rq.getPhone())){
            user.setPhone(rq.getPhone());
        }

        return UserMapper.INSTANCE.toUserResponseDTO(userRepository.save(user));
    }


    @Override
    public UserResponseDTO updateMyAvatar(MultipartFile file) {
        String avatar = firebaseFileService.saveFile(file);
        
        String username = UserPrincipal.getUsernameCurrent();
        ELUser user = userRepository.findByUsername(username).get();

        user.setAvatar(avatar);

        return UserMapper.INSTANCE.toUserResponseDTO(userRepository.save(user));
    }
}
