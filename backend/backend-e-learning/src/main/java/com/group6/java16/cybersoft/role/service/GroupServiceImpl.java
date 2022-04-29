package com.group6.java16.cybersoft.role.service;

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
import com.group6.java16.cybersoft.common.util.ServiceHelper;
import com.group6.java16.cybersoft.role.dto.GroupDTO;
import com.group6.java16.cybersoft.role.dto.GroupResponseDTO;
import com.group6.java16.cybersoft.role.mapper.GroupMapper;
import com.group6.java16.cybersoft.role.model.ELGroup;
import com.group6.java16.cybersoft.role.model.ELRole;
import com.group6.java16.cybersoft.role.repository.ELGroupRepository;
import com.group6.java16.cybersoft.role.repository.ELRoleRepository;

@Service
@PropertySources({ @PropertySource("classpath:/validation/message.properties") })

public class GroupServiceImpl implements GroupService {

	@Autowired
	private ELGroupRepository groupRepository;

	@Autowired
	private ELRoleRepository roleRepository;

	@Autowired
	private ServiceHelper<ELGroup> groupServiceHelper;

	@Autowired
	private ServiceHelper<ELRole> roleServiceHelper;

	@Value("${group.id.not-found}")
	private String messagesGroupIdNotFound;

	@Value("${role.id.not-found}")
	private String messagesRoleIdNotFound;

	@Value("${group.name.existed}")
	private String messagesGroupExistedName;

	@Override
	public PageResponseModel<GroupResponseDTO> search(PageRequestModel pageRequestModel) {

		int page = pageRequestModel.getPageCurrent() - 1;
		int size = pageRequestModel.getItemPerPage();
		boolean isAscending = pageRequestModel.isIncrementSort();
		String fieldNameSort = pageRequestModel.getFieldNameSort();
		String fieldNameSearch = pageRequestModel.getFieldNameSearch();
		String valueSearch = pageRequestModel.getValueSearch();
		Pageable pageable = PageRequest.of(page, size);
		Page<ELGroup> response = null;

		if (null != fieldNameSort && fieldNameSort.matches("name|createdAt|createdBy")) {
			pageable = PageRequest.of(page, size,
					isAscending ? Sort.by(fieldNameSort).ascending() : Sort.by(fieldNameSort).descending());

		}

		if ("name".equals(fieldNameSearch)) {
			response = groupRepository.searchByName(valueSearch, pageable);
		}

		if (response == null) {
			response = groupRepository.findAll(pageable);
		}

		return new PageResponseModel<>(response.getNumber() + 1, response.getTotalPages(),
				response.getContent().stream().map(GroupMapper.INSTANCE::toGroupResponseDTO)
						.collect(Collectors.toList()));
	}

	@Override
	public void deleteById(String id) {
		ELGroup group = groupServiceHelper.getEntityById(id, groupRepository, messagesGroupIdNotFound);
		groupRepository.delete(group);
	}

	@Override
	public GroupResponseDTO addRole(String groupId, String roleId) {
		ELGroup group = groupServiceHelper.getEntityById(groupId, groupRepository, messagesGroupIdNotFound);
		ELRole role = roleServiceHelper.getEntityById(roleId, roleRepository, messagesRoleIdNotFound);

		group.addRole(role);
		ELGroup modifiedGroup = groupRepository.save(group);

		return GroupMapper.INSTANCE.toGroupResponseDTO(modifiedGroup);
	}

	@Override
	public GroupResponseDTO save(GroupDTO dto) {

		return GroupMapper.INSTANCE.toGroupResponseDTO(groupRepository.save(GroupMapper.INSTANCE.toModel(dto)));
		
	}

	@Override
	public GroupResponseDTO update(String id, GroupDTO dto) {
		ELGroup group = groupServiceHelper.getEntityById(id, groupRepository, messagesGroupIdNotFound);

		if (groupServiceHelper.isValidString(dto.getName()) && !group.getName().equals(dto.getName())) {

			if(groupRepository.existsByName(dto.getName())){
				throw new BusinessException(messagesGroupExistedName);	
			}
			
			group.setName(dto.getName());
		}
		if (groupServiceHelper.isValidString(dto.getDescription())) {
			group.setDescription(dto.getDescription());
		}

		return GroupMapper.INSTANCE.toGroupResponseDTO(groupRepository.save(group));
	}

}
