package com.group6.java16.cybersoft.backedelearning.user.controller;

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;
import com.group6.java16.cybersoft.user.service.UserInformationService;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    @Test
    @WithMockUser("nam")
    public void givenJsonObject_whenSearchUser_theReturnStatus200AndResponseHelper() throws Exception{

        String results = "{\"hasErrors\":false,\"content\":{\"pageCurrent\":1,\"totalPage\":10,\"items\":[]}";

        when(service.search(new PageRequestModel(
			1,
			10,
			null,
			true,
			null,
			null
		) )).thenReturn(new PageResponseModel<UserResponseDTO>(1,10, new ArrayList<UserResponseDTO>()));

        mvc.perform(get("/api/v1/users"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(results)));
    }

    @Test
    @WithMockUser("nam")
    public void givenJsonObject_whenGetMyProfile_theReturnStatus200AndResponseHelper() throws Exception{
        UserResponseDTO expected = UserResponseDTO.builder()
            .id(UUID.fromString("c6b4c4a5-366c-4cbb-9f2b-8b24c9c893f6"))
            .username("nam")
            .displayName("Nam Nguyen")
            .build();
        
      
        when(service.getMyProfile()).thenReturn(expected);

        String results = "{\"hasErrors\":false,\"content\":{\"id\":\"c6b4c4a5-366c-4cbb-9f2b-8b24c9c893f6\",\"username\":\"nam\",\"displayName\":\"Nam Nguyen\"";

        mvc.perform(get("/api/v1/users/me"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(results)));
    }
}
