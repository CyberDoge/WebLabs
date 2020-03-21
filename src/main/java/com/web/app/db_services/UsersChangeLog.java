package com.web.app.db_services;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.web.app.model.User;
import org.bson.Document;

import java.util.UUID;

@ChangeLog
public class UsersChangeLog {

    private static final String USERS_COLLECTION_NAME = "users";

    @ChangeSet(id = "withMongoDatabase", order = "001", author = "me")
    public void changeSet1(MongoDatabase mongoDatabase) {
        mongoDatabase.getCollection(USERS_COLLECTION_NAME).insertOne(createMongoDocument(
                new User(UUID.randomUUID(), "John Doe", "foo", "password".toCharArray()))
        );
    }

    private Document createMongoDocument(User user) {
        return new Document()
                .append("name", user.getName())
                .append("surname", user.getId());
    }

}
