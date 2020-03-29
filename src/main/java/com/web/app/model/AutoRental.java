package com.web.app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class AutoRental implements Model {
    @JsonProperty("_id")
    private UUID id;
    private List<UUID> autos;


    public AutoRental() {
        this.id = UUID.randomUUID();
        this.autos = new ArrayList<>();
    }

    public AutoRental(UUID id, List<UUID> autos) {
        this.id = id;
        this.autos = autos;
    }

    @JsonCreator
    public static AutoRental createAutoRental(@JsonProperty("pointOfRental") String pointOfRental,
                                              @JsonProperty("autos") List<UUID> autos) {
        return new AutoRental(UUID.fromString(pointOfRental), autos);
    }


    public List<UUID> getAutos() {
        return autos;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public void setPrimaryField(String value) {
    }

    @JsonIgnore
    public UUID getAutoByIndex(int index) {
        return this.autos.get(index);
    }

    @JsonIgnore
    public int getAutosLength() {
        return this.autos.size();
    }

    @JsonIgnore
    public void removeAutoByIndex(int index) {
        this.autos.remove(index);
    }


    public List<Auto> sort(List<Auto> autos) {
        ArrayList<Auto> tmp = new ArrayList<>(new HashSet<>(autos));
        tmp.sort(Comparator.comparing(Auto::getPrice));
        return tmp;
    }
}
