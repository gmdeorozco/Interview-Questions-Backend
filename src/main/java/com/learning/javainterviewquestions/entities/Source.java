package com.learning.javainterviewquestions.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Source {
    @Id
    private Long id;

    private double elo;

    @OneToMany( mappedBy = "source")
    private List<QuestionEntity> questions;

    private String sourceLink;
    private String topic;


}
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

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Source{

    @Id
    private Long id;

    private double elo;

    @Column(length=500)
    private String sourceLink;

    @OneToMany(mappedBy="source")
    List<QuestionEntity> questions =  new ArrayList<>();
}