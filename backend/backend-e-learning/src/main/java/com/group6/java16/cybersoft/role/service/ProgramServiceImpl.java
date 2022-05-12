package com.group6.java16.cybersoft.role.service;

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
import org.springframework.stereotype.Service;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.role.dto.ProgramDTO;
import com.group6.java16.cybersoft.role.dto.ProgramResponseDTO;
import com.group6.java16.cybersoft.role.dto.ProgramUpdateDTO;
import com.group6.java16.cybersoft.role.mapper.ProgramMapper;
import com.group6.java16.cybersoft.role.model.ELProgram;
import com.group6.java16.cybersoft.role.repository.ELProgramRepository;

@Service
@PropertySources({ @PropertySource("classpath:/validation/message.properties") })
public class ProgramServiceImpl implements ProgramService {

	@Autowired
	private ELProgramRepository programRepository;

	@Value("${entity.id.invalid}")
	private String errorsIdInvalid;

	@Value("${program.not-found}")
	private String messagesProgramNotFound;

	@Value("${program.name.existed}")
	private String messagesProgramNameExisted;

	@Override
	public ProgramResponseDTO save(ProgramDTO dto) {
		ELProgram program = programRepository.save(ProgramMapper.INSTANCE.toModel(dto));

		return ProgramMapper.INSTANCE.toResponseDTO(program);
	}

	@Override
	public PageResponseModel<ProgramResponseDTO> search(PageRequestModel pageRequestModel) {
		int page = pageRequestModel.getPageCurrent() - 1;
		int size = pageRequestModel.getItemPerPage();
		boolean isAscending = pageRequestModel.isIncrementSort();
		String fieldNameSearch = pageRequestModel.getFieldNameSearch();
		String fieldNameSort = pageRequestModel.getFieldNameSort();
		String valueSearch = pageRequestModel.getValueSearch();
		Pageable pageable = PageRequest.of(page, size);
		Page<ELProgram> rp = null;

		if (null != fieldNameSort && fieldNameSort.matches("name|module|type")) {
			pageable = PageRequest.of(page, size,
					isAscending ? Sort.by(fieldNameSort).ascending() : Sort.by(fieldNameSort).descending());
		} else {
			pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
		}

		if ("name".equals(fieldNameSearch)) {
			rp = programRepository.searchByName(valueSearch, pageable);
		}
		if ("module".equals(fieldNameSearch)) {
			rp = programRepository.searchByModule(valueSearch, pageable);
		}
		if ("type".equals(fieldNameSearch)) {
			rp = programRepository.searchByType(valueSearch, pageable);
		}

		if (rp == null) {
			rp = programRepository.findAll(pageable);
		}

		return new PageResponseModel<>(rp.getNumber() + 1, rp.getTotalPages(),
				rp.getContent().stream().map(ProgramMapper.INSTANCE::toResponseDTO).collect(Collectors.toList()));

	}

	@Override
	public ProgramResponseDTO update(String id, ProgramUpdateDTO dto) {
		ELProgram program = getById(id);

		if (isValidString(dto.getName()) && !program.getName().equals(dto.getName())) {
			if (programRepository.existsByName(dto.getName())) {
				throw new BusinessException(messagesProgramNameExisted);
			}
			program.setName(dto.getName());
		}
		if (isValidString(dto.getDescription())) {
			program.setDescription(dto.getDescription());
		}
		if (dto.getModule() != null) {

			program.setModule(dto.getModule());
		}
		if (dto.getType() != null) {
			program.setType(dto.getType());
		}
		return ProgramMapper.INSTANCE.toResponseDTO(programRepository.save(program));
	}

	@Override
	public void deleteById(String id) {
		ELProgram program = getById(id);
		programRepository.delete(program);

	}

	private ELProgram getById(String id) {
		return programRepository.findById(UUID.fromString(id))
				.orElseThrow(() -> new BusinessException(messagesProgramNotFound));
	}

	private boolean isValidString(String s) {
		if (s == null || s.length() == 0) {
			return false;
		}
		return true;
	}
}
