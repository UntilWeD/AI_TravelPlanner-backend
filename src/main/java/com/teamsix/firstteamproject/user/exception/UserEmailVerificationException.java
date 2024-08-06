package com.teamsix.firstteamproject.user.exception;

public class UserEmailVerificationException extends RuntimeException{
    public UserEmailVerificationException(String email) {
        super("The User (" + email + ") didn't verify email!");
    }
}
