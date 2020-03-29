package com.web.app.main;

import com.web.app.controller.MainController;

public class Main {
    public static void main(final String[] args) {
        String argument = null;
        if (args.length > 0) {
            argument = args[0];
        }
        try (MainController mainController = new MainController(argument)) {
            mainController.start();
        }
    }
}
