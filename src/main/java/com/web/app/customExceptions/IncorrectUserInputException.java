package com.web.app.customExceptions;

public class IncorrectUserInputException extends Exception {
    public IncorrectUserInputException() {
    }

    public IncorrectUserInputException(String message) {
        super(message);
    }
}
