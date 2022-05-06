package com.group6.java16.cybersoft.role.service;



import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.role.dto.GroupDTO;
import com.group6.java16.cybersoft.role.dto.GroupResponseDTO;

public interface GroupService {

	PageResponseModel<GroupResponseDTO> search(PageRequestModel pageRequestModel);

	GroupResponseDTO update(String id, GroupDTO dto);
	
	GroupResponseDTO save(GroupDTO dto);

	void deleteById(String id);

	GroupResponseDTO addRole(String groupId, String roleId);


}
