package com.group6.java16.cybersoft.role.model;

import javax.persistence.*;
import com.group6.java16.cybersoft.common.model.BaseEntity;

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

