package com.learning.javainterviewquestions.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private Member member;

    @Id
    @ManyToOne
    private TopicEntity topic;

    @Builder.Default
    @PositiveOrZero
    private double elo = 1000; 

    @Builder.Default
    @OneToMany
    private List<QuestionEntity> questionEntities = new ArrayList<>();
}