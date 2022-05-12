package com.group6.java16.cybersoft.security.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequestDTO {
    @NotFoundUsername(message ="{user.username.not-existed}")
    @NotBlank(message = "{user.username.not-blank}")
    private String username;
    @Size(min = 6, max = 64 , message ="{user.password.size}")
    @NotBlank(message = "{user.password.not-blank}")
    private String password;
}
