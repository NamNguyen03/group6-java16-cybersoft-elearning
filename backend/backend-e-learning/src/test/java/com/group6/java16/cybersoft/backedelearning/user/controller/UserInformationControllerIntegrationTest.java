package com.group6.java16.cybersoft.backedelearning.user.controller;

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.role.repository.ELProgramRepository;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;
import com.group6.java16.cybersoft.user.service.UserInformationService;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserInformationControllerIntegrationTest {
    @MockBean
    private UserInformationService service;

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ELProgramRepository programRepository;

    @Test
    @WithMockUser("nam")
    public void givenJsonObject_whenSearchUser_theReturnStatus200AndResponseHelper() throws Exception {

        when(service.search(new PageRequestModel(
                1,
                10,
                null,
                true,
                null,
                null))).thenReturn(new PageResponseModel<UserResponseDTO>(1, 10, new ArrayList<UserResponseDTO>()));

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("nam");

        when(programRepository.existsByNameProgramAndUsername("searchUser", "nam")).thenReturn(true);

        mvc.perform(get("/api/v1/users")
                .param("pageCurrent", "1")
                .param("itemPerPage", "10")
                .param("fieldNameSort", "createdBy")
                .param("isIncrementSort", "true")
                .param("fieldNameSearch", "createBy")
                .param("valueFieldNameSearch", "nam"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("nam")
    public void givenJsonObject_whenGetMyProfile_theReturnStatus200AndResponseHelper() throws Exception {
        mvc.perform(get("/api/v1/users/me"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("nam")
    public void givenJsonObject_whenProfile_theReturnStatus200AndResponseHelper() throws Exception{

        mvc.perform(get("/api/v1/users/" + UUID.randomUUID().toString()))
            .andDo(print())
            .andExpect(status().isOk());
    }

}
