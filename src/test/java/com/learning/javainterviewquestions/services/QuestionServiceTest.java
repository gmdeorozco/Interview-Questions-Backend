package com.learning.javainterviewquestions.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.learning.javainterviewquestions.entities.QuestionEntity;
import com.learning.javainterviewquestions.entities.Source;
import com.learning.javainterviewquestions.entities.TopicEntity;
import com.learning.javainterviewquestions.repositories.QuestionRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;

@DataJpaTest
public class QuestionServiceTest {

    @TestConfiguration
    static class TopicServiceTestConfiguration {

        @Bean
        TopicService topicService() {
            return new TopicService();
        }
        
        @Bean
        SourcesService sourcesService() {
            return new SourcesService();
        }
        
    }
    
    
    @InjectMocks
    QuestionService service;

    @SpyBean
    QuestionRepository repository;

    @SpyBean
    SourcesService spySourcesService;
    
    @Autowired 
    TopicService topicService;

    @Autowired
    SourcesService sourceService;

    @Autowired
    EntityManager entityManager;
  
    TopicEntity javaTopic, javascriptTopic, gitTopic;
    QuestionEntity java, springboot, junit, git, javascript;
    Source source;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        
        javaTopic = TopicEntity.builder()
            .name("Java")
            .build();
        topicService.save(javaTopic);

        javascriptTopic = TopicEntity.builder()
        .name("Javascript")
        .build();
        topicService.save(javascriptTopic);

        gitTopic = TopicEntity.builder()
        .name("Git")
        .build();
        topicService.save(gitTopic);

        source = Source.builder()
            .name("java book")
            .sourceLink("java.com")
            .topic("Java")
            .theTopic(javaTopic)
            .build();
        sourceService.save(source);
        
        java = QuestionEntity.builder()
            .question("What is Java")
            .answer("It is a programming language")
            .topic("Java")
            .theTopic(javaTopic)
            .build();
        
        springboot = QuestionEntity.builder()
            .question("What is Springboot")
            .answer("It is a programming framework")
            .topic("Java")
            .source(source)
            .build();

        junit = QuestionEntity.builder()
            .question("What is JUnit")
            .answer("It is a programming testing framework")
            .topic("Java")
            .source(source)
            .build();

        git = QuestionEntity.builder()
            .question("What is Git")
            .answer("It is a versioning system")
            .topic("Git")
            .build();

        javascript = QuestionEntity.builder()
            .question("What is Javascript")
            .answer("It is a interpreted programming language")
            .topic("Javascript")
            .build();

            javascript.setTheTopic(javascriptTopic);
            javascript = service.save(javascript);
    
            springboot.setTheTopic(javaTopic);
            springboot = service.save(springboot);
    
            git.setTheTopic(gitTopic);
            git = service.save(git);
    
            junit.setTheTopic(javaTopic);
            junit = service.save(junit);
    }

    @Test
    @DisplayName("saving question")
    void givenQuestionAndTopic_whenSaveQuestion_thenReturnItWithId(){
        
        java = service.save(java);
        assertEquals("What is Java", java.getQuestion());
        
    }

    @Test
    @DisplayName("saving question without topic")
    void givenQuestionWithoutTopic_whenSaveQuestion_thenReturnException(){
        java.setTopic(null);
        assertThrows(PersistenceException.class, 
            () -> {
                service.save( java );
                entityManager.flush();
            }
        );
    }

    @Test
    @DisplayName("saving question without question")
    void givenQuestionWithoutQuestion_whenSaveQuestion_thenReturnException(){
        java.setQuestion("");
        assertThrows(ConstraintViolationException.class, 
            () ->{
                service.save( java );
                entityManager.flush();
            } 
        );
    }

    @Test
    @DisplayName("saving question without answer")
    void givenQuestionWithoutAnswer_whenSaveQuestion_thenReturnException(){
        java.setAnswer("");
        assertThrows(ConstraintViolationException.class, 
            () -> {
                service.save( java );
                entityManager.flush();
            } 
        );
    }

    @Test
    @DisplayName("saving question too short")
    void givenQuestionWithQuestionTooShort_whenSaveQuestion_thenReturnException(){
        java.setQuestion("abc");
        assertThrows(ConstraintViolationException.class, 
            () -> {
                service.save( java );
                entityManager.flush();
            }
        );
    }

    @Test
    @DisplayName("saving answer too short")
    void givenQuestionWithAnswerTooShort_whenSaveQuestion_thenReturnException(){
        java.setAnswer("abc");
        assertThrows(ConstraintViolationException.class, 
            () -> {
                service.save( java );
                entityManager.flush();
            } 
        );
    }

    @Test
    @DisplayName("saving topic question too short")
    void givenQuestionWithTopicTooShort_whenSaveQuestion_thenReturnException(){
        java.setTopic("ab");
        assertThrows(ConstraintViolationException.class, 
            () -> {
                service.save( java );
                entityManager.flush();
            } 
        );
    }

    @Test
    @DisplayName("delete existing question entity")
    void givenAnExistingQuestion_whenDeleteById_thenReturnTrue(){
        java.setTheTopic(javaTopic);
        java = service.save(java);
        Long id = java.getId();
        assertTrue( service.deleteById(id));
    }

    @Test
    @DisplayName("delete un existing question entity")
    void givenAnNonExistingQuestion_whenDeleteById_thenReturnFalse(){
        
        assertFalse( service.deleteById(1000L));
    }

    @Test
    @DisplayName("finding all the questions with Java as the topic")
    void givenTheQuestionsList_whenFindByTopic_thenReturnListOfQuestionsWithTheTopic(){
        java.setTheTopic(javaTopic);
        java = service.save(java);

        Pageable pageable = PageRequest.of(0, 20);
        Page<QuestionEntity> javaQuestions = service.findByTopic("Java", pageable);

        assertEquals(3, javaQuestions.getContent().size());

    }

    @Test
    @DisplayName("find questions by topic and source")
    void givenTopicAndSource_whenFindQuestions_returnQuestionsWithTheTopicAndSource(){
        Pageable pageable = PageRequest.of(0, 20);
        Page<QuestionEntity> questions = service.findByTopicAndSource("Java", 1L, pageable);
        assertEquals(2, questions.getContent().size());
    }

    @Test
    @DisplayName("saving a question entity with source id")
    void givenQuestionAndSource_whenSaveQuestion_ReturnQuestionWithSourceAttached(){
        
        QuestionEntity questionEntity = QuestionEntity.builder()
            .question("What is junit")
            .answer("it is a testing framework")
            .topic("Java")
            .theTopic(javaTopic)
            .build();
     

        Long sourceId = source.getId();

        QuestionEntity saved = service.save( questionEntity,  sourceId);

        assertEquals(sourceId, saved.getSource().getId());
    }

}
