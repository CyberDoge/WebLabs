package com.web.app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class AutoRental extends UnicastRemoteObject {
    private final UUID pointOfRental;
    private final List<Auto> autos;


    public AutoRental() throws RemoteException {
        super();
        this.pointOfRental = UUID.randomUUID();
        this.autos = new ArrayList<>();
    }

    private AutoRental(UUID pointOfRental, List<Auto> autos) throws RemoteException {
        super();
        this.pointOfRental = pointOfRental;
        this.autos = autos;
    }

    @JsonCreator
    public static AutoRental createAutoRental(@JsonProperty("pointOfRental") String pointOfRental,
                                              @JsonProperty("autos") List<Auto> autos) throws RemoteException {
        return new AutoRental(UUID.fromString(pointOfRental), autos);
    }

    public static void writeToFile(AutoRental autoRental) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("object.json"), autoRental.getAutos());
    }

    public static List<Auto> readFromFile(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(path), List.class);
    }

    public List<Auto> getAutos() {
        return autos;
    }

    public UUID getPointOfRental() {
        return pointOfRental;
    }

    @JsonIgnore
    public void setAutoModel(int index, String model) {
        getAutoByIndex(index).setModel(model);
    }

    @JsonIgnore
    public void setAutoProducer(int index, String producer) {
        getAutoByIndex(index).setProducer(producer);
    }

    @JsonIgnore
    public void setAutoCount(int index, int count) {
        getAutoByIndex(index).setCount(count);
    }

    @JsonIgnore
    public void setAutoPrice(int index, float price) {
        getAutoByIndex(index).setPrice(price);
    }

    @JsonIgnore
    public Auto getAutoByIndex(int index) {
        return this.autos.get(index);
    }

    @JsonIgnore
    public int getAutosLength() {
        return this.autos.size();
    }

    @JsonIgnore
    public void addAuto(Auto auto) {
        this.autos.add(auto);
    }

    @JsonIgnore
    public void addAuto(int index, Auto auto) {
        this.autos.add(index, auto);
    }

    @JsonIgnore
    public void removeAutoByIndex(int index) {
        this.autos.remove(index);
    }

    public void sort() {
        this.autos.sort(Comparator.comparing(Auto::getPrice));
    }

    public void randomSort() {
        for (int i = 0; i < autos.size(); i++) {
            Auto tmp1 = autos.get(i);
            int index = ((int) (Math.random() * autos.size()));
            Auto tmp2 = autos.get(index);

            autos.set(i, tmp2);
            autos.set(index, tmp1);
        }
    }

    public List<Auto> sort(List<Auto> autos) {
        ArrayList<Auto> tmp = new ArrayList<>(new HashSet<>(autos));
        tmp.sort(Comparator.comparing(Auto::getPrice));
        return tmp;
    }
}
