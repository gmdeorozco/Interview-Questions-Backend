package com.learning.javainterviewquestions.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Builder @Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "topic")
public class TopicEntity {
    
    @Id @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( unique = true )
    private String name;

    @OneToMany( mappedBy="theTopic", cascade = CascadeType.MERGE )
    List<QuestionEntity> questions =  new ArrayList<>();

    @OneToMany( mappedBy="theTopic", cascade = CascadeType.MERGE )
    List<Source> sources =  new ArrayList<>();
}
