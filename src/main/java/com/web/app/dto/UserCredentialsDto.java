package com.web.app.dto;

import com.web.app.model.Auto;
import com.web.app.model.AutoRental;
import com.web.app.model.User;

import java.util.Collections;
import java.util.List;

public class UserCredentialsDto {
    public String login;
    public String password;
    public String name;
    public List<AutoRental> autoRentals;
    public List<Auto> autos;

    public static UserCredentialsDto from(User user, List<AutoRental> autoRentals, List<Auto> autos) {
        var res = new UserCredentialsDto();
        res.login = user.getLogin();
        res.name = user.getName();
        res.autoRentals = autoRentals;
        res.autos = autos;
        return res;
    }

    public static UserCredentialsDto from(User user) {
        var res = new UserCredentialsDto();
        res.login = user.getLogin();
        res.name = user.getName();
        res.autoRentals = Collections.emptyList();
        res.autos = Collections.emptyList();
        return res;
    }
}
