package com.web.app.model;

import java.io.Serializable;
import java.util.Objects;

public class Auto implements Serializable {
    private String model;
    private String producer;
    private int count;
    private float price;

    public Auto(String model, String producer, int count, float price) {
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
        if (o == null || getClass() != o.getClass()) return false;
        Auto auto = (Auto) o;
        return count == auto.count &&
                Float.compare(auto.price, price) == 0 &&
                Objects.equals(model, auto.model) &&
                Objects.equals(producer, auto.producer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, producer, count, price);
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
