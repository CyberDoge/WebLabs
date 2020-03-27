package com.web.app.dao;

import com.mongodb.MongoClient;
import com.web.app.model.Model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModelDao<T extends Model> {

    List<T> getAll();

    Optional<T> getModelById(UUID id);

    void insertModel(Model model);

    void deleteModelById(UUID id);

    long getModelsCount();

    void setMongoClient(MongoClient mongoClient);

    void update(UUID id, Model model);

}
