package com.learning.javainterviewquestions.repositories;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.learning.javainterviewquestions.entities.TopicEntity;

import jakarta.validation.ConstraintViolationException;

@DataJpaTest
public class TopicRepositoryTest {

@Autowired
TopicRepository repository;

TopicEntity topicA, topicB;

@BeforeEach
public void setUp(){
    topicA = TopicEntity.builder()
        .name("Java")
        .build();
    
    topicB = TopicEntity.builder()
        .name("Java")
        .build();

}



    @Test
    @DisplayName("should not save topics with same names")
    void giventopicsWithSameName_whenSaveThem_thenThrowException(){
        repository.save(topicA);

        assertThrows(DataIntegrityViolationException.class,
            () -> repository.save(topicB));

    }

    @Test
    @DisplayName("should not save topic with a null name")
    void givenNullTopicName_whenSaveTopic_thenThrowException(){
        TopicEntity topic = TopicEntity.builder()
            .build();
        assertThrows(ConstraintViolationException.class,
            () -> repository.save(topic));

    }

     @Test
     @DisplayName("should not save topic with a name shorter than 3 characters")
    void givenShortTopicName_whenSaveTopic_thenThrowException(){
        TopicEntity topic = TopicEntity.builder()
            .name("22")
            .build();

        assertThrows(ConstraintViolationException.class,
            () -> repository.save(topic));

    }
}