package com.group6.java16.cybersoft.user.controller;

import javax.validation.Valid;

import com.group6.java16.cybersoft.common.util.ResponseHelper;
import com.group6.java16.cybersoft.user.dto.UpdatePasswordDTO;
import com.group6.java16.cybersoft.user.service.UserPasswordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users/password")
public class UserPasswordController {
    @Autowired
    private UserPasswordService service;

    @PostMapping("token/me")
    public Object generateToken() {
        service.generateToken();
        return ResponseHelper.getResponse("Generate token reset password success", HttpStatus.OK, false);
    }

    @PutMapping()
    public Object updatePassword(@Valid @RequestBody UpdatePasswordDTO rq) {
        service.updatePassword(rq);
        return ResponseHelper.getResponse("Update password success", HttpStatus.OK, false);
    }
}
