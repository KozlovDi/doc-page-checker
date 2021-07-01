package com.it.one.practice.config;

<<<<<<< HEAD
import com.fasterxml.jackson.databind.ObjectMapper;
=======
>>>>>>> f84593e106e9a700bafdb466ef41f7e22e72d259
import com.it.one.practice.entity.PageElements;

import java.util.List;

public class PageConfig {

    private final PageElements elements;


    public PageConfig(PageElements elements) {
        this.elements = elements;
    }

    public PageConfig(){}

    public PageConfig(PageElements elements){
        this.elements = elements;
    }

    public PageElements getElements() {
        return elements;
    }

    @Override
    public String toString() {
        return "PageConfig{" +
                "elements=" + elements +
                '}';
    }
}
