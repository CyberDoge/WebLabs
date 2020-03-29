package com.web.app.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.web.app.model.User;
import org.bson.Document;

import java.util.Optional;

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

    public Optional<User> getUserByLogin(String login) {
        ObjectMapper mapper = new ObjectMapper();
        MongoCursor<Document> cursor = getCollection().find(new Document("login", login)).cursor();
        if (cursor.hasNext()) {
            return Optional.of(mapper.convertValue(cursor.next(), User.class));
        }
        return Optional.empty();
    }
}
