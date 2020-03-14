package com.web.app.main;

import com.web.app.model.User;

import java.util.Objects;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        UUID run = UUID.randomUUID();
        User user = new User(run, "name", "login", null);
        User user2 = new User(run, "name", "login", null);
        System.out.println("started");
        System.out.println(Objects.equals(user, user2));
        System.out.println(user.toString());
    }
}
