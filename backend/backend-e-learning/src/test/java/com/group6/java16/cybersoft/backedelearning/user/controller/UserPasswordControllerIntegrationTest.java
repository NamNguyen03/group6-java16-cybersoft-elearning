package com.group6.java16.cybersoft.backedelearning.user.controller;

import com.google.gson.Gson;
import com.group6.java16.cybersoft.user.dto.UpdatePasswordDTO;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;
import com.group6.java16.cybersoft.user.service.UserPasswordService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
public class UserPasswordControllerIntegrationTest {
    @MockBean
    private UserPasswordService service;

    @MockBean
    private ELUserRepository repo;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser("nam")
    public void givenJsonObject_whenGetToken_thenReturnReturnStatus200() throws Exception{

        mvc.perform(post("/api/v1/users/password/token/me"))
            
            .andExpect(status().isOk());
    }
    


    @Test
    @WithMockUser("nam")
    public void givenJsonObject_whenUpdatePassword_thenReturnReturnStatus200() throws Exception{
        UpdatePasswordDTO rq = new UpdatePasswordDTO();
        rq.setPassword("111111111111111");
        rq.setToken("token");
        rq.setUsername("nam");
        Gson gson = new Gson();
        String json = gson.toJson(rq);

        when(repo.existsByUsername("nam")).thenReturn(true);

        mvc.perform(put("/api/v1/users/password")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("nam")
    public void givenJsonObject_whenUpdatePassword_thenReturnReturnStatus400() throws Exception{
        UpdatePasswordDTO rq = new UpdatePasswordDTO();
        rq.setPassword("111111111111111");
        rq.setToken("token");
        rq.setUsername("nam");
        Gson gson = new Gson();
        String json = gson.toJson(rq);

        when(repo.existsByUsername("nam")).thenReturn(false);

        mvc.perform(put("/api/v1/users/password")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            
            .andExpect(status().isBadRequest());
    }
    
    
}
