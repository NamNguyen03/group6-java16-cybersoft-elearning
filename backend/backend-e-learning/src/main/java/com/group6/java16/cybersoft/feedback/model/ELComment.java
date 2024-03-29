package com.group6.java16.cybersoft.feedback.model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group6.java16.cybersoft.common.model.BaseEntity;
import com.group6.java16.cybersoft.course.model.ELLesson;
import com.group6.java16.cybersoft.user.model.ELUser;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "el_comment")
public class ELComment extends BaseEntity  {
    private String content;

	@JsonIgnore
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "user_id",referencedColumnName = "id")
    private ELUser user;

    @JsonIgnore
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "lesson_id",referencedColumnName = "id")
    private ELLesson lesson;
    
    
}
