package com.group6.java16.cybersoft.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.group6.java16.cybersoft.user.validation.annotation.NotFoundUsername;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class UpdatePasswordDTO {
    @NotFoundUsername(message ="Username not existed.")
    private String username;
    
    @Size(min = 6, max = 64 , message ="user.password.size")
    @NotBlank(message = "{user.password.not-blank}")
    private String password;
    @NotBlank(message="{token.not-blank}")
    private String token;
}
