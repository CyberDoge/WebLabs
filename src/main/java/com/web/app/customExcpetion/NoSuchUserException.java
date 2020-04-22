package com.web.app.customExcpetion;

import javax.servlet.http.HttpServletResponse;

public class NoSuchUserException extends RuntimeException {
    private final String errorMessage;
    private int statusCode;

    public NoSuchUserException(int statusCode) {
        this.errorMessage = "No user with such login or password";
        this.statusCode = statusCode;
    }

    public NoSuchUserException() {
        this.errorMessage = "No user with such login or password";
        this.statusCode = HttpServletResponse.SC_FORBIDDEN;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
