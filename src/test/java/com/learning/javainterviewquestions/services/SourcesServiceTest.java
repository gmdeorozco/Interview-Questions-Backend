package com.learning.javainterviewquestions.services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.learning.javainterviewquestions.entities.Source;
import com.learning.javainterviewquestions.entities.TopicEntity;
import com.learning.javainterviewquestions.repositories.SourceRepository;
import com.learning.javainterviewquestions.repositories.TopicRepository;

@DataJpaTest
public class SourcesServiceTest {
    @SpyBean
    SourceRepository repository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    SourcesService service;

    Source source;
    TopicEntity java;

    @BeforeEach
    void setUp(){
        java = TopicEntity.builder()
            .name("Java")
            .build();
        
        topicRepository.save(java);
        
        source = Source.builder()
            .name("Source name")
            .sourceLink("http://www.java.com")
            .topic("Java")
            .theTopic(java)
            .build();
        
        repository.save(source);
        
        
    }

    @DisplayName("should return true deleting existing entity by id")
    @Test
    void givenExistingIdOfASource_whenDeleteById_thenReturnTrue(){
        Long id = source.getId();
        assertTrue( service.deleteById(id));
    }


}
