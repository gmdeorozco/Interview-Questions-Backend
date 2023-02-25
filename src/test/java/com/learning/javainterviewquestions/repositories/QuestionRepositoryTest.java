package com.learning.javainterviewquestions.repositories;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.learning.javainterviewquestions.entities.QuestionEntity;
import com.learning.javainterviewquestions.entities.Source;
import com.learning.javainterviewquestions.entities.TopicEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;

@DataJpaTest
public class QuestionRepositoryTest {

    @Autowired
    QuestionRepository repository;

    @Autowired 
    TopicRepository topicRepository;

    @Autowired
    SourceRepository sourcesRepository;

    @Autowired
    EntityManager entityManager;
  
    TopicEntity javaTopic, javascriptTopic, gitTopic;
    QuestionEntity java, springboot, junit, git, javascript;
    Source source;

    @BeforeEach
    public void setUp(){
        
        javaTopic = TopicEntity.builder()
            .name("Java")
            .build();
        topicRepository.save(javaTopic);

        javascriptTopic = TopicEntity.builder()
        .name("Javascript")
        .build();
        topicRepository.save(javascriptTopic);

        gitTopic = TopicEntity.builder()
        .name("Git")
        .build();
        topicRepository.save(gitTopic);

        source = Source.builder()
            .name("java book")
            .sourceLink("http://java.com")
            .topic("Java")
            .theTopic(javaTopic)
            .build();
        sourcesRepository.save(source);
        
        java = QuestionEntity.builder()
            .question("What is Java")
            .answer("It is a programming language")
            .topic("Java")
            .theTopic(javaTopic)
            .build();
        
        springboot = QuestionEntity.builder()
            .question("What is Springboot")
            .answer("It is a programming framework")
            .topic("Java")
            .source(source)
            .build();

        junit = QuestionEntity.builder()
            .question("What is JUnit")
            .answer("It is a programming testing framework")
            .topic("Java")
            .source(source)
            .build();

        git = QuestionEntity.builder()
            .question("What is Git")
            .answer("It is a versioning system")
            .topic("Git")
            .build();

        javascript = QuestionEntity.builder()
            .question("What is Javascript")
            .answer("It is a interpreted programming language")
            .topic("Javascript")
            .build();

            javascript.setTheTopic(javascriptTopic);
            javascript = repository.save(javascript);
    
            springboot.setTheTopic(javaTopic);
            springboot = repository.save(springboot);
    
            git.setTheTopic(gitTopic);
            git = repository.save(git);
    
            junit.setTheTopic(javaTopic);
            junit = repository.save(junit);
    }

 

    @Test
    @DisplayName("saving question without topic")
    void givenQuestionWithoutTopic_whenSaveQuestion_thenReturnException(){
        java.setTopic(null);
        assertThrows(PersistenceException.class, 
            () -> {
                repository.save( java );
                entityManager.flush();
            }
        );
    }

    @Test
    @DisplayName("saving question without question")
    void givenQuestionWithoutQuestion_whenSaveQuestion_thenReturnException(){
        java.setQuestion("");
        assertThrows(ConstraintViolationException.class, 
            () ->{
                repository.save( java );
                entityManager.flush();
            } 
        );
    }

  
    @Test
    @DisplayName("saving question too short")
    void givenQuestionWithQuestionTooShort_whenSaveQuestion_thenReturnException(){
        java.setQuestion("abc");
        assertThrows(ConstraintViolationException.class, 
            () -> {
                repository.save( java );
                entityManager.flush();
            }
        );
    }

 

    @Test
    @DisplayName("saving topic question too short")
    void givenQuestionWithTopicTooShort_whenSaveQuestion_thenReturnException(){
        java.setTopic("ab");
        assertThrows(ConstraintViolationException.class, 
            () -> {
                repository.save( java );
                entityManager.flush();
            } 
        );
    }



    @Test
    @DisplayName("finding all the questions with Java as the topic")
    void givenTheQuestionsList_whenFindByTopic_thenReturnListOfQuestionsWithTheTopic(){
        java.setTheTopic(javaTopic);
        java = repository.save(java);

        Pageable pageable = PageRequest.of(0, 20);
        Page<QuestionEntity> javaQuestions = repository.findByTopic("Java", pageable);

        assertEquals(3, javaQuestions.getContent().size());

    }


}
