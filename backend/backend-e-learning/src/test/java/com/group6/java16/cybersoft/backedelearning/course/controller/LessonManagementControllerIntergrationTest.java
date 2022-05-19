package com.group6.java16.cybersoft.backedelearning.course.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.group6.java16.cybersoft.course.controller.LessonManagementController;
import com.group6.java16.cybersoft.course.dto.CourseCreateDTO;
import com.group6.java16.cybersoft.course.dto.CourseUpdateDTO;
import com.group6.java16.cybersoft.course.dto.LessonCreateDTO;
import com.group6.java16.cybersoft.course.dto.LessonUpdateDTO;
import com.group6.java16.cybersoft.course.mapper.CourseMapper;
import com.group6.java16.cybersoft.course.mapper.LessonMapper;
import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.course.model.ELLesson;
import com.group6.java16.cybersoft.course.repository.ELCourseRepository;
import com.group6.java16.cybersoft.course.repository.ELLessonRepository;
import com.group6.java16.cybersoft.course.service.CourseManagementService;
import com.group6.java16.cybersoft.course.service.LessonManagementService;

@SpringBootTest
@AutoConfigureMockMvc
public class LessonManagementControllerIntergrationTest {
	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private LessonManagementService service;
	
	@MockBean
	private ELLessonRepository repository;
	
	@Mock
	private LessonMapper mapper;
	
    @Test
    @WithMockUser("phan")
    public void givenJsonObject_whenSearchLesson_theReturnStatus200() throws Exception{
        mvc.perform(get("/api/v1/lessons")
            .param("pageCurrent", "1")
            .param("itemPerPage", "10")
            .param("fieldNameSort", "createdBy")
            .param("isIncrementSort", "true")
            .param("fieldNameSearch", "createBy")
            .param("valueFieldNameSearch", "nam")
            )    
            .andDo(print())
            .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser("phan")
    public void givenJonObject_whenNameExistedIsUsedCreateLesson_thenReturnStatus400() throws Exception{
    
    	LessonCreateDTO lesson = new LessonCreateDTO("huyphan", "Hello","Nam","courseid");
    	Gson gson = new Gson();
        String json = gson.toJson(lesson);
        
        when(repository.findByName("huyphan")).thenReturn(Optional.of(new ELLesson()));
        
        mvc.perform(post("/api/v1/lessons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(print())
            .andExpect(status().isBadRequest());
        
        
    	
    }
    
    @Test
    @WithMockUser("nam")
    public void givenJonObject_whenCreateLessonSuccessfully_thenReturnStatus200() throws Exception{
    
    	LessonCreateDTO lesson = new LessonCreateDTO("name1212122", "Huy", "description","Phan");
    	Gson gson = new Gson();
        String json = gson.toJson(lesson);
        
        when(repository.findByName("name1212122")).thenReturn(Optional.empty());
        
        mvc.perform(post("/api/v1/lessons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(print())
            .andExpect(status().is2xxSuccessful());
        
        
    	
    }
    
    @Test
    @WithMockUser("nam")
    public void givenJonObject_whenUpdateLessonfully_thenReturnStatus200() throws Exception{
    
    	LessonUpdateDTO lesson = new LessonUpdateDTO("Huy", "Huy", "Phần");
    	Gson gson = new Gson();
        String json = gson.toJson(lesson);
        
        UUID uuid = UUID.randomUUID();
        
        mvc.perform(put("/api/v1/lessons/" + uuid.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(print())
            .andExpect(status().isOk());
     
    	
    }
    
    @Test
    @WithMockUser("phan")
    public void givenJonObject_whenDeleteSuccessfully_thenReturnStatus200() throws Exception{
        
        UUID uuid = UUID.randomUUID();
        
        mvc.perform(delete("/api/v1/lessons/" + uuid.toString()))
            .andDo(print())
            .andExpect(status().isOk());
        
        
    	
    }
}
