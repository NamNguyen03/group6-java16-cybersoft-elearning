package com.group6.java16.cybersoft.common.service.notification;

import java.util.Date;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.model.notification.ResetPasswordModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("emailSenderResetPassword") 
public class EmailSenderResetPassword extends EmailSenderImpl<ResetPasswordModel> {
    
    @Autowired
    private EmailConfiguration emailConfiguration; 

    public EmailSenderResetPassword(){
        super();
    }

    @Override
    public void send(String from, String to, String subject, ResetPasswordModel content)  {
        String html = " <h1>Reset Password Successfully.</h1> " +
            "  <form action='" + content.getUrlFrontend() + "' method='get' > " + 
            " <input type='hidden' name='username' value='" + content.getUsername() + "' /> " +
            " <input type='hidden' name='token' value='" + content.getToken() + "' /> " +
            " <button type='submit' style='font-size:20px; padding: 10px; border: none; background: rgb(158, 156, 156); cursor: pointer;'>Click Now</button> " +
            " </form>";
       
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
