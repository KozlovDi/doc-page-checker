package com.it.one.practice.config;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DocConfig {

    private List<PageConfig> pages;

    public static DocConfig load(File file) throws IOException {
        List<String> data = Files.readAllLines(Path.of(file.getAbsolutePath()));
        StringBuilder json = new StringBuilder();
        data.forEach(json::append);

        Gson gson = new Gson();
        return gson.fromJson(json.toString(), DocConfig.class);

    }

    public List<PageConfig> getPages() {
        return pages;
    }

    @Override
    public String toString() {
        return "DocConfig{" +
                "pages=" + pages +
                '}';
    }
}
