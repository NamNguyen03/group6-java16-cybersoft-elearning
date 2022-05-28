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
	@Query(value = " Select c from ELComment c where c.lesson.id = :idLesson and"
			+ " c.user.id in (select s.user.id from ELStatusComment s where s.status = 'PUBLIC'"
			+ " and s.course.id = :courseId )")
	List<ELComment> findByIdLesson(@Param("idLesson") UUID idLesson, @Param("courseId") UUID courseId);

	@Query(value = " Select c from ELComment c where c.lesson.id = :idLesson")
	List<ELComment> findByIdLesson(@Param("idLesson") UUID idLesson);

	@Query(value = " Select c from ELComment c where c.lesson.id = :idLesson and"
			+ " c.user.id in (select s.user.id from ELStatusComment s where (s.status = 'PUBLIC' or s.user.username = :usernameCurrent)"
			+ " and s.course.id = :courseId )")
	List<ELComment> findByIdLessonAndUserCurrent(@Param("idLesson") UUID idLesson, @Param("courseId") UUID courseId,
			@Param("usernameCurrent") String userCurrent);

}
