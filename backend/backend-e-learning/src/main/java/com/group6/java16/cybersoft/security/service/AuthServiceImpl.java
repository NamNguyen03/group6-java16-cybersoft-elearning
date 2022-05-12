package com.group6.java16.cybersoft.security.service;
import java.util.Optional;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.security.dto.LoginRequestDTO;
import com.group6.java16.cybersoft.security.jwt.JwtHelper;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@PropertySources({ @PropertySource("classpath:/validation/message.properties") })
public class AuthServiceImpl implements AuthService{

    @Value("${user.password.not-equals}")
    private String messagePasswordNotEquals;

    @Autowired
    private ELUserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
	private JwtHelper jwts;

    @Override
    public String login(LoginRequestDTO loginDTO) {
		Optional<ELUser> userOpt = userRepository.findByUsername(loginDTO.getUsername());
		
		String encodedPassword = userOpt.get().getPassword();
		
		if(!encoder.matches(loginDTO.getPassword(), encodedPassword)) {
			throw new BusinessException(messagePasswordNotEquals);
		}
		
		return jwts.generateJwtToken(loginDTO.getUsername());
    }
    
}
