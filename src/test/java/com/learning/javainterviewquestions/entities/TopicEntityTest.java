package com.learning.javainterviewquestions.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TopicEntityTest {

    TopicEntity topic = TopicEntity.builder()
        .id(1L)
        .name("Java Virtual Machine")
        .build();
    
    @Test
    @DisplayName("check topic creation")
    public void checkTopicCreation(){
        assertEquals(topic.getName(), "Java Virtual Machine");
    }

    @Test
    @DisplayName("check default list of source is not null")
    public void checkNotNullSourceList(){
        assertNotNull(topic.getSources());
    }
    
    @Test
    @DisplayName("check default list of questions is not null")
    public void checkNotNullQuestionsList(){
        assertNotNull(topic.getQuestions());
    }
    
}
