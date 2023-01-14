package com.learning.javainterviewquestions.models;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SourceModel extends RepresentationModel <SourceModel>{

    private Long id;
    private double elo;
    private List<QuestionModel> questions;
    private String sourceLink;
    private String topic;
    private String name;
}
