package com.web.app.dao;

import com.mongodb.MongoClient;

import java.util.UUID;

public interface ModelDao<T> {
    T getModelById(UUID id);

    void addModel(T model);

    T deleteModelById(UUID id);

    int getModelsCount(Class<T> modelClass);

    void setMongoClient(MongoClient mongoClient);
}
