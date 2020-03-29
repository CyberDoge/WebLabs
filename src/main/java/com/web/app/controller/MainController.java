package com.web.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.web.app.controlInterface.AutoControlInterface;
import com.web.app.controlInterface.ControlInterface;
import com.web.app.controlInterface.UserInputInterface;
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

import java.io.Closeable;
import java.util.*;

import static java.util.stream.Collectors.joining;

public class MainController implements Closeable {
    private DBService dbService;
    private ModelDao<User> userDao;
    private ModelDao<Auto> autoDao;
    private ModelDao<AutoRental> autoRentalDao;
    private ObjectMapper mapper;

    private ModelDao<? extends Model> currentModelDao;

    private ControlInterface controlInterface;

    public MainController(String controlType) {
        this.dbService = new DBService();
        this.dbService.runChangeLog();
        this.userDao = new UserDao(dbService.getOrCreateMongoClient());
        this.autoDao = new AutoDao(dbService.getOrCreateMongoClient());
        this.autoRentalDao = new AutoRentalDao(dbService.getOrCreateMongoClient());
        this.mapper = new ObjectMapper();

        if (controlType != null && controlType.equalsIgnoreCase("auto")) {
            List<String> uuids = JsonReader.readValuesFromFile("uuids.txt");
            List<String> autos = JsonReader.readValuesFromFile("autos.txt");
            List<String> users = JsonReader.readValuesFromFile("users.txt");
            List<String> autoRentals = JsonReader.readValuesFromFile("autoRentals.txt");
            this.controlInterface = new AutoControlInterface(uuids, users, autoRentals, autos);
        } else {
            this.controlInterface = new UserInputInterface();
        }
    }

    public void start() {
        try {
            this.controlInterface.setupDbModel();
            this.controlInterface.setupDbOperation();
            this.selectCurrentModel();
            int command = this.controlInterface.getOperation();
            executeAndPrintCommand(command);
        } catch (NoModelWithSuchIdException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Bad input, try again");
        } finally {
            this.endCycle();
            if (this.controlInterface.isContinue()) {
                start();
            }
        }
    }

    private void selectCurrentModel() {
        var model = this.controlInterface.getModel();
        switch (model) {
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
                Model model = mapper.readValue(controlInterface.getJsonObject(), controlInterface.getModel().getClazz());
                model.setPrimaryField("newField" + GregorianCalendar.getInstance().get(Calendar.MINUTE));
                model.setId(UUID.randomUUID());
                currentModelDao.insertModel(model);
                System.out.println("model inserted:\n" + model);
                break;
            }
            case 4: {
                UUID uuid = controlInterface.getId();
                this.controlInterface.getJsonObject();
                Model model = mapper.readValue(controlInterface.getJsonObject(), controlInterface.getModel().getClazz());
                currentModelDao.update(uuid, model);
                System.out.println("model was updated:\n" + model);
                break;
            }
            case 5:
                UUID uuid = controlInterface.getId();
                currentModelDao.deleteModelById(uuid);
                System.out.println("model was deleted");
                break;
        }
    }

    private Object mergeJSONObjects(Model primaryModel, String modelAsJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String primaryJson = objectMapper.writeValueAsString(primaryModel);
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectMapper.readTree(primaryJson).fields().forEachRemaining(entry -> objectNode.set(entry.getKey(), entry.getValue()));
        objectMapper.readTree(modelAsJson).fields().forEachRemaining(entry -> objectNode.set(entry.getKey(), entry.getValue()));
        return objectMapper;

    }

    private void endCycle() {
        this.currentModelDao = null;
        this.controlInterface.reset();
    }

    @Override
    public void close() {
        this.dbService.getOrCreateMongoClient().close();
    }
}
