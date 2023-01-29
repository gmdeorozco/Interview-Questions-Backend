package com.learning.javainterviewquestions.elo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @Data @NoArgsConstructor @AllArgsConstructor
public class Player {
    String oldElo;
    String newElo;
    String eloChange;
}
