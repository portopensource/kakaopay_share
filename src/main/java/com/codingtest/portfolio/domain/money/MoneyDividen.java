package com.codingtest.portfolio.domain.money;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MoneyDividen {
    private String token;
    private int cost;
    private int amount;
    private int balance;
    private int versionBalance;

    @Builder
    public MoneyDividen(String token, int cost, int amount, int balance, int versionBalance) {
        this.token = token;
        this.cost = cost;
        this.amount = amount;
        this.balance = balance;
        this.versionBalance = versionBalance;
    }
}