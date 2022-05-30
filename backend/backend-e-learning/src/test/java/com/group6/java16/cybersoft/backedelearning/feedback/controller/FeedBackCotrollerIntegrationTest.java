/**
 * 
 */
package com.group6.java16.cybersoft.backedelearning.feedback.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.group6.java16.cybersoft.feedback.dto.CommentCreateDTO;
import com.group6.java16.cybersoft.feedback.service.CommentService;

@SpringBootTest
@AutoConfigureMockMvc
public class FeedBackCotrollerIntegrationTest {
	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private CommentService service;
	
	@Test
    @WithMockUser
    public void whenfindCommentIntoLesson_thenReturnStatus200() throws Exception {
		 mvc.perform(get("/api/v1/comments/" + UUID.randomUUID().toString()))
         	
         	.andExpect(status().isOk());
	}
	
	@Test
    @WithMockUser
    public void whenDeleteComment_thenReturnStatus200() throws Exception {
		 mvc.perform(delete("/api/v1/comments/" + UUID.randomUUID().toString()))
  
         	.andExpect(status().isOk());
	}
	
	@Test
    @WithMockUser
    public void whenCommentCreateDTOInvalidIsUsedcreateComment_thenReturnStatus400() throws Exception {
		
		CommentCreateDTO rq = new CommentCreateDTO();
		Gson gson = new Gson();
        String json = gson.toJson(rq);
	   
        mvc.perform(post("/api/v1/comments/" )
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
               
                .andExpect(status().isBadRequest());
	}
	
	@Test
    @WithMockUser
    public void whenCommentCreateDTOValidIsUsedcreateComment_thenReturnStatus200() throws Exception {
		
		CommentCreateDTO rq = new CommentCreateDTO("new Comment",UUID.randomUUID().toString());
		Gson gson = new Gson();
        String json = gson.toJson(rq);
	   
        mvc.perform(post("/api/v1/comments/" )
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
               
                .andExpect(status().isOk());
	}
}
