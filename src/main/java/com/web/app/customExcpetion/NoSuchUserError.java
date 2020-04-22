package com.web.app.customExcpetion;

import javax.servlet.http.HttpServletResponse;

public class NoSuchUserError extends Error {
    private final String errorMessage;
    private int statusCode;

    public NoSuchUserError(int statusCode) {
        this.errorMessage = "No user with such login or password";
        this.statusCode = statusCode;
    }

    public NoSuchUserError() {
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
