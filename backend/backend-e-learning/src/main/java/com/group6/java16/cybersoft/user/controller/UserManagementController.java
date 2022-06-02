package com.group6.java16.cybersoft.user.controller;

import javax.validation.Valid;

import com.group6.java16.cybersoft.common.util.ResponseHelper;
import com.group6.java16.cybersoft.security.authorization.ELPermission;
import com.group6.java16.cybersoft.user.dto.UpdateMyProfileDTO;
import com.group6.java16.cybersoft.user.dto.UpdateUserDTO;
import com.group6.java16.cybersoft.user.dto.UserCreateDTO;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;
import com.group6.java16.cybersoft.user.dto.client.InstructorCourseClientDTO;
import com.group6.java16.cybersoft.user.service.UserManagementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "*")
public class UserManagementController {

    @Autowired
    private UserManagementService service;
    
    @ELPermission("create user")
    @PostMapping
    public Object createUser(@Valid @RequestBody UserCreateDTO rq, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseHelper.getResponse(result, HttpStatus.BAD_REQUEST, true);
        }

        UserResponseDTO rp = service.createUser(rq);

        return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
    }
    @ELPermission("delete user")
    @DeleteMapping("{id}")
    public Object deleteUser(@PathVariable("id") String id) {
        service.deleteUser(id);
        return ResponseHelper.getResponse("Delete user success", HttpStatus.OK, false);
    }
    
    @ELPermission("update my profile")
    @PutMapping("me")
    public Object updateMyProfile(@RequestBody UpdateMyProfileDTO rq) {
        UserResponseDTO rp = service.updateMyProfile(rq);

        return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
    }
    @ELPermission("update user")
    @PutMapping("{id}")
    public Object updateUser(@RequestBody UpdateUserDTO rq, @PathVariable("id") String id) {
        UserResponseDTO rp = service.update(id, rq);
        
        return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
    }

    @ELPermission("update my avatar")
    @PostMapping("me/avatar")
    public Object updateMyAvatar(@RequestParam(name = "file") MultipartFile file) {
        UserResponseDTO rp = service.updateMyAvatar(file);
        
        return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
    }
    @ELPermission("add group into user")
    @PostMapping("{user-id}/{group-id}")
    public Object addGroupIntoUser(@PathVariable("user-id") String userId,@PathVariable("group-id")String groupId) {
    	UserResponseDTO rp = service.addGroup(userId,groupId) ;   	
    	
    	return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
    }
    @ELPermission("delete group into user")
    @DeleteMapping("{user-id}/{group-id}")
    public Object deleteGroupIntoUser(@PathVariable("user-id") String userId,@PathVariable("group-id")String groupId) {
    	UserResponseDTO rp = service.deleteGroup(userId,groupId) ;   	
    	
    	return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
    }
    
//    @GetMapping("client/{createBy}")
//	public Object getInstructorCourse(@PathVariable("createBy") String createBy) {
//
//		InstructorCourseClientDTO rp = service.getInstructorCourse(createBy);
//
//		return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
//	}
    
    
}
