package com.group6.java16.cybersoft.backedelearning.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.Optional;
import java.util.UUID;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.model.notification.UserCreateModel;
import com.group6.java16.cybersoft.common.service.notification.EmailSender;
import com.group6.java16.cybersoft.user.dto.UpdateMyProfileDTO;
import com.group6.java16.cybersoft.user.dto.UpdateUserDTO;
import com.group6.java16.cybersoft.user.dto.UserCreateDTO;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;
import com.group6.java16.cybersoft.user.mapper.UserMapper;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.model.UserStatus;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;
import com.group6.java16.cybersoft.user.service.UserManagementService;
import com.group6.java16.cybersoft.user.service.UserManagementServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserManagerServiceIntegrationTest {
    @Mock
    private ELUserRepository userRepository;

    @Mock
    private UserMapper mapper;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserManagementService service = new UserManagementServiceImpl();

    @Mock
    private EmailSender<UserCreateModel> serviceSendEmailCreateUserSuccess;

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

        UserResponseDTO actual = service.updateMyProfile(rq);

        assertEquals(expected, actual);

        UpdateMyProfileDTO rq2 = new UpdateMyProfileDTO("displayName", "FirstName", "lastName", "hobbies", "facebook", "gender","");
        UserResponseDTO actual2 = service.updateMyProfile(rq2);
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

        assertThrows(BusinessException.class, () -> service.update(id.toString(), user));

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
        UserResponseDTO actual = service.update(id.toString(), rq);
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

        UserResponseDTO actual = service.createUser(rq);
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

}
