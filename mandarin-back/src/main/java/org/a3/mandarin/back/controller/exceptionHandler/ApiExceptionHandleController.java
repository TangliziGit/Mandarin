package org.a3.mandarin.back.controller.exceptionHandler;

import org.a3.mandarin.back.exception.ApiForbiddenException;
import org.a3.mandarin.back.exception.ApiNotFoundException;
import org.a3.mandarin.back.exception.ApiUnauthorizedException;
import org.a3.mandarin.back.model.RESTfulResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.UndeclaredThrowableException;

@ControllerAdvice
public class ApiExceptionHandleController {
    private final Logger logger= LoggerFactory.getLogger(ApiExceptionHandleController.class);

    @ExceptionHandler({ApiNotFoundException.class,
            ApiUnauthorizedException.class, ApiForbiddenException.class})
    public ResponseEntity<RESTfulResponse> handle(Exception e){
        logger.error("API统一异常处理: "+e.toString());

        RESTfulResponse response=RESTfulResponse.fail(e.getMessage());
        ResponseEntity<RESTfulResponse> responseEntity=ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        if (e instanceof ApiNotFoundException)
            responseEntity=ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        if (e instanceof ApiUnauthorizedException || e instanceof UndeclaredThrowableException)
            responseEntity=ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

        if (e instanceof ApiForbiddenException)
            responseEntity=ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

        logger.error("RESPONSE: "+response.getMessage());
        return responseEntity;
    }
}
