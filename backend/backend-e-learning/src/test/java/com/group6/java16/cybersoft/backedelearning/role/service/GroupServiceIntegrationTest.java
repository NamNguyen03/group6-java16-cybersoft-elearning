package com.group6.java16.cybersoft.backedelearning.role.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashSet;
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
import com.group6.java16.cybersoft.role.dto.GroupDTO;
import com.group6.java16.cybersoft.role.dto.GroupResponseDTO;
import com.group6.java16.cybersoft.role.dto.GroupUpdateDTO;
import com.group6.java16.cybersoft.role.mapper.GroupMapper;
import com.group6.java16.cybersoft.role.model.ELGroup;
import com.group6.java16.cybersoft.role.model.ELRole;
import com.group6.java16.cybersoft.role.repository.ELGroupRepository;
import com.group6.java16.cybersoft.role.repository.ELRoleRepository;
import com.group6.java16.cybersoft.role.service.GroupService;
import com.group6.java16.cybersoft.role.service.GroupServiceImpl;

@SpringBootTest
public class GroupServiceIntegrationTest {

	@Mock
	private ELGroupRepository groupRepository;
	@Mock
	private ELRoleRepository roleRepository;

	@Mock
	private GroupMapper mapper;

	@InjectMocks
	private GroupService service = new GroupServiceImpl();

	@Test
	public void whenExistsGroupIsUsedToSearchAll_thenReturnPageResponseGroup() {
		PageRequestModel request = new PageRequestModel(1, 10, null, true, null, null);

		Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());

		Page<ELGroup> page = new PageImpl<ELGroup>(new ArrayList<ELGroup>(), pageable, 10l);

		when(groupRepository.findAll(pageable)).thenReturn(page);

		PageResponseModel<GroupResponseDTO> expected = new PageResponseModel<GroupResponseDTO>(1, 1,
				new ArrayList<GroupResponseDTO>());

		PageResponseModel<GroupResponseDTO> response = service.search(request);

		assertEquals(expected.getItems(), response.getItems());
		assertEquals(expected.getTotalPage(), response.getTotalPage());
		assertEquals(expected.getPageCurrent(), response.getPageCurrent());

	}

	@Test
	public void whenExistsGroupIsUsedToSearchByName_thenReturnPageResponseGroup() {
		PageRequestModel request = new PageRequestModel(1, 10, null, true, "name", "value");

		Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());

		Page<ELGroup> page = new PageImpl<ELGroup>(new ArrayList<ELGroup>(), pageable, 10l);

		when(groupRepository.searchByName("value", pageable)).thenReturn(page);

		PageResponseModel<GroupResponseDTO> expected = new PageResponseModel<GroupResponseDTO>(1, 1,
				new ArrayList<GroupResponseDTO>());

		PageResponseModel<GroupResponseDTO> response = service.search(request);

		assertEquals(expected.getItems(), response.getItems());
		assertEquals(expected.getTotalPage(), response.getTotalPage());
		assertEquals(expected.getPageCurrent(), response.getPageCurrent());

	}

	@Test
	public void whenExistsGroupIsUsedToSearchAllAndSearch_thenReturnPageResponseGroup() {
		PageRequestModel request = new PageRequestModel(1, 10, "name", true, null, null);

		Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

		Page<ELGroup> page = new PageImpl<ELGroup>(new ArrayList<ELGroup>(), pageable, 101);

		when(groupRepository.findAll(pageable)).thenReturn(page);

		PageResponseModel<GroupResponseDTO> expected = new PageResponseModel<GroupResponseDTO>(1, 11,
				new ArrayList<GroupResponseDTO>());

		PageResponseModel<GroupResponseDTO> response = service.search(request);

		assertEquals(expected.getItems(), response.getItems());
		assertEquals(expected.getTotalPage(), response.getTotalPage());
		assertEquals(expected.getPageCurrent(), response.getPageCurrent());

	}

	@Test
	public void whenUpdateGroupSuccessfully_thenReturnGroupResponse() {
		UUID id = UUID.randomUUID();
		ELGroup group = ELGroup.builder()
				.id(id)
				.name("ADMIN")
				.description("hau test")
				.build();

		when(groupRepository.findById(UUID.fromString(id.toString()))).thenReturn(Optional.of(group));

		when(groupRepository.existsByName("LEADER")).thenReturn(false);
		GroupUpdateDTO request = GroupUpdateDTO.builder()
				.name("LEADER")
				.description("hau test")
				.build();

		when(groupRepository.save(group)).thenReturn(group);

		GroupResponseDTO expected = GroupResponseDTO.builder()
				.id(id)
				.name("LEADER")
				.description("hau test")
				.roles(new LinkedHashSet<>())
				.build();

		when(mapper.toGroupResponseDTO(group)).thenReturn(expected);

		assertEquals(expected, service.update(id.toString(), request));

	}

	@Test
	public void whenUpdateGroupExistsName_thenThrowBusinessException() {
		UUID id = UUID.randomUUID();

		GroupUpdateDTO request = GroupUpdateDTO.builder()
				.name("LEADER").build();
		when(groupRepository.findById(UUID.fromString(id.toString()))).thenReturn(Optional.of(ELGroup.builder()
				.id(id)
				.name("ADMIN")
				.description("hau test")
				.build()));
		when(groupRepository.existsByName(request.getName())).thenReturn(true);

		assertThrows(BusinessException.class, () -> service.update(id.toString(), request));
	}

	@Test
	public void whenCreateGroupSuccessfully_thenReturnRoleResponseDTO() {
		GroupDTO rq = new GroupDTO("name", "description");

		assertDoesNotThrow(() -> service.save(rq));
	}

	@Test
	public void whenDeleteGroupSuccessfully_DoesNotThrowException() {
		UUID id = UUID.randomUUID();
		ELGroup group = ELGroup.builder()
				.id(id)
				.name("LEADER")
				.description("hau test")
				.build();
		when(groupRepository.findById(UUID.fromString(id.toString()))).thenReturn(Optional.of(group));

		assertDoesNotThrow(() -> service.deleteById(id.toString()));
	}

	@Test
	public void whenAddRoleSuccessfully_DoesNotThrowException() {
		UUID groupId = UUID.randomUUID();
		ELGroup group = ELGroup.builder()
				.id(groupId)
				.name("ADMIN")
				.description("hau test")
				.build();
		UUID roleId = UUID.randomUUID();
		ELRole role = ELRole.builder()
				.id(roleId)
				.name("LEADER")
				.description("hau test")
				.build();
		when(groupRepository.findById(UUID.fromString(groupId.toString()))).thenReturn(Optional.of(group));

		when(roleRepository.findById(UUID.fromString(roleId.toString()))).thenReturn(Optional.of(role));
		assertDoesNotThrow(() -> service.addRole(groupId.toString(), roleId.toString()));

	}

	@Test
	public void whenAddRoleAndRoleIdInvalid_ThrowException() {
		UUID groupId = UUID.randomUUID();
		ELGroup group = ELGroup.builder()
				.id(groupId)
				.name("ADMIN")
				.description("hau test")
				.build();

		when(groupRepository.findById(UUID.fromString(groupId.toString()))).thenReturn(Optional.of(group));
		assertThrows(BusinessException.class, () -> service.addRole(groupId.toString(), "1234"));
		assertThrows(BusinessException.class, () -> service.addRole(groupId.toString(), groupId.toString()));

	}

}
