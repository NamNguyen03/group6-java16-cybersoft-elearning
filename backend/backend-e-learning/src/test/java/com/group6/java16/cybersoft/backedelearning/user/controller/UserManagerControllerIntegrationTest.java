package com.group6.java16.cybersoft.backedelearning.user.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.CoreMatchers.containsString;
import java.util.UUID;

import com.google.gson.Gson;
import com.group6.java16.cybersoft.user.dto.UpdateMyProfileDTO;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;
import com.group6.java16.cybersoft.user.service.UserManagementService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserManagerControllerIntegrationTest {
    
    @MockBean
    private UserManagementService service;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser("nam")
    public void givenJsonObject_whenUpdateMyProfile_theReturnStatus200AndResponseHelper() throws Exception{
        UpdateMyProfileDTO rq = UpdateMyProfileDTO
            .builder()
            .displayName("Nam Nguyen")
            .build();
        Gson gson = new Gson();
        String json = gson.toJson(rq);

        when( service.updateMyProfile(rq)).thenReturn(UserResponseDTO
            .builder()
            .id(UUID.fromString("5117d63c-a38e-4042-9f69-94f7d7777985"))
            .username("nam")
            .displayName("Nam Nguyen")
            .build());
        
        String results = "\"hasErrors\":false,\"content\":{\"id\":\"5117d63c-a38e-4042-9f69-94f7d7777985\",\"username\":\"nam\",\"displayName\":\"Nam Nguyen\"";

        mvc.perform(put("/api/v1/users/me")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(results)));
    }
}
