package com.group6.java16.cybersoft.backedelearning.sercutity.controller;

import com.google.gson.Gson;
import com.group6.java16.cybersoft.security.dto.LoginRequestDTO;
import com.group6.java16.cybersoft.security.service.AuthService;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {
    
    @MockBean
    private AuthService authService;

    @MockBean
    private ELUserRepository userRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenJsonObject_whenLoginDTOInvalidIsUsedLogin_theReturnStatus400() throws Exception{

        LoginRequestDTO rq = new LoginRequestDTO("nam","123");

        Gson gson = new Gson();
        String json = gson.toJson(rq);

        mvc.perform(post("/api/v1/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    public void givenJsonObject_whenLoginDTOValidIsUsedLogin_theReturnResponseStatus200() throws Exception{
        LoginRequestDTO rq = new LoginRequestDTO("nam_nam123","1236782123");
        Gson gson = new Gson();
        String json = gson.toJson(rq);

        when(authService.login(rq)).thenReturn("123");
        when(userRepository.existsByUsername("nam_nam123")).thenReturn(true);
        mvc.perform(post("/api/v1/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(print())
            .andExpect(status().isOk());
    }
}
