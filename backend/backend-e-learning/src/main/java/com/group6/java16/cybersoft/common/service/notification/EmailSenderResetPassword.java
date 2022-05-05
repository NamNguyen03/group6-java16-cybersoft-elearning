package com.group6.java16.cybersoft.common.service.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Component("emailSenderResetPassword") 
@PropertySources({ @PropertySource("classpath:application-config.properties") })
public class EmailSenderResetPassword extends EmailSenderImpl<String> {

    @Value("${notification.google.gmail.email}")
    private String email;
    @Value("${notification.google.gmail.password")
    private String password;

    public EmailSenderResetPassword(){
        super();
    }

    @Override
    public void send(String from, String to, String subject, String content)  {
        
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }
    
}
