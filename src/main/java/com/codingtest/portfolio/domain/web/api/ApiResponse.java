package com.codingtest.portfolio.domain.web.api;

import com.codingtest.portfolio.support.BaseObject;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ApiResponse<T extends ApiResponse> extends BaseObject {
    protected String code = ResponseCode.NORMAL.getCode();

    public ApiResponse code(String code) {
        this.code = code;
        return this;
    }
}
