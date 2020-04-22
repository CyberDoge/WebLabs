package com.web.app.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.web.app.model.Model;
import org.bson.Document;

import java.util.*;

/**
 * Реализация класса, который управляет запросами к базе данных.
 *
 * @param <T>
 */
public abstract class ModelDaoImpl<T extends Model> implements ModelDao<T> {
    final ObjectMapper mapper;
    protected TypeReference<T> typeReference;
    private MongoClient mongoClient;

    public ModelDaoImpl(MongoClient mongoClient) {
        mapper = new ObjectMapper();
        this.mongoClient = mongoClient;
        this.typeReference = this.createTypeReference();
    }

    @Override
    public Document update(UUID id, String json) throws JsonProcessingException {
        Document update = new Document();
        T data = mapper.readValue(json, typeReference);
        Document document = new Document(mapper.convertValue(data, new TypeReference<>() {
        }));
        document.values().removeAll(Collections.singleton(null));
        update.append("$set", document);
        return this.getCollection().findOneAndUpdate(new BasicDBObject("_id", id), update);
    }

    @Override
    public List<T> getAll() {
        MongoCursor<Document> cursor = getCollection().find().cursor();
        List<T> result = new ArrayList<>();
        while (cursor.hasNext()) {
            result.add(mapper.convertValue(cursor.next(), typeReference));
        }
        return result;
    }

    @Override
    public Optional<T> getModelById(UUID id) {
        MongoCursor<Document> cursor = this.getCollection().find(new BasicDBObject("_id", id)).cursor();
        if (cursor.hasNext()) {
            return Optional.of(mapper.convertValue(cursor.next(), typeReference));
        }
        return Optional.empty();
    }

    @Override
    public List<T> getAllByIds(List<UUID> uuidList) {
        List<T> result = new ArrayList<>();
        MongoCursor<Document> cursor = this.getCollection().find(new BasicDBObject("_id", new BasicDBObject("$in", uuidList))).cursor();
        while (cursor.hasNext()) {
            result.add(mapper.convertValue(cursor.next(), typeReference));
        }
        return result;
    }

    @Override
    public void insertModel(Model model) {
        TypeFactory typeFactory = mapper.getTypeFactory();
        MapType mapType = typeFactory.constructMapType(HashMap.class, String.class, Object.class);
        getCollection().insertOne(new Document(mapper.convertValue(model, mapType)));
    }

    @Override
    public DeleteResult deleteModelById(UUID id) {
        return getCollection().deleteOne(new BasicDBObject("_id", id));
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

    @Override
    public void close() {
        this.getMongoClient().close();
    }
}
