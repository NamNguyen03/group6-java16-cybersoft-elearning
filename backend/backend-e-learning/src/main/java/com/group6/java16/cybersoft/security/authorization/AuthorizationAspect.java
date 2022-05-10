package com.group6.java16.cybersoft.security.authorization;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

import com.group6.java16.cybersoft.common.exception.UnauthorizedException;
import com.group6.java16.cybersoft.common.util.UserPrincipal;
import com.group6.java16.cybersoft.role.repository.ELProgramRepository;

@Component
@Aspect
@PropertySources({ @PropertySource("classpath:application-config.properties") })
public class AuthorizationAspect {
	@Autowired
	private ELProgramRepository programRepository;
	
	@Value("${environment.is-production}")
	private boolean isProduction;
	
	@Before("@annotation(permission))")
	public void programAuthorizer (ELPermission permission) {
		if(!isProduction) {
			return;
		}
		String name = permission.value();
		String username = UserPrincipal.getUsernameCurrent();
		
		boolean isAuthorized = programRepository.existsByNameProgramAndUsername(name, username);
		
		if (!isAuthorized){
            throw new UnauthorizedException("Unauthorized method " + name);
        }
			
	}

}
