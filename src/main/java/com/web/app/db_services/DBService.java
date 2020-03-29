package com.web.app.db_services;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.MongockBuilder;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.util.HashMap;

public class DBService {
    public static final String MONGODB_URI = "mongodb://127.0.0.1:27017";
    public static final String DATABASE_NAME = "web";
    private HashMap<String, MongoClient> mongoClientsPool;

    public DBService() {
        mongoClientsPool = new HashMap<>();
    }

    public synchronized MongoClient getOrCreateMongoClient(String poolName) {
        if (!this.mongoClientsPool.containsKey(poolName)) {
            MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGODB_URI));
            mongoClientsPool.put(poolName, mongoClient);
            return mongoClient;
        }
        return mongoClientsPool.get(poolName);
    }

    public void runChangeLog() {
        MongoClient connection = this.getOrCreateMongoClient("changelog");
        Mongock runner = new MongockBuilder(
                connection, DATABASE_NAME, UsersChangeLog.class.getPackage().getName()
        )
                .setLockQuickConfig()
                .build();
        runner.execute();
        connection.close();
    }
}

