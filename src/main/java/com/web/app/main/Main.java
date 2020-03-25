package com.web.app.main;

import com.web.app.dao.UserDao;
import com.web.app.db_services.DBService;
import com.web.app.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        DBService dbService = new DBService();
        dbService.runChangeLog();
        UserDao userDao = new UserDao();
        userDao.setMongoClient(dbService.getOrCreateMongoClient());
        UUID id = UUID.randomUUID();
        userDao.insertModel(new User(id, "test1", "login1", "password", List.of()));
        var allUsers = userDao.getAll();
        var user = userDao.getModelById(id);
        System.out.println(allUsers.size());
        System.out.println(Arrays.toString(allUsers.toArray()));
        user.ifPresent(System.out::println);
        userDao.deleteModelById(id);
        allUsers = userDao.getAll();
        System.out.println(allUsers.size());
        System.out.println(Arrays.toString(allUsers.toArray()));
    }
}
