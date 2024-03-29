package com.group6.java16.cybersoft.backedelearning.user.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.security.core.userdetails.User;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.exception.UnauthorizedException;
import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.common.model.notification.UserCreateModel;
import com.group6.java16.cybersoft.common.service.notification.EmailSender;
import com.group6.java16.cybersoft.common.service.storage.MyFirebaseService;
import com.group6.java16.cybersoft.role.dto.GroupResponseDTO;
import com.group6.java16.cybersoft.role.model.ELGroup;
import com.group6.java16.cybersoft.role.repository.ELGroupRepository;
import com.group6.java16.cybersoft.user.dto.UpdateMyProfileDTO;
import com.group6.java16.cybersoft.user.dto.UpdateUserDTO;
import com.group6.java16.cybersoft.user.dto.UserCreateDTO;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;
import com.group6.java16.cybersoft.user.mapper.UserMapper;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.model.UserStatus;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;
import com.group6.java16.cybersoft.user.service.UserInformationService;
import com.group6.java16.cybersoft.user.service.UserInformationServiceImpl;
import com.group6.java16.cybersoft.user.service.UserManagementService;
import com.group6.java16.cybersoft.user.service.UserManagementServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserServiceIntegrationTest {
    @Mock
    private ELUserRepository userRepository;

    @Mock
    private ELGroupRepository groupRepository;

    @Mock
    private UserMapper mapper;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserManagementService serviceInfo = new UserManagementServiceImpl();
    
    @InjectMocks
    private UserInformationService service = new UserInformationServiceImpl();


    @Mock
    private EmailSender<UserCreateModel> serviceSendEmailCreateUserSuccess;

    @Mock
    private MyFirebaseService firebaseFileService;

    @Test
    public void whenUpdateMyProfileSuccess_theReturnUserResponse() {
        UpdateMyProfileDTO rq = new UpdateMyProfileDTO("displayName", "FirstName", "lastName", "hobbies", "facebook", "gender", "phone2");

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("nam");

        UUID id = UUID.randomUUID();

        ELUser u = ELUser.builder().username("nam").id(id).groups(null).build();

        ELUser user = ELUser.builder()
                .id(id)
                .username("nam")
                .displayName("displayName")
                .facebook("facebook")
                .gender("gender")
                .groups(null)
                .hobbies("hobbies")
                .lastName("lastName")
                .phone("phone")
                .build();

        when(userRepository.findByUsername("nam")).thenReturn(Optional.of(u));

        when(userRepository.save(any())).thenReturn(user);

        UserResponseDTO expected = new UserResponseDTO();
        expected.setId(id);
        expected.setUsername("nam");
        expected.setDisplayName("displayName");
        expected.setFacebook("facebook");
        expected.setGender("gender");
        expected.setGroups(null);
        expected.setHobbies("hobbies");
        expected.setLastName("lastName");
        expected.setPhone("phone");

        UserResponseDTO actual = serviceInfo.updateMyProfile(rq);

        assertEquals(expected, actual);

        UpdateMyProfileDTO rq2 = new UpdateMyProfileDTO("displayName", "FirstName", "lastName", "hobbies", "facebook", "gender","");
        UserResponseDTO actual2 = serviceInfo.updateMyProfile(rq2);
        expected.setPhone("phone");
        assertEquals(expected, actual2);
        
    }

    @Test
    public void whenUpdateUserFails_thenThrowsBusinessException() throws Exception {
        UUID id = UUID.randomUUID();
        UpdateUserDTO user = new UpdateUserDTO();
        user.setEmail("nam@gmail.com");

        ELUser u = ELUser.builder()
                .id(id)
                .email("s@gmail.com")
                .username("nam")
                .displayName("Nam")
                .firstName("Nguyen")
                .lastName("Nam")
                .hobbies("swimming")
                .facebook("facebook.com")
                .phone("11111222222")
                .status(UserStatus.ACTIVE)
                .build();
        when(userRepository.findById(UUID.fromString(id.toString()))).thenReturn(Optional.of(u));

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(BusinessException.class, () -> serviceInfo.update(id.toString(), user));

    }

    @Test
    public void whenUpdateUserSuccessfully_thenReturnUserResponse() {
        UUID id = UUID.randomUUID();
        ELUser user = ELUser.builder()
                .id(id)
                .email("s@gmail.com")
                .username("nam")
                .displayName("Nam")
                .firstName("Nguyen")
                .lastName("Nam")
                .hobbies("swimming")
                .facebook("facebook.com")
                .phone("11111222222")
                .groups(null)
                .status(UserStatus.ACTIVE)
                .build();
        when(userRepository.findById(UUID.fromString(id.toString()))).thenReturn(Optional.of(user));

        UpdateUserDTO rq = new UpdateUserDTO("nam@gmail.com", "IT", "dev", UserStatus.ACTIVE);
        user.setEmail("nam@gmail.com");
        user.setDepartment("IT");
        user.setMajor("dev");
        when(userRepository.save(any())).thenReturn(user);
        UserResponseDTO expected = new UserResponseDTO();
        expected.setId(id);
        expected.setEmail("nam@gmail.com");
        expected.setUsername("nam");
        expected.setDisplayName("Nam");
        expected.setFirstName("Nguyen");
        expected.setLastName("Nam");
        expected.setHobbies("swimming");
        expected.setFacebook("facebook.com");
        expected.setDepartment("IT");
        expected.setMajor("dev");
        expected.setPhone("11111222222");
        expected.setGroups(null);
        expected.setStatus(UserStatus.ACTIVE);
        UserResponseDTO actual = serviceInfo.update(id.toString(), rq);
        assertEquals(expected, actual);
    }

    @Test
    public void whenCreateUserSuccessfully_theReturnUserResponseDTO() {
        UserCreateDTO rq = new UserCreateDTO("username", "password", "displayName", "email@gmail.com",
                UserStatus.ACTIVE, "firstName", "lastName", "department", "major");

        UUID id = UUID.randomUUID();
        ELUser user = ELUser.builder()
                .id(id)
                .username("username")
                .password("password")
                .displayName("displayName")
                .email("email@gmail.com")
                .status(UserStatus.ACTIVE)
                .firstName("firstName")
                .lastName("lastName")
                .department("department")
                .major("major")
                .groups(null)
                .build();

        when(userRepository.save(any())).thenReturn(user);

        UserResponseDTO actual = serviceInfo.createUser(rq);
        UserResponseDTO expected = new UserResponseDTO();
        expected.setId(id);
        expected.setUsername("username");
        expected.setDisplayName("displayName");
        expected.setEmail("email@gmail.com");
        expected.setFirstName("firstName");
        expected.setLastName("lastName");
        expected.setStatus(UserStatus.ACTIVE);
        expected.setDepartment("department");
        expected.setMajor("major");
        expected.setGroups(null);

        assertEquals(expected, actual);
    }

    @Test
    public void whenUserExistedIsUsedToDeleteUser_thenDeleteSuccessfully(){

        UUID id = UUID.randomUUID();

        ELUser user =  ELUser.builder()
            .email("nam@gmail.com")
            .username("nam")
            .displayName("Nam")
            .firstName("Nguyen")
            .lastName("Nam")
            .hobbies("swimming")
            .facebook("facebook.com")
            .phone("11111222222")
            .groups(null)
            .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));


        assertDoesNotThrow( () ->serviceInfo.deleteUser(id.toString()));
    }

    @Test
    public void whenUpdateAvatar_thenUpdateSuccessfully(){
        String imageName = "filename";
        MockMultipartFile file = 
            new MockMultipartFile(
                imageName, 
                imageName + ".txt", 
                MediaType.TEXT_PLAIN_VALUE, 
                "Hello, World!".getBytes()
            );
        
        String url = "https://firebasestorage.googleapis.com/v0/b/e-learning-5efea.appspot.com/o/" +
        imageName + "?alt=media&token=" + imageName;
       
        when(firebaseFileService.saveFile(file)).thenReturn(url);

        ELUser user = ELUser.builder()
            .username("username")
            .password("password")
            .displayName("displayName")
            .email("email@gmail.com")
            .status(UserStatus.ACTIVE)
            .firstName("firstName")
            .lastName("lastName")
            .department("department")
            .major("major")
            .groups(null)
            .build();        
        
        UserResponseDTO expected = new UserResponseDTO();
            expected.setAvatar(url);
            expected.setUsername("username");
            expected.setDisplayName("displayName");
            expected.setEmail("email@gmail.com");
            expected.setFirstName("firstName");
            expected.setLastName("lastName");
            expected.setStatus(UserStatus.ACTIVE);
            expected.setDepartment("department");
            expected.setMajor("major");
            expected.setGroups(null);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("nam");

        when(userRepository.findByUsername("nam")).thenReturn(Optional.of(user));
        user.setAvatar(url);
        when(userRepository.save(user)).thenReturn(user);

        UserResponseDTO actual =  serviceInfo.updateMyAvatar(file);

        assertEquals(expected, actual);
    }

    @Test
    public void whenGroupAndUserExistsIsUsedAddGroupIntoUser_thenReturnUserDTO(){
        UUID idUser = UUID.randomUUID();
        UUID idGroup = UUID.randomUUID();
        ELUser user = ELUser.builder().id(idUser).build();
        ELGroup group = ELGroup.builder().id(idGroup).build();
        group.setRoles(null);
        when(userRepository.findById(idUser)).thenReturn(Optional.of(user));
        when(groupRepository.findById(idGroup)).thenReturn(Optional.of(group));
        
        ELUser userSave = ELUser.builder().id(idUser).build();
        userSave.addGroup(group);
        when(userRepository.save(any())).thenReturn(userSave);
        UserResponseDTO expected = new UserResponseDTO();
        GroupResponseDTO groupResponse = new GroupResponseDTO();
        groupResponse.setRoles(null);
        Set<GroupResponseDTO> groups = new LinkedHashSet<>();
        groups.add(groupResponse);
        groupResponse.setId(idGroup);
        expected.setId(idUser);
        expected.setGroups(groups);

        UserResponseDTO actual =  serviceInfo.addGroup(idUser.toString(), idGroup.toString());

        assertEquals(expected, actual);
    }

    @Test
    public void whenGroupAndUserExistsIsUsedRemoveGroupIntoUser_thenReturnUserDTO(){
        UUID idUser = UUID.randomUUID();
        UUID idGroup = UUID.randomUUID();
        ELUser user = ELUser.builder().id(idUser).build();
        ELGroup group = ELGroup.builder().id(idGroup).build();
        group.setRoles(null);
        user.addGroup(group);
        when(userRepository.findById(idUser)).thenReturn(Optional.of(user));
        when(groupRepository.findById(idGroup)).thenReturn(Optional.of(group));

        ELUser userSave = ELUser.builder().id(idUser).build();
        userSave.setGroups(null);

        when(userRepository.save(any())).thenReturn(userSave);

        UserResponseDTO expected = new UserResponseDTO();
        expected.setId(idUser);
        expected.setGroups(null);

        UserResponseDTO actual =  serviceInfo.deleteGroup(idUser.toString(), idGroup.toString());

        assertEquals(expected, actual);
    }
    
    @Test
    public void whenExistsUserIsUsedToSortFieldNotExists_thenReturnPageResponseUser(){
        PageRequestModel rq =  new PageRequestModel(1,10, "anyField", true, null, null);

       
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());

        Page<ELUser> page = new PageImpl<ELUser>(new ArrayList<ELUser>(), pageable, 10l);

        when(userRepository.findAll(pageable)).thenReturn(page);

        PageResponseModel<UserResponseDTO> expected = new PageResponseModel<UserResponseDTO>(1,1, new ArrayList<UserResponseDTO>());

        PageResponseModel<UserResponseDTO> rp = service.search(rq);
    
        //search all
        assertEquals(expected.getItems(), rp.getItems());
        assertEquals(expected.getTotalPage(), rp.getTotalPage());
        assertEquals(expected.getPageCurrent(), rp.getPageCurrent());
    }

    @Test
    public void whenExistsUserIsUsedToSearchAll_thenReturnPageResponseUser(){
        PageRequestModel rq = new PageRequestModel(1,10, null, true, null, null);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());

        Page<ELUser> page = new PageImpl<ELUser>(new ArrayList<ELUser>(), pageable, 10l);

        when(userRepository.findAll(pageable)).thenReturn(page);

        PageResponseModel<UserResponseDTO> expected = new PageResponseModel<UserResponseDTO>(1,1, new ArrayList<UserResponseDTO>());

        PageResponseModel<UserResponseDTO> rp = service.search(rq);
    
        //search all
        assertEquals(expected.getItems(), rp.getItems());
        assertEquals(expected.getTotalPage(), rp.getTotalPage());
        assertEquals(expected.getPageCurrent(), rp.getPageCurrent());

    }

    
    @Test
    public void whenExistsUserIsUsedToSearchByUsername_thenReturnPageResponseUser(){
        PageRequestModel rq =  new PageRequestModel(1,10, null, true, "username", "value");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());

        Page<ELUser> page = new PageImpl<ELUser>(new ArrayList<ELUser>(), pageable, 10l);

        when(userRepository.searchByUsername("value",pageable)).thenReturn(page);

        PageResponseModel<UserResponseDTO> expected = new PageResponseModel<UserResponseDTO>(1,1, new ArrayList<UserResponseDTO>());

        PageResponseModel<UserResponseDTO> rp = service.search(rq);
    
        //search all
        assertEquals(expected.getItems(), rp.getItems());
        assertEquals(expected.getTotalPage(), rp.getTotalPage());
        assertEquals(expected.getPageCurrent(), rp.getPageCurrent());
    }

    @Test
    public void whenExistsUserIsUsedToSearchByDisplayName_thenReturnPageResponseUser(){
        PageRequestModel rq =  new PageRequestModel(1,10, null, true, "displayName", "value");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());

        Page<ELUser> page = new PageImpl<ELUser>(new ArrayList<ELUser>(), pageable, 10l);

        when(userRepository.searchByDisplayName("value",pageable)).thenReturn(page);

        PageResponseModel<UserResponseDTO> expected = new PageResponseModel<UserResponseDTO>(1,1, new ArrayList<UserResponseDTO>());

        PageResponseModel<UserResponseDTO> rp = service.search(rq);
    
        //search all
        assertEquals(expected.getItems(), rp.getItems());
        assertEquals(expected.getTotalPage(), rp.getTotalPage());
        assertEquals(expected.getPageCurrent(), rp.getPageCurrent());
    }

    @Test
    public void whenExistsUserIsUsedToSearchByEmail_thenReturnPageResponseUser(){
        PageRequestModel rq =  new PageRequestModel(1,10, null, true, "email", "value");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());

        Page<ELUser> page = new PageImpl<ELUser>(new ArrayList<ELUser>(), pageable, 10l);

        when(userRepository.searchByEmail("value",pageable)).thenReturn(page);

        PageResponseModel<UserResponseDTO> expected = new PageResponseModel<UserResponseDTO>(1,1, new ArrayList<UserResponseDTO>());

        PageResponseModel<UserResponseDTO> rp = service.search(rq);
    
        //search all
        assertEquals(expected.getItems(), rp.getItems());
        assertEquals(expected.getTotalPage(), rp.getTotalPage());
        assertEquals(expected.getPageCurrent(), rp.getPageCurrent());
    }

    @Test
    public void whenExistsUserIsUsedToSearchByFirstName_thenReturnPageResponseUser(){
        PageRequestModel rq =  new PageRequestModel(1,10, null, true, "firstName", "value");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());

        Page<ELUser> page = new PageImpl<ELUser>(new ArrayList<ELUser>(), pageable, 10l);

        when(userRepository.searchByFirstName("value",pageable)).thenReturn(page);

        PageResponseModel<UserResponseDTO> expected = new PageResponseModel<UserResponseDTO>(1,1, new ArrayList<UserResponseDTO>());

        PageResponseModel<UserResponseDTO> rp = service.search(rq);
    
        //search all
        assertEquals(expected.getItems(), rp.getItems());
        assertEquals(expected.getTotalPage(), rp.getTotalPage());
        assertEquals(expected.getPageCurrent(), rp.getPageCurrent());
    }

    @Test
    public void whenExistsUserIsUsedToSearchByLastNameAndSort_thenReturnPageResponseUser(){
        PageRequestModel rq =  new PageRequestModel(1,10, "major", false, "lastName", "value");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("major").descending());

        Page<ELUser> page = new PageImpl<ELUser>(new ArrayList<ELUser>(), pageable, 10l);

        when(userRepository.searchByLastName("value",pageable)).thenReturn(page);

        PageResponseModel<UserResponseDTO> expected = new PageResponseModel<UserResponseDTO>(1,1, new ArrayList<UserResponseDTO>());

        PageResponseModel<UserResponseDTO> rp = service.search(rq);
    
        //search all
        assertEquals(expected.getItems(), rp.getItems());
        assertEquals(expected.getTotalPage(), rp.getTotalPage());
        assertEquals(expected.getPageCurrent(), rp.getPageCurrent());
    }

    @Test
    public void whenExistsUserIsUsedToSearchAllAndSort_thenReturnPageResponseUser(){
        PageRequestModel rq = new PageRequestModel(1,10, "firstName", true, null, null);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("firstName").ascending());

        Page<ELUser> page = new PageImpl<ELUser>(new ArrayList<ELUser>(), pageable, 10l);

        when(userRepository.findAll(pageable)).thenReturn(page);

        PageResponseModel<UserResponseDTO> expected = new PageResponseModel<UserResponseDTO>(1,1, new ArrayList<UserResponseDTO>());

        PageResponseModel<UserResponseDTO> rp = service.search(rq);
        assertEquals(expected.getItems(), rp.getItems());
        assertEquals(expected.getTotalPage(), rp.getTotalPage());
        assertEquals(expected.getPageCurrent(), rp.getPageCurrent());
    }

    @Test
    public void whenGetMyProfile_theReturnUserResponse(){
        
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("nam");

        ELUser user =  ELUser.builder()
            .email("nam@gmail.com")
            .username("nam")
            .displayName("Nam")
            .firstName("Nguyen")
            .lastName("Nam")
            .hobbies("swimming")
            .facebook("facebook.com")
            .phone("11111222222")
            .groups(null)
            .build();

        when(userRepository.findByUsername("nam")).thenReturn(Optional.of(user));

    
        UserResponseDTO expected = new UserResponseDTO();
        expected.setEmail("nam@gmail.com");	
        expected.setUsername("nam");
        expected.setDisplayName("Nam");
        expected.setLastName("Nam");
        expected.setHobbies("swimming");
        expected.setFacebook("facebook.com");
        expected.setPhone("11111222222");
        expected.setFirstName("Nguyen");
        expected.setGroups(null);

        UserResponseDTO actual =  service.getMyProfile();

        assertEquals(expected,actual);
    }
    
    @Test
    public void whenGetMyProfileWithUserCurrentIsUserDetails_theReturnUserResponse(){
        
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(new User("nam","123456", new ArrayList<>()));

        ELUser user =  ELUser.builder()
            .email("nam@gmail.com")
            .username("nam")
            .displayName("Nam")
            .firstName("Nguyen")
            .lastName("Nam")
            .hobbies("swimming")
            .facebook("facebook.com")
            .phone("11111222222")
            .groups(null)
            .build();

        when(userRepository.findByUsername("nam")).thenReturn(Optional.of(user));

    
        UserResponseDTO expected = new UserResponseDTO();
        expected.setEmail("nam@gmail.com");	
        expected.setUsername("nam");
        expected.setDisplayName("Nam");
        expected.setLastName("Nam");
        expected.setHobbies("swimming");
        expected.setFacebook("facebook.com");
        expected.setPhone("11111222222");
        expected.setFirstName("Nguyen");
        expected.setGroups(null);

        UserResponseDTO actual =  service.getMyProfile();

        assertEquals(expected,actual);
    }
    
    @Test
    public void whenUserCurrentIsNullGetMyProfile_theReturnThrowBusinessException(){
        
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(null);
        assertThrows(UnauthorizedException.class, ()-> service.getMyProfile());
    }

    @Test
    public void whenIdNotExistsIsUsedToGetProfile_thenThrowBusinessException() throws Exception {
        UUID id = UUID.randomUUID();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, ()-> service.getProfile(id.toString()));
    }

    @Test
    public void whenIdExistsIsUsedToGetProfile_thenReturnUser() throws Exception {
        UUID id = UUID.randomUUID();
        ELUser user =  ELUser.builder()
            .email("nam@gmail.com")
            .username("nam")
            .displayName("Nam")
            .firstName("Nguyen")
            .lastName("Nam")
            .hobbies("swimming")
            .facebook("facebook.com")
            .phone("11111222222")
            .groups(null)
            .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserResponseDTO expected = new UserResponseDTO();
        expected.setEmail("nam@gmail.com");	
        expected.setUsername("nam");
        expected.setDisplayName("Nam");
        expected.setLastName("Nam");
        expected.setHobbies("swimming");
        expected.setFacebook("facebook.com");
        expected.setPhone("11111222222");
        expected.setFirstName("Nguyen");
        expected.setGroups(null);

        UserResponseDTO actual =  service.getProfile(id.toString());

        assertEquals(expected,actual);
    }
}