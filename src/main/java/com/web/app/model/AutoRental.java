package com.web.app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AutoRental implements Model {
    @JsonProperty("_id")
    private UUID id;
    private List<UUID> autos;


    public AutoRental() {
    }

    public AutoRental(UUID id, List<UUID> autos) {
        this.id = id;
        this.autos = autos;
    }

    @JsonCreator
    public static AutoRental createAutoRental(@JsonProperty("_id") String pointOfRental,
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

    @Override
    public String toString() {
        return "AutoRental{" +
                "id=" + id +
                ", autos=[" + autos.stream().map(UUID::toString).collect(Collectors.joining(", ")) +
                '}';
    }
}
