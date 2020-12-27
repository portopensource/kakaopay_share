package com.codingtest.portfolio.domain.web.api.error;

import com.codingtest.portfolio.support.AppConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Order(AppConst.ADVICE_ORDER + 1)
@Slf4j
@ControllerAdvice
public class GlobalViewExceptionHandler extends ResponseEntityExceptionHandler {

    // 서블릿 container 내부 발생 Exception하위 모든 예외
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, HttpServletRequest request) {

        // 정적 페이지 위치 반환
        return "/error/404.html";
    }
}
