package com.learning.javainterviewquestions.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import com.learning.javainterviewquestions.elo.EloResult;
import com.learning.javainterviewquestions.entities.Member;
import com.learning.javainterviewquestions.entities.MemberElo;
import com.learning.javainterviewquestions.entities.QuestionEntity;
import com.learning.javainterviewquestions.entities.TopicEntity;
import com.learning.javainterviewquestions.entities.QuestionInteraction;
import com.learning.javainterviewquestions.repositories.InteractionRepository;
import com.learning.javainterviewquestions.repositories.MemberEloRepository;
import com.learning.javainterviewquestions.repositories.TopicRepository;
import com.learning.javainterviewquestions.services.MemberService;
import com.learning.javainterviewquestions.services.QuestionService;


import jakarta.transaction.Transactional;

@RestController
@RequestMapping("api/v1/member")
@CrossOrigin(origins = "*")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    QuestionService questionService;

    @Autowired
    MemberEloRepository memberEloRepository;

    @Autowired
    InteractionRepository interactionRepository;


    @GetMapping("/{memberId}")
    public Member getById( @PathVariable Long memberId){
        return memberService.findById(memberId).get();
    }

    @GetMapping("elo/{memberId}/{topic}")
    public MemberElo getByEloByMemberAndTopic( @PathVariable Long memberId , 
        @PathVariable String topic){

        TopicEntity topicEntity = topicRepository.findByName(topic);
        Member member = memberService.findById(memberId).get();

       

        List<MemberElo> memberElos = memberEloRepository.findAll();

        List<MemberElo> returnMember = memberElos.stream()
            .filter( m -> m != null )
            .filter(m -> m.getTopic().getName().equalsIgnoreCase( topicEntity.getName() ))
            .filter(m -> m.getMember().getId() == memberId )
            .toList();

        if( returnMember.isEmpty() ){
            return memberEloRepository.save(MemberElo.builder()
                .member(member)
                .topic(topicEntity)
                .build());

        }

        return returnMember
            .get(0);
    }
    

   
    @Transactional
    @GetMapping( "/{memberId}/{questionId}/{whoWon}" )
    public ResponseEntity<EloResult> processElos(
        @PathVariable Long memberId, 
        @PathVariable Long questionId, 
        @PathVariable int whoWon){

        Member member = memberService.findById( memberId ).get();
        QuestionEntity question = questionService.findById( questionId).get();
        String topicEntity = question.getTopic();
       

        MemberElo memberElo = memberEloRepository.findAll().stream()
            .filter(m -> m.getTopic().getName().equalsIgnoreCase( topicEntity ))
            .filter(m -> m.getMember().getId() == member.getId() )
            .toList()
            .get(0);

        double eloFirstPlayer = memberElo.getElo();
        double eloSecondPlayer = question.getElo();

        int nGamesFirstPlayer = memberElo.getQuestionEntities().size();
        int nGamesSecondPlayer = question.getNumberOfAnswers();


        WebClient client = WebClient.create(
            "https://eloservice-production.up.railway.app/elo/"
                
                ) ;
            
       
        EloResult createdResult = client.get()
            .uri(eloFirstPlayer
            +"/"+eloSecondPlayer
            +"/"+nGamesFirstPlayer
            +"/"+nGamesSecondPlayer
            +"/"+whoWon)
            .retrieve()
            .bodyToMono(EloResult.class)
            .block();
        
        question.getMembersWhoAnswered().add( member );
        member.getAnsweredQuestions().add( question );
        memberElo.getQuestionEntities().add( question );
        

        memberElo.setElo( Double.valueOf( createdResult.getPlayer1().getNewElo() ) );
        question.setElo( Double.valueOf( createdResult.getPlayer2().getNewElo() ) );

        
        
        memberEloRepository.save( memberElo);

        QuestionInteraction interaction = QuestionInteraction.builder()
            .member(member)
            .question(question)
            .build();

        member.getInteractions().add(interaction);
        question.getInteractions().add(interaction);

        interactionRepository.save( interaction );
        questionService.save( question );
        memberService.save( member );
        
        

        return ResponseEntity.ok( createdResult );
    }
}
