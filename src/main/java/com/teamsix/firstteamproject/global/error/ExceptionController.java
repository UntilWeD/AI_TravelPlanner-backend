package com.teamsix.firstteamproject.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {


    /*
    // Internal Server에러시 해당 컨트롤러가 errorMessage에 응답을 담고 반환한다.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<ErrorResult> exHandle(Exception e){
        log.error("Exception : {} ", e);

        return new ResponseEntity<>(new ErrorResult(e.getMessage(), "500"), HttpStatus.BAD_REQUEST);
    }
}
