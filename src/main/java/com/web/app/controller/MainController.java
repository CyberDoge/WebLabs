package com.web.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.app.controlInterface.ControlInterface;
import com.web.app.controlInterface.ModelValue;
import com.web.app.controlInterface.UserEmulatorControlInterface;
import com.web.app.controlInterface.UserInputInterface;
import com.web.app.customExceptions.NoModelWithSuchIdException;
import com.web.app.dao.AutoDao;
import com.web.app.dao.AutoRentalDao;
import com.web.app.dao.ModelDao;
import com.web.app.dao.UserDao;
import com.web.app.db_services.DBService;
import com.web.app.model.Auto;
import com.web.app.model.AutoRental;
import com.web.app.model.Model;
import com.web.app.model.User;
import com.web.app.utils.JsonReader;
import org.bson.Document;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.joining;

public class MainController {
    private ModelDao<User> userDao;
    private ModelDao<Auto> autoDao;
    private ModelDao<AutoRental> autoRentalDao;
    private ObjectMapper mapper;

    private ModelDao<? extends Model> currentModelDao;

    private ControlInterface controlInterface;
    private ModelValue model;

    public MainController(DBService dbService, String controlType) {
        this.userDao = new UserDao(dbService.getOrCreateMongoClient("users"));
        this.autoDao = new AutoDao(dbService.getOrCreateMongoClient("autos"));
        this.autoRentalDao = new AutoRentalDao(dbService.getOrCreateMongoClient("autoRentals"));
        this.mapper = new ObjectMapper();

        if (controlType != null && controlType.equalsIgnoreCase("auto")) {
            List<String> uuids = JsonReader.readValuesFromFile("uuids.txt");
            List<String> autos = JsonReader.readValuesFromFile("autos.txt");
            List<String> users = JsonReader.readValuesFromFile("users.txt");
            List<String> autoRentals = JsonReader.readValuesFromFile("autoRentals.txt");
            this.controlInterface = new UserEmulatorControlInterface(uuids, users, autoRentals, autos);
        } else {
            this.controlInterface = new UserInputInterface();
        }
    }

    public void start() {
        if (this.controlInterface.isNeedToInterrupt()) {
            System.out.println("nothing was started...");
        } else {
            startLoop();
        }
    }

    private void startLoop() {
        try {
            this.selectCurrentModel();
            int command = this.controlInterface.selectDbOperation();
            executeAndPrintCommand(command);
        } catch (NoModelWithSuchIdException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Bad input, try again");
        } finally {
            this.endCycle();
            if (this.controlInterface.isContinue()) {
                startLoop();
            }
        }
    }

    private void selectCurrentModel() throws Exception {
        this.model = this.controlInterface.selectDbModel();
        switch (this.model) {
            case USER:
                this.currentModelDao = this.userDao;
                break;
            case AUTO:
                this.currentModelDao = this.autoDao;
                break;
            case AUTO_RENTAL:
                this.currentModelDao = this.autoRentalDao;
                break;
        }
    }

    private void executeAndPrintCommand(int command) throws IllegalArgumentException, JsonProcessingException, NoModelWithSuchIdException {
        switch (command) {
            case 1: {
                UUID id = controlInterface.getId();
                Optional<?> modelById = this.currentModelDao.getModelById(id);
                var model = modelById.orElseThrow(() -> new NoModelWithSuchIdException(id.toString()));
                System.out.println("model was found: " + model.toString());
                break;
            }
            case 2:
                System.out.println(
                        currentModelDao
                                .getAll()
                                .stream()
                                .map(Object::toString)
                                .collect(
                                        joining(
                                                "\n", "all models: \n", "\n-------------\n"
                                        )
                                )
                );
                break;
            case 3: {
                Model model = mapper.readValue(controlInterface.getJsonObject(), this.model.getClazz());
                if (model.getId() == null) {
                    model.setId(UUID.randomUUID());
                }
                currentModelDao.insertModel(model);
                System.out.println("model inserted:\n" + model);
                break;
            }
            case 4: {
                UUID uuid = controlInterface.getId();
                Document result = currentModelDao.update(uuid, controlInterface.getJsonObject());
                System.out.println("model was updated:\n" + result);
                break;
            }
            case 5:
                UUID uuid = controlInterface.getId();
                currentModelDao.deleteModelById(uuid);
                System.out.println("model was deleted");
                break;
        }
    }

    private void endCycle() {
        this.currentModelDao = null;
    }
}
