package com.web.app.controlInterface;

import java.io.IOException;
import java.util.UUID;

public interface ControlInterface {
    boolean isContinue();

    void setupDbModel() throws Exception;

    void setupDbOperation() throws IOException, NumberFormatException;

    ModelValue getModel();

    UUID getId();

    String getJsonObject();

    int getOperation();

    void reset();
}
