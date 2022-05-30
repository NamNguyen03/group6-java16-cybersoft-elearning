package com.group6.java16.cybersoft.backedelearning.role.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.group6.java16.cybersoft.role.dto.RoleDTO;
import com.group6.java16.cybersoft.role.dto.RoleResponseDTO;
import com.group6.java16.cybersoft.role.dto.RoleUpdateDTO;
import com.group6.java16.cybersoft.role.model.ProgramModule;
import com.group6.java16.cybersoft.role.model.ProgramType;
import com.group6.java16.cybersoft.role.service.GroupService;
import com.group6.java16.cybersoft.role.service.ProgramService;
import com.group6.java16.cybersoft.role.service.RoleService;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleModuleControllerIntegrationTest {
	@MockBean
	private GroupService groupService;
	
	@MockBean
	private ProgramService programService;
	
	@MockBean
	private RoleService roleService;
	
	@Autowired
	private MockMvc mvc;

	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenSearchGroup_thenReturnStatus200AndResponseHelper() throws Exception {

		String results = "{\"hasErrors\":false,\"content\":{\"pageCurrent\":1,\"totalPage\":10,\"items\":[]}";

		when(groupService.search(new PageRequestModel(
				1,
				10,
				null,
				true,
				null,
				null))).thenReturn(new PageResponseModel<GroupResponseDTO>(1, 10, new ArrayList<GroupResponseDTO>()));

		mvc.perform(get("/api/v1/groups"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(results)));
	}

	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenCreateGroup_thenReturnStatus200() throws Exception {
		GroupDTO rq = new GroupDTO("name", "description");
		Gson gson = new Gson();
		String json = gson.toJson(rq);
		when(groupService.save(rq)).thenReturn(new GroupResponseDTO());
		mvc.perform(post("/api/v1/groups")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenCreateGroup_thenReturnStatus400() throws Exception {
		GroupDTO rq = new GroupDTO(null, "description");
		Gson gson = new Gson();
		String json = gson.toJson(rq);

		mvc.perform(post("/api/v1/groups")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenUpdateGroup_thenReturnStatus200AndResponseHelper() throws Exception {
		GroupUpdateDTO rq = GroupUpdateDTO
				.builder()
				.name("LEADER")
				.description("test")
				.build();
		Gson gson = new Gson();
		String json = gson.toJson(rq);
		when(groupService.update("5117d63c-a38e-4042-9f69-94f7d7777985", rq)).thenReturn(GroupResponseDTO
				.builder()
				.id(UUID.fromString("5117d63c-a38e-4042-9f69-94f7d7777985"))
				.name("LEADER")
				.build());
		String results = "\"hasErrors\":false,\"content\":{\"id\":\"5117d63c-a38e-4042-9f69-94f7d7777985\",\"name\":\"LEADER\"";

		mvc.perform(put("/api/v1/groups/5117d63c-a38e-4042-9f69-94f7d7777985")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(results)));
	}

	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenUpdateGroup_thenReturnStatus400() throws Exception {
		GroupUpdateDTO rq = GroupUpdateDTO
				.builder()
				.name("LEADER")
				.build();
		Gson gson = new Gson();
		String json = gson.toJson(rq);

		when(groupService.update(any(), any())).thenThrow(new BusinessException("name invalid"));

		mvc.perform(put("/api/v1/groups/5117d63c-a38e-4042-9f69-94f7d7777985")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser("hau")
	public void givenGroupId_whenDeleteGroup_thenReturnStatus200AndResponseHelper() throws Exception {

		assertDoesNotThrow(() -> groupService.deleteById("5117d63c-a38e-4042-9f69-94f7d7777985"));

		mvc.perform(delete("/api/v1/groups/5117d63c-a38e-4042-9f69-94f7d7777985")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	@WithMockUser("hau")
	public void givenGroupIdAndRoleId_whenAddRole_thenReturnStatus200AndResponseHelper() throws Exception {
		mvc.perform(post("/api/v1/groups/5117d63c-a38e-4042-9f69-94f7d7777985/5117d63c-a38e-4042-9f69-94f7d7777955")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	@Test
	@WithMockUser("hau")
	public void givenGroupId_whenGetGroupDetails_thenReturnStatus200AndResponseHelper() throws Exception {

		mvc.perform(get("/api/v1/groups/5117d63c-a38e-4042-9f69-94f7d7777985")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}
	@Test
	@WithMockUser("hau")
	public void givenRoleId_whenAddRoleIntoGroup_thenReturnStatus200AndResponseHelper() throws Exception {

		mvc.perform(post("/api/v1/groups/5117d63c-a38e-4042-9f69-94f7d7777985/5117d63c-a38e-4042-9f69-94f7d7777986")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}
	@Test
	@WithMockUser("hau")
	public void givenRoleId_whenDeleteRoleIntoGroup_thenReturnStatus200AndResponseHelper() throws Exception {

		mvc.perform(delete("/api/v1/groups/5117d63c-a38e-4042-9f69-94f7d7777985/5117d63c-a38e-4042-9f69-94f7d7777986")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}
	

	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenSearchProgram_thenReturnStatus200AndResponseHelper() throws Exception {

		String results = "{\"hasErrors\":false,\"content\":{\"pageCurrent\":1,\"totalPage\":10,\"items\":[]}";

		when(programService.search(new PageRequestModel(
				1,
				10,
				null,
				true,
				null,
				null)))
				.thenReturn(new PageResponseModel<ProgramResponseDTO>(1, 10, new ArrayList<ProgramResponseDTO>()));

		mvc.perform(get("/api/v1/programs"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString(results)));
	}

	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenCreateProgram_thenReturnStatus200() throws Exception {
		ProgramDTO rq = new ProgramDTO("name", ProgramModule.ROLE, ProgramType.READ, "description");
		Gson gson = new Gson();
		String json = gson.toJson(rq);
		when(programService.save(rq)).thenReturn(new ProgramResponseDTO());
		mvc.perform(post("/api/v1/programs")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenCreateProgram_thenReturnStatus400() throws Exception {
		ProgramDTO rq = new ProgramDTO(null, ProgramModule.ROLE, ProgramType.READ, "description");
		Gson gson = new Gson();
		String json = gson.toJson(rq);

		mvc.perform(post("/api/v1/programs")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isBadRequest());

	}

	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenUpdateProgram_thenReturnStatus200AndResponseHelper() throws Exception {
		ProgramUpdateDTO rq = new ProgramUpdateDTO("LEADER", ProgramModule.ROLE, ProgramType.READ, "test");

		Gson gson = new Gson();
		String json = gson.toJson(rq);
		when(programService.update("5117d63c-a38e-4042-9f69-94f7d7777985", rq))
				.thenReturn(new ProgramResponseDTO(UUID.fromString("5117d63c-a38e-4042-9f69-94f7d7777985"), "LEADER",
						ProgramModule.ROLE, ProgramType.READ, "test"));

		mvc.perform(put("/api/v1/programs/5117d63c-a38e-4042-9f69-94f7d7777985")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenUpdateProgram_thenReturnStatus400() throws Exception {
		ProgramUpdateDTO rq = new ProgramUpdateDTO("LEADER", ProgramModule.ROLE, ProgramType.READ, "test");

		Gson gson = new Gson();
		String json = gson.toJson(rq);

		when(programService.update(any(), any())).thenThrow(new BusinessException("name invalid"));

		mvc.perform(put("/api/v1/programs/5117d63c-a38e-4042-9f69-94f7d7777985")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser("hau")
	public void givenProgramId_whenDeleteProgram_thenReturnStatus200AndResponseHelper() throws Exception {

		assertDoesNotThrow(() -> programService.deleteById("5117d63c-a38e-4042-9f69-94f7d7777985"));

		mvc.perform(delete("/api/v1/programs/5117d63c-a38e-4042-9f69-94f7d7777985")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}
	
	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenSearchRole_thenReturnStatus200AndResponseHelper() throws Exception {

		String results = "{\"hasErrors\":false,\"content\":{\"pageCurrent\":1,\"totalPage\":10,\"items\":[]}";

		when(roleService.search(new PageRequestModel(
				1,
				10,
				null,
				true,
				null,
				null))).thenReturn(new PageResponseModel<RoleResponseDTO>(1, 10, new ArrayList<RoleResponseDTO>()));

		mvc.perform(get("/api/v1/roles"))
				
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(results)));
	}

	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenCreateRole_thenReturnStatus200() throws Exception {
		RoleDTO rq = new RoleDTO("name", "description");
		Gson gson = new Gson();
		String json = gson.toJson(rq);
		when(roleService.createRole(rq)).thenReturn(new RoleResponseDTO());
		mvc.perform(post("/api/v1/roles")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenCreateRole_thenReturnStatus400() throws Exception {
		RoleDTO rq = new RoleDTO(null, "description");
		Gson gson = new Gson();
		String json = gson.toJson(rq);

		mvc.perform(post("/api/v1/roles")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenUpdateRole_thenReturnStatus200AndResponseHelper() throws Exception {
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
				
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser("hau")
	public void givenJsonObject_whenUpdateRole_thenReturnStatus400AndResponseNull() throws Exception {
		RoleUpdateDTO rq = RoleUpdateDTO
				.builder()
				.name("LEADER")
				.build();
		Gson gson = new Gson();
		String json = gson.toJson(rq);
		when(roleService.update(any(), any())).thenThrow(new BusinessException("name invalid"));

		mvc.perform(put("/api/v1/roles/5117d63c-a38e-4042-9f69-94f7d7777985")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser("hau")
	public void givenRoleId_whenDeleteRole_thenReturnStatus200AndResponseHelper() throws Exception {

		mvc.perform(delete("/api/v1/roles/5117d63c-a38e-4042-9f69-94f7d7777985")
				.contentType(MediaType.APPLICATION_JSON))
				
				.andExpect(status().isOk());

	}
	@Test
	@WithMockUser("hau")
	public void givenRoleId_whenGetRoleDetails_thenReturnStatus200AndResponseHelper() throws Exception {

		mvc.perform(get("/api/v1/roles/5117d63c-a38e-4042-9f69-94f7d7777985")
				.contentType(MediaType.APPLICATION_JSON))
				
				.andExpect(status().isOk());

	}
	@Test
	@WithMockUser("hau")
	public void givenProgramId_whenAddProgramIntoRole_thenReturnStatus200AndResponseHelper() throws Exception {

		mvc.perform(post("/api/v1/roles/5117d63c-a38e-4042-9f69-94f7d7777985/5117d63c-a38e-4042-9f69-94f7d7777986")
				.contentType(MediaType.APPLICATION_JSON))
				
				.andExpect(status().isOk());

	}
	@Test
	@WithMockUser("hau")
	public void givenProgramId_whenDeleteProgramIntoRole_thenReturnStatus200AndResponseHelper() throws Exception {

		mvc.perform(delete("/api/v1/roles/5117d63c-a38e-4042-9f69-94f7d7777985/5117d63c-a38e-4042-9f69-94f7d7777986")
				.contentType(MediaType.APPLICATION_JSON))
				
				.andExpect(status().isOk());

	}


}
