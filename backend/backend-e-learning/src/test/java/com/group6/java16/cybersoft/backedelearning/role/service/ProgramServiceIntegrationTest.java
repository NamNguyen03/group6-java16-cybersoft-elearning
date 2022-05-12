package com.group6.java16.cybersoft.backedelearning.role.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.role.dto.ProgramDTO;
import com.group6.java16.cybersoft.role.dto.ProgramResponseDTO;
import com.group6.java16.cybersoft.role.dto.ProgramUpdateDTO;
import com.group6.java16.cybersoft.role.mapper.ProgramMapper;
import com.group6.java16.cybersoft.role.model.ELProgram;
import com.group6.java16.cybersoft.role.model.ProgramModule;
import com.group6.java16.cybersoft.role.model.ProgramType;
import com.group6.java16.cybersoft.role.repository.ELProgramRepository;
import com.group6.java16.cybersoft.role.service.ProgramService;
import com.group6.java16.cybersoft.role.service.ProgramServiceImpl;

@SpringBootTest
public class ProgramServiceIntegrationTest {
	@Mock
	private ELProgramRepository repository;

	@Mock
	private ProgramMapper mapper;

	@InjectMocks
	private ProgramService service = new ProgramServiceImpl();

	@Test
	public void whenExistsProgramIsUsedToSearchAll_thenReturnPageResponse() {
		PageRequestModel request = new PageRequestModel(1, 10, null, true, null, null);

		Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());

		Page<ELProgram> page = new PageImpl<ELProgram>(new ArrayList<ELProgram>(), pageable, 10l);

		when(repository.findAll(pageable)).thenReturn(page);

		PageResponseModel<ProgramResponseDTO> expected = new PageResponseModel<ProgramResponseDTO>(1, 1,
				new ArrayList<ProgramResponseDTO>());

		PageResponseModel<ProgramResponseDTO> response = service.search(request);

		assertEquals(expected.getItems(), response.getItems());
		assertEquals(expected.getTotalPage(), response.getTotalPage());
		assertEquals(expected.getPageCurrent(), response.getPageCurrent());

	}

	@Test
	public void whenExistsProgramIsUsedToSearchByName_thenReturnPageResponse() {
		PageRequestModel request = new PageRequestModel(1, 10, null, true, "name", "value");

		Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());

		Page<ELProgram> page = new PageImpl<ELProgram>(new ArrayList<ELProgram>(), pageable, 10l);

		when(repository.searchByName("value", pageable)).thenReturn(page);

		PageResponseModel<ProgramResponseDTO> expected = new PageResponseModel<ProgramResponseDTO>(1, 1,
				new ArrayList<ProgramResponseDTO>());

		PageResponseModel<ProgramResponseDTO> response = service.search(request);

		assertEquals(expected.getItems(), response.getItems());
		assertEquals(expected.getTotalPage(), response.getTotalPage());
		assertEquals(expected.getPageCurrent(), response.getPageCurrent());

	}

	@Test
	public void whenExistsGroupIsUsedToSearchAllAndSearch_thenReturnPageResponseGroup() {
		PageRequestModel request = new PageRequestModel(1, 10, "name", true, null, null);

		Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

		Page<ELProgram> page = new PageImpl<ELProgram>(new ArrayList<ELProgram>(), pageable, 101);

		when(repository.findAll(pageable)).thenReturn(page);

		PageResponseModel<ProgramResponseDTO> expected = new PageResponseModel<ProgramResponseDTO>(1, 11,
				new ArrayList<ProgramResponseDTO>());

		PageResponseModel<ProgramResponseDTO> response = service.search(request);

		assertEquals(expected.getItems(), response.getItems());
		assertEquals(expected.getTotalPage(), response.getTotalPage());
		assertEquals(expected.getPageCurrent(), response.getPageCurrent());

	}

	@Test
	public void whenUpdateProgramSuccessfully_thenReturnRoleResponse() {
		UUID id = UUID.randomUUID();
		ELProgram program = ELProgram.builder()
				.id(id)
				.name("ADMIN")
				.module(ProgramModule.ROLE)
				.type(ProgramType.READ)
				.description("hau test")
				.build();

		when(repository.findById(UUID.fromString(id.toString()))).thenReturn(Optional.of(program));

		when(repository.existsByName("LEADER")).thenReturn(false);
		ProgramUpdateDTO request = new ProgramUpdateDTO();
		request.setName("LEADER");
		request.setDescription("hau test");

		when(repository.save(program)).thenReturn(program);

		ProgramResponseDTO expected = new ProgramResponseDTO(id, "LEADER", ProgramModule.ROLE, ProgramType.READ,
				"hau test");

		when(mapper.toResponseDTO(program)).thenReturn(expected);

		ProgramResponseDTO rp = service.update(id.toString(), request);

		assertEquals(expected, rp);

	}

	@Test
	public void whenUpdateProgramExistsName_thenThrowBusinessException() {
		UUID id = UUID.randomUUID();
		ELProgram program = ELProgram.builder()
				.id(id)
				.name("LEADER")
				.description("hau test")
				.build();
		ProgramUpdateDTO request = new ProgramUpdateDTO();
		request.setName("LEADER");
		when(repository.findById(UUID.fromString(id.toString()))).thenReturn(Optional.of(program));
		when(repository.existsByName(request.getName())).thenReturn(true);

		assertThrows(BusinessException.class, () -> service.update(id.toString(), request));
	}

	@Test
	public void whenCreateProgramSuccessfully_thenReturnRoleResponseDTO() {
		ProgramDTO rq = new ProgramDTO("name", ProgramModule.ROLE, ProgramType.READ, "description");

		assertDoesNotThrow(() -> service.save(rq));
	}

	@Test
	public void whenDeleteProgramSuccessfully_DoesNotThrowException() {
		UUID id = UUID.randomUUID();
		ELProgram program = ELProgram.builder()
				.name("name")
				.module(ProgramModule.ROLE)
				.type(ProgramType.READ)
				.description("").build();

		when(repository.findById(UUID.fromString(id.toString()))).thenReturn(Optional.of(program));

		assertDoesNotThrow(() -> service.deleteById(id.toString()));
	}
}
