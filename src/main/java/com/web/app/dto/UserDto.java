package com.web.app.dto;

import com.web.app.model.User;

import java.util.List;

public class UserDto {
    public String login;
    public String name;
    public List<AutoRentalDto> autoRentals;

    public static UserDto from(User user, List<AutoRentalDto> autoRentals) {
        var res = new UserDto();
        res.login = user.getLogin();
        res.name = user.getName();
        res.autoRentals = autoRentals;
        return res;
    }
}
