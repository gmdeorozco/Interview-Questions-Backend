package com.learning.javainterviewquestions.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String question;

    @Lob
    @Column(length=9000)
    
    private String answer;
    private String topic;
    private double elo;

    @ManyToOne
    @JoinColumn(name="question_id", nullable=true)
    private Source source;

   

}
