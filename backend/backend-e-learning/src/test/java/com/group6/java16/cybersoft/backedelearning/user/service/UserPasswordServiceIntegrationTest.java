package com.group6.java16.cybersoft.backedelearning.user.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.model.notification.ResetPasswordModel;
import com.group6.java16.cybersoft.common.service.notification.EmailSender;
import com.group6.java16.cybersoft.user.dto.UpdatePasswordDTO;
import com.group6.java16.cybersoft.user.mapper.UserMapper;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.model.ELUserToken;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;
import com.group6.java16.cybersoft.user.repository.UserTokenRepository;
import com.group6.java16.cybersoft.user.service.UserPasswordService;
import com.group6.java16.cybersoft.user.service.UserPasswordServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserPasswordServiceIntegrationTest {
    @Mock
    private ELUserRepository userRepository;

    @Mock
    private UserMapper mapper;

    @Mock
    @Qualifier("emailSenderResetPassword")
    private EmailSender<ResetPasswordModel> serviceSendEmailSenderResetPassword;

    @Mock
    private UserTokenRepository userTokenRepository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserPasswordService service = new UserPasswordServiceImpl();

    @Test
    public void whenGenerateToken_thenNotThrowException(){
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("nam");

        when(userRepository.findByUsername("nam")).thenReturn(Optional.of(ELUser.builder().build()));

        assertDoesNotThrow( () -> service.generateToken());
    }

    @Test
    public void whenNotExistsTokenIsUsedUpdatePassword_thenThrowsBusinessException(){
        when(userTokenRepository.findByUsername("nam")).thenReturn(new ArrayList<ELUserToken>());
        UpdatePasswordDTO rq = new UpdatePasswordDTO("nam","123456454","token");
        assertThrows(BusinessException.class, () -> service.updatePassword(rq));
    }

    @Test
    public void whenTokenInvalidIsUsedUpdatePassword_thenThrowsBusinessException(){
        when(userTokenRepository.findByUsername("nam")).thenReturn(Arrays.asList(new ELUserToken("234", new ELUser())));
        UpdatePasswordDTO rq = new UpdatePasswordDTO("nam","123456454","token");
        assertThrows(BusinessException.class, () -> service.updatePassword(rq));
    }

    @Test
    public void whenTokenExpiredIsUsedUpdatePassword_thenThrowsBusinessException(){

        ELUserToken token = ELUserToken.builder()
            .value("token")
            .createdAt(LocalDateTime.now().minusHours(1))
            .build();

        when(userTokenRepository.findByUsername("nam")).thenReturn(Arrays.asList(token));
        UpdatePasswordDTO rq = new UpdatePasswordDTO("nam","123456454","token");
        assertThrows(BusinessException.class, () -> service.updatePassword(rq));
    }

    @Test
    public void whenNotExistsUserIsUsedUpdatePassword_thenThrowsBusinessException(){
        when(userTokenRepository.findByUsername("nam")).thenReturn(Arrays.asList(
            ELUserToken.builder()
                .value("token")
                .createdAt(LocalDateTime.now().plusMinutes(2))
                .build()
        ));
        when( userRepository.findByUsername("nam")).thenReturn(Optional.empty());

        UpdatePasswordDTO rq = new UpdatePasswordDTO("nam","123456454","token");
        assertThrows(BusinessException.class, () -> service.updatePassword(rq));
    }

    @Test
    public void whenUpdatePassword_thenSuccessfully(){
        when(userTokenRepository.findByUsername("nam")).thenReturn(Arrays.asList(
            ELUserToken.builder()
                .value("token")
                .createdAt(LocalDateTime.now().plusMinutes(2))
                .build()
        ));
        when( userRepository.findByUsername("nam")).thenReturn(Optional.of(new ELUser()));

        UpdatePasswordDTO rq = new UpdatePasswordDTO("nam","123456454","token");

        assertDoesNotThrow( () ->service.updatePassword(rq));
    }
}
