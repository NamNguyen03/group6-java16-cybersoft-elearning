package com.group6.java16.cybersoft.user.controller;

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.common.util.ResponseHelper;
import com.group6.java16.cybersoft.security.authorization.ELPermission;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;
import com.group6.java16.cybersoft.user.service.UserInformationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "*")
public class UserInformationController {
    
    @Autowired 
    private UserInformationService service;

    @ELPermission("searchUser")
    @GetMapping
    public Object searchUser(
        @RequestParam(value = "pageCurrent", defaultValue = "1") int pageCurrent,
        @RequestParam(value = "itemPerPage", defaultValue = "10") int itemPerPage,
        @RequestParam(value = "fieldNameSort", required = false) String fieldNameSort,
        @RequestParam(value = "isIncrementSort", defaultValue = "true") boolean isIncrementSort,
        @RequestParam(value = "fieldNameSearch", required = false) String fieldNameSearch,
        @RequestParam(value = "valueFieldNameSearch", required = false) String valueFieldNameSearch
        ){
        
        PageResponseModel<UserResponseDTO> rp = service.search(new PageRequestModel(
			pageCurrent,
			itemPerPage,
			fieldNameSort,
			isIncrementSort,
			fieldNameSearch,
			valueFieldNameSearch
		));

        return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
    }

    @GetMapping("me")
    public Object getMyProfile(){

        UserResponseDTO rp = service.getMyProfile();

        return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
    }

        
    @GetMapping("{id}")
    public Object getProfile(@PathVariable("id")String id){

        UserResponseDTO rp = service.getProfile(id);

        return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
    }
}
