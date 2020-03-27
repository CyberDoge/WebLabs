package com.web.app.model;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.web.app.dao.UserDao;

import java.util.List;
import java.util.UUID;

public class User implements Model {

    @JsonProperty("_id")
    private UUID id;
    private String name;
    private String login;
    private String passwordHash;
    private List<UUID> autoRentalIds;
    @JsonIgnore
    private UserDao userDao;

    public User() {
    }

    public User(UUID id, String name, String login, String password, List<UUID> autoRentalIds) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.passwordHash = this.hashPassword(password.toCharArray());
        this.autoRentalIds = autoRentalIds;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void addAutoRentalId(UUID id) {
        this.autoRentalIds.add(id);
    }

    public void deleteAutoRentalIndex(int index) {
        this.autoRentalIds.remove(index);
    }

    public UUID getId() {
        return UUID.randomUUID();
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<UUID> getAutoRentalIds() {
        return autoRentalIds;
    }

    public void setAutoRentalIds(List<UUID> autoRentalIds) {
        this.autoRentalIds = autoRentalIds;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String hashPassword(char[] password) {
        return BCrypt.withDefaults().hashToString(16, password);
    }

    public void setPassword(String password) {
        this.passwordHash = hashPassword(password.toCharArray());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}