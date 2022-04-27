package com.group6.java16.cybersoft.role.service;


import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.role.dto.RoleDTO;
import com.group6.java16.cybersoft.role.dto.RoleResponseDTO;

public interface RoleService {

	RoleResponseDTO createRole( RoleDTO dto);

	PageResponseModel<RoleResponseDTO> search(PageRequestModel pageRequestModel);

	RoleResponseDTO update(String id, RoleDTO dto);

	void deleteById(String id);

}
