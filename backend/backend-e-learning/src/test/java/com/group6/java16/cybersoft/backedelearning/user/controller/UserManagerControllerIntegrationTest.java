package com.group6.java16.cybersoft.backedelearning.user.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import com.google.gson.Gson;
import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.user.dto.UpdateMyProfileDTO;
import com.group6.java16.cybersoft.user.dto.UpdateUserDTO;
import com.group6.java16.cybersoft.user.dto.UserCreateDTO;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;
import com.group6.java16.cybersoft.user.model.UserStatus;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;
import com.group6.java16.cybersoft.user.service.UserManagementService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserManagerControllerIntegrationTest {
    
    @MockBean
    private UserManagementService service;

    @MockBean
    private ELUserRepository repository;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser("nam")
    public void givenJsonObject_whenUpdateMyProfile_theReturnStatus200AndResponseHelper() throws Exception{
        UpdateMyProfileDTO rq = new UpdateMyProfileDTO("displayName","firstName","lastName","hobbies","facebook", "gender", "phone");
        Gson gson = new Gson();
        String json = gson.toJson(rq);
        mvc.perform(put("/api/v1/users/me")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("nam")
    public void givenJsonObject_whenUpdateUser_theReturnStatus200() throws Exception{
        UpdateUserDTO rq = new UpdateUserDTO();
        Gson gson = new Gson();
        String json = gson.toJson(rq);

        mvc.perform(put("/api/v1/users/5117d63c-a38e-4042-9f69-94f7d7777985")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("nam")
    public void givenJsonObject_whenUpdateUser_theReturnStatus400() throws Exception{
        UpdateUserDTO rq =  new UpdateUserDTO("nam@gmail.com","IT","dev",UserStatus.ACTIVE);
        Gson gson = new Gson();
        String json = gson.toJson(rq);

        when(service.update(any(),any())).thenThrow(new BusinessException("User not found."));

        mvc.perform(put("/api/v1/users/5117d63c-a38e-4042-9f69-94f7d7777985")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser("nam")
    public void givenJsonObject_whenCreateUser_theReturnStatus200() throws Exception{
        UserCreateDTO rq = new UserCreateDTO("username", "password", "displayName", "email@gmail.com", 
            UserStatus.ACTIVE, "firstName", "lastName", "department", "major");
        Gson gson = new Gson();
        String json = gson.toJson(rq);

        when(service.createUser(rq)).thenReturn(new UserResponseDTO());

        mvc.perform(post("/api/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(print())
            .andExpect(status().isOk());
            
    }

    @Test
    @WithMockUser("nam")
    public void givenJsonObject_whenUsernameExistsIsUsedToCreateUser_theReturnStatus400() throws Exception{
        UserCreateDTO rq = new UserCreateDTO("nam12234", "password", "displayName", "email@gmail.com", 
            UserStatus.ACTIVE, "firstName", "lastName", "department", "major");
        Gson gson = new Gson();
        String json = gson.toJson(rq);
        when(repository.existsByUsername("nam12234")).thenReturn(true);

        mvc.perform(post("/api/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(print())
            .andExpect(status().isBadRequest());
            
    }

    @Test
    @WithMockUser("nam")
    public void givenJsonObject_whenEmailExistsIsUsedToCreateUser_theReturnStatus400() throws Exception{
        UserCreateDTO rq = new UserCreateDTO("nam12234", "password", "displayName", "email@gmail.com", 
            UserStatus.ACTIVE, "firstName", "lastName", "department", "major");
        Gson gson = new Gson();
        String json = gson.toJson(rq);
        when(repository.existsByEmail("email@gmail.com")).thenReturn(true);

        mvc.perform(post("/api/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(print())
            .andExpect(status().isBadRequest());
            
    }

    @Test
    @WithMockUser("nam")
    public void whenDeleteUser_thenReturnStatus200() throws Exception{
        mvc.perform(delete("/api/v1/users/" + UUID.randomUUID().toString()))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test 
    @WithMockUser("nam")
    public void whenUpdateAvatar_thenReturnStatus200() throws Exception{
        MockMultipartFile file 
            = new MockMultipartFile(
            "file", 
            "hello.txt", 
            MediaType.TEXT_PLAIN_VALUE, 
            "Hello, World!".getBytes()
            );
        mvc.perform(multipart("/api/v1/users/me/avatar").file(file))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("nam")
    public void whenAddGroupIntoUserSuccess_thenReturnStatus200() throws Exception{
        mvc.perform(post("/api/v1/users/"+ UUID.randomUUID().toString() + "/" + UUID.randomUUID().toString()))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("nam")
    public void whenDeleteGroupIntoUserSuccess_thenReturnStatus200() throws Exception{
        mvc.perform(delete("/api/v1/users/"+ UUID.randomUUID().toString() + "/" + UUID.randomUUID().toString()))
            .andDo(print())
            .andExpect(status().isOk());
    }
}