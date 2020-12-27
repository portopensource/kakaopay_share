package com.codingtest.portfolio.domain.money.json;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GivenInfo {
    private int cost;
    private int userId;

    @Builder
    public GivenInfo(int cost, int userId) {
        this.cost = cost;
        this.userId = userId;
    }
}

