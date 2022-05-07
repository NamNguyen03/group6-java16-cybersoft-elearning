package com.group6.java16.cybersoft.backedelearning.course.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.group6.java16.cybersoft.course.service.CourseManagementService;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseManagementControllerIntergratiomTest {
	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private CourseManagementService service;
	
    @Test
    @WithMockUser("nam")
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

}
