package com.it.one.practice.config;

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
import java.util.List;

public class DocConfig {

    private List<PageConfig> pages;

    public DocConfig(){}

    public DocConfig(List<PageConfig> pages){
        this.pages = pages;
    }

    public static void load(File file) throws IOException, ElementNotFoundException {
        ObjectMapper mapper = new ObjectMapper();

        DocConfig docConfig = mapper.readValue(file, DocConfig.class);

        //PageConfig pageConfig = mapper.readValue(file, PageConfig.class);

        System.out.println(docConfig.pages.get(0).getElements().getMarkers().get(0).getName());
    }

    private List<PageConfig> getPages(){
        return this.pages;
    }

    /*public PageElements getPageElements(int pageIndex){

    }*/

}
