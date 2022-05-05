package com.group6.java16.cybersoft.user.repository;

import java.util.UUID;

import com.group6.java16.cybersoft.user.model.ELUserToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<ELUserToken, UUID>  {
     
}
