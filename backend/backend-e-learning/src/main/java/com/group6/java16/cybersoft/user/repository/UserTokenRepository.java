package com.group6.java16.cybersoft.user.repository;

import java.util.List;
import java.util.UUID;
import com.group6.java16.cybersoft.user.model.ELUserToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<ELUserToken, UUID>  {
    @Query("Select t From ELUserToken t where t.user.username = :username ")
    List<ELUserToken> findByUsername(@Param("username") String username);
     
}
