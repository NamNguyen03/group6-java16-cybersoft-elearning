package com.group6.java16.cybersoft.common.model.notification;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResetPasswordModel {
    private String username;
    private String token;
    private String urlFrontend;
}
