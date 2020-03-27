package com.web.app.db_services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.web.app.model.User;
import org.bson.Document;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@ChangeLog
public class UsersChangeLog {

    private static final String USERS_COLLECTION_NAME = "users";

    @ChangeSet(id = "setUsers", order = "001", author = "me")
    public void changeSet1(MongoDatabase mongoDatabase) {
        final ObjectMapper mapper = new ObjectMapper();
        User user = new User(UUID.randomUUID(), "John Doe", "foo", "password", List.of(UUID.randomUUID()));
        mongoDatabase.getCollection(USERS_COLLECTION_NAME).insertOne(
                new Document(mapper.convertValue(user, Map.class))
        );
    }

    @ChangeSet(id = "setUsersMoreThan5Users", order = "002", author = "me")
    public void changeSet2(MongoDatabase mongoDatabase) {
        final ObjectMapper mapper = new ObjectMapper();
        for (int i = 0; i < 6; i++) {
            User user = new User(
                    UUID.randomUUID(),
                    "John Doe" + i,
                    "foo" + i,
                    "password" + i,
                    List.of(
                            UUID.randomUUID(),
                            UUID.randomUUID(),
                            UUID.randomUUID()
                    )
            );
            mongoDatabase.getCollection(USERS_COLLECTION_NAME).insertOne(
                    new Document(mapper.convertValue(user, Map.class))
            );
        }
    }

    private Document createMongoDocument(User user) {
        return new Document()
                .append("_id", user.getId())
                .append("name", user.getName())
                .append("login", user.getLogin())
                .append("passwordHash", user.getPasswordHash())
                .append("autoRentalIds", user.getAutoRentalIds());
    }

}
