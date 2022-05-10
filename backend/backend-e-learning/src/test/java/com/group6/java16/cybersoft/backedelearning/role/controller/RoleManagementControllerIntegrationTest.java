package com.group6.java16.cybersoft.backedelearning.role.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Random;
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
import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.role.dto.RoleDTO;
import com.group6.java16.cybersoft.role.dto.RoleResponseDTO;
import com.group6.java16.cybersoft.role.dto.RoleUpdateDTO;
import com.group6.java16.cybersoft.role.service.RoleService;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleManagementControllerIntegrationTest {
	
	@MockBean
	private RoleService service;
	
	@Autowired
    private MockMvc mvc;
	
	 @Test
	 @WithMockUser("hau")
	 public void givenJsonObject_whenSearchRole_thenReturnStatus200AndResponseHelper() throws Exception{

	        String results = "{\"hasErrors\":false,\"content\":{\"pageCurrent\":1,\"totalPage\":10,\"items\":[]}";

	        when(service.search(new PageRequestModel(
				1,
				10,
				null,
				true,
				null,
				null
			) )).thenReturn(new PageResponseModel<RoleResponseDTO>(1,10, new ArrayList<RoleResponseDTO>()));

	        mvc.perform(get("/api/v1/roles"))
	            .andDo(print())
	            .andExpect(status().isOk())
	            .andExpect(content().string(containsString(results)));
	    }
	
	 @Test
	 @WithMockUser("hau")
	 public void givenJsonObject_whenCreateRole_thenReturnStatus200() throws Exception{
		 RoleDTO rq = new RoleDTO("name","description");
		 Gson gson = new Gson();
		 String json = gson.toJson(rq);
		 when(service.createRole(rq)).thenReturn(new RoleResponseDTO());
		 mvc.perform(post("/api/v1/roles")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(json))
				 .andDo(print())
				 .andExpect(status().isOk());
	 }
	
	 
	 @Test
	 @WithMockUser("hau")
	 public void givenJsonObject_whenCreateRole_thenReturnStatus400() throws Exception{
		 RoleDTO rq = new RoleDTO(null,"description");
		 Gson gson = new Gson();
		 String json = gson.toJson(rq);
		
		 mvc.perform(post("/api/v1/roles")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(json))
				 .andDo(print())
				 .andExpect(status().isBadRequest());
	 }
	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenUpdateRole_thenReturnStatus200AndResponseHelper() throws Exception{
		RoleUpdateDTO rq = RoleUpdateDTO
				.builder()
				.name("LEADER")
				.description("test")
				.build();
		Gson gson = new Gson(); 
		String json = gson.toJson(rq);
		
        mvc.perform(put("/api/v1/roles/5117d63c-a38e-4042-9f69-94f7d7777985")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(json))
        		.andDo(print())
                .andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenUpdateRole_thenReturnStatus400AndResponseNull() throws Exception{
		RoleUpdateDTO rq = RoleUpdateDTO
				.builder()
				.name("LEADER")
				.build();
		Gson gson = new Gson(); 
		String json = gson.toJson(rq);
		when(service.update(any(),any())).thenThrow(new BusinessException("name invalid"));
       
        
        mvc.perform(put("/api/v1/roles/5117d63c-a38e-4042-9f69-94f7d7777985")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(json))
        		.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("name invalid")));
	}
	
	@Test
	@WithMockUser("hau")
	public void givenRoleId_whenDeleteRole_thenReturnStatus200AndResponseHelper() throws Exception{
		
		assertDoesNotThrow(() -> service.deleteById("5117d63c-a38e-4042-9f69-94f7d7777985"));
       
        
        mvc.perform(delete("/api/v1/roles/5117d63c-a38e-4042-9f69-94f7d7777985")
        		.contentType(MediaType.APPLICATION_JSON))
        		.andDo(print())
                .andExpect(status().isOk());
                
	}

}
