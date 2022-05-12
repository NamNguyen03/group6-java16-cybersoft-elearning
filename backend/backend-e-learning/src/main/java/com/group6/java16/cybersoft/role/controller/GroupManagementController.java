package com.group6.java16.cybersoft.role.controller;


import javax.validation.Valid;

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

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.common.util.ResponseHelper;
import com.group6.java16.cybersoft.role.dto.GroupDTO;
import com.group6.java16.cybersoft.role.dto.GroupResponseDTO;
import com.group6.java16.cybersoft.role.dto.GroupUpdateDTO;
import com.group6.java16.cybersoft.role.service.GroupService;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;




@RestController
@RequestMapping("api/v1/groups")
@CrossOrigin(origins = "*")
public class GroupManagementController {
	    @Autowired
	    private GroupService service;

	    
	    @GetMapping
	    public Object search(@RequestParam(value = "pageCurrent", defaultValue = "1") int pageCurrent,
	            @RequestParam(value = "itemPerPage", defaultValue = "10") int itemPerPage,
	            @RequestParam(value = "fieldNameSort", required = false) String fieldNameSort,
	            @RequestParam(value = "isIncrementSort", defaultValue = "true") boolean isIncrementSort,
	            @RequestParam(value = "fieldNameSearch", required = false) String fieldNameSearch,
	            @RequestParam(value = "valueFieldNameSearch", required = false) String valueFieldNameSearch) {

	        PageResponseModel<GroupResponseDTO> response = service.search(new PageRequestModel(
	                pageCurrent,
	                itemPerPage,
	                fieldNameSort,
	                isIncrementSort,
	                fieldNameSearch,
	                valueFieldNameSearch));

	        return ResponseHelper.getResponse(response, HttpStatus.OK, false);

	    }
	    
	    @PostMapping
		public Object createGroup(@RequestBody @Valid GroupDTO dto,BindingResult bindingResult) {
			if(bindingResult.hasErrors()) {
				return ResponseHelper.getResponse(bindingResult, HttpStatus.BAD_REQUEST, true);
			}
			
			GroupResponseDTO response = service.save(dto);
			
			return ResponseHelper.getResponse(response, HttpStatus.OK, false); 
			
		}
	    
	    @PutMapping("{id}")
	    public Object updateGroup(@PathVariable("id") String id,@RequestBody GroupUpdateDTO dto){
	       	GroupResponseDTO response = service.update(id,dto);
	        
	        return ResponseHelper.getResponse(response, HttpStatus.OK,false);
	    }
	    
	    @DeleteMapping("{id}")
	    public Object deleteGroup(@PathVariable("id") String id) {
	    	
	    	service.deleteById(id);
	    	return ResponseHelper.getResponse("Delete successfully", HttpStatus.OK, false);
	    }
	    
	    @PostMapping("{group-id}/{role-id}")
	    public Object addRoleIntoGroup(@PathVariable(value="group-id")String groupId,@PathVariable(value="role-id")String roleId) {
	    	GroupResponseDTO response = service.addRole(groupId,roleId);
	    	
	    	return ResponseHelper.getResponse(response, HttpStatus.OK,false);
	    	
	    }
	    @DeleteMapping("{group-id}/{role-id}")
		public Object deleteRoleIntoGroup(@PathVariable("group-id") String groupId,@PathVariable("role-id") String roleId) {
			GroupResponseDTO response = service.deleteRole(groupId,roleId);
			return ResponseHelper.getResponse(response, HttpStatus.OK, false);
		
		}
	    @GetMapping("{id}")
	    public Object getGroupDetail(@PathVariable("id")String id){

	        GroupResponseDTO rp = service.getGroupDetail(id);

	        return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
	    }
	   

	}

