package com.group6.java16.cybersoft.role.service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.common.util.ServiceHelper;
import com.group6.java16.cybersoft.role.dto.RoleDTO;
import com.group6.java16.cybersoft.role.dto.RoleResponseDTO;
import com.group6.java16.cybersoft.role.dto.RoleUpdateDTO;
import com.group6.java16.cybersoft.role.mapper.RoleMapper;
import com.group6.java16.cybersoft.role.model.ELProgram;
import com.group6.java16.cybersoft.role.model.ELRole;
import com.group6.java16.cybersoft.role.repository.ELProgramRepository;
import com.group6.java16.cybersoft.role.repository.ELRoleRepository;

@Service
@PropertySources({ @PropertySource("classpath:/validation/message.properties") })
public class RoleServiceImpl extends ServiceHelper<ELRole> implements RoleService {

	@Autowired
	private ELRoleRepository roleRepository;

	@Autowired
	private ELProgramRepository programRepository;

	@Value("${entity.id.invalid}")
	private String errorsIdInvalid;

	@Value("${role.not-found}")
	private String errorsRoleNotFound;

	@Value("${role.name.existed}")
	private String messagesRoleNameExisted;

	@Override
	public RoleResponseDTO createRole(RoleDTO dto) {

		return RoleMapper.INSTANCE.toResponseDTO(roleRepository.save(RoleMapper.INSTANCE.toModel(dto)));
	}

	@Override
	public PageResponseModel<RoleResponseDTO> search(PageRequestModel pageRequestModel) {
		int page = pageRequestModel.getPageCurrent() - 1;
		int size = pageRequestModel.getItemPerPage();
		boolean isAscending = pageRequestModel.isIncrementSort();
		String fieldNameSearch = pageRequestModel.getFieldNameSearch();
		String fieldNameSort = pageRequestModel.getFieldNameSort();
		String valueSearch = pageRequestModel.getValueSearch();
		Pageable pageable = PageRequest.of(page, size);
		Page<ELRole> rp = null;

		if (null != fieldNameSort && fieldNameSort.matches("name")) {
			pageable = PageRequest.of(page, size,
					isAscending ? Sort.by(fieldNameSort).ascending() : Sort.by(fieldNameSort).descending());
		} else {
			pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
		}

		if ("name".equals(fieldNameSearch)) {
			rp = roleRepository.searchByName(valueSearch, pageable);
		}

		if (rp == null) {
			rp = roleRepository.findAll(pageable);
		}

		return new PageResponseModel<>(rp.getNumber() + 1, rp.getTotalPages(),
				rp.getContent().stream().map(RoleMapper.INSTANCE::toResponseDTO).collect(Collectors.toList()));

	}

	@Override
	public RoleResponseDTO update(String id, RoleUpdateDTO dto) {
		ELRole role = getById(id);

		if (isValidString(dto.getName()) && !role.getName().equals(dto.getName())) {

			if (roleRepository.existsByName(dto.getName())) {
				throw new BusinessException(messagesRoleNameExisted);
			}

			role.setName(dto.getName());
		}

		if (isValidString(dto.getDescription())) {
			role.setDescription(dto.getDescription());
		}

		return RoleMapper.INSTANCE.toResponseDTO(roleRepository.save(role));

	}

	@Override
	public void deleteById(String id) {
		ELRole role = getById(id);
		roleRepository.delete(role);
	}

	@Override
	protected String getMessageIdInvalid() {
		return errorsIdInvalid;
	}

	@Override
	protected JpaRepository<ELRole, UUID> getRepository() {
		return roleRepository;
	}

	@Override
	protected String getErrorNotFound() {
		return errorsRoleNotFound;
	}

	@Override
	public RoleResponseDTO addProgram(String roleId, String programId) {
		ELRole role = getById(roleId);
		ELProgram program = getProgramById(programId);

		role.addProgram(program);
		return RoleMapper.INSTANCE.toResponseDTO(roleRepository.save(role));

	}

	@Override
	public RoleResponseDTO deleteProgram(String roleId, String programId) {
		ELRole role = getById(roleId);
		ELProgram program = getProgramById(programId);

		role.removeProgram(program);
		return RoleMapper.INSTANCE.toResponseDTO(roleRepository.save(role));
	}

	private ELProgram getProgramById(String id) {
		UUID uuid;
		try {
			uuid = UUID.fromString(id);
		} catch (Exception e) {
			throw new BusinessException(getMessageIdInvalid());
		}

		Optional<ELProgram> entityOpt = programRepository.findById(uuid);

		if (entityOpt.isEmpty()) {
			throw new BusinessException(errorsRoleNotFound);
		}
		return entityOpt.get();
	}

	@Override
	public RoleResponseDTO getRoleDetail(String id) {
		return RoleMapper.INSTANCE.toResponseDTO(getById(id));
	}

}
