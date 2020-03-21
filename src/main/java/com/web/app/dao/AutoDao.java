package com.web.app.dao;

import com.mongodb.MongoClient;
import com.web.app.model.Auto;

import java.util.UUID;

public class AutoDao implements ModelDao<Auto> {

    private MongoClient mongoClient;

    @Override
    public Auto getModelById(UUID id) {
        return null;
//        return mongoClient.getDatabase("web").getCollection("auto");
    }

    @Override
    public void addModel(Auto model) {

    }

    @Override
    public Auto deleteModelById(UUID id) {
        return null;
    }

    @Override
    public int getModelsCount(Class<Auto> modelClass) {
        return 0;
    }

    @Override
    public void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }
}
