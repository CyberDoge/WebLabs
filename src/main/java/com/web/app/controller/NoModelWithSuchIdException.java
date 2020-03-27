package com.web.app.controller;

public class NoModelWithSuchIdException extends Exception {
    public NoModelWithSuchIdException(String id) {
        super("No model with id = " + id + " in database");
    }
}
