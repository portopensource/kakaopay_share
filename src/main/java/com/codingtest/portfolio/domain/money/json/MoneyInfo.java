package com.codingtest.portfolio.domain.money.json;

import com.codingtest.portfolio.domain.web.api.ApiResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MoneyInfo extends ApiResponse<MoneyInfo> {
    private String token;
    private long timestamp;
    private int budget;
    private int given;
    private List<GivenInfo> givenInfo = new ArrayList<>(); // default []

    @Builder
    public MoneyInfo(String token, long timestamp, int budget, int given, List<GivenInfo> givenInfo) {
        this.token = token;
        this.timestamp = timestamp;
        this.budget = budget;
        this.given = given;
        this.givenInfo = givenInfo;
    }

}