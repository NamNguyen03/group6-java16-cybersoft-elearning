package com.group6.java16.cybersoft.role.service;


import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.role.dto.ProgramDTO;
import com.group6.java16.cybersoft.role.dto.ProgramResponseDTO;
import com.group6.java16.cybersoft.role.dto.ProgramUpdateDTO;

public interface ProgramService {

	ProgramResponseDTO save(ProgramDTO program);

	PageResponseModel<ProgramResponseDTO> search(PageRequestModel pageRequestModel);

	void deleteById(String id);

	ProgramResponseDTO update(String id, ProgramUpdateDTO dto);

}
