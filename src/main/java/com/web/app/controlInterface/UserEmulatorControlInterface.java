package com.web.app.controlInterface;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserEmulatorControlInterface implements ControlInterface {
    private ModelValue model;

    private int step = 0;
    private int jsonListIndex = 0;
    private int operationNumber = 1;

    private List<UUID> uuidList;
    private List<String> userJsonList;
    private List<String> autoRentalJsonList;
    private List<String> autoJsonList;

    public UserEmulatorControlInterface(List<String> uuidList, List<String> userJsonList, List<String> autoRentalJsonList, List<String> autoJsonList) {
        this.uuidList = uuidList.stream().map(UUID::fromString).collect(Collectors.toList());
        this.userJsonList = userJsonList;
        this.autoRentalJsonList = autoRentalJsonList;
        this.autoJsonList = autoJsonList;
    }

    @Override
    public boolean isContinue() {
        reset();
        return step < 3;
    }

    @Override
    public ModelValue selectDbModel() {
        switch (step) {
            case 0:
                model = ModelValue.USER;
                break;
            case 1:
                model = ModelValue.AUTO_RENTAL;
                break;
            case 2:
                model = ModelValue.AUTO;
                break;
        }
        System.out.println("for model " + model.getValue());
        return model;
    }

    @Override
    public int selectDbOperation() {
        return this.operationNumber;
    }

    @Override
    public UUID getId() {
        return this.uuidList.get(step);
    }

    @Override
    public String getJsonObject() {
        switch (this.model) {
            case USER:
                return userJsonList.get(jsonListIndex++);
            case AUTO:
                return autoJsonList.get(jsonListIndex++);
            case AUTO_RENTAL:
                return autoRentalJsonList.get(jsonListIndex++);
            default:
                return null;
        }
    }


    private void reset() {
        if (this.operationNumber == 5) {
            ++this.step;
            this.operationNumber = 1;
            this.jsonListIndex = 0;
        } else {
            ++this.operationNumber;
        }
    }

    @Override
    public boolean isNeedToInterrupt() {
        return false;
    }
}
