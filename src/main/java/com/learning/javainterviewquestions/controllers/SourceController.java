package com.learning.javainterviewquestions.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learning.javainterviewquestions.assemblers.SourceModelAssembler;
import com.learning.javainterviewquestions.entities.Source;
import com.learning.javainterviewquestions.entities.TopicEntity;
import com.learning.javainterviewquestions.models.SourceModel;
import com.learning.javainterviewquestions.services.SourcesService;
import com.learning.javainterviewquestions.services.TopicService;
import com.mysql.cj.util.StringUtils;

@RestController
@RequestMapping( "/api/v1" )
@CrossOrigin(origins = "*")
public class SourceController {

    @Autowired
    SourcesService sourcesService;

    @Autowired
    SourceModelAssembler sourceModelAssembler;

    @Autowired
    private PagedResourcesAssembler<Source> pagedResourcesAssembler;

    @Autowired
    TopicService topicService;

    @GetMapping("/source/{id}")
    public ResponseEntity<SourceModel> findById(@PathVariable (value = "id") Long id) {
        return sourcesService.findById( id )
            .map( sourceModelAssembler :: toModel )
            .map( ResponseEntity :: ok)
            .orElse( ResponseEntity.notFound().build());
    }

    @DeleteMapping("/source/{id}/delete")
    public ResponseEntity<SourceModel> deleteById( @PathVariable(value = "id") Long id) {

        ResponseEntity<SourceModel> model = sourcesService.findById( id )
            .map( sourceModelAssembler :: toModel )
            .map( ResponseEntity :: ok)
            .orElse( ResponseEntity.notFound().build());

        return sourcesService.deleteById(id) ? model : ResponseEntity.notFound().build();
    }

    @PutMapping("source/update")
    public ResponseEntity<SourceModel> update( @RequestBody Source entity) {
        return ResponseEntity.ok(
                (sourceModelAssembler.toModel( sourcesService.save ( entity ) )));
    }

    @GetMapping("source/allpaginated")
    public ResponseEntity<CollectionModel<SourceModel>> findAllPaginated( @RequestParam(defaultValue = "0")int page
    , @RequestParam(defaultValue = "10") int size ) {
        
        Pageable pageable = PageRequest.of(page, size);
        
        Page<Source> sourceEntities = sourcesService.findAll(pageable);
        
        return new ResponseEntity<>
            (pagedResourcesAssembler.toModel(sourceEntities
                ,sourceModelAssembler), HttpStatus.OK);
    }

    @PostMapping("source/create")
    public ResponseEntity<SourceModel> save( @RequestBody Source source) {

        TopicEntity topic = topicService.findByName(source.getTopic()).get();
        source.setTheTopic(topic);

        Source s = sourcesService.save ( source );
        topic.getSources().add(s);

            return ResponseEntity.ok(
                (sourceModelAssembler.toModel( s )));
             
    }

    @GetMapping("source/topics")
    public Set<String> getAllTopics(){
        return sourcesService.getAllTopics();
    }

    @GetMapping("source/topic/{topic}")
    public ResponseEntity<CollectionModel<SourceModel>> 
        findByTopic( 
            @PathVariable(name = "topic", required = false) String topic,
    @RequestParam(defaultValue = "0")int page
    , @RequestParam(defaultValue = "10") int size) {


        Pageable pageable = PageRequest.of(page, size);

        Page<Source> questionEntities = (topic!=null && topic!="all" && topic!="") ? 
            sourcesService.findByTopic( topic, pageable )
            : sourcesService.findAll(pageable);
        
        return new ResponseEntity<>
            (pagedResourcesAssembler.toModel(questionEntities
                ,sourceModelAssembler), HttpStatus.OK);
    }

    @GetMapping( value = {"source/topic/{topic}/all","source/topic/all"} )
    public ResponseEntity<CollectionModel<SourceModel>> findByTopic( 
        @PathVariable(required = false) String topic ) {


        ArrayList<Source> sourceEntities = 
            topic != null ? 
            sourcesService.findByTopic( topic )
            : (ArrayList<Source>) sourcesService.findAll();
        
        return new ResponseEntity<>(
            sourceModelAssembler.toCollectionModel(sourceEntities),
            HttpStatus.OK);
    }

    @GetMapping("source/all")
    public ResponseEntity<CollectionModel<SourceModel>> findAll() {
        List<Source> enitities = sourcesService.findAll();
        
        return new ResponseEntity<>(
            sourceModelAssembler.toCollectionModel(enitities),
            HttpStatus.OK
        );
    }
    
}
