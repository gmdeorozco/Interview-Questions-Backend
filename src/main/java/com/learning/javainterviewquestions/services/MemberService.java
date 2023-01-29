package com.learning.javainterviewquestions.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.javainterviewquestions.entities.Member;
import com.learning.javainterviewquestions.repositories.MemberRepository;

@Service
public class MemberService {

    @Autowired 
    MemberRepository memberRepository;

    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Member save(Member member) {
        return memberRepository.save(member);
    }
    

}
