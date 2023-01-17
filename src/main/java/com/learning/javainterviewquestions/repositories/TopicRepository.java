package com.learning.javainterviewquestions.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.learning.javainterviewquestions.entities.TopicEntity;

public interface TopicRepository extends  JpaRepository < TopicEntity, Long >{

    TopicEntity findByName(String topic);
    
}
