package com.web.app.controlInterface;

import com.web.app.customExceptions.IncorrectUserInputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class UserInputInterface implements ControlInterface {
    private BufferedReader reader;

    public UserInputInterface() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public boolean isNeedToInterrupt() {
        System.out.println("exit? y/N");
        try {
            return this.reader.readLine().equalsIgnoreCase("y");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
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

    public ModelValue selectDbModel() throws IncorrectUserInputException, IOException {
        System.out.println("select model: user, autoRental, auto");
        String res = this.reader.readLine();
        if (res.equalsIgnoreCase("user")) {
            return ModelValue.USER;
        } else if (res.equalsIgnoreCase("autoRental")) {
            return ModelValue.AUTO_RENTAL;
        } else if (res.equalsIgnoreCase("auto")) {
            return ModelValue.AUTO;
        } else {
            throw new IncorrectUserInputException("bad input");
        }
    }

    public int selectDbOperation() throws IOException, NumberFormatException {
        System.out.println("print number of operation: \n" +
                "(1) read by id,\n" +
                "(2) read all,\n" +
                "(3) add,\n" +
                "(4) update by id,\n" +
                "(5) delete by id");

        return Integer.parseInt(this.reader.readLine());
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
}
