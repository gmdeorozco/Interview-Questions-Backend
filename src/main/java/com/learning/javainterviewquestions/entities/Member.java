package com.learning.javainterviewquestions.entities;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder

public class Member {

    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Builder.Default
    private double elo=1000;

    @Builder.Default
    @ManyToMany(cascade =
    {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinTable(
        name = "member_question", 
        joinColumns = @JoinColumn(name = "member_id"), 
        inverseJoinColumns = @JoinColumn(name = "question_id"))
    private List<QuestionEntity> answeredQuestions = new ArrayList<>();

    public int getNumberOfAnswers(){
        return this.answeredQuestions.size();
    }

}
