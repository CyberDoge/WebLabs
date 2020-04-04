package com.web.app.customExceptions;

public class NoModelWithSuchIdException extends Exception {
    public NoModelWithSuchIdException(String id) {
        super("No model with id = " + id + " in database");
    }
}
