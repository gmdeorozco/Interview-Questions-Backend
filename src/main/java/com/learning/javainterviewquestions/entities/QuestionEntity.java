package com.learning.javainterviewquestions.entities;

import java.io.Serializable;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @Lob
    @Column(length=9000, nullable = false, unique = true)
    @Size(max = 9000, min = 5)
    @NotBlank
    private String question;

    @Lob
    @Column(length=9000, nullable = false)
    @Size(max = 9000, min = 5)
    @NotBlank
    private String answer;

    @Lob
    @Column(length=9000)
    private String code_snippet;

    @Column(nullable = false)
    @Size(min = 3)
    private String topic;

    @ManyToOne
    @JoinColumn(name="theTopic_id", nullable = false)
    private TopicEntity theTopic;

    private double elo;

    @ManyToOne
    @JoinColumn(name="source_id", nullable=true)
    private Source source;


}
