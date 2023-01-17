package com.learning.javainterviewquestions.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.learning.javainterviewquestions.entities.QuestionEntity;

public interface QuestionRepository extends  JpaRepository < QuestionEntity, Long >{

    Page<QuestionEntity> findByTopic ( String topic, Pageable pageable );

    Page<QuestionEntity> findByTopicAndSource(String topic, Long sourceId, Pageable pageable);
    
}
