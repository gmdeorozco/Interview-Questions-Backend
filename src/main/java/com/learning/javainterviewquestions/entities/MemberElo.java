package com.learning.javainterviewquestions.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Builder @AllArgsConstructor @Data @NoArgsConstructor
@IdClass( EloType.class )
public class MemberElo implements Serializable{
    @Id
    @ManyToOne
    @JsonIgnore
    private Member member;

    @Id
    @ManyToOne
    @JsonIgnore
    private TopicEntity topic;

    @Builder.Default
    @PositiveOrZero
    private double elo = 1000; 

    @Builder.Default
    @OneToMany
    @JsonIgnore
    private List<QuestionEntity> questionEntities = new ArrayList<>();
}