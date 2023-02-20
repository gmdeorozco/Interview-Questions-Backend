package com.learning.javainterviewquestions.controllers;

import java.net.URI;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;
import org.springframework.web.util.UriBuilder;

import com.learning.javainterviewquestions.elo.EloResult;
import com.learning.javainterviewquestions.entities.EloType;
import com.learning.javainterviewquestions.entities.Member;
import com.learning.javainterviewquestions.entities.MemberElo;
import com.learning.javainterviewquestions.entities.QuestionEntity;
import com.learning.javainterviewquestions.entities.TopicEntity;
import com.learning.javainterviewquestions.repositories.MemberEloRepository;
import com.learning.javainterviewquestions.repositories.TopicRepository;
import com.learning.javainterviewquestions.services.MemberService;
import com.learning.javainterviewquestions.services.QuestionService;

import io.netty.handler.codec.http.HttpHeaders;
import reactor.core.publisher.Mono;

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

    @GetMapping("/{memberId}")
    public Member getById( @PathVariable Long memberId){
        return memberService.findById(memberId).get();
    }

    @GetMapping("elo/{memberId}/{topic}")
    public MemberElo getByEloByMemberAndTopic( @PathVariable Long memberId , 
        @PathVariable String topic){

        TopicEntity topicEntity = topicRepository.findByName(topic);
        Member member = memberService.findById(memberId).get();

        return memberEloRepository.findById( 
            EloType.builder()
                .member( member )
                .topic( topicEntity )
                .build()
        ).orElse(
            memberEloRepository.save( MemberElo.builder()
                .member(member)
                .topic(topicEntity)
                .build())
        );
    }
    
    @GetMapping("/{memberId}/{questionId}/{whoWon}")
    public ResponseEntity<EloResult> processElos(
        @PathVariable Long memberId, @PathVariable Long questionId, @PathVariable int whoWon){

        Member member = memberService.findById( memberId ).get();
        QuestionEntity question = questionService.findById( questionId).get();
        EloType eloType = new EloType( member, question.getTheTopic());

        MemberElo memberElo = memberEloRepository.findById(eloType)
            .orElse(MemberElo.builder()
                        .elo(10000)
                        .member(member)
                        .topic(question.getTheTopic())
                        .build());

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
        
        memberElo.getQuestionEntities().add(question);
        question.getMembersWhoAnswered().add(member);

        memberElo.setElo( Double.valueOf( createdResult.getPlayer1().getNewElo() ) );
        question.setElo( Double.valueOf( createdResult.getPlayer2().getNewElo() ) );

        memberService.save( member );
        questionService.save( question );
        memberEloRepository.save( memberElo);

        return ResponseEntity.ok( createdResult );
    }
}
