package com.learning.javainterviewquestions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.learning.javainterviewquestions.entities.TopicEntity;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
class JavaInterviewQuestionsApplicationTests {

	public static String asJsonString(final Object obj) {
		try {
		  return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
		  throw new RuntimeException(e);
		}
	  }

	@Autowired
    private MockMvc mvc;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("create a TopicEntity")
	public void postANewTopicEntity()
  		throws Exception {
			
    mvc.perform( MockMvcRequestBuilders.post("/api/v1/topics/create" )
      .content(asJsonString(new  TopicEntity(null, "Java", null, null)))
	  .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated())
      .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
		  
	}



}
