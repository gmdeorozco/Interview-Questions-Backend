
package com.learning.javainterviewquestions.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Entity @NoArgsConstructor @AllArgsConstructor @Data
public class Source{

    @Id
    private Long id;

    private double elo;

    @Column(length=500)
    private String sourceLink;

    @OneToMany(mappedBy="source")
    List<QuestionEntity> questions =  new ArrayList<>();

    private String topic;
}