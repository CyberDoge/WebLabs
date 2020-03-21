package com.web.app.main;

import com.web.app.db_services.DBService;
import com.web.app.model.User;

import java.util.Objects;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        DBService dbService = new DBService();
        dbService.runChangeLog();
        
    }
}
