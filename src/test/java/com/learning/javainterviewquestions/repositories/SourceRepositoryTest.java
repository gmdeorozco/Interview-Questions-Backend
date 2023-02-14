package com.learning.javainterviewquestions.repositories;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.learning.javainterviewquestions.entities.Source;
import com.learning.javainterviewquestions.entities.TopicEntity;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;

@DataJpaTest
public class SourceRepositoryTest {

    @Autowired 
    SourceRepository repository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    private EntityManager entityManager;

    TopicEntity topic;

    @BeforeEach
    void setUp(){
        topic = TopicEntity.builder()
            .name("Java")
            .build();

        topicRepository.save(topic);
    }


    @Test
    @DisplayName("should not save Source with null name")
    void givenSourceWithoutName_whenSave_ReturnException(){
        
        Source source = Source.builder()
            .theTopic(topic)
            .topic("Java")
            .sourceLink("http://www.java.com")
            .build();
        
        assertThrows(DataIntegrityViolationException.class, 
            () -> repository.save(source)
        );
    }

    @Test
    @DisplayName("should not save Source with name less than 5 characters")
    void givenSourceWithNameTooShort_whenSave_ReturnException(){
        
        Source source = Source.builder()
            .sourceLink("http://www.link.com")
            .topic("Java")
            .name("ssre")
            .theTopic(topic)
            .build();
        
       // assertTrue(repository.save(source).getId() > 0);
        assertThrows(ConstraintViolationException.class, 
            () -> {
                repository.save(source);
                entityManager.flush();
            }
        );
    }
    
    @Test
    @DisplayName("should not save sources with same link")
    void givenSourcesWithSameLink_whenSave_thenThrowException(){
        Source source = Source.builder()
            .sourceLink("http://www.link.com")
            .topic("Java")
            .name("name 1")
            .theTopic(topic)
            .build();
            
        Source source2 = Source.builder()
            .sourceLink("http://www.link.com")
            .topic("Java")
            .name("name 2")
            .theTopic(topic)
            .build();

        repository.save(source);

        assertThrows(DataIntegrityViolationException.class, () ->
            repository.save(source2)
        );
    }

    @Test
    @DisplayName("should not save Source wrong link")
    void givenSourceWithWrongLink_whenSave_ReturnException(){
        
        Source source = Source.builder()
            .sourceLink("htt://www.link.com")
            .topic("Java")
            .name("Source name")
            .theTopic(topic)
            .build();
        
       // assertTrue(repository.save(source).getId() > 0);
        assertThrows(ConstraintViolationException.class, 
            () -> {
                repository.save(source);
                entityManager.flush();
            }
        );
    }

    @Test
    @DisplayName("should not save elo that is not numeric positive")
    void givenSourceWithWrongElo_whenSave_ReturnException(){
        
        Source source = Source.builder()
            .sourceLink("https://www.link.com")
            .topic("Java")
            .name("Source name")
            .theTopic(topic)
            .elo(-5)
            .build();
        
       // assertTrue(repository.save(source).getId() > 0);
        assertThrows(ConstraintViolationException.class, 
            () -> {
                repository.save(source);
                entityManager.flush();
            }
        );
    }
}


