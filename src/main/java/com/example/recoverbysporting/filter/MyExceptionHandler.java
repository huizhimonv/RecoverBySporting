package com.example.recoverbysporting.filter;

import com.example.recoverbysporting.utils.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class MyExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    public Object ErrorHandler(AuthorizationException e) {
        log.error("没有通过权限验证！", e);
        return new ResultBody<>(false,400,"权限不足！");
    }
}
