package com.group6.java16.cybersoft.common.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface MyFirebaseService {
    public String saveFile(MultipartFile file);
    
}
