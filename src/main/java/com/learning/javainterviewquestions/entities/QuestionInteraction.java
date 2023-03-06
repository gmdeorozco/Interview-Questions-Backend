package com.learning.javainterviewquestions.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public
class QuestionInteraction{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @JsonIgnore
    private Member member;

    @JsonIgnore
    private QuestionEntity question;

    @Builder.Default
    private LocalDate date = LocalDate.now();

}