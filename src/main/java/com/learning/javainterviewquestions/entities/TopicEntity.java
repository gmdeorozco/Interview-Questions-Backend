package com.learning.javainterviewquestions.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import org.springframework.validation.annotation.Validated;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Builder 
@Data 
@AllArgsConstructor 
@NoArgsConstructor
@Table(name = "topic")
public class TopicEntity implements Serializable {
    
    @Id @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( length=200, unique = true, nullable = false )
    @Size(min = 3, max = 200, message 
      = "Topic name must be between 3 and 200 characters")
    @NotBlank
    private String name;

    @Builder.Default
    @OneToMany( mappedBy="theTopic", cascade = CascadeType.MERGE )
    List<QuestionEntity> questions =  new ArrayList<>();

    @Builder.Default
    @OneToMany( mappedBy="theTopic", cascade = CascadeType.MERGE )
    List<Source> sources =  new ArrayList<>();
}
