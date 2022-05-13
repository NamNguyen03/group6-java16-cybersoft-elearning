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
import com.group6.java16.cybersoft.role.dto.GroupDTO;
import com.group6.java16.cybersoft.role.dto.GroupResponseDTO;
import com.group6.java16.cybersoft.role.dto.GroupUpdateDTO;
import com.group6.java16.cybersoft.role.dto.ProgramDTO;
import com.group6.java16.cybersoft.role.dto.ProgramResponseDTO;
import com.group6.java16.cybersoft.role.dto.ProgramUpdateDTO;
import com.group6.java16.cybersoft.role.model.ProgramModule;
import com.group6.java16.cybersoft.role.model.ProgramType;
import com.group6.java16.cybersoft.role.service.ProgramService;

@SpringBootTest
@AutoConfigureMockMvc
public class ProgramManagementControllerIntegrationTest {
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ProgramService service;
	
	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenSearchProgram_thenReturnStatus200AndResponseHelper() throws Exception {

		String results = "{\"hasErrors\":false,\"content\":{\"pageCurrent\":1,\"totalPage\":10,\"items\":[]}";

		when(service.search(new PageRequestModel(
				1,
				10,
				null,
				true,
				null,
				null))).thenReturn(new PageResponseModel<ProgramResponseDTO>(1, 10, new ArrayList<ProgramResponseDTO>()));

		mvc.perform(get("/api/v1/programs"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(results)));
	}

	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenCreateProgram_thenReturnStatus200() throws Exception {
		ProgramDTO rq = new ProgramDTO("name",ProgramModule.ROLE,ProgramType.READ,"description");
		Gson gson = new Gson();
		String json = gson.toJson(rq);
		when(service.save(rq)).thenReturn(new ProgramResponseDTO());
		mvc.perform(post("/api/v1/programs")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenCreateProgram_thenReturnStatus400() throws Exception {
		ProgramDTO rq = new ProgramDTO(null,ProgramModule.ROLE,ProgramType.READ,"description");
		Gson gson = new Gson();
		String json = gson.toJson(rq);

		mvc.perform(post("/api/v1/programs")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andDo(print())
				.andExpect(status().isBadRequest());
		
	}

	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenUpdateProgram_thenReturnStatus200AndResponseHelper() throws Exception {
		ProgramUpdateDTO rq = new ProgramUpdateDTO("LEADER",ProgramModule.ROLE,ProgramType.READ,"test");
				
		Gson gson = new Gson();
		String json = gson.toJson(rq);
		when(service.update("5117d63c-a38e-4042-9f69-94f7d7777985", rq)).thenReturn(new ProgramResponseDTO(UUID.fromString("5117d63c-a38e-4042-9f69-94f7d7777985"),"LEADER",ProgramModule.ROLE,ProgramType.READ,"test"));

		mvc.perform(put("/api/v1/programs/5117d63c-a38e-4042-9f69-94f7d7777985")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenUpdateProgram_thenReturnStatus400() throws Exception {
		ProgramUpdateDTO rq = new ProgramUpdateDTO("LEADER",ProgramModule.ROLE,ProgramType.READ,"test");
				
		Gson gson = new Gson();
		String json = gson.toJson(rq);

		when(service.update(any(), any())).thenThrow(new BusinessException("name invalid"));

		mvc.perform(put("/api/v1/programs/5117d63c-a38e-4042-9f69-94f7d7777985")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser("hau")
	public void givenProgramId_whenDeleteProgram_thenReturnStatus200AndResponseHelper() throws Exception {

		assertDoesNotThrow(() -> service.deleteById("5117d63c-a38e-4042-9f69-94f7d7777985"));

		mvc.perform(delete("/api/v1/programs/5117d63c-a38e-4042-9f69-94f7d7777985")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());

	}

	
}
