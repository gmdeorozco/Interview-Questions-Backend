package com.learning.javainterviewquestions.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;

import com.learning.javainterviewquestions.entities.TopicEntity;
import com.learning.javainterviewquestions.repositories.TopicRepository;

@DataJpaTest
public class TopicServiceTest {

    @TestConfiguration
    static class TopicServiceTestConfiguration {

        @Bean
        TopicService topicService() {
            return new TopicService();
        }
    }


    @Autowired
    TopicService topicService;
    

    @Test
    @DisplayName("save topic to repository")
    void givenTopicEntity_whenSaving_thenReturnTopicEntityWithId()
    {
        TopicEntity topic = TopicEntity.builder()
        .name("Java")
        .build();

        TopicEntity savedTopic = topicService.save(topic);

        assertAll("saved", 
            () -> assertNotNull( savedTopic.getId()),
            () -> assertNotNull( savedTopic.getQuestions(), "questions list should not be null"),
            () -> assertNotNull( savedTopic.getSources(), "sources list should not be null")
        );
        
    }

    @Test
    @DisplayName("should not accept same name on topicEntities")
    void givenTwoEntitiesWithSameName_whenSavingEntities_ReturnException(){
        TopicEntity javaTopic = TopicEntity.builder()
        .name("Java")
        .build();

        TopicEntity sameNameTopic = TopicEntity.builder()
        .name("Java")
        .build();

        topicService.save( javaTopic );

        assertThrows(DataIntegrityViolationException.class, 
            () -> topicService.save( sameNameTopic )
        );

        
    }

    @Test
    @DisplayName("return list of all topic entities")
    void givenSavedEntities_whenFindAllTriggered_thenReturnListOfTopicEntities(){
        TopicEntity javaTopic = TopicEntity.builder()
        .name("Java")
        .build();

        TopicEntity javascriptTopic = TopicEntity.builder()
        .name("Javascript")
        .build();

        TopicEntity gitTopic = TopicEntity.builder()
        .name("Topic")
        .build();

        assertTrue( topicService.findAll().isEmpty() );

        topicService.save(gitTopic);
        topicService.save(javaTopic);
        topicService.save(javascriptTopic);

        assertEquals(3, topicService.findAll().size() );


    }

    @Test
    @DisplayName("get a Topic Entity by name")
    void givenSavedEntities_whenFindByName_returnTheRequestedTopic(){
        TopicEntity javaTopic = TopicEntity.builder()
        .name("Java")
        .build();

        TopicEntity javascriptTopic = TopicEntity.builder()
        .name("Javascript")
        .build();

        TopicEntity gitTopic = TopicEntity.builder()
        .name("Git")
        .build();

        topicService.save(gitTopic);
        topicService.save(javaTopic);
        topicService.save(javascriptTopic);

        assertEquals("Java", topicService.findByName("Java").get().getName());
        

    }

    @Test
    @DisplayName("return not present optional if search an unexisting topic")
    void givenUnexistingTopicName_whenFindByName_ReturnEmptyOptional()
    {
        TopicEntity gitTopic = TopicEntity.builder()
        .name("Git")
        .build();

        topicService.save(gitTopic);
        assertFalse(topicService.findByName("C++").isPresent());

    }

    
    
}
