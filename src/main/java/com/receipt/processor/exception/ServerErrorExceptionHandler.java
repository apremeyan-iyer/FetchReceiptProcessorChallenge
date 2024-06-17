package com.receipt.processor.exception;


import com.receipt.processor.data.ServerError;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServerErrorExceptionHandler {

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<ServerError> handleServerErrorException(ServerErrorException ex){
        return ResponseEntity.status(HttpStatusCode.valueOf(Integer.parseInt(ex.serverError.getCode())))
                .body(ex.serverError);
    }

}
