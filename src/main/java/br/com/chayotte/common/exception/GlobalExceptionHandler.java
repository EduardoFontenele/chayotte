package br.com.chayotte.common.exception;

import br.com.chayotte.common.dto.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handler(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ErrorResponse> handler(HttpMessageNotReadableException ex) {
        var errors = List.of(ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errors), HttpStatus.BAD_REQUEST);
    }
}
