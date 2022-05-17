package com.group6.java16.cybersoft.user.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.group6.java16.cybersoft.common.model.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Entity
@Table(name = "el_user_token")
public class ELUserToken  extends BaseEntity  {
    private String value;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private ELUser user;
}
