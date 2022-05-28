package com.group6.java16.cybersoft.feedback.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.group6.java16.cybersoft.feedback.model.ELRating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<ELRating, UUID> {
	@Query(value = " Select r from ELRating r left join r.lesson l where  l.id = :id")
	List<ELRating> findByIdLesson(@Param("id") UUID idLesson);

	@Query(value = " Select r from ELRating r  where  r.lesson.id = :lessonId and r.user.username = :username")
	Optional<ELRating> getRatingByLessonAndUser(@Param("lessonId") String lessonId, @Param("username") String username);

	@Query(value = " select count(r)>0 from ELRating r where r.user.id = :userId and r.lesson.id = :lessonId ")
	boolean existsByUserAndLesson(@Param("userId") UUID userId,@Param("lessonId") UUID lessonId);

}
