package com.learning.javainterviewquestions.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.learning.javainterviewquestions.controllers.QuestionController;
import com.learning.javainterviewquestions.controllers.SourceController;
import com.learning.javainterviewquestions.entities.QuestionEntity;
import com.learning.javainterviewquestions.entities.QuestionInteraction;
import com.learning.javainterviewquestions.entities.Source;
import com.learning.javainterviewquestions.models.QuestionModel;
import com.learning.javainterviewquestions.models.SourceModel;
import com.learning.javainterviewquestions.services.QuestionService;



import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class QuestionModelAssembler extends
    RepresentationModelAssemblerSupport <QuestionEntity, QuestionModel> 
{
    public QuestionModelAssembler(){
        super( QuestionController.class, QuestionModel.class);
    }

    @Autowired
    QuestionService questionService;


    @Override
    public QuestionModel toModel(QuestionEntity  entity) {

        QuestionModel questionModel = instantiateModel(entity);

        questionModel.add( linkTo(
            methodOn( QuestionController.class )
                .findById( entity.getId() )
        ).withSelfRel());

        questionModel.add( linkTo( 
            methodOn(QuestionController.class)
                .deleteById( entity.getId())
         ).withRel("delete"));

         questionModel.add( linkTo(
            methodOn(QuestionController.class)
                .findAll()
         ).withRel("findAll"));

         questionModel.add( linkTo(
            methodOn(QuestionController.class)
                .update( entity )
         ).withRel("update"));

        List<QuestionInteraction> interactions  = entity.getInteractions().stream()
            .filter( inter -> inter.getMember().getId() == 1L )
            .toList();

        Long daysFromLastInteraction = -1L;
        LocalDate lastInteractionDate = null;

        if( interactions.isEmpty() == false ){
            lastInteractionDate = interactions.stream()
                .filter( inter -> inter.getMember().getId() == 1L )
                .map( inter -> inter.getDate() )
                .max( LocalDate::compareTo)
                .get();
            
            daysFromLastInteraction = ChronoUnit.DAYS.between(lastInteractionDate, LocalDate.now());
        }

       
        
        

        questionModel.setLastInteractionDays( daysFromLastInteraction );

       

        questionModel.setId( entity.getId() );
        questionModel.setQuestion( entity.getQuestion());
        questionModel.setAnswer( entity.getAnswer());
        questionModel.setTopic( entity.getTheTopic().getName() );
        questionModel.setSource( toSourceModel( entity.getSource()) );
        questionModel.setCode_snippet(entity.getCode_snippet());
        questionModel.setNumberOfAnswers( entity.getNumberOfAnswers());
        questionModel.setElo(entity.getElo());
        

        return questionModel;
    }


    private SourceModel toSourceModel(Source source) {
        if ( source == null  )
        return null;

        return SourceModel.builder()
            .id( source.getId())
            .elo( source.getElo())
            .sourceLink( source.getSourceLink())
            .topic( source.getTheTopic().getName() )
            .name( source.getName())
            .build() 
        .add( linkTo(
            methodOn(SourceController.class)
                .findById( source.getId() )
        ).withSelfRel());
        
    }

    
    
}
