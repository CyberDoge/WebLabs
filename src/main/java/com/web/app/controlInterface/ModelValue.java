package com.web.app.controlInterface;

import com.web.app.model.Auto;
import com.web.app.model.Model;
import com.web.app.model.User;

public enum ModelValue {
    USER("user", User.class),
    AUTO("auto", Auto.class),
    AUTO_RENTAL("autoRental", Auto.class);
    private String value;

    private Class<? extends Model> clazz;

    ModelValue(String value, Class<? extends Model> clazz) {
        this.value = value;
        this.clazz = clazz;
    }

    public Class<? extends Model> getClazz() {
        return clazz;
    }

    public String getValue() {
        return value;
    }
}
