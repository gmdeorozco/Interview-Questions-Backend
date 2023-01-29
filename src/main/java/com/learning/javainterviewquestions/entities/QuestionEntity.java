package com.learning.javainterviewquestions.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
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

    @Lob
    @Column(length=9000)
    private String question;

    @Lob
    @Column(length=9000)
    private String answer;

    @Lob
    @Column(length=9000)
    private String code_snippet;

    private String topic;

    @ManyToOne
    @JoinColumn(name="theTopic_id")
    private TopicEntity theTopic;

    

    private double elo;

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
