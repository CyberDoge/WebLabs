package com.web.app.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.web.app.model.Auto;
import org.bson.Document;

import java.lang.reflect.Type;

public class AutoDao extends ModelDaoImpl<Auto> {
    public AutoDao(MongoClient mongoClient) {
        super(mongoClient);
    }

    @Override
    protected MongoCollection<Document> getCollection() {
        return getMongoClient().getDatabase("web").getCollection("autos");
    }

    @Override
    protected TypeReference<Auto> createTypeReference() {
        return new TypeReference<>(){};
    }
}
