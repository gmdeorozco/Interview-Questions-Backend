package com.learning.javainterviewquestions.assemblers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.learning.javainterviewquestions.controllers.QuestionController;
import com.learning.javainterviewquestions.controllers.SourceController;
import com.learning.javainterviewquestions.entities.QuestionEntity;
import com.learning.javainterviewquestions.entities.Source;
import com.learning.javainterviewquestions.models.QuestionModel;
import com.learning.javainterviewquestions.models.SourceModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SourceModelAssembler extends
RepresentationModelAssemblerSupport <Source, SourceModel> {

    public SourceModelAssembler(){
        super( QuestionController.class, SourceModel.class);
    }

    @Override
    public SourceModel toModel(Source entity) {
        
        SourceModel sourceModel = instantiateModel(entity);
        sourceModel.setId( entity.getId() );
        sourceModel.setElo( entity.getElo() );
        sourceModel.setQuestions( toQuestionModel( entity.getQuestions()));
        sourceModel.setSourceLink( entity.getSourceLink());
        sourceModel.setTopic( entity.getTopic());
        sourceModel.setName( entity.getName());
        
        sourceModel.add(
            linkTo(
                methodOn(SourceController.class )
                    .findById( entity.getId() )
            ).withSelfRel()
        );

        sourceModel.add( linkTo( 
            methodOn(SourceController.class)
                .deleteById( entity.getId())
         ).withRel("delete"));

         sourceModel.add( linkTo(
            methodOn(SourceController.class)
                .findAll()
         ).withRel("findAll"));

         sourceModel.add( linkTo(
            methodOn(SourceController.class)
                .update( entity )
         ).withRel("update"));

        return sourceModel;
    }

    private List<QuestionModel> toQuestionModel(List<QuestionEntity> questions) {
        if( questions.isEmpty() ){
            new ArrayList<>();
         }
        return questions.stream()
        .map( question -> QuestionModel.builder()
            .answer( question.getAnswer())
            .id( question.getId())
            .question( question.getQuestion())
            .answer( question.getAnswer())
            .topic( question.getTopic())
            .build())
        .collect( Collectors.toList());
    }
    
}
