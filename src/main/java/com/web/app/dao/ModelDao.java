package com.web.app.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.MongoClient;
import com.web.app.model.Model;
import org.bson.Document;

import java.io.Closeable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModelDao<T extends Model> extends Closeable {

    List<T> getAll();

    Optional<T> getModelById(UUID id);

    void insertModel(Model model);

    void deleteModelById(UUID id);

    long getModelsCount();

    void setMongoClient(MongoClient mongoClient);

    Document update(UUID id, String json) throws JsonProcessingException;

}
