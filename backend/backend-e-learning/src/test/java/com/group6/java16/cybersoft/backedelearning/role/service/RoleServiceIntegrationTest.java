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
import com.group6.java16.cybersoft.role.dto.RoleDTO;
import com.group6.java16.cybersoft.role.dto.RoleResponseDTO;
import com.group6.java16.cybersoft.role.dto.RoleUpdateDTO;
import com.group6.java16.cybersoft.role.mapper.RoleMapper;
import com.group6.java16.cybersoft.role.model.ELRole;
import com.group6.java16.cybersoft.role.repository.ELRoleRepository;
import com.group6.java16.cybersoft.role.service.RoleService;
import com.group6.java16.cybersoft.role.service.RoleServiceImpl;


@SpringBootTest
public class RoleServiceIntegrationTest {
	@Mock
	private ELRoleRepository roleRepository;

	@Mock
	private RoleMapper mapper;

	@InjectMocks
	private RoleService service = new RoleServiceImpl ();


	@Test
	public void whenExistsRoleIsUsedToSearchAll_thenReturnPageResponseRole(){
		PageRequestModel request = new PageRequestModel(1,10, null, true, null, null);

		Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());

		Page<ELRole> page = new PageImpl<ELRole>(new ArrayList<ELRole>(),pageable,10l);

		when(roleRepository.findAll(pageable)).thenReturn(page);

		PageResponseModel<RoleResponseDTO> expected = new PageResponseModel<RoleResponseDTO>(1,1,new ArrayList<RoleResponseDTO>());

		PageResponseModel<RoleResponseDTO> response = service.search(request);

		assertEquals(expected.getItems(),response.getItems());
		assertEquals(expected.getTotalPage(),response.getTotalPage());
		assertEquals(expected.getPageCurrent(),response.getPageCurrent());

	}
	@Test
	public void whenExistsRoleIsUsedToSearchByName_thenReturnPageResponseRole(){
		PageRequestModel request = new PageRequestModel(1,10, null, true, "name", "value");

		Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());

		Page<ELRole> page = new PageImpl<ELRole>(new ArrayList<ELRole>(),pageable,10l);

		when(roleRepository.searchByName("value",pageable)).thenReturn(page);

		PageResponseModel<RoleResponseDTO> expected = new PageResponseModel<RoleResponseDTO>(1,1,new ArrayList<RoleResponseDTO>());

		PageResponseModel<RoleResponseDTO> response = service.search(request);

		assertEquals(expected.getItems(),response.getItems());
		assertEquals(expected.getTotalPage(),response.getTotalPage());
		assertEquals(expected.getPageCurrent(),response.getPageCurrent());

	}
	@Test
	public void whenExistsRoleIsUsedToSearchAllAndSearch_thenReturnPageResponseRole(){
		PageRequestModel request = new PageRequestModel(1,10, "name", true, null, null);

		Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

		Page<ELRole> page = new PageImpl<ELRole>(new ArrayList<ELRole>(),pageable,101);

		when(roleRepository.findAll(pageable)).thenReturn(page);

		PageResponseModel<RoleResponseDTO> expected = new PageResponseModel<RoleResponseDTO>(1,11,new ArrayList<RoleResponseDTO>());

		PageResponseModel<RoleResponseDTO> response = service.search(request);

		assertEquals(expected.getItems(),response.getItems());
		assertEquals(expected.getTotalPage(),response.getTotalPage());
		assertEquals(expected.getPageCurrent(),response.getPageCurrent());

	}

	@Test
	public void whenUpdateRoleSuccessfully_thenReturnRoleResponse() {
		UUID id = UUID.randomUUID();
		ELRole role = ELRole.builder()
				.id(id)
				.name("ADMIN")
				.description("hau test")
				.build();

		when(roleRepository.findById(UUID.fromString(id.toString()))).thenReturn(Optional.of(role));

		when(roleRepository.existsByName("LEADER")).thenReturn(false);
		RoleUpdateDTO request = RoleUpdateDTO.builder()
				.name("LEADER")
				.description("hau test")
				.build();

		when(roleRepository.save(role)).thenReturn(role);

		RoleResponseDTO expected = RoleResponseDTO.builder()
				.id(id)
				.name("LEADER")
				.description("hau test")
				.build();

		when(mapper.toResponseDTO(role)).thenReturn(expected);

		assertEquals(expected, service.update(id.toString(), request));


	}

	@Test
	public void whenUpdateRoleExistsName_thenThrowBusinessException() {
		UUID id = UUID.randomUUID();
		ELRole role = ELRole.builder()
				.id(UUID.randomUUID())
				.name("LEADER")
				.description("hau test")
				.build();
		RoleUpdateDTO request = RoleUpdateDTO.builder()
				.name("LEADER").build();
		when(roleRepository.findById(UUID.fromString(id.toString()))).thenReturn(Optional.of(ELRole.builder()
				.id(id)
				.name("ADMIN")
				.description("hau test")
				.build()));
		when(roleRepository.existsByName(request.getName())).thenReturn(true);

		assertThrows(BusinessException.class,() -> service.update(id.toString(),request));
	}
	@Test
	public void whenCreateRoleSuccessfully_thenReturnRoleResponseDTO(){
		RoleDTO rq = new RoleDTO("name", "description");

		assertDoesNotThrow( () ->service.createRole(rq));
	}

	@Test
	public void whenDeleteRoleSuccessfully_DoesNotThrowException(){
		UUID id = UUID.randomUUID();
		ELRole role = ELRole.builder()
				.id(id)
				.name("LEADER")
				.description("hau test")
				.build();
		when(roleRepository.findById(UUID.fromString(id.toString()))).thenReturn(Optional.of(role));
		
		assertDoesNotThrow( () ->service.deleteById(id.toString()));
	}




}
