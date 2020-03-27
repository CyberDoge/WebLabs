package com.web.app.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.web.app.model.AutoRental;
import org.bson.Document;

public class AutoRentalDao extends ModelDaoImpl<AutoRental> {

    public AutoRentalDao(MongoClient mongoClient) {
        super(mongoClient);
    }

    @Override
    protected MongoCollection<Document> getCollection() {
        return getMongoClient().getDatabase("web").getCollection("auto-rentals");
    }

    @Override
    protected TypeReference<AutoRental> createTypeReference() {
        return new TypeReference<>() {
        };
    }
}
