package com.group6.java16.cybersoft.common.service.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
@PropertySources({ @PropertySource("classpath:application-config.properties") })
public class EmailConfiguration {
    @Value("${notification.google.gmail.email}")
    @Getter
    private String email;
    @Value("${notification.google.gmail.password}")
    @Getter
    private String password;

}
