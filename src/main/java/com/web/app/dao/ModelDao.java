package com.web.app.dao;

import com.mongodb.MongoClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModelDao<T> {

    List<T> getAll();

    Optional<T> getModelById(UUID id);

    void insertModel(T model);

    void deleteModelById(UUID id);

    long getModelsCount();

    void setMongoClient(MongoClient mongoClient);

}
