package com.group6.java16.cybersoft.backedelearning.course.controller;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.course.dto.CourseCreateDTO;
import com.group6.java16.cybersoft.course.dto.CourseResponseDTO;
import com.group6.java16.cybersoft.course.dto.CourseUpdateDTO;
import com.group6.java16.cybersoft.course.mapper.CourseMapper;
import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.course.repository.ELCourseRepository;
import com.group6.java16.cybersoft.course.service.CourseManagementService;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseManagementControllerIntergratiomTest {
	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private CourseManagementService service;
	
	@MockBean
	private ELCourseRepository repository;
	
	@Mock
	private CourseMapper mapper;
	
    @Test
    @WithMockUser("phan")
    public void givenJsonObject_whenSearchCourse_theReturnStatus200() throws Exception{
        mvc.perform(get("/api/v1/courses")
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
    public void givenJonObject_whenNameExistedIsUsedCreateCourse_thenReturnStatus400() throws Exception{
    
    	CourseCreateDTO course = new CourseCreateDTO("name1212122", 12, "description");
    	Gson gson = new Gson();
        String json = gson.toJson(course);
        
        when(repository.findByCourseName("name1212122")).thenReturn(Optional.of(new ELCourse()));
        
        mvc.perform(post("/api/v1/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(print())
            .andExpect(status().isBadRequest());
        
        
    	
    }
    
    @Test
    @WithMockUser("nam")
    public void givenJonObject_whenCreateCourseSuccessfully_thenReturnStatus200() throws Exception{
    
    	CourseCreateDTO course = new CourseCreateDTO("name1212122", 12, "description");
    	Gson gson = new Gson();
        String json = gson.toJson(course);
        
        when(repository.findByCourseName("name1212122")).thenReturn(Optional.empty());
        
        mvc.perform(post("/api/v1/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(print())
            .andExpect(status().is2xxSuccessful());
        
        
    	
    }
    
    @Test
    @WithMockUser("nam")
    public void givenJonObject_whenUpdateSuccessfully_thenReturnStatus200() throws Exception{
    
    	CourseUpdateDTO course = new CourseUpdateDTO("nam12232", 12, "asdfsdfsd");
    	Gson gson = new Gson();
        String json = gson.toJson(course);
        
        UUID uuid = UUID.randomUUID();
        
        mvc.perform(put("/api/v1/courses/" + uuid.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(print())
            .andExpect(status().isOk());
        
        
    	
    }
    
    @Test
    @WithMockUser("phan")
    public void givenJonObject_whenDeleteSuccessfully_thenReturnStatus200() throws Exception{
        
        UUID uuid = UUID.randomUUID();
        
        mvc.perform(delete("/api/v1/courses/" + uuid.toString()))
            .andDo(print())
            .andExpect(status().isOk());
        
        
    	
    }
    
    @Test
    @WithMockUser("phan")
    public void givenJonObject_whenCousreExistedIsUsedGetCourseDatails_thenReturnStatus200() throws Exception{
        
        UUID uuid = UUID.randomUUID();
        
        mvc.perform(get("/api/v1/courses/" + uuid.toString()))
            .andDo(print())
            .andExpect(status().isOk());
        
        
    	
    }
    
    @Test
    public void whenCourseExistedIsUsedToDeleteCourse_thenDeleteSuccessfully(){

        UUID id = UUID.randomUUID();

        ELCourse course =  ELCourse.builder()
        	.courseName("Java Back End")
        	.courseTime(32)
        	.description("Hello Nam")
            .build();

        when(repository.findById(id)).thenReturn(Optional.of(course));
        assertDoesNotThrow( () ->service.deleteById(id.toString()));
       
    }
    
}
