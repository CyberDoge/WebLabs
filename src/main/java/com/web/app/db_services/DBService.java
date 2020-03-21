package com.web.app.db_services;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.MongockBuilder;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class DBService {
    public static final String MONGODB_URI = "mongodb://127.0.0.1:27017";
    public static final String DATABASE_NAME = "web";
    private MongoClient mongoClient;

    public MongoClient getOrCreateMongoClient() {
        if (this.mongoClient == null) {
            this.mongoClient = new MongoClient(new MongoClientURI(MONGODB_URI));
        }
        return this.mongoClient;
    }

    public void runChangeLog() {
        Mongock runner = new MongockBuilder(
                this.getOrCreateMongoClient(), DATABASE_NAME, UsersChangeLog.class.getPackage().getName()
        )
                .setLockQuickConfig()
                .build();
        runner.execute();
    }
}

