package com.it.one.practice.config;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.one.practice.entity.PageElements;
import com.it.one.practice.entity.PageMarker;
import com.it.one.practice.exceptions.ElementNotFoundException;
import com.sun.org.apache.bcel.internal.generic.DCMPG;
import com.sun.org.apache.bcel.internal.generic.LUSHR;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.Arrays;
=======
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
>>>>>>> f84593e106e9a700bafdb466ef41f7e22e72d259
import java.util.List;

public class DocConfig {

    private List<PageConfig> pages;

<<<<<<< HEAD
    public DocConfig(){}

    public DocConfig(List<PageConfig> pages){
        this.pages = pages;
    }

    public static void load(File file) throws IOException, ElementNotFoundException {
        ObjectMapper mapper = new ObjectMapper();

        DocConfig docConfig = mapper.readValue(file, DocConfig.class);
=======
    public static DocConfig load(File file) throws IOException {
        List<String> data = Files.readAllLines(Path.of(file.getAbsolutePath()));
        StringBuilder json = new StringBuilder();
        data.forEach(json::append);

        Gson gson = new Gson();
        return gson.fromJson(json.toString(), DocConfig.class);
>>>>>>> f84593e106e9a700bafdb466ef41f7e22e72d259

        //PageConfig pageConfig = mapper.readValue(file, PageConfig.class);

        System.out.println(docConfig.pages.get(0).getElements().getMarkers().get(0).getName());
    }

    private List<PageConfig> getPages(){
        return this.pages;
    }

<<<<<<< HEAD
    /*public PageElements getPageElements(int pageIndex){

    }*/

=======
    public List<PageConfig> getPages() {
        return pages;
    }

    @Override
    public String toString() {
        return "DocConfig{" +
                "pages=" + pages +
                '}';
    }
>>>>>>> f84593e106e9a700bafdb466ef41f7e22e72d259
}
