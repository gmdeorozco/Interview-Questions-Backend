package com.learning.javainterviewquestions.controllers;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Retry.Topic;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.javainterviewquestions.entities.TopicEntity;
import com.learning.javainterviewquestions.services.QuestionService;
import com.learning.javainterviewquestions.services.SourcesService;
import com.learning.javainterviewquestions.services.TopicService;

@RestController
@RequestMapping( "/api/v1" )
@CrossOrigin(origins = "*")

public class TopicController {

    @Autowired
    QuestionService questionService;

    @Autowired
    TopicService topicService;

    @Autowired 
    SourcesService sourcesService;

    @GetMapping("question/topics")
    public List<String> getAllTopics(){
        return findAll();
    }

    @GetMapping("/topics")
    public List<String> findAll(){
        return topicService.findAll().stream()
            .map( t -> t.getName())
            .toList();
    }

    @PostMapping("/topics/create")
    public TopicEntity creaTopicEntity( @RequestBody TopicEntity entity ){
        
        return topicService.save(entity);
    }


    
}
