package com.learning.javainterviewquestions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.javainterviewquestions.entities.QuestionInteraction;

public interface InteractionRepository extends JpaRepository<QuestionInteraction, Long> {
    
}
