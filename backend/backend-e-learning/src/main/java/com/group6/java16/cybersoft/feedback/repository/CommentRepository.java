package com.group6.java16.cybersoft.feedback.repository;

import java.util.List;
import java.util.UUID;

import com.group6.java16.cybersoft.feedback.model.ELComment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<ELComment, UUID> {
	 @Query( value =  " Select c from ELComment c left join c.lesson l"
	 		+ " left join l.course t "
	 		+ " where 0 < (select count(sc) from ELStatusComment sc where ( sc.user.username = :userCurrent or "
	 		+ " sc.status = 'PUBLIC') and ( c.createdBy = sc.user.username and t.id = sc.course.id ) ) and l.id = :id order by c.createdAt DESC ") 
	 
	List<ELComment> findByIdLesson(@Param("id")UUID idLesson,@Param("userCurrent")String userCurrent);
    
}
