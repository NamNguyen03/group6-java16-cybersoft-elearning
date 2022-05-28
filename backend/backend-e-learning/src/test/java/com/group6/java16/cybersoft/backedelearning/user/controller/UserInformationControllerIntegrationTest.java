package com.group6.java16.cybersoft.backedelearning.user.controller;

import com.group6.java16.cybersoft.role.repository.ELProgramRepository;
import com.group6.java16.cybersoft.user.service.UserInformationService;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    public void givenJsonObject_whenSearchUser_theReturnStatus200() throws Exception{
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
    public void givenJsonObject_whenGetMyProfile_theReturnStatus200() throws Exception{
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
