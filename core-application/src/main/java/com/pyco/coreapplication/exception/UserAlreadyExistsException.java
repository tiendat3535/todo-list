package com.pyco.coreapplication.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public static final String USER_ALREADY_EXISTS_EXCEPTION_MESSAGE = "UserDto: \"%s\" already exists";

    private String username;

    public UserAlreadyExistsException(String username) {
        super(String.format(USER_ALREADY_EXISTS_EXCEPTION_MESSAGE, username));
        this.username = username;
    }

    public UserAlreadyExistsException(String username, Throwable cause) {
        super(String.format(USER_ALREADY_EXISTS_EXCEPTION_MESSAGE, username), cause);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

