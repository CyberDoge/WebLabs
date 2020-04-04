package com.web.app.controlInterface;

import com.web.app.customExceptions.IncorrectUserInputException;

import java.io.IOException;
import java.util.UUID;

public interface ControlInterface {
    boolean isContinue();

    ModelValue selectDbModel() throws IncorrectUserInputException, IOException;

    int selectDbOperation() throws IOException, NumberFormatException;

    UUID getId();

    String getJsonObject();

    boolean isNeedToInterrupt();
}
