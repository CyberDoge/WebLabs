package com.web.app.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.MongoClient;
import com.mongodb.client.result.DeleteResult;
import com.web.app.model.Model;
import org.bson.Document;

import java.io.Closeable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModelDao<T extends Model> extends Closeable {

    List<T> getAll();

    Optional<T> getModelById(UUID id);

    List<T> getAllByIds(List<UUID> uuidList);

    void insertModel(Model model);

    DeleteResult deleteModelById(UUID id);

    long getModelsCount();

    void setMongoClient(MongoClient mongoClient);

    Document update(UUID id, String json) throws JsonProcessingException;

}
