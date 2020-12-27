package com.codingtest.portfolio.domain.web.api;

import lombok.Getter;

@Getter
public enum ResponseCode {
    NORMAL("001") // 성공
    , SERVER_ERROR("002")
    , RECEIVE_FAIL("003")
    , MAKE_FAIL("004")
    , INFO_NOTVALID("005")
    , INFO_FAIL("006")
    ;
    private String code;

    ResponseCode(String code) {
        this.code = code;
    }
}