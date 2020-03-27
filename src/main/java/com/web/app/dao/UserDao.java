package com.web.app.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.web.app.model.User;
import org.bson.Document;

public class UserDao extends ModelDaoImpl<User> {

    public UserDao(MongoClient mongoClient) {
        super(mongoClient);
    }

    @Override
    protected MongoCollection<Document> getCollection() {
        return getMongoClient().getDatabase("web").getCollection("users");
    }

    @Override
    protected TypeReference<User> createTypeReference() {
        return new TypeReference<>() {
        };
    }
}
