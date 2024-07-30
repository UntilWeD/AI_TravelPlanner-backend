package com.teamsix.firstteamproject.global.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class ResultDTO<T> {
    private final int status;
    private final String message;
    private final T data;


}
