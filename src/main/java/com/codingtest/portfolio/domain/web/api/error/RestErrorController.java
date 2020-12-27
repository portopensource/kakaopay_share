package com.codingtest.portfolio.domain.web.api.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class RestErrorController implements ErrorController {

    private static final String ERROR_PATH = "error";

    @Deprecated
    @Override
    public String getErrorPath() {
        return null;
    }

    @RequestMapping(value = "/error", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String error() {
        // 40x 처리 필요.

        // 제품 에러 50x

        throw new RuntimeException();
    }

}