package com.group6.java16.cybersoft.backedelearning.course.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.course.dto.CourseCreateDTO;
import com.group6.java16.cybersoft.course.dto.CourseUpdateDTO;
import com.group6.java16.cybersoft.course.dto.LessonCreateDTO;
import com.group6.java16.cybersoft.course.dto.LessonUpdateDTO;
import com.group6.java16.cybersoft.course.service.CourseManagementService;
import com.group6.java16.cybersoft.course.service.LessonManagementService;
import com.group6.java16.cybersoft.role.repository.ELProgramRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseMoudleControllerIntegrationTest {
	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private LessonManagementService lessonManagementService;

    @MockBean
	private CourseManagementService courseManagementService;

    @MockBean
	private ELProgramRepository programRepository;
	
    @BeforeEach
    public void setUp(){
        when(programRepository.existsByNameProgramAndUsername(any(), any())).thenReturn(true);
    }

    @Test
    @WithMockUser
    public void givenJsonObject_whenSearchLesson_theReturnStatus200() throws Exception{
        mvc.perform(get("/api/v1/lessons")
            .param("pageCurrent", "1")
            .param("itemPerPage", "10")
            .param("fieldNameSort", "createdBy")
            .param("isIncrementSort", "true")
            .param("fieldNameSearch", "createBy")
            .param("valueFieldNameSearch", "nam"))
            .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser
    public void givenJonObject_whenNameExistedIsUsedCreateLesson_thenReturnStatus400() throws Exception{
    
    	LessonCreateDTO lesson = new LessonCreateDTO();
    	lesson.setContent("Lesson 1");
    	lesson.setDescription("Java Core");
    	lesson.setName("Review Java");
    	Gson gson = new Gson();
        String json = gson.toJson(lesson);
        mvc.perform(post("/api/v1/lessons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isBadRequest());

    }
    
    @Test
    @WithMockUser
    public void givenJonObject_whenCreateLessonSuccessfully_thenReturnStatus200() throws Exception{
    
    	
    	LessonCreateDTO lesson = new LessonCreateDTO();
    	lesson.setContent("Lesson 1");
    	lesson.setDescription("Java Core");
    	lesson.setName("Review Java");
    	lesson.setCourseId("course1");
    	Gson gson = new Gson();
        String json = gson.toJson(lesson);
        mvc.perform(post("/api/v1/lessons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().is2xxSuccessful());

    }
    
    @Test
    @WithMockUser
    public void givenJonObject_whenUpdateLessonSuccessfully_thenReturnStatus200() throws Exception{
    
    	LessonUpdateDTO lesson = new LessonUpdateDTO();
    	lesson.setContent("Lesson 1");
    	lesson.setDescription("Java Core");
    	lesson.setName("Review Java");
    	Gson gson = new Gson();
        String json = gson.toJson(lesson);
        
        UUID uuid = UUID.randomUUID();
        
        mvc.perform(put("/api/v1/lessons/" + uuid.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk());

    }
    
    @Test
    @WithMockUser
    public void givenJonObject_whenUpdateLessonError_thenReturnStatus400() throws Exception{
    
    	LessonUpdateDTO lesson = new LessonUpdateDTO();
    	lesson.setContent("Lesson 1");
    	lesson.setDescription("Java Core");
    	lesson.setName("Review Java");
    	Gson gson = new Gson();
        String json = gson.toJson(lesson);
        
        when(lessonManagementService.updateLesson(any(),any())).thenThrow(new BusinessException("Lesson not found."));
        
        UUID uuid = UUID.randomUUID();
        
        mvc.perform(put("/api/v1/lessons/" + uuid.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isBadRequest());

    }
    
    @Test
    @WithMockUser
    public void givenJonObject_whenDeleteLessonSuccessfully_thenReturnStatus200() throws Exception{
        
        UUID uuid = UUID.randomUUID();
        
        mvc.perform(delete("/api/v1/lessons/" + uuid.toString()))
            .andExpect(status().isOk());
        
    }
    
    @Test
    @WithMockUser
    public void givenJonObject_whenLessonsExistedIsUsedGetLessonInfo_thenReturnStatus200() throws Exception{
        
        UUID uuid = UUID.randomUUID();
        mvc.perform(get("/api/v1/lessons/" + uuid.toString()))
            .andExpect(status().isOk());
    }
    
    @Test 
    @WithMockUser()
    public void whenPostImg_thenReturnStatus200() throws Exception{
        MockMultipartFile file 
            = new MockMultipartFile(
            "file", 
            "hello.txt", 
            MediaType.TEXT_PLAIN_VALUE, 
            "Hello, World!".getBytes()
            );
        mvc.perform(multipart("/api/v1/lessons/img").file(file))
            .andExpect(status().isOk());
    }
	
    @Test
    @WithMockUser
    public void givenJsonObject_whenSearchCourse_theReturnStatus200() throws Exception{
        mvc.perform(get("/api/v1/courses")
            .param("pageCurrent", "1")
            .param("itemPerPage", "10")
            .param("fieldNameSort", "createdBy")
            .param("isIncrementSort", "true")
            .param("fieldNameSearch", "createBy")
            .param("valueFieldNameSearch", "nam")
            )    
            .andExpect(status().isOk());
    }
    
    @WithMockUser
    @Test
    public void givenJonObject_whenNameExistedIsUsedCreateCourse_thenReturnStatus400() throws Exception{
    
    	CourseCreateDTO course = new CourseCreateDTO();
    	course.setCourseName("Java Backend");
    	course.setImg("anh.img");
    	course.setDescription("hello Java Backend");
    	course.setSkill1("Java");
    	course.setSkill2("HTML-CSS");
    	course.setSkill3("SQL");
    	Gson gson = new Gson();
        String json = gson.toJson(course);

        when(courseManagementService.createCourse(any())).thenThrow(new BusinessException(""));
        
        mvc.perform(post("/api/v1/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isBadRequest());
    }
    
    
    @Test
    @WithMockUser
    public void givenJonObject_whenCreateCourseSuccessfully_thenReturnStatus200() throws Exception{
    
    	CourseCreateDTO course = new CourseCreateDTO();
    	course.setCourseName("Java Backend");
    	course.setImg("anh.img");
    	course.setDescription("hello Java Backend");
    	course.setSkill1("Java");
    	course.setSkill2("HTML-CSS");
    	course.setSkill3("SQL");
    	Gson gson = new Gson();
        String json = gson.toJson(course);
        
        mvc.perform(post("/api/v1/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().is2xxSuccessful());
    	
    }
    
    @Test
    @WithMockUser
    public void givenJonObject_whenUpdateSuccessfully_thenReturnStatus200() throws Exception{
    
    	CourseUpdateDTO course = new CourseUpdateDTO();

    	Gson gson = new Gson();
        String json = gson.toJson(course);
        
        UUID uuid = UUID.randomUUID();
        
        mvc.perform(put("/api/v1/courses/" + uuid.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk());
        
    }
    
    @Test
    @WithMockUser
    public void givenJonObject_whenUpdateError_thenReturnStatus400() throws Exception{
    
    	CourseUpdateDTO course = new CourseUpdateDTO();
    	course.setCourseName("Java Backend");
    	course.setImg("anh.img");
    	course.setDescription("hello Java Backend");
    	course.setSkill1("Java");
    	course.setSkill2("HTML-CSS");
    	course.setSkill3("SQL");
    	Gson gson = new Gson();
        String json = gson.toJson(course);
        
        when(courseManagementService.updateCourse(any(),any())).thenThrow(new BusinessException("Course not found."));
        
        UUID uuid = UUID.randomUUID();
        
        mvc.perform(put("/api/v1/users/" + uuid.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());

    }
    
    @Test
    @WithMockUser
    public void givenJonObject_whenDeleteCourseSuccessfully_thenReturnStatus200() throws Exception{
        
        UUID uuid = UUID.randomUUID();
        
        mvc.perform(delete("/api/v1/courses/" + uuid.toString()))
            .andExpect(status().isOk());
        
    }
    
    @Test
    @WithMockUser
    public void givenJonObject_whenCousreExistedIsUsedGetCourseDatails_thenReturnStatus200() throws Exception{
        
        UUID uuid = UUID.randomUUID();
        
        mvc.perform(get("/api/v1/courses/" + uuid.toString()))
            .andExpect(status().isOk());

    }
}
