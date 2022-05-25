package com.group6.java16.cybersoft.feedback.service;

import java.util.List;

import com.group6.java16.cybersoft.feedback.dto.CommentCreateDTO;
import com.group6.java16.cybersoft.feedback.dto.CommentResponseDTO;

public interface CommentService {
	/*
	 * tìm theo id bài học
	 * trả về list bình luận
	 * điều kiện find
	 * nếu là chủ khóa học (course.createdBy == usernameCurrent)
	 * find all
	 * 
	 * nếu là người dùng
	 * 
	 * find (comment left join lesson left course left statusComment s  where s.status = public or s.user.username = usernameCurrent  )
	 * 
	 * 
	 * */
	List<CommentResponseDTO> search(String lessonId);
	
	/*
	 * kiểm tra người dùng hiện là trạng thái bình luận gì trong bài học (satuscomment)
	 * satuscomment = block throw exception
	 * satuscomment = null
	 * lưu satuscomment là private
	 * lưu comment vào db
	 * 
	 * nếu là chủ 
	 * lưu satuscomment là public
	 *  
	 * */
	CommentResponseDTO create(CommentCreateDTO dto);

	void delete(String commentId);
}
