package com.learning.javainterviewquestions.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.learning.javainterviewquestions.entities.QuestionEntity;
import com.learning.javainterviewquestions.models.QuestionModel;
import com.learning.javainterviewquestions.repositories.QuestionRepository;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public Optional<QuestionEntity> findById( Long id ) {
        return questionRepository.findById(id);
    }

    public QuestionEntity save(QuestionEntity questionEntity) {
        return questionRepository.save( questionEntity );
    }

    public boolean deleteById ( Long id ){
        try{

            
                questionRepository.delete(findById(id).get());
                return true;
                

        } catch( Exception e){
            return false;
        }
    }

    public List<QuestionEntity> findAll() {
        return (List<QuestionEntity>) questionRepository.findAll();
    }

    public List<QuestionEntity>  save(List<QuestionEntity> questionEntities) {
        return (List<QuestionEntity>) questionRepository.saveAll( questionEntities );
    }

    public Page<QuestionEntity> findByTopic( String topic, Pageable pageable) {
        return questionRepository.findByTopic(topic, pageable);
    }

    public Page <QuestionEntity> findAll( Pageable pageable ) {
        return questionRepository.findAll(pageable);
    }

    public Set<String> getAllTopics(){
        return findAll().stream()
            .map( question -> question.getTopic())
            .collect(Collectors.toSet());
    }

}