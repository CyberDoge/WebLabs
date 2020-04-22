package com.web.app.dbServices;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.web.app.model.Auto;
import com.web.app.model.AutoRental;
import com.web.app.model.User;
import com.web.app.utils.ModelsUtils;
import org.bson.Document;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.web.app.utils.ModelsUtils.*;

/**
 * Контроль миграции бд. Сохраняет изменения структры базы данных, при условии что структура будет меняться
 * через changelog-и
 */
@ChangeLog
public class UsersChangeLog {

    @ChangeSet(id = "setPrimaryData", order = "001", author = "me")
    public void changeSet1(MongoDatabase mongoDatabase) throws IOException {
        List<Auto> autos = create200Autos();
        List<AutoRental> autoRentals = create20AutoRentals(autos);
        List<User> users = create4Users(autoRentals);
        mongoDatabase.getCollection("users").insertMany(
                users.stream().map(this::createMongoUserDocument).collect(Collectors.toList())
        );
        mongoDatabase.getCollection("autoRentals").insertMany(
                autoRentals.stream().map(this::createMongoAutoRentalDocument).collect(Collectors.toList())
        );
        mongoDatabase.getCollection("autos").insertMany(
                autos.stream().map(this::createMongoAutoDocument).collect(Collectors.toList())
        );

        ModelsUtils.writeObjectsToFilesByLines(autos, autoRentals, users);
    }

    private Document createMongoUserDocument(User user) {
        return new Document()
                .append("_id", user.getId())
                .append("name", user.getName())
                .append("login", user.getLogin())
                .append("passwordHash", user.getPasswordHash())
                .append("autoRentalIds", user.getAutoRentalIds());
    }

    private Document createMongoAutoRentalDocument(AutoRental autoRental) {
        return new Document()
                .append("_id", autoRental.getId())
                .append("autos", autoRental.getAutos());
    }

    private Document createMongoAutoDocument(Auto auto) {
        return new Document()
                .append("_id", auto.getId())
                .append("model", auto.getModel())
                .append("producer", auto.getProducer())
                .append("count", auto.getCount())
                .append("price", auto.getPrice());
    }

}
