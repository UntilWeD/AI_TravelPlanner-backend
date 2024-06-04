package com.teamsix.firstteamproject.global.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {

    private String message;
    private String code;
}
