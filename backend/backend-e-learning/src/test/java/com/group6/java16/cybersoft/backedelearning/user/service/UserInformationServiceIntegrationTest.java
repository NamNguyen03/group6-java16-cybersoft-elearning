package com.group6.java16.cybersoft.backedelearning.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Optional;

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;
import com.group6.java16.cybersoft.user.mapper.UserMapper;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;
import com.group6.java16.cybersoft.user.service.UserInformationService;
import com.group6.java16.cybersoft.user.service.UserInformationServiceImpl;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
public class UserInformationServiceIntegrationTest {
    @Mock
    private ELUserRepository userRepository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserInformationService service = new UserInformationServiceImpl();


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
    public void whenExistsUserIsUsedToSearchByLastName_thenReturnPageResponseUser(){
        PageRequestModel rq =  new PageRequestModel(1,10, null, true, "lastName", "value");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());

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
            .build();

        when(userRepository.findByUsername("nam")).thenReturn(Optional.of(user));

    
        UserResponseDTO expected = UserResponseDTO.builder()
            .email("nam@gmail.com")
            .username("nam")
            .displayName("Nam")
            .firstName("Nguyen")
            .lastName("Nam")
            .hobbies("swimming")
            .facebook("facebook.com")
            .phone("11111222222")
            .groups(new LinkedHashSet<>())
            .build();


        when(mapper.toUserResponseDTO(user)).thenReturn(expected);

        assertEquals(expected, service.getMyProfile());
    }
}
