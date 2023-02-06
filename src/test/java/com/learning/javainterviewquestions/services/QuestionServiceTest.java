package com.learning.javainterviewquestions.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import com.learning.javainterviewquestions.entities.QuestionEntity;
import com.learning.javainterviewquestions.entities.TopicEntity;
import com.learning.javainterviewquestions.repositories.QuestionRepository;

@DataJpaTest
public class QuestionServiceTest {

    @TestConfiguration
    static class TopicServiceTestConfiguration {

        @Bean
        TopicService topicService() {
            return new TopicService();
        }
        
    }
    
    
    @InjectMocks
    QuestionService service;

    @SpyBean
    QuestionRepository repository;
    
    @Autowired 
    TopicService topicService;
  
    TopicEntity topic;
    QuestionEntity java;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        
        topic = TopicEntity.builder()
            .name("Java")
            .build();
        topicService.save(topic);
        
        java = QuestionEntity.builder()
            .question("What is Java")
            .answer("It is a programming language")
            .topic("Java")
            .build();
    }

    @Test
    @DisplayName("saving question")
    void givenQuestionAndTopic_whenSaveQuestion_thenReturnItWithId(){

        java.setTheTopic(topic);
        QuestionEntity saved = service.save(java);
        assertEquals("What is Java", saved.getQuestion());
        
    }

    @Test
    @DisplayName("saving question without topic")
    void givenQuestionWithoutTopic_whenSaveQuestion_thenReturnException(){
        java.setTopic(null);
        assertThrows(DataIntegrityViolationException.class, 
            () -> service.save( java )
        );
    }

    @Test
    @DisplayName("saving question without question")
    void givenQuestionWithoutQuestion_whenSaveQuestion_thenReturnException(){
        java.setQuestion("");
        assertThrows(DataIntegrityViolationException.class, 
            () -> service.save( java )
        );
    }

    @Test
    @DisplayName("saving question without answer")
    void givenQuestionWithoutAnswer_whenSaveQuestion_thenReturnException(){
        java.setAnswer("");
        assertThrows(DataIntegrityViolationException.class, 
            () -> service.save( java )
        );
    }

    @Test
    @DisplayName("saving question too short")
    void givenQuestionWithQuestionTooShort_whenSaveQuestion_thenReturnException(){
        java.setQuestion("abc");
        assertThrows(DataIntegrityViolationException.class, 
            () -> service.save( java )
        );
    }

    @Test
    @DisplayName("saving answer too short")
    void givenQuestionWithAnswerTooShort_whenSaveQuestion_thenReturnException(){
        java.setAnswer("abc");
        assertThrows(DataIntegrityViolationException.class, 
            () -> service.save( java )
        );
    }

    @Test
    @DisplayName("saving topic question too short")
    void givenQuestionWithTopicTooShort_whenSaveQuestion_thenReturnException(){
        java.setTopic("ab");
        assertThrows(DataIntegrityViolationException.class, 
            () -> service.save( java )
        );
    }

    @Test
    @DisplayName("delete existing question entity")
    void givenAnExistingQuestion_whenDeleteById_thenReturnTrue(){
        java.setTheTopic(topic);
        service.save( java );
        assertTrue( service.deleteById(1L));
    }

    @Test
    @DisplayName("delete un existing question entity")
    void givenAnNonExistingQuestion_whenDeleteById_thenReturnFalse(){
        java.setTheTopic(topic);
        service.save( java );
        assertFalse( service.deleteById(10L));
    }


}
