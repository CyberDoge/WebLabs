package com.web.app.dto;

import com.web.app.model.Auto;

import java.util.UUID;

public class AutoDto {
    public UUID id;
    public String model;
    public String producer;
    public int count;
    public float price;

    public static AutoDto from(Auto auto) {
        var res = new AutoDto();
        res.id = auto.getId();
        res.model = auto.getModel();
        res.producer = auto.getProducer();
        res.count = auto.getCount();
        res.price = auto.getPrice();
        return res;
    }
}
