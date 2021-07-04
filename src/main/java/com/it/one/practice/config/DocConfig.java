package com.it.one.practice.config;

import com.it.one.practice.entity.PageElements;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import java.nio.file.Paths;
import java.nio.file.Files;


import java.util.List;

public class DocConfig {

    private List<PageConfig> pages;

    public DocConfig(List<PageConfig> pages){
        this.pages = pages;
    }

    public static DocConfig load(File file) throws IOException {
        List<String> data = Files.readAllLines(Paths.get(file.getAbsolutePath()));
        StringBuilder json = new StringBuilder();
        data.forEach(json::append);
        Gson gson = new Gson();
        return gson.fromJson(json.toString(), DocConfig.class);
    }

    public PageElements getPageElements(int pageIndex){
        return pages.get(pageIndex).getElements();
    }

    @Override
    public String toString() {
        return "DocConfig{" +
                "pages=" + pages +
                '}';
    }

}
