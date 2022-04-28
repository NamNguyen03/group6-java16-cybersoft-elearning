package com.group6.java16.cybersoft.user.service;

import java.util.stream.Collectors;

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.common.util.UserPrincipal;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;
import com.group6.java16.cybersoft.user.mapper.UserMapper;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserInformationServiceImpl implements UserInformationService{

    @Autowired
    private ELUserRepository repository;

    @Override
    public PageResponseModel<UserResponseDTO> search(PageRequestModel pageRequestModel) {
        int page = pageRequestModel.getPageCurrent()-1;
        int size = pageRequestModel.getItemPerPage();
        boolean isAscending = pageRequestModel.isIncrementSort();
        String fieldNameSearch = pageRequestModel.getFieldNameSearch();
        String fieldNameSort = pageRequestModel.getFieldNameSort();
        String valueSearch = pageRequestModel.getValueSearch();
        Pageable pageable = PageRequest.of(page, size);
        Page<ELUser> rp = null;

        if (null != fieldNameSort && fieldNameSort.matches("username|displayName|email|firstName|lastName|major|department|status")) {
            pageable = PageRequest.of(page, size, isAscending ? Sort.by(fieldNameSort).ascending() : Sort.by(fieldNameSort).descending());

        }

        //username
        if("username".equals(fieldNameSearch)){
            rp =  repository.searchByUsername(valueSearch, pageable);
        }

        //display name
        if("displayName".equals(fieldNameSearch)){
            rp =  repository.searchByDisplayName(valueSearch, pageable);
        }

        //email
        if("email".equals(fieldNameSearch)){
            rp =  repository.searchByEmail(valueSearch, pageable);
        }

        //first name
        if("firstName".equals(fieldNameSearch)){
            rp =  repository.searchByFirstName(valueSearch, pageable);
        }

        //last name
        if("lastName".equals(fieldNameSearch)){
            rp =  repository.searchByLastName(valueSearch, pageable);
        }

        //if firstName not existed then search all
        if(rp == null ){
            rp = repository.findAll(pageable);
        }

        return new PageResponseModel<>(rp.getNumber() + 1, rp.getTotalPages(), 
            rp.getContent().stream().map(UserMapper.INSTANCE::toUserResponseDTO).collect(Collectors.toList()));
    }
    
    @Override
    public UserResponseDTO getMyProfile() {
        String usernameCurrent = UserPrincipal.getUsernameCurrent();

        return UserMapper.INSTANCE.toUserResponseDTO( repository.findByUsername(usernameCurrent).get());
    }
}
