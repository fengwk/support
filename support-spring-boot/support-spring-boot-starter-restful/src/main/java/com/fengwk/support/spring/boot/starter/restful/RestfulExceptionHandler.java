package com.fengwk.support.spring.boot.starter.restful;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fengwk.support.core.code.ErrorCode;
import com.fengwk.support.core.exception.ErrorCodeException;
import com.fengwk.support.core.result.Result;
import com.fengwk.support.core.result.Results;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestfulExceptionHandler {

    @Autowired
    volatile HttpServletResponse response;
    
    @ExceptionHandler(Throwable.class)
    public Result<Void> resolveException(Throwable e) {
        
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        
        if (e instanceof MissingServletRequestParameterException) {
            String errMessage = ((MissingServletRequestParameterException) e).getMessage();
            log.warn("参数异常,errMessage={}.", errMessage);
            return Results.error(new IllegalArgumentErrorCode(errMessage));
            
        } else if (e instanceof ConstraintViolationException) {
            String errMessage = parse((ConstraintViolationException) e);
            log.warn("参数异常,errMessage={}.", errMessage);
            return Results.error(new IllegalArgumentErrorCode(errMessage));
            
        } else if (e instanceof ConstraintDeclarationException) {
            String errMessage = parse((ConstraintDeclarationException) e);
            log.warn("参数异常,errMessage={}.", errMessage);
            return Results.error(new IllegalArgumentErrorCode(errMessage));
            
        } else if (e instanceof MethodArgumentNotValidException) {
            String errMessage = parse((MethodArgumentNotValidException) e);
            log.warn("参数异常,errMessage={}.", errMessage);
            return Results.error(new IllegalArgumentErrorCode(errMessage));
            
        } else if (e instanceof IllegalArgumentException) {
            String errMessage = e.getMessage();
            log.warn("参数异常,errMessage={}.", errMessage);
            return Results.error(new IllegalArgumentErrorCode(errMessage));
            
        } else if (e instanceof ErrorCodeException) {
            ErrorCode errorCode = ((ErrorCodeException) e).getErrorCode();
            log.warn("业务异常,errorCode={}.", errorCode);
            return Results.error(errorCode);
            
        } else {
            log.error("系统异常", e);
            return Results.error(SystemErrorCode.INSTANCE);
        }
    }
    
    private String parse(ConstraintViolationException e) {
        return e.getLocalizedMessage();
    }
    
    private String parse(ConstraintDeclarationException e) {
        return e.getLocalizedMessage();
    }
    
    private String parse(MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + fieldError.getDefaultMessage())
                .collect(Collectors.joining(","));
    }
    
}
