package com.learning.javainterviewquestions.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.learning.javainterviewquestions.entities.QuestionEntity;
import com.learning.javainterviewquestions.entities.Source;
import com.learning.javainterviewquestions.repositories.QuestionRepository;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired 
    SourcesService sourcesService;

    public QuestionEntity save(QuestionEntity questionEntity) {
        return questionRepository.save( questionEntity );
    }

    public Optional<QuestionEntity> findById( Long id ) {
        return questionRepository.findById(id);
    }

    public boolean deleteById ( Long id ){
        try{
                QuestionEntity entity = findById(id).get();
                entity.getTheTopic().getQuestions().remove(entity);
                questionRepository.delete(findById(id).get());
                return true;   
        } catch( Exception e){
            return false;
        }
    }

    public List<QuestionEntity> findAll() {
        return (List<QuestionEntity>) questionRepository.findAll();
    }

    public List<QuestionEntity>  save(List<QuestionEntity> questionEntities) {
        return (List<QuestionEntity>) questionRepository.saveAll( questionEntities );
    }

    public Page<QuestionEntity> findByTopic( String topic, Pageable pageable) {
        return questionRepository.findByTopic(topic, pageable);
    }

    public Page<QuestionEntity> findByTopicAndSource( String topic, Long sourceId, Pageable pageable) {
        List<QuestionEntity> users = questionRepository.findAll().stream()
            .filter(question -> question.getSource() != null )
            .filter(question -> question.getSource().getId() == sourceId)
            .toList();

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), users.size());
        final Page<QuestionEntity> page = new PageImpl<>(users.subList(start, end), pageable, users.size());

        return page;
            
    }

    public Page <QuestionEntity> findAll( Pageable pageable ) {
        return questionRepository.findAll(pageable);
    }

    
    public QuestionEntity setSource( Long questionId, Long sourceId ){
       
            QuestionEntity question = findById( questionId ).get();
            Source source = sourcesService.findById( sourceId ).get();

            question.setSource(source);
            return save(question);


    }

    public QuestionEntity save(QuestionEntity questionEntity, Long sourceId) {
        QuestionEntity question = save(questionEntity);
        Source source = sourcesService.findById(sourceId).get();
        question.setSource( source );
        source.getQuestions().add(question);
        sourcesService.save(source);

        return question;
    }

}
