package com.group6.java16.cybersoft.backedelearning.sercutity.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.group6.java16.cybersoft.security.service.UserDetailsServiceImpl;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootTest
public class UserDetailsServiceIntegrationTest {
    @Mock
    private ELUserRepository userRepository;

    @InjectMocks
    private UserDetailsService service = new UserDetailsServiceImpl();

    @Test
    public void whenUsernameNotExistsIsUsedToLoadByUsername_thenThrowUsernameNotFoundException(){
        when(userRepository.findByUsername("nam")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("nam"));
    }

    @Test
    public void whenUsernameExistsIsUsedLoadByUsername_thenReturnUserDetails(){
        ELUser user = ELUser.builder()
            .email("nam@gmail.com")
            .username("nam")
            .displayName("Nam")
            .password("password")
            .firstName("Nguyen")
            .build();

        when(userRepository.findByUsername("nam")).thenReturn(Optional.of(user));

        assertDoesNotThrow( () -> service.loadUserByUsername("nam"));
    }
}
