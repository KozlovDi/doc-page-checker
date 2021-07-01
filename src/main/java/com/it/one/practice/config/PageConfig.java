package com.it.one.practice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.one.practice.entity.PageElements;

import java.util.List;

public class PageConfig {

    private PageElements elements;

    public PageConfig(){}

    public PageConfig(PageElements elements){
        this.elements = elements;
    }

    public PageElements getElements() {
        return this.elements;
    }
}
