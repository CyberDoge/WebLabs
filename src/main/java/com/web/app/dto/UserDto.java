package com.web.app.dto;

import com.web.app.model.User;

import java.util.List;
import java.util.UUID;

/**
 * Data transfer object. Объект для передачи только нужных полей и значений http ответа в виде JSON.
 */

public class UserDto {
    public UUID id;
    public String login;
    public String name;
    public List<AutoRentalDto> autoRentals;

    public static UserDto from(User user, List<AutoRentalDto> autoRentals) {
        var res = new UserDto();
        res.id = user.getId();
        res.login = user.getLogin();
        res.name = user.getName();
        res.autoRentals = autoRentals;
        return res;
    }
}
