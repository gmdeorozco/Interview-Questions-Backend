package com.learning.javainterviewquestions.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Builder @AllArgsConstructor @Data @NoArgsConstructor
public class MemberElo implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
   
    @ManyToOne
    @JsonIgnore
    private Member member;

    @ManyToOne
    @JsonIgnore
    private TopicEntity topic;

    @Builder.Default
    @PositiveOrZero
    private double elo = 1000; 

    @Builder.Default
    @ManyToMany
    @JsonIgnore
    private List<QuestionEntity> questionEntities = new ArrayList<>();
}