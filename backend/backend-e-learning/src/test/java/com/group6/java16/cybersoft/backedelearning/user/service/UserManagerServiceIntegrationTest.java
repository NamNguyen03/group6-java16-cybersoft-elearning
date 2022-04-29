package com.group6.java16.cybersoft.backedelearning.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.util.ServiceHelper;
import com.group6.java16.cybersoft.user.dto.UpdateMyProfileDTO;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;
import com.group6.java16.cybersoft.user.mapper.UserMapper;
import com.group6.java16.cybersoft.user.model.ELUser;
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

@SpringBootTest
public class UserManagerServiceIntegrationTest {
    @Mock
    private ELUserRepository userRepository;

    @Mock
    private ServiceHelper<ELUser> serviceUserHelper;
    
    @Mock
    private UserMapper mapper;

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

        when(userRepository.findByUsername("nam")).thenReturn(Optional.of(
           user
        ));

        when(userRepository.save(user)).thenReturn(user);
    
        UserResponseDTO expected = UserResponseDTO.builder()
            .email("nam@gmail.com")
            .username("nam")
            .displayName("Nam")
            .firstName("Nguyen")
            .lastName("Nam")
            .hobbies("swimming")
            .facebook("facebook.com")
            .phone("11111222222")
            .build();


        when(mapper.toUserResponseDTO(user)).thenReturn(expected);

        assertEquals(expected, service.updateMyProfile(rq));
    }
}
