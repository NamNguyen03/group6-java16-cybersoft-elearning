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
import com.group6.java16.cybersoft.role.dto.ProgramDTO;
import com.group6.java16.cybersoft.role.dto.ProgramResponseDTO;
import com.group6.java16.cybersoft.role.dto.ProgramUpdateDTO;
import com.group6.java16.cybersoft.role.service.ProgramService;
import com.group6.java16.cybersoft.security.authorization.ELPermission;

@RestController
@RequestMapping("api/v1/programs")
@CrossOrigin(origins = "*")
public class ProgramController {

	@Autowired
	private ProgramService service;
	
    @ELPermission("search program")
	@GetMapping
	public Object searchProgram(@RequestParam(value = "pageCurrent", defaultValue = "1") int pageCurrent,
			@RequestParam(value = "itemPerPage", defaultValue = "10") int itemPerPage,
			@RequestParam(value = "fieldNameSort", required = false) String fieldNameSort,
			@RequestParam(value = "isIncrementSort", defaultValue = "true") boolean isIncrementSort,
			@RequestParam(value = "fieldNameSearch", required = false) String fieldNameSearch,
			@RequestParam(value = "valueFieldNameSearch", required = false) String valueFieldNameSearch
			){

		PageResponseModel<ProgramResponseDTO> rp = service.search(new PageRequestModel(
				pageCurrent,
				itemPerPage,
				fieldNameSort,
				isIncrementSort,
				fieldNameSearch,
				valueFieldNameSearch
				));

		return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
	}
    @ELPermission("create program")
	@PostMapping
	public Object createProgram(@Valid @RequestBody ProgramDTO program, BindingResult result){
		if(result.hasErrors()) {
			return ResponseHelper.getResponse(result, HttpStatus.BAD_REQUEST, true);
		}

		ProgramResponseDTO response  = service.save(program);

		return ResponseHelper.getResponse(response, HttpStatus.OK, false);
	}

    @ELPermission("delete program")
	@DeleteMapping("{id}")
	public Object deleteProgram(@PathVariable("id") String id) {
		service.deleteById(id);
		return ResponseHelper.getResponse("Delete successfully", HttpStatus.OK, false);
	}
    
    @ELPermission("update program")
	@PutMapping("{id}")
	public Object updateProgram(@PathVariable("id") String id,@RequestBody ProgramUpdateDTO dto) {
		ProgramResponseDTO response = service.update(id,dto);
		return ResponseHelper.getResponse(response,HttpStatus.OK,false);
	}

}
