package com.teamsix.firstteamproject.user.controller;

import com.teamsix.firstteamproject.global.dto.ResultDTO;
import com.teamsix.firstteamproject.global.util.ApiUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class UserExceptionController {

    @ExceptionHandler(ExpiredJwtException.class)
    public ResultDTO handleExpiredJwtException(ExpiredJwtException ex){
        log.warn("Expired JWT token: {}", ex.getMessage());
        return ApiUtils.error(HttpStatus.BAD_REQUEST,
                "TOKEN_EXPIRED : The access token has expired. Please refresh your token.");
    }

}
