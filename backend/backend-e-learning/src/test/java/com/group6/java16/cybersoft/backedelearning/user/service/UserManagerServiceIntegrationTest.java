package com.group6.java16.cybersoft.backedelearning.user.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.UUID;

import com.group6.java16.cybersoft.common.exception.BusinessException;
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

    @Test
    public void whenUpdateMyProfileExistsEmail_thenThrowBusinessException(){
        UpdateMyProfileDTO rq = UpdateMyProfileDTO.builder()
            .email("nam@gmail.com")
            .build();
        
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("nam");

        when(userRepository.findByUsername("nam")).thenReturn(Optional.of(
            ELUser.builder()
            .email("not-nam@gmail.com")
            .username("nam")
            .build()
        ));

        when(userRepository.existsByEmail("nam@gmail.com")).thenReturn(true);

        assertThrows(BusinessException.class, () -> service.updateMyProfile(rq));
    }


    @Test
    public void whenUpdateMyProfileSuccess_theReturnUserResponse(){
        UpdateMyProfileDTO rq = UpdateMyProfileDTO.builder()
            .email("nam@gmail.com")
            .displayName("Nam")
            .firstName("Nguyen")
            .lastName("Nam")
            .hobbies("swimming")
            .facebook("facebook.com")
            .gender("male")
            .phone("11111222222")
            .build();
        
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("nam");

        ELUser user =  ELUser.builder()
            .username("nam")
            .build();

        when(userRepository.findByUsername("nam")).thenReturn(Optional.of(user));

        when(userRepository.save(user)).thenReturn(user);
    
        UserResponseDTO expected = UserResponseDTO.builder()
            .email("nam@gmail.com")
            .username("nam")
            .displayName("Nam")
            .firstName("Nguyen")
            .gender("male")
            .lastName("Nam")
            .hobbies("swimming")
            .facebook("facebook.com")
            .phone("11111222222")
            .groups(new LinkedHashSet<>())
            .build();


        when(mapper.toUserResponseDTO(user)).thenReturn(expected);

        assertEquals(expected, service.updateMyProfile(rq));
    }


    @Test
    public void whenUpdateUserSuccessfully_theReturnUserResponse(){
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
            .status(UserStatus.ACTIVE)
            .build();
        when(userRepository.findById(UUID.fromString(id.toString()))).thenReturn(Optional.of(user));
        
        when(userRepository.existsByUsername("nam@gmail.com")).thenReturn(false);
        UpdateUserDTO rq = UpdateUserDTO.builder()
            .email("nam@gmail.com")
            .department("IT")
            .major("dev")
            .build();
        when(userRepository.save(user)).thenReturn(user);
        UserResponseDTO expected = UserResponseDTO.builder()
            .id(id)
            .email("nam@gmail.com")
            .username("nam")
            .displayName("Nam")
            .firstName("Nguyen")
            .lastName("Nam")
            .hobbies("swimming")
            .facebook("facebook.com")
            .department("IT")
            .major("dev")
            .phone("11111222222")
            .groups(new LinkedHashSet<>())
            .build();
        when(mapper.toUserResponseDTO(user)).thenReturn(expected);

        assertEquals(expected, service.update(id.toString(), rq));
    }

   @Test
   public void whenCreateUserSuccessfully_theReturnUserResponseDTO(){
        UserCreateDTO rq = new UserCreateDTO("username", "password", "displayName", "email@gmail.com", 
            UserStatus.ACTIVE, "firstName", "lastName", "department", "major");
            
        assertDoesNotThrow( () ->service.createUser(rq));
   }

}
