package com.group6.java16.cybersoft.security.controller;

import javax.validation.Valid;

import com.group6.java16.cybersoft.common.util.ResponseHelper;
import com.group6.java16.cybersoft.security.authorization.ELPermission;
import com.group6.java16.cybersoft.security.dto.LoginRequestDTO;
import com.group6.java16.cybersoft.security.dto.RegisterDTO;
import com.group6.java16.cybersoft.security.service.AuthService;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/auth")
@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService service;

    @ELPermission("login")
    @PostMapping("login")
    public Object login(@RequestBody @Valid LoginRequestDTO rq, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseHelper.getResponse(result, HttpStatus.BAD_REQUEST, true);
        }

        String rp = service.login(rq);

        return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
    }

    @ELPermission("register")
    @PostMapping("register")
    public Object register(@RequestBody @Valid RegisterDTO rq, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseHelper.getResponse(result, HttpStatus.BAD_REQUEST, true);
        }

        UserResponseDTO rp = service.register(rq);

        return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
    }
}
