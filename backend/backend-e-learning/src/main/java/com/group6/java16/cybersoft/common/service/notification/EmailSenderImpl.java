package com.group6.java16.cybersoft.common.service.notification;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public abstract class EmailSenderImpl<T> implements EmailSender<T>{

    protected Session session;

    @Override
    public abstract void send(String from, String to, String subject, T content);
    
    public abstract String getEmail();

    public abstract String getPassword();

    protected EmailSenderImpl(){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); 
        props.put("mail.smtp.port", "587"); 
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); 
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(getEmail(),  getPassword());
            }
        };
        session = Session.getInstance(props, auth);
    }

}
