package com.group6.java16.cybersoft.backedelearning.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    
        assertEquals(expected.getItems(), rp.getItems());
        assertEquals(expected.getTotalPage(), rp.getTotalPage());
        assertEquals(expected.getPageCurrent(), rp.getPageCurrent());
    }
}
