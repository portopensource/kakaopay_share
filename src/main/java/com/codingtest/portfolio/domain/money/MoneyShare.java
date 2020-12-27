package com.codingtest.portfolio.domain.money;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MoneyShare {
    private String token;
    private String roomId;
    private Integer userId;
    private int budget;
    private int amount;
    private int balance;
    private long madeTime;
    private int versionBalance;

    @Builder
    public MoneyShare(String token, String roomId, Integer userId, int budget, int amount, int balance, long madeTime) {
        this.token = token;
        this.roomId = roomId;
        this.userId = userId;
        this.budget = budget;
        this.amount = amount;
        this.balance = balance;
        this.madeTime = madeTime;
    }
}

