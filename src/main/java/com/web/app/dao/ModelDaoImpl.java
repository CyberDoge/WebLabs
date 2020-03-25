package com.web.app.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import java.lang.reflect.Type;
import java.util.*;

public abstract class ModelDaoImpl<T> implements ModelDao<T> {
    private MongoClient mongoClient;
    private TypeReference<T> typeReference = new TypeReference<>() {
        @Override
        public Type getType() {
            return super.getType();
        }

        @Override
        public int compareTo(TypeReference<T> o) {
            return super.compareTo(o);
        }
    };

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
        MongoCursor<Document> cursor = this.getCollection().find(new BasicDBObject("_id", id)).cursor();
        if (cursor.hasNext()) {
            return Optional.of(mapper.convertValue(cursor.next(), typeReference));
        }
        return Optional.empty();
    }

    @Override
    public void insertModel(T model) {
        final ObjectMapper mapper = new ObjectMapper();
        getCollection().insertOne(new Document(
                mapper.convertValue(model, Map.class)
        ));
    }

    @Override
    public void deleteModelById(UUID id) {
        DeleteResult id1 = getCollection().deleteOne(new BasicDBObject("_id", id));
        System.out.println(id1);
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
}
