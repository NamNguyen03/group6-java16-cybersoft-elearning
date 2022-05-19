package com.group6.java16.cybersoft.feedback.model;

import com.group6.java16.cybersoft.common.model.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.user.model.ELUser;

import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "el_status_comment")
public class ELStatusComment extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private EnumStatusComment status;

    @JsonIgnore
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "user_id",referencedColumnName = "id")
    private ELUser user;

    @JsonIgnore
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "course_id",referencedColumnName = "id")
    private ELCourse course;
}
