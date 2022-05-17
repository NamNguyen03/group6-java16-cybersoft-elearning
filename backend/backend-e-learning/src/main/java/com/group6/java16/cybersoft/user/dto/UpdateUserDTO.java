package com.group6.java16.cybersoft.user.dto;


import com.group6.java16.cybersoft.user.model.UserStatus;

import lombok.AllArgsConstructor;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateUserDTO {
    private String email;
    private String department;
    private String major;
    private UserStatus status;
}
