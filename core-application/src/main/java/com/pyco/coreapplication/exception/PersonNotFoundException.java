package com.pyco.coreapplication.exception;

public class PersonNotFoundException extends RuntimeException {

    public static final String USER_NOT_FOUND_EXCEPTION_MESSAGE = "Counld not find person with username: '%s' in system";

    public PersonNotFoundException(String username) {
        super(String.format(USER_NOT_FOUND_EXCEPTION_MESSAGE, username));
    }
}
