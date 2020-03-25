package com.web.app.dao;

import com.mongodb.client.MongoCollection;
import com.web.app.model.User;
import org.bson.Document;

public class UserDao extends ModelDaoImpl<User> {

    @Override
    protected MongoCollection<Document> getCollection() {
        return getMongoClient().getDatabase("web").getCollection("users");
    }
}
