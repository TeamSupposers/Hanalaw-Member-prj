package com.member.handlers;

import javax.security.sasl.AuthenticationException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.member.exception.KeyValidationException;
import com.member.exception.TokenValidationException;
import com.member.response.ErrorResponse;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(TokenValidationException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorResponse tokenValidationExceptionHandler(Exception ex) {
    	ErrorResponse response = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "유효한 토큰값이 아닙니다.");
        return response;
    }
    
    @ExceptionHandler(KeyValidationException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorResponse keyValidationExceptionHandler(Exception ex) {
    	ErrorResponse response = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage());
        return response;
    }
    
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorResponse duplicateKeyExceptionHandler(Exception ex) {
    	ErrorResponse response = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage());
        return response;
    }

}