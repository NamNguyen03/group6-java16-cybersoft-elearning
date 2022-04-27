package com.group6.java16.cybersoft.role.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group6.java16.cybersoft.common.model.BaseEntity;
import com.group6.java16.cybersoft.user.model.ELUser;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "el_group")
public class ELGroup  extends BaseEntity {
    
    private String name;
    private String description;

    
    
    
}

