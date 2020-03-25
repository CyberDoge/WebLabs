package com.web.app.dao;

import com.mongodb.client.MongoCollection;
import com.web.app.model.Auto;
import org.bson.Document;

public class AutoDao extends ModelDaoImpl<Auto> {
    @Override
    protected MongoCollection<Document> getCollection() {
        return getMongoClient().getDatabase("web").getCollection("autos");
    }
}
