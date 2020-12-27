package com.codingtest.portfolio.domain.money;


import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class MoneyGiven {
    private String token;
    private Integer userId;
    private int cost;

    @Builder
    public MoneyGiven(String token, Integer userId, int cost) {
        this.token = token;
        this.userId = userId;
        this.cost = cost;
    }
}


