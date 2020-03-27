package com.web.app.main;

import com.web.app.controller.MainController;

public class Main {
    public static void main(String[] args) {
        try (MainController mainController = new MainController(args[0])) {
            mainController.start();
        }
    }
}
