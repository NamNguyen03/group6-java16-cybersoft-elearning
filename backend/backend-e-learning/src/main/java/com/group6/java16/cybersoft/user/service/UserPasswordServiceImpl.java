package com.group6.java16.cybersoft.user.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.model.notification.ResetPasswordModel;
import com.group6.java16.cybersoft.common.service.notification.EmailSender;
import com.group6.java16.cybersoft.common.util.UserPrincipal;
import com.group6.java16.cybersoft.user.dto.UpdatePasswordDTO;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.model.ELUserToken;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;
import com.group6.java16.cybersoft.user.repository.UserTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@PropertySources({ @PropertySource("classpath:application-config.properties") })
public class UserPasswordServiceImpl implements UserPasswordService{

    @Autowired
    private ELUserRepository userRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Value("${user.token.exp}")
    private long exp; 

    @Value("${user.token.key}")
    private String key; 
    
    @Value("${frontend.url}")
    private String url; 

    @Value("${token.not-found}")
    private String messageErrorTokenNotFound;
    
    @Value("${user.not-found}")
    private String messageErrorUserNotFound;

    @Value("${token.expires}")
    private String messageErrorTokenExpires;

    @Autowired
    @Qualifier("emailSenderResetPassword")
    private EmailSender<ResetPasswordModel> serviceSendEmailSenderResetPassword;

    @Autowired
    private PasswordEncoder encoder;

	@Override
	public void generateToken() {
        String username = UserPrincipal.getUsernameCurrent();
        ELUser user = userRepository.findByUsername(username).get();
		ELUserToken token = ELUserToken.builder()
            .value(generateToken(username))
            .user(user)
            .build();
        
        userTokenRepository.save(token);

        url = url + "/reset-password";
        serviceSendEmailSenderResetPassword.send("User Service", user.getEmail(), "Reset Password" , new ResetPasswordModel(username, token.getValue(), url));
	}
    
    private String generateToken(String username) {
        
        return encoder.encode(username + key + new Random().nextInt());
    }

    @Override
    public void updatePassword(UpdatePasswordDTO rq) {
        List<ELUserToken> tokens = userTokenRepository.findByUsername(rq.getUsername());
        if(tokens.isEmpty()) {
            throw new BusinessException(messageErrorTokenNotFound);
        }
        
        ELUserToken token   = tokens.stream().filter(
            t -> rq.getToken().equals(t.getValue())
            ).findAny().orElseThrow(() -> new BusinessException(messageErrorTokenNotFound));
        
        if(token.getCreatedAt().plusMinutes(exp).isBefore(LocalDateTime.now())){
            throw new BusinessException(messageErrorTokenExpires);
        }
        
        ELUser user = userRepository.findByUsername(rq.getUsername()).orElseThrow(() -> new BusinessException(messageErrorTokenNotFound));
        user.setPassword(encoder.encode(rq.getPassword()));

        tokens.stream().forEach(userTokenRepository::delete);
        
        userRepository.save(user);
       
        
    }
}
