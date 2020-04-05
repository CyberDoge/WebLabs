package com.web.app.main;

import com.web.app.controller.LoginController;
import com.web.app.controller.MainController;
import com.web.app.dbServices.DBService;

public class Main {
    public static void main(final String[] args) {
        String controlType = null;
        if (args.length > 0) {
            controlType = args[0];
        }
        DBService dbService = new DBService();
        dbService.runChangeLog();
        MainController mainController = new MainController(dbService, controlType);
        LoginController loginController = new LoginController(dbService);
        mainController.start();
        System.out.println(loginController.authenticateUser("login1", "password1".toCharArray()));
    }
}
