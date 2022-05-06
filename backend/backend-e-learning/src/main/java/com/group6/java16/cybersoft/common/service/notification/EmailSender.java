package com.group6.java16.cybersoft.common.service.notification;

public interface EmailSender<T> {
    void send(String from, String to, String subject, T content) ;
}
