package com.learning.javainterviewquestions.controllers;

import java.net.URI;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;
import org.springframework.web.util.UriBuilder;

import com.learning.javainterviewquestions.elo.EloResult;
import com.learning.javainterviewquestions.entities.Member;
import com.learning.javainterviewquestions.entities.QuestionEntity;
import com.learning.javainterviewquestions.services.MemberService;
import com.learning.javainterviewquestions.services.QuestionService;

import io.netty.handler.codec.http.HttpHeaders;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    QuestionService questionService;
    
    @GetMapping("/{memberId}/{questionId}/{whoWon}")
    public ResponseEntity<EloResult> processElos(
        @PathVariable Long memberId, @PathVariable Long questionId, @PathVariable int whoWon){

        Member member = memberService.findById( memberId ).get();
        QuestionEntity question = questionService.findById( questionId).get();

        double eloFirstPlayer = member.getElo();
        double eloSecondPlayer = question.getElo();
        int nGamesFirstPlayer = member.getNumberOfAnswers();
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
        
        member.getAnsweredQuestions().add(question);
        question.getMembersWhoAnswered().add(member);
        member.setElo( Double.valueOf( createdResult.getPlayer1().getNewElo() ) );
        question.setElo( Double.valueOf( createdResult.getPlayer2().getNewElo() ) );

        memberService.save( member );
        questionService.save( question );

        return ResponseEntity.ok( createdResult );
    }
}
