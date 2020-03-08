package model;

import java.util.UUID;

public class User {
    private UUID id;

    public User() {
    }

    private String name;
    private String login;
    private char[] password;

    public User(UUID id, String name, String login, char[] password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public UUID getId() {
        return id;
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

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }


}
