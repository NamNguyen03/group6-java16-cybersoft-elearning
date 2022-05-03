package com.group6.java16.cybersoft.user.dto;


import com.group6.java16.cybersoft.user.model.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class UpdateUserDTO {
    private String email;
    private String department;
    private String major;
    private UserStatus status;
}
