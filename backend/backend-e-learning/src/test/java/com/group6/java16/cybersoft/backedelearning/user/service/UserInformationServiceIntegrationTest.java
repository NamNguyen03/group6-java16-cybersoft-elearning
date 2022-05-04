package com.group6.java16.cybersoft.backedelearning.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
    public void whenExistsUserIsUsedToSearch_thenReturnPageResponseUser(){
        PageRequestModel rq = new PageRequestModel(1,10, null, true, null, null);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());

        Page<ELUser> page = new PageImpl<ELUser>(new ArrayList<ELUser>(), pageable, 10l);

        when(userRepository.findAll(pageable)).thenReturn(page);

        PageResponseModel<UserResponseDTO> expected = new PageResponseModel<UserResponseDTO>(1,1, new ArrayList<UserResponseDTO>());

        PageResponseModel<UserResponseDTO> rp = service.search(rq);
    
        //search all
        assertEquals(expected.getItems(), rp.getItems(), "search all User error");
        assertEquals(expected.getTotalPage(), rp.getTotalPage(), "search all User error");
        assertEquals(expected.getPageCurrent(), rp.getPageCurrent(), "search all User error");


        //search username
        rq = new PageRequestModel(1,10, null, true, "username", "name");
        when(userRepository.searchByUsername("name",pageable)).thenReturn(page);
        
        rp = service.search(rq);

        assertEquals(expected.getItems(), rp.getItems(), "search User by username error");
        assertEquals(expected.getTotalPage(), rp.getTotalPage(), "search User by username error");
        assertEquals(expected.getPageCurrent(), rp.getPageCurrent(), "search User by username error");
        

        //search display name
        rq = new PageRequestModel(1,10, null, true, "displayName", "value");
        when(userRepository.searchByDisplayName("value", pageable)).thenReturn(page);

        rp = service.search(rq);

        assertEquals(expected.getItems(), rp.getItems(), "search User by display name error");
        assertEquals(expected.getTotalPage(), rp.getTotalPage(), "search User by display name error");
        assertEquals(expected.getPageCurrent(), rp.getPageCurrent(), "search User by display name error");


         //search email
         rq = new PageRequestModel(1,10, null, true, "email", "value");
         when(userRepository.searchByEmail("value", pageable)).thenReturn(page);

         rp = service.search(rq);

         assertEquals(expected.getItems(), rp.getItems(), "search User by email error");
         assertEquals(expected.getTotalPage(), rp.getTotalPage(), "search User by email error");
         assertEquals(expected.getPageCurrent(), rp.getPageCurrent(), "search User by email error");


         //search first name
         rq = new PageRequestModel(1,10, null, true, "firstName", "value");
         when(userRepository.searchByFirstName("value", pageable)).thenReturn(page);

         rp = service.search(rq);

         assertEquals(expected.getItems(), rp.getItems(), "search User by first name error");
         assertEquals(expected.getTotalPage(), rp.getTotalPage(), "search User by first name error");
         assertEquals(expected.getPageCurrent(), rp.getPageCurrent(), "search User by first name error");


         //search last name
         rq = new PageRequestModel(1,10, null, true, "lastName", "value");
         when(userRepository.searchByLastName("value",pageable)).thenReturn(page);

         rp = service.search(rq);

         assertEquals(expected.getItems(), rp.getItems(), "search User by last name error");
         assertEquals(expected.getTotalPage(), rp.getTotalPage(), "search User by last name error");
         assertEquals(expected.getPageCurrent(), rp.getPageCurrent(), "search User by last name error");


        //search all and sort
        rq = new PageRequestModel(1,11, "firstName", true, null, null);
        pageable = PageRequest.of(0, 11, Sort.by("firstName").ascending());
        when(userRepository.findAll(pageable)).thenReturn(page);
        
        rp = service.search(rq);
        
        assertEquals(expected.getItems(), rp.getItems(), "sort all User error");
        assertEquals(expected.getTotalPage(), rp.getTotalPage(), "sort all User error");
        assertEquals(expected.getPageCurrent(), rp.getPageCurrent(), "sort all User error");
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
            .build();


        when(mapper.toUserResponseDTO(user)).thenReturn(expected);

        assertEquals(expected, service.getMyProfile());
    }
}
