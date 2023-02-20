package com.learning.javainterviewquestions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.javainterviewquestions.entities.Member;

public interface MemberRepository extends  JpaRepository < Member, Long > {
    
}
