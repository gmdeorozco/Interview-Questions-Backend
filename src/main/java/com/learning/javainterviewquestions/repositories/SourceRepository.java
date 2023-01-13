package com.learning.javainterviewquestions.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.javainterviewquestions.entities.Source;

public interface SourceRepository extends  JpaRepository < Source, Long >{
    Page<Source> findByTopic ( String topic, Pageable pageable );
}
