package com.group6.java16.cybersoft.feedback.controller;

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.common.util.ResponseHelper;
import com.group6.java16.cybersoft.feedback.dto.StatusCommentDTO;
import com.group6.java16.cybersoft.feedback.dto.StatusCommentResponseDTO;
import com.group6.java16.cybersoft.feedback.model.EnumStatusComment;
import com.group6.java16.cybersoft.feedback.service.StatusCommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("api/v1/status-comments")
@CrossOrigin(origins = "*")
public class StatusCommentController {

    @Autowired
    private StatusCommentService service;
    
    @GetMapping
    public Object searchStatusComments(
        @RequestParam(value = "pageCurrent", defaultValue = "1") int pageCurrent,
        @RequestParam(value = "itemPerPage", defaultValue = "10") int itemPerPage,
        @RequestParam(value = "fieldNameSort", required = false) String fieldNameSort,
        @RequestParam(value = "isIncrementSort", defaultValue = "true") boolean isIncrementSort,
        @RequestParam(value = "fieldNameSearch", required = false) String fieldNameSearch,
        @RequestParam(value = "valueFieldNameSearch", required = false) String valueFieldNameSearch
        ){
        
        PageResponseModel<StatusCommentResponseDTO> rp = service.search(new PageRequestModel(
			pageCurrent,
			itemPerPage,
			fieldNameSort,
			isIncrementSort,
			fieldNameSearch,
			valueFieldNameSearch
		));

        return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
    }

    @PutMapping("{id}")
    public Object updateStatusComment(@RequestBody EnumStatusComment status, @PathVariable("id") String id){
        StatusCommentResponseDTO rp = service.updateStatusComment(id, status); 

        return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
    }

    @PostMapping()
    public Object createStatusComment(@RequestBody StatusCommentDTO rq){
        StatusCommentResponseDTO rp = service.createStatusComment(rq);

        return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
    }
}
