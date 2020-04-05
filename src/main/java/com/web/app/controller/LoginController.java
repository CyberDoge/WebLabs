package com.web.app.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.web.app.dao.UserDao;
import com.web.app.dbServices.DBService;
import com.web.app.model.User;

import java.util.Optional;

public class LoginController {
    private UserDao userDao;

    public LoginController(DBService dbService) {
        this.userDao = new UserDao(dbService.getOrCreateMongoClient("users"));
    }

    public boolean authenticateUser(String login, char[] password) {
        Optional<User> userByLogin = this.userDao.getUserByLogin(login);
        return userByLogin.filter(user -> BCrypt.verifyer().verify(password, user.getPasswordHash()).verified).isPresent();
    }
}
