package com.group6.java16.cybersoft.user.service;

import java.util.Optional;
import java.util.UUID;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.model.notification.UserCreateModel;
import com.group6.java16.cybersoft.common.service.notification.EmailSender;
import com.group6.java16.cybersoft.common.service.storage.MyFirebaseService;
import com.group6.java16.cybersoft.common.util.ServiceHelper;
import com.group6.java16.cybersoft.common.util.UserPrincipal;
import com.group6.java16.cybersoft.role.model.ELGroup;
import com.group6.java16.cybersoft.role.repository.ELGroupRepository;
import com.group6.java16.cybersoft.user.dto.UpdateMyProfileDTO;
import com.group6.java16.cybersoft.user.dto.UpdateUserDTO;
import com.group6.java16.cybersoft.user.dto.UserCreateDTO;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;
import com.group6.java16.cybersoft.user.mapper.UserMapper;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private ELGroupRepository groupRepository;

    @Autowired
    private PasswordEncoder encoder;
    
    


    @Autowired
    @Qualifier("emailSenderCreateSuccessfully")
    private EmailSender<UserCreateModel> serviceSendEmailCreateUserSuccess;

    @Value("${user.not-found}")
    private String errorsUserNotFound;

    @Value("${user.email.existed}")
    private String errorsEmailExisted;
    
    @Value("${group.id.not-found}")
    private String errorsGroupIdNotFound;

    @Value("${entity.id.invalid}")
    private String errorsIdInvalid;


    @Autowired
    private MyFirebaseService firebaseFileService;

    @Override
    public UserResponseDTO createUser(UserCreateDTO user) {
        String password = user.getPassword();
        user.setPassword(encoder.encode(password));
        ELUser rp = userRepository.save(UserMapper.INSTANCE.toModel(user));
        serviceSendEmailCreateUserSuccess.send("User Service", user.getEmail(), "Create Account Success", new UserCreateModel(user.getUsername(), password));
        return UserMapper.INSTANCE.toUserResponseDTO(rp);
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

        ELUser newUser = userRepository.save(user);

        return UserMapper.INSTANCE.toUserResponseDTO(newUser);
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

        ELUser newUser = userRepository.save(u);

        return UserMapper.INSTANCE.toUserResponseDTO(newUser);
    }

	
	@Override
	public UserResponseDTO addGroup(String userId, String groupId) {
		ELUser user = getById(userId);
		ELGroup group = getGroupById(groupId);
		
		user.addGroup(group);
		
        return UserMapper.INSTANCE.toUserResponseDTO(userRepository.save(user));

	}

	@Override
	public UserResponseDTO getProfile(String id) {
		ELUser user = getById(id);
		return UserMapper.INSTANCE.toUserResponseDTO(user);
	}

	@Override
	public UserResponseDTO deleteGroup(String userId, String groupId) {
		ELUser user = getById(userId);
		ELGroup group = getGroupById(groupId);
		
		user.removeGroup(group);
		return  UserMapper.INSTANCE.toUserResponseDTO(userRepository.save(user));
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
    
    private ELGroup getGroupById(String id) {
        UUID uuid;
        try{
            uuid = UUID.fromString(id);
        }catch(Exception e){
            throw new BusinessException(getMessageIdInvalid());
        }
        
        Optional<ELGroup> entityOpt = groupRepository.findById(uuid);
        
        if(entityOpt.isEmpty()){
            throw new BusinessException(errorsGroupIdNotFound);
        }
        return entityOpt.get();
    }

}
