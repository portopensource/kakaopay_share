package com.codingtest.portfolio.domain.money.json;

import com.codingtest.portfolio.domain.web.api.ApiResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ReceiveInfo extends ApiResponse<ReceiveInfo> {
    private int cost;

    @Builder
    public ReceiveInfo(int cost) {
        this.cost = cost;
    }
}

