package com.group6.java16.cybersoft.security.service;

import java.util.ArrayList;

import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {
	@Autowired
	private ELUserRepository repository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ELUser user = repository.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("User Not Found with username: " + username));

		return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}

}