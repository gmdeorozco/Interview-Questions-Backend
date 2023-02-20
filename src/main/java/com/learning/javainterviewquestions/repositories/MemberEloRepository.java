package com.learning.javainterviewquestions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.javainterviewquestions.entities.MemberElo;

public interface MemberEloRepository extends JpaRepository <  MemberElo, Long >{
    
}
