package com.group6.java16.cybersoft.feedback.repository;

import java.util.UUID;

import com.group6.java16.cybersoft.feedback.model.ELComment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<ELComment, UUID> {
	@Query(value = " Select c from ELComment c where c.lesson.id = :idLesson and"
			+ " c.user.id in (select s.user.id from ELStatusComment s where s.status = 'PUBLIC'"
			+ " and s.course.id = :courseId )")
	Page<ELComment> findByIdLesson(@Param("idLesson") UUID idLesson, @Param("courseId") UUID courseId, Pageable page);

	@Query(value = " Select c from ELComment c where c.lesson.id = :idLesson")
	Page<ELComment> findByIdLesson(@Param("idLesson") UUID idLesson, Pageable page);

	@Query(value = " Select c from ELComment c where c.lesson.id = :idLesson and"
			+ " c.user.id in (select s.user.id from ELStatusComment s where (s.status = 'PUBLIC' or s.user.username = :usernameCurrent)"
			+ " and s.course.id = :courseId )")
	Page<ELComment> findByIdLessonAndUserCurrent(@Param("idLesson") UUID idLesson, @Param("courseId") UUID courseId,
			@Param("usernameCurrent") String userCurrent, Pageable page);

}
