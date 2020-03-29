package com.web.app.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.app.model.Auto;
import com.web.app.model.AutoRental;
import com.web.app.model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ModelsUtils {
    private static int fromIndex = 0;

    public static void main(String[] args) throws IOException {
        fromIndex = 1000000;
        pullModels();
        fromIndex = 0;
    }

    public static void pullModels() throws IOException {
        var startAutos = new Date();
        List<Auto> autos = create200Autos();
        var endAutos = new Date();
        List<AutoRental> autoRentals = create20AutoRentals(autos);
        var endAutoRentals = new Date();
        List<User> users = create4Users(autoRentals);
        var endUsers = new Date();
        System.out.println("Auto time " + (endAutos.getTime() - startAutos.getTime()));
        System.out.println("AutoRental time " + (endAutoRentals.getTime() - endAutos.getTime()));
        System.out.println("Users time " + (endUsers.getTime() - endAutoRentals.getTime()));
        writeObjectsToFilesByLines(autos, autoRentals, users);
        writeUUIDtoFiles(autos, autoRentals, users);
    }


    public static void writeUUIDtoFiles(List<Auto> autos, List<AutoRental> autoRentals, List<User> users) throws IOException {

        try (FileWriter writer = new FileWriter("uuids.txt")) {
            for (var model : users) {
                writer.write(model.getId().toString() + '\n');
            }
            for (var model : autoRentals) {
                writer.write(model.getId().toString() + '\n');
            }
            for (var model : autos) {
                writer.write(model.getId().toString() + '\n');
            }
        }
    }

    public static void writeObjectsToFilesByLines(List<Auto> autos, List<AutoRental> autoRentals, List<User> users) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (FileWriter writer = new FileWriter("users.txt")) {
            for (var model : users) {
                writer.write(mapper.writeValueAsString(model) + '\n');
            }
        }
        try (FileWriter writer = new FileWriter("autoRentals.txt")) {
            for (var model : autoRentals) {
                writer.write(mapper.writeValueAsString(model) + '\n');
            }
        }
        try (FileWriter writer = new FileWriter("autos.txt")) {
            for (var model : autos) {
                writer.write(mapper.writeValueAsString(model) + '\n');
            }
        }

        try (FileWriter writer = new FileWriter("uuids.txt")) {
            writer.write(users.get(0).getId().toString() + '\n');
            writer.write(autoRentals.get(0).getId().toString() + '\n');
            writer.write(autos.get(0).getId().toString() + '\n');

        }
    }

    public static List<Auto> create200Autos() {
        List<Auto> autos = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            UUID id = UUID.randomUUID();
            Auto auto = new Auto(
                    id,
                    "model #" + (i + fromIndex),
                    "producer #" + (i + fromIndex),
                    i * (10 + fromIndex),
                    i * (100 + fromIndex)
            );
            autos.add(auto);
        }
        return autos;
    }

    public static List<AutoRental> create20AutoRentals(List<Auto> autos) {
        List<AutoRental> autoRentals = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            UUID id = UUID.randomUUID();
            List<UUID> autoIds = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                autoIds.add(autos.get(10 * i + j).getId());
            }
            AutoRental autoRental = new AutoRental(id, autoIds);
            autoRentals.add(autoRental);
        }
        return autoRentals;
    }

    public static List<User> create4Users(List<AutoRental> autoRentals) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            UUID id = UUID.randomUUID();
            List<UUID> autoRentalIds = new ArrayList<>();

            for (int j = 0; j < 5; j++) {
                autoRentalIds.add(autoRentals.get(5 * i + j).getId());
            }
            User user = new User(
                    id,
                    "name" + (i + fromIndex),
                    "login" + (i + fromIndex),
                    "password" + (i + fromIndex),
                    autoRentalIds
            );
            users.add(user);
        }
        return users;
    }
}
