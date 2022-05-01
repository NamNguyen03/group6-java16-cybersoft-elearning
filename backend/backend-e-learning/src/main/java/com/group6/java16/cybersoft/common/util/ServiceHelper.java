package com.group6.java16.cybersoft.common.util;

import java.util.Optional;
import java.util.UUID;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class ServiceHelper<T extends BaseEntity> {
    
    protected abstract String getMessageIdInvalid();

    protected abstract JpaRepository<T, UUID> getRepository();

    protected abstract String getErrorNotFound();

    protected T getById(String id) {
        UUID uuid;
        try{
            uuid = UUID.fromString(id);
        }catch(Exception e){
            throw new BusinessException(getMessageIdInvalid());
        }
        
        Optional<T> entityOpt = getRepository().findById(uuid);
        
        if(entityOpt.isEmpty()){
            throw new BusinessException(getErrorNotFound());
        }
        return entityOpt.get();
    }

    protected boolean isValidString(String s) {
        if(s == null || s.length() == 0) {
            return false;
        }
        return true;
    }
}
