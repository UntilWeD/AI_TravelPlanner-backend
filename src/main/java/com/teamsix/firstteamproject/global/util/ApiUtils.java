package com.teamsix.firstteamproject.global.util;

import com.teamsix.firstteamproject.global.dto.ResultDTO;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;


public class ApiUtils {

    public static <T> ResultDTO<T> ok(T data){
        return ResultDTO.<T>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(data)
                .build();
    }

    public static <T> ResultDTO<T> error(HttpStatus status, String message){
        return ResultDTO.<T>builder()
                .status(status.value())
                .message(message)
                .build();
    }

    public static <T> Mono<ResultDTO<T>> MonoOk(Mono<T> data) {
        return data.map(resolvedData ->
                ResultDTO.<T>builder()
                        .status(HttpStatus.OK.value())
                        .message("Success")
                        .data(resolvedData)
                        .build()
        );
    }

}
