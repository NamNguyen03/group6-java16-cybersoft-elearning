package com.group6.java16.cybersoft.common.service.notification;

import java.util.Date;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.model.notification.UserCreateModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("emailSenderCreateSuccessfully") 
public class EmailSenderCreateSuccessfully extends EmailSenderImpl<UserCreateModel> {

    @Autowired
    private EmailConfiguration emailConfiguration; 

    public EmailSenderCreateSuccessfully(){
        super();
    }

    @Override
    public void send(String from, String to, String subject, UserCreateModel content) {

        String html = " <h1>Create Account Successfully.</h1> " +
            " <label>Username: " + content.getUserName() + " </label> <br/> " +
            " <label>Password: " + content.getPassword() + " </label> <br/> " +
            " <p>You should reset your password when login for the first time.</p> ";
       
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(emailConfiguration.getEmail(), from));
           
            msg.setReplyTo(InternetAddress.parse(emailConfiguration.getEmail(), false));
            msg.setSubject(subject, "UTF-8");
            msg.setContent(html, "text/html; charset=utf-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));  
            Transport.send(msg);  
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("send email fails");
        }
       
    }

    @Override
    public String getEmail() {
        return emailConfiguration.getEmail();
    }

    @Override
    public String getPassword() {
        return emailConfiguration.getPassword();
    }
    
}
