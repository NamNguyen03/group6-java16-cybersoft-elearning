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
import com.group6.java16.cybersoft.role.dto.RoleDTO;
import com.group6.java16.cybersoft.role.dto.RoleResponseDTO;
import com.group6.java16.cybersoft.role.dto.RoleUpdateDTO;
import com.group6.java16.cybersoft.role.service.RoleService;


@RestController
@RequestMapping("api/v1/roles")
@CrossOrigin(origins = "*")
public class RoleManagementController {

	@Autowired
	private RoleService service;

	@PostMapping
	public Object createRole(@Valid @RequestBody RoleDTO dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseHelper.getResponse(bindingResult, HttpStatus.BAD_REQUEST, true);
		}
		RoleResponseDTO response = service.createRole(dto);
		return ResponseHelper.getResponse(response, HttpStatus.OK, false);
	}

	@GetMapping
	public Object search(@RequestParam(value = "pageCurrent", defaultValue = "1") int pageCurrent,
			@RequestParam(value = "itemPerPage", defaultValue = "10") int itemPerPage,
			@RequestParam(value = "fieldNameSort", required = false) String fieldNameSort,
			@RequestParam(value = "isIncrementSort", defaultValue = "true") boolean isIncrementSort,
			@RequestParam(value = "fieldNameSearch", required = false) String fieldNameSearch,
			@RequestParam(value = "valueFieldNameSearch", required = false) String valueFieldNameSearch) {
		PageResponseModel<RoleResponseDTO> response = service.search(new PageRequestModel(
				pageCurrent,
				itemPerPage,
				fieldNameSort,
				isIncrementSort,
				fieldNameSearch,
				valueFieldNameSearch));
		return ResponseHelper.getResponse(response, HttpStatus.OK, false);
	}
	
	@PutMapping("{id}")
	public Object updateRole(@PathVariable("id") String id, @RequestBody RoleUpdateDTO dto) {
		
		RoleResponseDTO response = service.update(id,dto);
		
		return ResponseHelper.getResponse(response, HttpStatus.OK, false);
	}
	
	@DeleteMapping("{id}")
	public Object deleteRole(@PathVariable("id") String id) {
		service.deleteById(id);
    	return ResponseHelper.getResponse("Delete successfully", HttpStatus.OK, false);
	}
	@PostMapping("{role-id}/{program-id}")
	public Object addProgramIntoRole(@PathVariable("role-id") String roleId,@PathVariable("program-id") String programId) {
		RoleResponseDTO response = service.addProgram(roleId,programId);
		return ResponseHelper.getResponse(response, HttpStatus.OK, false);
		
	}
	
	@DeleteMapping("{role-id}/{program-id}")
	public Object deleteProgramIntoRole(@PathVariable("role-id") String roleId,@PathVariable("program-id") String programId) {
		RoleResponseDTO response = service.deleteProgram(roleId,programId);
		return ResponseHelper.getResponse(response, HttpStatus.OK, false);
	
	}
}
