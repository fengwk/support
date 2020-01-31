package com.fengwk.support.spring.boot.starter.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolationException;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fengwk.support.core.exception.BizException;
import com.fengwk.support.core.exception.ExceptionCodes;
import com.fengwk.support.core.result.Response;
import com.fengwk.support.core.result.Responses;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public Response<Void> resolveException(Throwable e) {
        
        if (e instanceof ConstraintViolationException) {
            String errMessage = parse((ConstraintViolationException) e);
            log.error("参数异常,{}", errMessage);
            return Responses.failure(ExceptionCodes.arg().code(), errMessage);
        } else if (e instanceof ConstraintDeclarationException) {
            String errMessage = parse((ConstraintDeclarationException) e);
            log.error("参数异常,{}", errMessage);
            return Responses.failure(ExceptionCodes.arg().code(), errMessage);
        } else if (e instanceof MethodArgumentNotValidException) {
            String errMessage = parse((MethodArgumentNotValidException) e);
            log.error("参数异常,{}", errMessage);
            return Responses.failure(ExceptionCodes.arg().code(), errMessage);
        } else if (e instanceof IllegalArgumentException) {
            log.error("参数异常,{}", e.getMessage());
            return Responses.failure(ExceptionCodes.arg().code(), e.getMessage());
        } else if (e instanceof BizException) {
            log.error("业务异常", e.getMessage());
            return Responses.failure(((BizException) e).getCode(), null, e.getMessage());
        } else {
            log.error("系统异常", e);
            return Responses.failure(ExceptionCodes.unknown().code(), "系统异常");
        }
    }
    
    private String parse(ConstraintViolationException e) {
        return e.getLocalizedMessage();
    }
    
    private String parse(ConstraintDeclarationException e) {
        return e.getLocalizedMessage();
    }
    
    private String parse(MethodArgumentNotValidException e) {
        BindingResult br = e.getBindingResult();
        List<FieldError> fieldErrors = br.getFieldErrors();
        List<String> errs = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errs.add(fieldError.getField() + fieldError.getDefaultMessage());
        }
        return errs.stream().collect(Collectors.joining(","));
    }
    
}
