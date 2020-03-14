package com.web.app.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class User {
    private int id;
    private String name;
    private String login;
    private char[] password;
    private List<AutoRental> autoRentalList;

    public User() {
    }

    public User(UUID id, String name, String login, char[] password) {
        this.id = 1;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public void addAutoRental(AutoRental autoRental) {
        this.autoRentalList.add(autoRental);
    }

    public Optional<Auto> getProduct(Auto auto) {
        return this.autoRentalList.stream()
                .flatMap(
                        autoRental -> autoRental.getAutos().stream()
                ).filter(auto::equals).findFirst();
    }

    public void deleteAutoRentalByIndex(int index) {
        this.autoRentalList.remove(index);
    }

    public boolean deleteAutoRentalById(UUID pointOfRental) {
        return this.autoRentalList.removeIf(
                autoRental -> autoRental.getPointOfRental().equals(pointOfRental)
        );
    }

    public int getAutoRentalSize() {
        return this.autoRentalList.size();
    }

    public UUID getId() {
        return UUID.randomUUID();
    }

    public void setId(UUID id) {
        this.id = 0;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
