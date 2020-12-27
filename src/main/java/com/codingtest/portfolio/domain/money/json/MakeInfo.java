package com.codingtest.portfolio.domain.money.json;

import com.codingtest.portfolio.domain.web.api.ApiResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MakeInfo extends ApiResponse<MakeInfo> {
    private String token;

    @Builder
    public MakeInfo(String token) {
        this.token = token;
    }
}

