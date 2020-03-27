package com.web.app.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JsonReader {
    public static List<String> readValuesFromFile(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of();
    }
}
