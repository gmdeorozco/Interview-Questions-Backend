package com.learning.javainterviewquestions.entities;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
    @Column( length=9000, nullable = true )
    @Size( max = 9000, min = 0 )
    private String answer;

    @Lob
    @Column(length=9000 , nullable = true )
    @Size(max = 9000, min = 0)
    private String code_snippet;

    @Column(nullable = false)
    @Size(min = 3)
    private String topic;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="theTopic_id", nullable = false)
    private TopicEntity theTopic;

    
    @Builder.Default
    @Positive
    private double elo = 1000;

    @ManyToOne
    @JoinColumn(name="source_id", nullable=true)
    private Source source;

    @JsonIgnore
    @Builder.Default
    @ManyToMany(mappedBy = "answeredQuestions")
    private List<Member> membersWhoAnswered =  new ArrayList<>();

    public int getNumberOfAnswers(){
        return this.membersWhoAnswered.size();
    }


}
