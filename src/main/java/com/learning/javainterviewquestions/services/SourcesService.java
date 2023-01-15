package com.learning.javainterviewquestions.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import com.learning.javainterviewquestions.entities.Source;
import com.learning.javainterviewquestions.models.SourceModel;
import com.learning.javainterviewquestions.repositories.SourceRepository;

@Service
public class SourcesService {

    @Autowired
    SourceRepository sourceRepository;

    public Optional<Source> findById(Long id) {
        return sourceRepository.findById(id);
    }

	public boolean deleteById(Long id) {
		try{

            
            sourceRepository.delete(findById(id).get());
            return true;
            

    } catch( Exception e){
        return false;
    }
	}

    public Source save(Source entity) {
        return sourceRepository.save(entity);
    }

    public Page<Source> findAll(Pageable pageable) {
        return sourceRepository.findAll(pageable);
    }

    public Set<String> getAllTopics() {
        return findAll().stream()
        .map( source -> source.getTopic())
        .collect(Collectors.toSet());
    }

    public List<Source> findAll() {
        return (List<Source>) sourceRepository.findAll();
    }

    public Page<Source> findByTopic(String topic, Pageable pageable) {
        return sourceRepository.findByTopic(topic, pageable);
    }


    public ArrayList<Source> findByTopic(String topic) {
        return (ArrayList<Source>) sourceRepository.findByTopic(topic);
    }
    
}
