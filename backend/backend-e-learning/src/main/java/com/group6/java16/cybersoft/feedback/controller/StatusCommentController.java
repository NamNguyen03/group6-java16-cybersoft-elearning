package com.group6.java16.cybersoft.feedback.controller;

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.common.util.ResponseHelper;
import com.group6.java16.cybersoft.feedback.dto.StatusCommentDTO;
import com.group6.java16.cybersoft.feedback.dto.UpdateStatusCommentDTO;
import com.group6.java16.cybersoft.feedback.dto.reponse.status.StatusCommentResponseDTO;
import com.group6.java16.cybersoft.feedback.service.StatusCommentService;
import com.group6.java16.cybersoft.security.authorization.ELPermission;

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

    @ELPermission("search status comment")
    @GetMapping
    public Object searchStatusComments(
            @RequestParam(value = "pageCurrent", defaultValue = "1") int pageCurrent,
            @RequestParam(value = "itemPerPage", defaultValue = "10") int itemPerPage,
            @RequestParam(value = "fieldNameSort", required = false) String fieldNameSort,
            @RequestParam(value = "isIncrementSort", defaultValue = "true") boolean isIncrementSort,
            @RequestParam(value = "fieldNameSearch", required = false) String fieldNameSearch,
            @RequestParam(value = "valueFieldNameSearch", required = false) String valueFieldNameSearch) {

        PageResponseModel<StatusCommentResponseDTO> rp = service.search(new PageRequestModel(
                pageCurrent,
                itemPerPage,
                fieldNameSort,
                isIncrementSort,
                fieldNameSearch,
                valueFieldNameSearch));

        return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
    }

    @ELPermission("update status comment")
    @PutMapping("{id}")
    public Object updateStatusComment(@RequestBody UpdateStatusCommentDTO rq, @PathVariable("id") String id) {
        StatusCommentResponseDTO rp = service.updateStatusComment(id, rq.getStatus());

        return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
    }

    @ELPermission("create status comment")
    @PostMapping()
    public Object createStatusComment(@RequestBody StatusCommentDTO rq) {
        StatusCommentResponseDTO rp = service.createStatusComment(rq);

        return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
    }

    @ELPermission("get status comment details")
    @GetMapping("{id}")
    public Object getStatusCommentDetails(@PathVariable("id") String id) {
        StatusCommentResponseDTO rp = service.getById(id);
        return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
    }
}
