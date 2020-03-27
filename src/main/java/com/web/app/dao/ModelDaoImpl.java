package com.web.app.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.web.app.model.Model;
import org.bson.Document;

import java.util.*;

public abstract class ModelDaoImpl<T extends Model> implements ModelDao<T> {
    protected TypeReference<T> typeReference;
    private MongoClient mongoClient;

    public ModelDaoImpl(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
        this.typeReference = this.createTypeReference();
    }

    @Override
    public void update(UUID id, Model model) {
        final ObjectMapper mapper = new ObjectMapper();
        this.getCollection().updateOne(new BasicDBObject("_id", id), new Document(
                mapper.convertValue(model, Map.class)
        ));
    }

    @Override
    public List<T> getAll() {
        final ObjectMapper mapper = new ObjectMapper();

        MongoCursor<Document> cursor = getCollection().find().cursor();
        List<T> result = new ArrayList<>();
        while (cursor.hasNext()) {
            result.add(mapper.convertValue(cursor.next(), typeReference));
        }
        return result;
    }

    @Override
    public Optional<T> getModelById(UUID id) {
        final ObjectMapper mapper = new ObjectMapper();
        MongoCursor<Document> cursor = this.getCollection().find(new BasicDBObject("_id", id.toString())).cursor();
        if (cursor.hasNext()) {
            return Optional.of(mapper.convertValue(cursor.next(), typeReference));
        }
        return Optional.empty();
    }

    @Override
    public void insertModel(Model model) {
        final ObjectMapper mapper = new ObjectMapper();
        getCollection().insertOne(new Document(
                mapper.convertValue(model, Map.class)
        ));
    }

    @Override
    public void deleteModelById(UUID id) {
        DeleteResult result = getCollection().deleteOne(new BasicDBObject("_id", id));
        System.out.println(result);
    }

    @Override
    public long getModelsCount() {
        return getCollection().countDocuments();
    }

    protected MongoClient getMongoClient() {
        return this.mongoClient;
    }

    @Override
    public void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    protected abstract MongoCollection<Document> getCollection();

    protected abstract TypeReference<T> createTypeReference();
}
