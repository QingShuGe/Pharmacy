package com.qing.forestpharmacy.common.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.qing.forestpharmacy.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

//全局异常处理器,处理Controller层的异常
@ControllerAdvice(annotations = {RestController.class,Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    //处理完整性约束异常
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        //重复键异常
        if(ex.getMessage().contains("Duplicate entry")){
            return Result.failed("C0351","唯一约束异常:"+ex.getMessage().split(" ")[2]+"已存在");
        }
        return Result.reCode("C0300");
    }
//    @ExceptionHandler(JWTVerificationException.class)
//    public Result<String> exceptionHandler(JWTVerificationException ex){
//        return Result.reCode("A0230");
//    }
//    @ExceptionHandler(AuthenticationException.class)
//    public Result<String> exceptionHandler(AuthenticationException ex){
//
//        return Result.reCode("A0210");
//    }
    @ExceptionHandler(RuntimeException.class)
    public Result<String> exceptionHandler(RuntimeException ex){

        return Result.failed("A1111",ex.getMessage());
    }
}
