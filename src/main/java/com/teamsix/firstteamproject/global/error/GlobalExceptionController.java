package com.teamsix.firstteamproject.global.error;

import com.teamsix.firstteamproject.global.dto.ResultDTO;
import com.teamsix.firstteamproject.global.util.ApiUtils;
import com.teamsix.firstteamproject.user.exception.UserAlreadyExistsException;
import com.teamsix.firstteamproject.user.exception.UserEmailVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionController {

//    필터레벨에선 처리가 서블릿 전단계에서 이루어지기 때문에 처리가 불가능하다.
//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResultDTO handleExpiredJwtException(ExpiredJwtException ex){
//        log.warn("Expired JWT token: {}", ex.getMessage());
//        return ApiUtils.error(HttpStatus.BAD_REQUEST,
//                "TOKEN_EXPIRED : The access token has expired. Please refresh your token.");
//    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResultDTO handleUserAlreadyExistsException(UserAlreadyExistsException ex){
        log.warn("UserAlready Exists : {}", ex.getMessage());
        return ApiUtils.error(HttpStatus.BAD_REQUEST,
                ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResultDTO handleUsernameNotFountException(UsernameNotFoundException ex){
        log.warn("User is Not Exists..");
        return ApiUtils.error(HttpStatus.BAD_REQUEST,
                ex.getMessage());
    }

    @ExceptionHandler(UserEmailVerificationException.class)
    public ResultDTO handleUserEmailVerificationException(UserEmailVerificationException ex){
        log.warn("User didn't verify email...");
        return ApiUtils.error(HttpStatus.BAD_REQUEST,
                ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResultDTO handleUserRuntimeException(RuntimeException ex){
        log.warn("User Runtime Exception : {}", ex);
        return ApiUtils.error(HttpStatus.BAD_REQUEST,
                "Internal Server Error");
    }

}
