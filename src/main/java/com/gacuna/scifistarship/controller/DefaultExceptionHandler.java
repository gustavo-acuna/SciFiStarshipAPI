package com.gacuna.scifistarship.controller;

import com.gacuna.scifistarship.exception.FunctionalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(FunctionalException.class)
    public ResponseEntity<String> handleFunctionalException(FunctionalException ex) {
        log.error("functional error: ",ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        log.error("technical error: ",ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se produjo un error interno en el servidor");
    }
}
