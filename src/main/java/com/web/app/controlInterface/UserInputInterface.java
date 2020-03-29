package com.web.app.controlInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class UserInputInterface implements ControlInterface {
    private BufferedReader reader;
    private ModelValue model;
    private int operation;

    public UserInputInterface() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void reset() {
        this.model = null;
        this.operation = -1;
    }

    public boolean isContinue() {
        System.out.println("continue? Y/n");
        String answer = "n";
        try {
            answer = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return !answer.equalsIgnoreCase("n");

    }

    public void setupDbModel() throws Exception {
        System.out.println("select model: user, autoRental, auto");
        String res = this.reader.readLine();
        if (res.equalsIgnoreCase("user")) {
            this.model = ModelValue.USER;
        } else if (res.equalsIgnoreCase("autoRental")) {
            this.model = ModelValue.AUTO_RENTAL;
        } else if (res.equalsIgnoreCase("auto")) {
            this.model = ModelValue.AUTO;
        } else {
            throw new Exception("bad input");
        }
    }

    public void setupDbOperation() throws IOException, NumberFormatException {
        System.out.println("print number of operation: \n" +
                "(1) read by id,\n" +
                "(2) read all,\n" +
                "(3) add,\n" +
                "(4) update by id,\n" +
                "(5) delete by id");
        this.operation = Integer.parseInt(this.reader.readLine());
    }

    public ModelValue getModel() {
        return model;
    }

    @Override
    public UUID getId() {
        return readIdAsUUID();
    }

    private UUID readIdAsUUID() throws IllegalArgumentException {
        try {
            System.out.println("print id as uuid");
            return UUID.fromString(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getJsonObject() {
        return this.askForJsonObject();
    }

    private String askForJsonObject() {
        System.out.println("please enter model in json format");
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int getOperation() {
        return operation;
    }
}
