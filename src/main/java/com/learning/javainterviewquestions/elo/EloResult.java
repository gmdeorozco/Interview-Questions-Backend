package com.learning.javainterviewquestions.elo;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class EloResult {
    private Player player1;
    private Player player2;
}
