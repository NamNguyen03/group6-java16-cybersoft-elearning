package com.group6.java16.cybersoft.backedelearning.role.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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
import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.role.dto.GroupDTO;
import com.group6.java16.cybersoft.role.dto.GroupResponseDTO;
import com.group6.java16.cybersoft.role.dto.GroupUpdateDTO;
import com.group6.java16.cybersoft.role.service.GroupService;

@SpringBootTest
@AutoConfigureMockMvc
public class GroupManagementControllerIntegrationTest {
	@MockBean
	private GroupService service;
	
	@Autowired
    private MockMvc mvc;
	
	 @Test
	 @WithMockUser("hau")
	 public void givenJsonObject_whenSearchGroup_thenReturnStatus200AndResponseHelper() throws Exception{

	        String results = "{\"hasErrors\":false,\"content\":{\"pageCurrent\":1,\"totalPage\":10,\"items\":[]}";

	        when(service.search(new PageRequestModel(
				1,
				10,
				null,
				true,
				null,
				null
			) )).thenReturn(new PageResponseModel<GroupResponseDTO>(1,10, new ArrayList<GroupResponseDTO>()));

	        mvc.perform(get("/api/v1/groups"))
	            .andDo(print())
	            .andExpect(status().isOk())
	            .andExpect(content().string(containsString(results)));
	    }
	
	 @Test
	 @WithMockUser("hau")
	 public void givenJsonObject_whenCreateGroup_thenReturnStatus200() throws Exception{
		 GroupDTO rq = new GroupDTO("name","description");
		 Gson gson = new Gson();
		 String json = gson.toJson(rq);
		 when(service.save(rq)).thenReturn(new GroupResponseDTO());
		 mvc.perform(post("/api/v1/groups")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(json))
				 .andDo(print())
				 .andExpect(status().isOk());
	 }
	
	 
	 @Test
	 @WithMockUser("hau")
	 public void givenJsonObject_whenCreateGroup_thenReturnStatus400() throws Exception{
		 GroupDTO rq = new GroupDTO(null,"description");
		 Gson gson = new Gson();
		 String json = gson.toJson(rq);
		
		 mvc.perform(post("/api/v1/groups")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(json))
				 .andDo(print())
				 .andExpect(status().isBadRequest());
	 }
	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenUpdateGroup_thenReturnStatus200AndResponseHelper() throws Exception{
		GroupUpdateDTO rq = GroupUpdateDTO
				.builder()
				.name("LEADER")
				.description("test")
				.build();
		Gson gson = new Gson(); 
		String json = gson.toJson(rq);
		when(service.update("5117d63c-a38e-4042-9f69-94f7d7777985",rq)).thenReturn(GroupResponseDTO
				.builder()
				.id(UUID.fromString("5117d63c-a38e-4042-9f69-94f7d7777985"))
				.name("LEADER")
				.build());
        String results = "\"hasErrors\":false,\"content\":{\"id\":\"5117d63c-a38e-4042-9f69-94f7d7777985\",\"name\":\"LEADER\"";
        
        mvc.perform(put("/api/v1/groups/5117d63c-a38e-4042-9f69-94f7d7777985")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(json))
        		.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(results)));
	}
	
	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenUpdateGroup_thenReturnStatus400AndResponseNull() throws Exception{
		GroupUpdateDTO rq = GroupUpdateDTO
				.builder()
				.name("LEADER")
				.build();
		Gson gson = new Gson(); 
		String json = gson.toJson(rq);
		when(service.update("5117d63c-a38e-4042-9f69-94f7d7777985",rq)).thenReturn(null);
       
        
        mvc.perform(put("/api/v1/groups/5117d63c-a38e-4042-9f69-94f7d7777985")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(json))
        		.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("name invalid")));
	}
	
	@Test
	@WithMockUser("hau")
	public void givenGroupId_whenDeleteGroup_thenReturnStatus200AndResponseHelper() throws Exception{
		
		assertDoesNotThrow(() -> service.deleteById("5117d63c-a38e-4042-9f69-94f7d7777985"));
       
        
        mvc.perform(delete("/api/v1/groups/5117d63c-a38e-4042-9f69-94f7d7777985")
        		.contentType(MediaType.APPLICATION_JSON))
        		.andDo(print())
                .andExpect(status().isOk());
                
	}
	@Test
	@WithMockUser("hau")
	public void givenGroupIdAndRoleId_whenAddRole_thenReturnStatus200AndResponseHelper() throws Exception {
		assertDoesNotThrow(()-> service.addRole("5117d63c-a38e-4042-9f69-94f7d7777985", "5117d63c-a38e-4042-9f69-94f7d7777955"));
		mvc.perform(post("/api/v1/groups/add-group/5117d63c-a38e-4042-9f69-94f7d7777985/5117d63c-a38e-4042-9f69-94f7d7777955")
        		.contentType(MediaType.APPLICATION_JSON))
        		.andDo(print())
                .andExpect(status().isOk());
	}

}
