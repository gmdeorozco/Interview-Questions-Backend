package com.learning.javainterviewquestions.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class EloType implements Serializable {
    
    private Member member;
    private TopicEntity topic;
}
