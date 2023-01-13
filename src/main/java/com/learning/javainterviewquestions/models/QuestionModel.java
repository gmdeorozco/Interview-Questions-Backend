package com.learning.javainterviewquestions.models;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.learning.javainterviewquestions.entities.Source;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false)

public class QuestionModel extends RepresentationModel <QuestionModel> {

    private Long id;
    private String question;
    private String answer;
    private String topic;
    private SourceModel source;
    
    
}
