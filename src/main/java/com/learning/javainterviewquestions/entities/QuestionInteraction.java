package com.learning.javainterviewquestions.entities;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class QuestionInteraction{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private Member member;
    private QuestionEntity question;

    @Builder.Default
    private LocalDate date = LocalDate.now();

}