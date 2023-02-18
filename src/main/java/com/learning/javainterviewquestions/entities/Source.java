package com.learning.javainterviewquestions.entities;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

@Entity @NoArgsConstructor @AllArgsConstructor @Data @Builder
public class Source {

    @Id @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(length =  200, nullable = false )
    @Size(max = 200, min = 5)
    private String name;

    @Positive
    @Builder.Default
    private double elo = 1000;

    @Column( length=2048, nullable = false, unique = true)
    @Size( max = 2048)
    @URL
    private String sourceLink;

    @Builder.Default
    @OneToMany( mappedBy="source", cascade = CascadeType.MERGE )
    List<QuestionEntity> questions =  new ArrayList<>();

    @Column( nullable = false)
    @Size(max = 200, min = 3)
    private String topic;

    @ManyToOne
    @JoinColumn(name="theTopic_id", nullable = false)
    private TopicEntity theTopic;

}