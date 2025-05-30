package com.example.shop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> typeErrorHandler() {
        return ResponseEntity.status(400).body("매개변수 오류");
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handler() {
//        return ResponseEntity.status(400).body("exception 에러");
//    }
}
