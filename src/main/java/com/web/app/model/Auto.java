package com.web.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Auto implements Serializable, Model {
    @JsonProperty("_id")
    private UUID id;
    private String model;
    private String producer;
    private int count;
    private float price;

    public Auto(UUID id, String model, String producer, int count, float price) {
        this.id = id;
        this.model = model;
        this.producer = producer;
        this.count = count;
        this.price = price;
    }

    public Auto() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Auto)) return false;
        Auto auto = (Auto) o;
        return count == auto.count &&
                Float.compare(auto.price, price) == 0 &&
                Objects.equals(id, auto.id) &&
                Objects.equals(model, auto.model) &&
                Objects.equals(producer, auto.producer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, producer, count, price);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "model='" + model + '\'' +
                ", producer='" + producer + '\'' +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}
