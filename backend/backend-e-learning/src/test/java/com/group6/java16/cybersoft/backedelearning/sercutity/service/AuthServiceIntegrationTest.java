package com.group6.java16.cybersoft.backedelearning.sercutity.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.security.dto.LoginRequestDTO;
import com.group6.java16.cybersoft.security.jwt.JwtHelper;
import com.group6.java16.cybersoft.security.service.AuthService;
import com.group6.java16.cybersoft.security.service.AuthServiceImpl;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class AuthServiceIntegrationTest {
    
    @Mock
    private ELUserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtHelper jwts;

    @InjectMocks
    private AuthService service = new AuthServiceImpl();

    @Test
    public void whenUsernameNotExistsIsUsedLogin_thenThrowsBusinessException(){
        
        LoginRequestDTO loginDTO = new LoginRequestDTO("nam","12345665");

        when(userRepository.existsByUsername("nam")).thenReturn(false);

        assertThrows(BusinessException.class, () -> service.login(loginDTO));
    }

    @Test
    public void whenPasswordNotEqualsIsUsedLogin_thenThrowsBusinessException(){
        
        LoginRequestDTO loginDTO = new LoginRequestDTO("nam","12345665");

        ELUser user = new ELUser().builder()
            .password("xyz123")
            .username("nam")
            .build();

        when(userRepository.existsByUsername("nam")).thenReturn(true);
        when(userRepository.findByUsername("nam")).thenReturn(Optional.of(user));
        when(encoder.matches(loginDTO.getPassword(), "xyz123")).thenReturn(false);

        assertThrows(BusinessException.class, () -> service.login(loginDTO));
    }

    @Test
    public void whenLoginDTOValidIsUsedLogin_thenReturnJWT(){
        LoginRequestDTO loginDTO = new LoginRequestDTO("nam","12345665");

        ELUser user = new ELUser().builder()
            .password("xyz123")
            .username("nam")
            .build();

        when(userRepository.existsByUsername("nam")).thenReturn(true);
        when(userRepository.findByUsername("nam")).thenReturn(Optional.of(user));
        when(encoder.matches(loginDTO.getPassword(), "xyz123")).thenReturn(true);
        when(jwts.generateJwtToken(loginDTO.getUsername())).thenReturn("jwt_xyz");

        assertEquals("jwt_xyz", service.login(loginDTO));
    }
}
