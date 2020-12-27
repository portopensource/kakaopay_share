package com.codingtest.portfolio.domain.web.api.error;

import com.codingtest.portfolio.domain.web.api.ApiResponse;
import com.codingtest.portfolio.domain.web.api.ResponseCode;
import com.codingtest.portfolio.support.AppConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Order(AppConst.ADVICE_ORDER)
@Slf4j
@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

    // 서블릿 container 내부 발생 Exception하위 모든 예외
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(Exception.class)
    @ResponseBody // json 만들기 or Map으로 반환
    public ResponseEntity<ApiResponse> handleException(Exception e, HttpServletRequest request) {

        // Filter, Interceptor ...

        // Http 요청 관련 Exception 처리

        // 서블릿 내부 Exception
        // json 반환
        return new ResponseEntity(new ApiResponse().code(ResponseCode.SERVER_ERROR.getCode())
            , HttpStatus.INTERNAL_SERVER_ERROR
            );

    }

}
