package com.group6.java16.cybersoft.common.util;

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
        
        return getRepository().findById(uuid).orElseThrow(() -> new BusinessException(getErrorNotFound()));
    }

    protected boolean isValidString(String s) {
        if(s == null || s.length() == 0) {
            return false;
        }
        return true;
    }
}
