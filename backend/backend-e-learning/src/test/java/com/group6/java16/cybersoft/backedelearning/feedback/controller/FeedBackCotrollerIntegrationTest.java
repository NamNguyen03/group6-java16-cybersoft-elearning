/**
 * 
 */
package com.group6.java16.cybersoft.backedelearning.feedback.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.group6.java16.cybersoft.feedback.dto.CommentCreateDTO;
import com.group6.java16.cybersoft.feedback.dto.RatingCreateDTO;
import com.group6.java16.cybersoft.feedback.dto.StatusCommentDTO;
import com.group6.java16.cybersoft.feedback.dto.UpdateStatusCommentDTO;
import com.group6.java16.cybersoft.feedback.dto.reponse.status.StatusCommentResponseDTO;
import com.group6.java16.cybersoft.feedback.model.EnumStatusComment;
import com.group6.java16.cybersoft.feedback.service.CommentService;
import com.group6.java16.cybersoft.feedback.service.RatingService;
import com.group6.java16.cybersoft.feedback.service.StatusCommentService;

@SpringBootTest
@AutoConfigureMockMvc
public class FeedBackCotrollerIntegrationTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private CommentService commentService;

	@MockBean
	private RatingService ratingService;
	
	@MockBean
	private StatusCommentService statusCommentService;

	@Test
	@WithMockUser
	public void whenfindCommentIntoLesson_thenReturnStatus200() throws Exception {
		mvc.perform(get("/api/v1/comments/" + UUID.randomUUID().toString()))
		.andExpect(status().isOk());
	}

	@Test
	@WithMockUser
	public void whenDeleteComment_thenReturnStatus200() throws Exception {
		mvc.perform(delete("/api/v1/comments/" + UUID.randomUUID().toString()))
		.andExpect(status().isOk());
	}

	@Test
	@WithMockUser
	public void whenCommentCreateDTOInvalidIsUsedcreateComment_thenReturnStatus400() throws Exception {

		CommentCreateDTO rq = new CommentCreateDTO();
		Gson gson = new Gson();
		String json = gson.toJson(rq);

		mvc.perform(post("/api/v1/comments/" )
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser
	public void whenCommentCreateDTOValidIsUsedcreateComment_thenReturnStatus200() throws Exception {

		CommentCreateDTO rq = new CommentCreateDTO("new Comment",UUID.randomUUID().toString());
		Gson gson = new Gson();
		String json = gson.toJson(rq);

		mvc.perform(post("/api/v1/comments/" )
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isOk());
	}

	@Test
	@WithMockUser
	public void whenFindMyRatingIntoLesson_thenReturnStatus200() throws Exception {
		mvc.perform(get("/api/v1/ratings/me/" + UUID.randomUUID().toString()))
		.andExpect(status().isOk());
	}

	@Test
	@WithMockUser
	public void whenRatingCreateDTOValidCreateRating_thenReturnStatus200() throws Exception {

		RatingCreateDTO rq = new RatingCreateDTO();
		rq.setValue(2);
		rq.setLessonId(UUID.randomUUID().toString());
		Gson gson = new Gson();
		String json = gson.toJson(rq);

		mvc.perform(post("/api/v1/ratings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isOk());
	}

	@Test
	@WithMockUser
	public void whenRatingCreateDTOInvalidCreateRating_thenReturnStatus400() throws Exception {

		RatingCreateDTO rq = new RatingCreateDTO();
		rq.setValue(20);
		rq.setLessonId(UUID.randomUUID().toString());
		Gson gson = new Gson();
		String json = gson.toJson(rq);

		mvc.perform(post("/api/v1/ratings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isBadRequest());
	}


	@Test
	@WithMockUser
	public void whenSearchStatusCommentSuccess_thenReturnStatus200() throws Exception{
		mvc.perform(get("/api/v1/status-comments")
				.param("pageCurrent", "-1")
				.param("itemPerPage", "-1")
				.param("fieldNameSort", "createdBy")
				.param("isIncrementSort", "true")
				.param("fieldNameSearch", "displayName")
				.param("valueFieldNameSearch", "Nam Nguyen"))
				.andExpect(status().isOk());

	}
	
	@Test
	@WithMockUser
	public void whenGetStatusCommentByIdSuccess_thenReturnStatus200() throws Exception{
		
		UUID id = UUID.randomUUID();
		
		when(statusCommentService.getById(id.toString())).thenReturn(new StatusCommentResponseDTO());
		
		mvc.perform(get("/api/v1/status-comments/" + id.toString()))
				.andExpect(status().isOk());

	}
	
	@Test
	@WithMockUser
	public void whenCreateSatusCommentSuccess_thenReturnStatus200() throws Exception{
		StatusCommentDTO rq = new StatusCommentDTO();
		
		when(statusCommentService.createStatusComment(rq)).thenReturn(new StatusCommentResponseDTO());
		
		Gson gson = new Gson();
		String json = gson.toJson(rq);

		mvc.perform(post("/api/v1/status-comments/" )
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	public void whenUpdateStatusCommentSuccess_thenReturnStatus200() throws Exception {
		UpdateStatusCommentDTO rq = new UpdateStatusCommentDTO(EnumStatusComment.BLOCKED);
		UUID id = UUID.randomUUID();
		when(statusCommentService.updateStatusComment(id.toString(), EnumStatusComment.BLOCKED)).thenReturn(new StatusCommentResponseDTO());
		
		Gson gson = new Gson();
		String json = gson.toJson(rq);

		mvc.perform(put("/api/v1/status-comments/" + id.toString())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isOk());
	}

	@Test
	@WithMockUser
	public void whenIdIsNotTypeUUIDUpdateStatusCommentFails_thenReturnStatus400() throws Exception {
		UpdateStatusCommentDTO rq = new UpdateStatusCommentDTO(EnumStatusComment.BLOCKED);
		when(statusCommentService.updateStatusComment("123", EnumStatusComment.BLOCKED)).thenThrow(new IllegalArgumentException());
		
		Gson gson = new Gson();
		String json = gson.toJson(rq);

		mvc.perform(put("/api/v1/status-comments/" + "123")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isBadRequest());
	}
}
