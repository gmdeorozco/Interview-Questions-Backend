package com.learning.javainterviewquestions.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Entity @NoArgsConstructor @AllArgsConstructor @Data
public class Source{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double elo;

    @Column(length=500)
    private String sourceLink;

    @OneToMany(mappedBy="source")
    List<QuestionEntity> questions =  new ArrayList<>();

    private String topic;
}