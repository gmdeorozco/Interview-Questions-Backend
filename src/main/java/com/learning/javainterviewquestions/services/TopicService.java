package com.learning.javainterviewquestions.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.javainterviewquestions.entities.TopicEntity;
import com.learning.javainterviewquestions.repositories.TopicRepository;

@Service
public class TopicService {

    @Autowired
    TopicRepository topicRepository;

    public TopicEntity save( TopicEntity topic ){
        return topicRepository.save( topic );
    }

    public List<TopicEntity> findAll() {
        return topicRepository.findAll();
    }

    public Optional<TopicEntity> findByName(String topic) {
        return Optional.ofNullable(topicRepository.findByName( topic ));
    }
    
}
