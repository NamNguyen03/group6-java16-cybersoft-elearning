package com.group6.java16.cybersoft.feedback.dto.reponse.status;

import java.util.UUID;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    private String displayName;
}
