package com.group6.java16.cybersoft.user.service;

import java.util.UUID;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.service.storage.MyFirebaseService;
import com.group6.java16.cybersoft.common.util.ServiceHelper;
import com.group6.java16.cybersoft.common.util.UserPrincipal;
import com.group6.java16.cybersoft.user.dto.UpdateMyProfileDTO;
import com.group6.java16.cybersoft.user.dto.UpdateUserDTO;
import com.group6.java16.cybersoft.user.dto.UserCreateDTO;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;
import com.group6.java16.cybersoft.user.mapper.UserMapper;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@PropertySources({ @PropertySource("classpath:/validation/message.properties") })
public class UserManagementServiceImpl extends ServiceHelper<ELUser> implements UserManagementService {

    @Autowired
    private ELUserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Value("${user.not-found}")
    private String errorsUserNotFound;

    @Value("${user.email.existed}")
    private String errorsEmailExisted;

    @Value("${entity.id.invalid}")
    private String errorsIdInvalid;

    @Autowired
    private MyFirebaseService firebaseFileService;

    @Override
    public UserResponseDTO createUser(UserCreateDTO user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return UserMapper.INSTANCE.toUserResponseDTO(userRepository.save(UserMapper.INSTANCE.toModel(user)));
    }

    @Override
    public void deleteUser(String id) {
        ELUser user = getById(id);
        userRepository.delete(user);
    }

    @Override
    public UserResponseDTO updateMyProfile(UpdateMyProfileDTO rq) {
        String username = UserPrincipal.getUsernameCurrent();
        ELUser user = userRepository.findByUsername(username).get();
        
        if (isValidString(rq.getDisplayName())) {
            user.setDisplayName(rq.getDisplayName());
        }

        if (isValidString(rq.getFirstName())) {
            user.setFirstName(rq.getFirstName());
        }

        if (isValidString(rq.getLastName())) {
            user.setLastName(rq.getLastName());
        }

        if (isValidString(rq.getHobbies())) {
            user.setHobbies(rq.getHobbies());
        }

        if (isValidString(rq.getFacebook())) {
            user.setFacebook(rq.getFacebook());
        }

        if (isValidString(rq.getGender())) {
            user.setGender(rq.getGender());
        }

        if (isValidString(rq.getPhone())) {
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

    @Override
    public UserResponseDTO update(String id, UpdateUserDTO user) {
        ELUser u = getById(id);
        if (isValidString(user.getEmail())) {
            if (!u.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(user.getEmail())) {
                throw new BusinessException(errorsEmailExisted);
            }
            u.setEmail(user.getEmail());
        }

        if (isValidString(user.getDepartment())) {
            u.setDepartment(user.getDepartment());
        }

        if (isValidString(user.getMajor())) {
            u.setMajor(user.getMajor());
        }
        u.setStatus(user.getStatus());

        return UserMapper.INSTANCE.toUserResponseDTO(userRepository.save(u));
    }

    @Override
    protected String getMessageIdInvalid() {
        return errorsIdInvalid;
    }

    @Override
    protected JpaRepository<ELUser, UUID> getRepository() {
        return userRepository;
    }

    @Override
    protected String getErrorNotFound() {
        return errorsUserNotFound;
    }

}
