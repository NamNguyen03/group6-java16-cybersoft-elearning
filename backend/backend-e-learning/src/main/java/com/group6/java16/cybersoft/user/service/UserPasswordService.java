package com.group6.java16.cybersoft.user.service;

import com.group6.java16.cybersoft.user.dto.UpdatePasswordDTO;

public interface UserPasswordService {

	void generateToken();

	void updatePassword(UpdatePasswordDTO rq);
    
}
